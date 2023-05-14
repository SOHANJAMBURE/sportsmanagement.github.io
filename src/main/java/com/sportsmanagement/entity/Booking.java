package com.sportsmanagement.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Booking {
	
	@Id   // primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY)// auto incremented
	private int id;    
	
	private String bookingId;   // private is done to abstration
	
	private String batch;
	
	private String date;
	
	private int userId;
	
	private int sportsId;
	
	private String status;

}
