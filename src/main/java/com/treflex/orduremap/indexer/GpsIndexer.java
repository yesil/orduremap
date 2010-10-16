package com.treflex.orduremap.indexer;

import java.io.IOException;
import java.io.InputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

import org.apache.sanselan.ImageReadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gdata.util.ServiceException;
import com.treflex.orduremap.FusionTableHelper;
import com.treflex.orduremap.GPSData;
import com.treflex.orduremap.JpegHelper;
import com.treflex.orduremap.dao.OrdureDao;
import com.treflex.orduremap.model.Ordure;

@Service
public class GpsIndexer {
	@Autowired
	private JpegHelper jpegHelper;
	@Autowired
	private OrdureDao ordureDao;
	@Autowired
	private FusionTableHelper fhelper;
	private final Format dateFormatter = new SimpleDateFormat("yyyy.MM.dd 'à' HH:mm:ss Z", Locale.FRENCH);
	private static final Logger LOGGER = LoggerFactory.getLogger("Email indexer");

	public void index(final Properties props) {
		LOGGER.info("Vérification de la boite mail");
		final Properties mailProps = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		final Session session = Session.getDefaultInstance(mailProps, null);
		Store store;
		Folder inbox;
		Folder trash;
		Message[] messages;
		try {
			store = session.getStore("imaps");
			store.connect(props.getProperty("address"), props.getProperty("login"), props.getProperty("password"));
			inbox = store.getFolder("Inbox");
			trash = store.getFolder("[Gmail]/Corbeille");
			inbox.open(Folder.READ_WRITE);
			messages = inbox.getMessages();
			for (Message message : messages) {
				index(message);
			}
			inbox.copyMessages(messages, trash);
		} catch (NoSuchProviderException e) {
			LOGGER.error("Erreur mail", e);
		} catch (MessagingException e) {
			LOGGER.error("Erreur mail", e);
		}
	}

	public void index(final Message message) {
		try {
			if (!message.isSet(Flag.SEEN)) {
				final String subject = message.getSubject();
				final Object content = message.getContent();
				final Address[] froms = message.getFrom();
				final String from = froms[0].toString();
				Date date = message.getReceivedDate();
				if (date == null) {
					date = new Date();
				}
				LOGGER.info("Message reçu par {} le {} avec les tags {}", new String[] { from, dateFormatter.format(date), subject });
				if (content instanceof MimeMultipart) {
					final MimeMultipart messagePart = (MimeMultipart) content;
					final int count = messagePart.getCount();
					for (int i = 0; i < count; i++) {
						final BodyPart bodyPart = messagePart.getBodyPart(i);
						final boolean isJPG = bodyPart.isMimeType("IMAGE/JPEG");
						if (isJPG) {
							index(bodyPart.getInputStream(), bodyPart.getFileName(), subject, from, date);
						}

					}
				}
			}
		} catch (MessagingException e) {
			LOGGER.error("Erreur mail", e);
		} catch (IOException e) {
			LOGGER.error("Erreur lors de la lecture du content du message", e);
		}
	}

	public void index(final InputStream imageStream, final String fileName, final String subject, final String from, final Date date) {
		try {
			final GPSData gpsData = jpegHelper.getGpsData(imageStream, fileName);
			LOGGER.info("GPS data: lattitude:{} - longitude:{}", gpsData.getLatitude(), gpsData.getLongitude());
			final Ordure ordure = new Ordure(gpsData.toString(), subject, from, date);
			ordureDao.save(ordure);
			fhelper.insert(ordure);
		} catch (ImageReadException e) {
			LOGGER.error("Erreur lors de la lecture de l'image", e);
		} catch (IOException e) {
			LOGGER.error("Erreur IO", e);
		} catch (ServiceException e) {
			LOGGER.error("Error de service google lors de l'envoie des données GPS de l'ordure sur fusion tables ", e);
		}
	}
}