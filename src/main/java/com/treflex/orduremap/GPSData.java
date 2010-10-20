package com.treflex.orduremap;

import java.util.Date;

public class GPSData {
	@Override
	public String toString() {
		return latitude + "," + longitude;
	}

	private String description;
	private double altitude;
	private double latitude;
	private double longitude;
	private Date datePhoto;
	private byte[] thumbnail;

	public byte[] getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(byte[] thumbnail) {
		this.thumbnail = thumbnail;
	}

	public final Date getDatePhoto() {
		return datePhoto;
	}

	public final void setDatePhoto(Date datePhoto) {
		this.datePhoto = datePhoto;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(final double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(final double latitude) {
		this.latitude = latitude;
	}

	public final double getAltitude() {
		return altitude;
	}

	public final void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public GPSData(final double longitude, final double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}
}
