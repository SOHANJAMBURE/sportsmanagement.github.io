package com.sportsmanagement.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sportsmanagement.dto.CommanApiResponse;
import com.sportsmanagement.dto.SportsAddRequest;
import com.sportsmanagement.entity.Sports;
import com.sportsmanagement.entity.User;
import com.sportsmanagement.service.SportsService;
import com.sportsmanagement.service.UserService;
import com.sportsmanagement.utility.Constants.ResponseCode;
import com.sportsmanagement.utility.StorageService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/sports/")
@CrossOrigin(origins = "http://localhost:3000")
public class SportsController {
	
	Logger LOG = LoggerFactory.getLogger(SportsController.class);
	
	@Autowired
	private SportsService sportsService;
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("add")
	@ApiOperation(value = "Api to add sports")
	public ResponseEntity<?> addSports(SportsAddRequest sportsAddRequest) {
		LOG.info("Recieved request for Add Sports");

		CommanApiResponse response = new CommanApiResponse();

		if (sportsAddRequest == null) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Request Object is null");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}
		
		if(sportsAddRequest.getCoachId() == 0 ) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Coach Not Selected for the Sport");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}
		
		Sports sports = new Sports();
		sports.setDescription(sportsAddRequest.getDescription());
		sports.setName(sportsAddRequest.getName());
		sports.setPrice(sportsAddRequest.getPrice());
		sports.setCoachId(sportsAddRequest.getCoachId());
		
        String image = storageService.store(sportsAddRequest.getImage());
        
        sports.setImage(image);
        
        Sports addedSports= this.sportsService.addSports(sports);
        
		if (addedSports != null) {
			
			User coach = this.userService.getUserById(sportsAddRequest.getCoachId());
			coach.setSportsId(addedSports.getId());
			this.userService.registerUser(coach);
			
			response.setResponseCode(ResponseCode.SUCCESS.value());
			response.setResponseMessage("Ground Added Successfully");
			return new ResponseEntity(response, HttpStatus.OK);
			
		}

		else {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Failed to add Ground");
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("id")
	@ApiOperation(value = "Api to fetch grounds by using Sports Id")
	public ResponseEntity<?> fetchHotel(@RequestParam("sportsId") int sportsId) {
		LOG.info("Recieved request for Fetch Sports using Sports Id");

		Sports sports = null;
		
		sports = this.sportsService.getSportsById(sportsId);

		return new ResponseEntity(sports, HttpStatus.OK);

	}

	@GetMapping("fetch")
	@ApiOperation(value = "Api to fetch all sports")
	public ResponseEntity<?> fetchAllSports() {
		LOG.info("Recieved request for Fetch sports");

		List<Sports> sports = new ArrayList<>();

		sports = this.sportsService.getAllSports();
		
		return new ResponseEntity(sports, HttpStatus.OK);

	}
	
	@GetMapping(value="/{sportsImageName}", produces = "image/*")
	@ApiOperation(value = "Api to fetch sports image by using image name")
	public void fetchSportsImage(@PathVariable("sportsImageName") String sportsImageName, HttpServletResponse resp) {
		System.out.println("request came for fetching product pic");
		System.out.println("Loading file: " + sportsImageName);
		Resource resource = storageService.load(sportsImageName);
		if(resource != null) {
			try(InputStream in = resource.getInputStream()) {
				ServletOutputStream out = resp.getOutputStream();
				FileCopyUtils.copy(in, out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("response sent!");
	}
	
}
