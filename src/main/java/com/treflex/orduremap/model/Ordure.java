package com.treflex.orduremap.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Ordure {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	@Persistent
	private String position;
	@Persistent
	private String tags;
	@Persistent
	private String reporter;
	@Persistent
	private Date date;

	public Ordure(String position, String tags, String reporter, Date date) {
		super();
		this.position = position;
		this.tags = tags;
		this.reporter = reporter;
		this.date = date;
	}

	public Key getKey() {
		return key;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	@Override
	public String toString() {
		return "Ordure [key=" + key + ", position=" + position + ", tags=" + tags + ", reporter=" + reporter + ", date=" + date + "]";
	}
}
