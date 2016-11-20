package com.devmpv.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Thread {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "thread")
	private Set<Message> messages;

	@Enumerated(EnumType.ORDINAL)
	private BoardName board;

	public BoardName getBoard() {
		return board;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public void setBoard(BoardName board) {
		this.board = board;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}
}
