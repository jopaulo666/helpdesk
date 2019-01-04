package com.joaopaulo.helpdesk.api.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.joaopaulo.helpdesk.api.enums.StatusEnum;

@Document
public class ChangeStatus {
	
	@Id
	private String id;
	
	@DBRef
	private Ticket ticket;
	
	@DBRef
	private User userCharge;
	
	private Date dateChangeStatus;
	
	private StatusEnum status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public User getUserCharge() {
		return userCharge;
	}

	public void setUserCharge(User userCharge) {
		this.userCharge = userCharge;
	}

	public Date getDateChangeStatus() {
		return dateChangeStatus;
	}

	public void setDateChangeStatus(Date dateChargeStatus) {
		this.dateChangeStatus = dateChargeStatus;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	
	

}
