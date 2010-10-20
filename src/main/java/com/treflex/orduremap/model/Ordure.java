package com.treflex.orduremap.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Ordure {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	@Persistent
	private double altitude;
	@Persistent
	private String position;
	@Persistent
	private String tags;
	@Persistent
	private String reporter;
	@Persistent
	private Date dateReceive;
	@Persistent
	private Date datePhoto;
	@Persistent
	private Blob image;
	@Persistent
	private Blob thumbnail;

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public Date getDateReceive() {
		return dateReceive;
	}

	public void setDateReceive(Date dateReceive) {
		this.dateReceive = dateReceive;
	}

	public Date getDatePhoto() {
		return datePhoto;
	}

	public void setDatePhoto(Date datePhoto) {
		this.datePhoto = datePhoto;
	}

	public Ordure(String position) {
		super();
		this.position = position;
	}

	public Key getKey() {
		return key;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public final Blob getThumbnail() {
		return thumbnail;
	}

	public final void setThumbnail(Blob thumbnail) {
		this.thumbnail = thumbnail;
	}

	@Override
	public String toString() {
		return "Ordure [key=" + key + ", position=" + position + ", tags=" + tags + ", reporter=" + reporter + ", dateReceive="
				+ dateReceive + ", datePhoto=" + datePhoto + "]";
	}

}
