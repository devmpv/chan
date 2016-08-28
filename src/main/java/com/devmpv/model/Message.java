package com.devmpv.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

@Entity
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;

	@Lob
	@Column(nullable = false)
	private String text;

	@Column(nullable = false)
	private Long timestamp;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	Set<Attachment> attachments;

	protected Message() {
	}

	public Message(String title, String text) {
		this.title = title;
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public String getTitle() {
		return title;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
