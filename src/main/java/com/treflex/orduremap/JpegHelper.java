package com.treflex.orduremap;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.springframework.stereotype.Service;

import com.treflex.orduremap.exception.OrdureException;

@Service
public class JpegHelper {
	private static final Logger LOGGER = Logger.getLogger("JPEG Helper");

	public GPSData getGpsData(final InputStream imageStream, final String filename) throws ImageReadException, IOException, OrdureException {
		final IImageMetadata metadata = Sanselan.getMetadata(imageStream, filename);
		if (!(metadata instanceof JpegImageMetadata)) {
			throw new OrdureException("La metadonnée JPEG n'est pas trouvé dans l'image");
		}
		final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
		final TiffImageMetadata exifMetadata = jpegMetadata.getExif();
		if (exifMetadata == null) {
			throw new OrdureException("La metadonnée EXIF n'est pas trouvé dans l'image");
		}

		final TiffImageMetadata.GPSInfo gpsInfo = exifMetadata.getGPS();
		if (gpsInfo == null) {
			throw new OrdureException("La metadonnée GPS n'est pas trouvé dans l'image");
		}
		GPSData gpsData = null;
		try {
			final double longitude = gpsInfo.getLongitudeAsDegreesEast();
			final double latitude = gpsInfo.getLatitudeAsDegreesNorth();
			gpsData = new GPSData(longitude, latitude);
			final String gpsDescription = gpsInfo.toString();
			gpsData.setDescription(gpsDescription);
			final double altitude = jpegMetadata.findEXIFValue(TiffField.GPS_TAG_GPS_ALTITUDE).getDoubleValue();
			gpsData.setAltitude(altitude);
			DateFormat df = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
			final Date datePhoto = df.parse(jpegMetadata.findEXIFValue(TiffField.TIFF_TAG_DATE_TIME).getStringValue());

			gpsData.setDatePhoto(datePhoto);
			final BufferedImage thumbnail = jpegMetadata.getEXIFThumbnail();
			if (thumbnail != null) {
				final ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(thumbnail, "jpg", baos);
				baos.flush();
				final byte[] imageInByte = baos.toByteArray();
				baos.close();
				gpsData.setThumbnail(imageInByte);
				LOGGER.info("Une image d'apperçu est trouvé pour l'ordure, taille:" + imageInByte.length);
			}
		} catch (ParseException e) {
			LOGGER.error("Problème lors de la lecture de la date de prise de photo de l'ordure", e);
		}
		if (gpsData == null) {
			throw new OrdureException("Probléme général lors de la lecture des métadonnées dans l'image");
		}
		return gpsData;
	}
}
