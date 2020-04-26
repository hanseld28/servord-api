package com.hanseld.servord.api.exceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Issue {
	
	private Integer status;
	private OffsetDateTime datetime;
	private String title;
	private List<Field> fields;
	
	
	public Issue() {
		
	}
	
	public Issue(Integer status, String title, OffsetDateTime datetime) {
		this.status = status;
		this.title = title;
		this.datetime = datetime;
	}
	
	public Issue(Integer status, String title, OffsetDateTime datetime, List<Field> fields) {
		this.status = status;
		this.title = title;
		this.datetime = datetime;
		this.fields = fields;
	}
	
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public OffsetDateTime getDatetime() {
		return datetime;
	}
	public void setDatetime(OffsetDateTime datetime) {
		this.datetime = datetime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
