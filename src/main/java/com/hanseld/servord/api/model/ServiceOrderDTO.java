package com.hanseld.servord.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.hanseld.servord.domain.model.ServiceOrderStatus;

public class ServiceOrderDTO {
	
	private Long id;
	private ClientSummaryDTO client;
	private String description;
	private BigDecimal price;
	private ServiceOrderStatus status;
	private OffsetDateTime openingDate;
	private OffsetDateTime completionDate;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ClientSummaryDTO getClient() {
		return client;
	}
	public void setClient(ClientSummaryDTO client) {
		this.client = client;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public ServiceOrderStatus getStatus() {
		return status;
	}
	public void setStatus(ServiceOrderStatus status) {
		this.status = status;
	}
	public OffsetDateTime getOpeningDate() {
		return openingDate;
	}
	public void setOpeningDate(OffsetDateTime openingDate) {
		this.openingDate = openingDate;
	}
	public OffsetDateTime getCompletionDate() {
		return completionDate;
	}
	public void setCompletionDate(OffsetDateTime completionDate) {
		this.completionDate = completionDate;
	}
}
