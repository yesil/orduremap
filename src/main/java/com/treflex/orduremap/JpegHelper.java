package com.treflex.orduremap;

import java.io.IOException;
import java.io.InputStream;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.springframework.stereotype.Service;

import com.treflex.orduremap.exception.OrdureException;

@Service
public class JpegHelper {
	public GPSData getGpsData(final InputStream imageStream, final String filename) throws ImageReadException, IOException {
		GPSData gpsData = null;
		final IImageMetadata metadata = Sanselan.getMetadata(imageStream, filename);
		if (!(metadata instanceof JpegImageMetadata)) {
			throw new OrdureException("La metadonnée GPS n'est pas trouvé dans l'image");
		}
		final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
		final TiffImageMetadata exifMetadata = jpegMetadata.getExif();
		if (null != exifMetadata) {
			final TiffImageMetadata.GPSInfo gpsInfo = exifMetadata.getGPS();
			if (null != gpsInfo) {
				final String gpsDescription = gpsInfo.toString();
				final double longitude = gpsInfo.getLongitudeAsDegreesEast();
				final double latitude = gpsInfo.getLatitudeAsDegreesNorth();
				gpsData = new GPSData(gpsDescription, longitude, latitude);
			}
		}
		return gpsData;
	}
}
