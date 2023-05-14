package com.sportsmanagement.dto;

import java.util.List;

import com.sportsmanagement.entity.User;

import lombok.Data;

@Data
public class UsersResponseDto extends CommanApiResponse {
	
	private List<User> users;
	
	private User user;

}
