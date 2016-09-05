package com.devmpv.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

@Entity
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long thread;

	private String title;

	@Lob
	@Column(nullable = false)
	private String text;

	@Column(nullable = false)
	private Long timestamp;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "message_attachment")
	Set<Attachment> attachments = new HashSet<>();

	protected Message() {
	}

	public Message(String title, String text) {
		this.title = title;
		this.text = text;
	}

	public Set<Attachment> getAttachments() {
		return attachments;
	}

	public Long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public Long getThread() {
		return thread;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public String getTitle() {
		return title;
	}

	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setThread(Long thread) {
		this.thread = thread;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
