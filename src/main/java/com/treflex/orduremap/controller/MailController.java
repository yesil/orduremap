package com.treflex.orduremap.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.treflex.orduremap.indexer.GpsIndexer;

@Controller
public class MailController {
	private static final Logger LOGGER = LoggerFactory.getLogger("Appspot indexer");

	@Autowired
	private GpsIndexer gpsIndexer;

	@RequestMapping(value = "/_ah/mail/*")
	public void mail(final InputStream mailStream, final OutputStream out) throws IOException {
		LOGGER.info("Reçeption d'un nouveau mail");
		final Properties props = new Properties();
		final Session session = Session.getDefaultInstance(props, null);
		try {
			final MimeMessage message = new MimeMessage(session, mailStream);
			gpsIndexer.index(message);
		} catch (MessagingException e) {
			LOGGER.error("Erreur lors de la traitement du message", e);
		}
	}
}
