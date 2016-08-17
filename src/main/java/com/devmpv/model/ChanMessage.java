package com.devmpv.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class ChanMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;

	private String text;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	Set<Attachment> attachments;

	public ChanMessage(String title, String text) {
		this.title = title;
		this.text = text;
	}

	protected ChanMessage() {
	}

	public String getText() {
		return text;
	}

	public String getTitle() {
		return title;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Object getId() {
		return id;
	}
}
