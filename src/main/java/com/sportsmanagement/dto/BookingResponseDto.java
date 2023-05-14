package com.sportsmanagement.dto;

import lombok.Data;

@Data
public class BookingResponseDto {
	
    private int id;
	
	private String bookingId;
	
	private String timeSlot;
	
	private String date;
	
	private int userId;
	
	private int sportId;
	
	private String status;
	
	private String customerName;
	
	private String customerContact;
	
	private String sportName;
	
	private String sportImage;
	
	private String price;
	
}
