package com.sportsmanagement.dto;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class SportsAddRequest {
	
	private int id;

    private String name;
	
	private String description;
	
	private double price;
	
	private int coachId;
	
	private MultipartFile image;	
	

}
