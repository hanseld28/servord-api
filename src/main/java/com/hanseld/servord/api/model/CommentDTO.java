package com.hanseld.servord.api.model;

import java.time.OffsetDateTime;

public class CommentDTO {
	
	private Long id;
	private String description;
	private OffsetDateTime sendDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public OffsetDateTime getSendDate() {
		return sendDate;
	}
	public void setSendDate(OffsetDateTime sendDate) {
		this.sendDate = sendDate;
	}
}
