package com.treflex.orduremap;

public class GPSData {
	@Override
	public String toString() {
		return latitude + "," + longitude;
	}

	private String description;
	private double latitude;
	private double longitude;

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

	public GPSData(final String description, final double longitude, final double latitude) {
		super();
		this.description = description;
		this.longitude = longitude;
		this.latitude = latitude;
	}
}
