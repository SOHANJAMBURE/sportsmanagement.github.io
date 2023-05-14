package com.sportsmanagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sportsmanagement.dto.BookingResponseDto;
import com.sportsmanagement.dto.CommanApiResponse;
import com.sportsmanagement.dto.UpdateBookingStatusRequestDto;
import com.sportsmanagement.entity.Booking;
import com.sportsmanagement.entity.Sports;
import com.sportsmanagement.entity.User;
import com.sportsmanagement.service.BookingService;
import com.sportsmanagement.service.SportsService;
import com.sportsmanagement.service.UserService;
import com.sportsmanagement.utility.Constants.Batch;
import com.sportsmanagement.utility.Constants.BookingStatus;
import com.sportsmanagement.utility.Constants.ResponseCode;
import com.sportsmanagement.utility.Helper;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/book/sports")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {  

	Logger LOG = LoggerFactory.getLogger(BookingController.class);

	@Autowired
	private BookingService bookingService;

	@Autowired
	private UserService userService;

	@Autowired
	private SportsService sportsService;

	@PostMapping("/")
	@ApiOperation(value = "Api to book sports")
	public ResponseEntity<?> bookGround(Booking booking) {
		LOG.info("Recieved request for booking hotel");

		System.out.println(booking);

		CommanApiResponse response = new CommanApiResponse();

		if (booking == null) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("sports Booking Failed");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		if (booking.getUserId() == 0) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("User is not not looged in");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		if (booking.getSportsId() == 0) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("sports not found to Book");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		Sports sports = sportsService.getSportsById(booking.getSportsId());

		if (sports == null) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("No sports present with this Id");
		}

		long count = this.bookingService.getBookingByDateAndSportsIdAndBatchAndStatus(booking.getDate(),
				booking.getSportsId(), booking.getBatch(), BookingStatus.APPROVED.value());

		if (count  >= 10) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Selected Slot has been selected for given batch");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		booking.setStatus(BookingStatus.PENDING.value());
		booking.setBookingId(Helper.getAlphaNumericId());

		Booking bookedHotel = this.bookingService.addBooking(booking);

		if (bookedHotel != null) {
			response.setResponseCode(ResponseCode.SUCCESS.value());
			response.setResponseMessage("sports Booked Successfully, Please Check Approval Status on Booking Option");
			return new ResponseEntity(response, HttpStatus.OK);
		}

		else {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Failed to Book Hotel");
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/fetch/all")
	@ApiOperation(value = "Api to fetch all booked hotel")
	public ResponseEntity<?> fetchAllHotelBooking() {
		LOG.info("Recieved request for fetch all booking");

		List<BookingResponseDto> bookings = new ArrayList<>();

		List<Booking> allBookings = this.bookingService.getAllBooking();

		for (Booking booking : allBookings) {

			BookingResponseDto b = new BookingResponseDto();

			User customer = this.userService.getUserById(booking.getUserId());
			Sports sports = this.sportsService.getSportsById(booking.getSportsId());

			b.setBookingId(booking.getBookingId());
			b.setCustomerContact(customer.getContact());
			b.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
			b.setDate(booking.getDate());
			b.setSportId(booking.getSportsId());
			b.setSportImage(sports.getImage());
			b.setSportName(sports.getName());
			b.setId(booking.getId());
			b.setStatus(booking.getStatus());
			b.setTimeSlot(booking.getBatch());
			b.setUserId(customer.getId());
			b.setPrice(String.valueOf(sports.getPrice()));

			bookings.add(b);
		}

		return new ResponseEntity(bookings, HttpStatus.OK);

	}
	
	@GetMapping("/fetch/all/bookings")
	@ApiOperation(value = "Api to fetch all my bookings")
	public ResponseEntity<?> fetchAllSportsBooking(@RequestParam("userId") int userId) {
		LOG.info("Recieved request for fetch all booking");

		List<BookingResponseDto> bookings = new ArrayList<>();

		User user = this.userService.getUserById(userId);
		
		List<Booking> allBookings = this.bookingService.getBookingsBySportsId(user.getSportsId());

		for (Booking booking : allBookings) {

			BookingResponseDto b = new BookingResponseDto();

			User customer = this.userService.getUserById(booking.getUserId());
			Sports sports = this.sportsService.getSportsById(booking.getSportsId());

			b.setBookingId(booking.getBookingId());
			b.setCustomerContact(customer.getContact());
			b.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
			b.setDate(booking.getDate());
			b.setSportId(booking.getSportsId());
			b.setSportImage(sports.getImage());
			b.setSportName(sports.getName());
			b.setId(booking.getId());
			b.setStatus(booking.getStatus());
			b.setTimeSlot(booking.getBatch());
			b.setUserId(customer.getId());
			b.setPrice(String.valueOf(sports.getPrice()));

			bookings.add(b);
		}

		return new ResponseEntity(bookings, HttpStatus.OK);

	}

	@GetMapping("/fetch")
	@ApiOperation(value = "Api to fetch my booked sports")
	public ResponseEntity<?> fetchMyBooking(@RequestParam("userId") int userId) {
		LOG.info("Recieved request for fetch all booking by using userId");

		List<BookingResponseDto> bookings = new ArrayList<>();

		List<Booking> allBookings = this.bookingService.getBookingsByUserId(userId);

		for (Booking booking : allBookings) {

			BookingResponseDto b = new BookingResponseDto();

			User customer = this.userService.getUserById(booking.getUserId());
			Sports sports = this.sportsService.getSportsById(booking.getSportsId());

			b.setBookingId(booking.getBookingId());
			b.setCustomerContact(customer.getContact());
			b.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
			b.setDate(booking.getDate());
			b.setSportId(booking.getSportsId());
			b.setSportImage(sports.getImage());
			b.setSportName(sports.getName());
			b.setId(booking.getId());
			b.setStatus(booking.getStatus());
			b.setTimeSlot(booking.getBatch());
			b.setUserId(customer.getId());
			b.setPrice(String.valueOf(sports.getPrice()));
			bookings.add(b);
		}

		return new ResponseEntity(bookings, HttpStatus.OK);

	}

	@GetMapping("/fetch/bookingId")
	@ApiOperation(value = "Api to fetch my booked sports")
	public ResponseEntity<?> fetchBooking(@RequestParam("id") int bookingId) {
		LOG.info("Recieved request for fetch all booking by booking Id ");

		Booking booking = this.bookingService.getBookById(bookingId);

		BookingResponseDto b = new BookingResponseDto();

		User customer = this.userService.getUserById(booking.getUserId());
		Sports sports = this.sportsService.getSportsById(booking.getSportsId());

		b.setBookingId(booking.getBookingId());
		b.setCustomerContact(customer.getContact());
		b.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
		b.setDate(booking.getDate());
		b.setSportId(booking.getSportsId());
		b.setSportImage(sports.getImage());
		b.setSportName(sports.getName());
		b.setId(booking.getId());
		b.setStatus(booking.getStatus());
		b.setTimeSlot(booking.getBatch());
		b.setUserId(customer.getId());
		b.setPrice(String.valueOf(sports.getPrice()));

		return new ResponseEntity(booking, HttpStatus.OK);

	}

	@GetMapping("/fetch/status")
	@ApiOperation(value = "Api to fetch all booking status")
	public ResponseEntity<?> fetchAllBookingStatus() {
		LOG.info("Recieved request for fetch all booking status");

		List<String> response = new ArrayList<>();

		for (BookingStatus status : BookingStatus.values()) {
			response.add(status.value());
		}

		return new ResponseEntity(response, HttpStatus.OK);

	}

	@PostMapping("/update/status")
	@ApiOperation(value = "Api to update sports booking")
	public ResponseEntity<?> updateGroundBookingStatus(@RequestBody UpdateBookingStatusRequestDto request) {

		LOG.info("Recieved request for updating the Booking Status");

		
		if (request == null) {
			return new ResponseEntity("Request found NULL", HttpStatus.BAD_REQUEST);
		}

		Booking book = this.bookingService.getBookById(request.getBookingId());

		if (BookingStatus.APPROVED.value().equals(request.getStatus())) {
			long count = this.bookingService.getBookingByDateAndSportsIdAndBatchAndStatus( book.getDate(),
					book.getSportsId(), book.getBatch(), BookingStatus.APPROVED.value());
			if (count<=10) {
				book.setStatus(request.getStatus());
				this.bookingService.addBooking(book);
				return new ResponseEntity("Booking " + book.getStatus() + "Successfully", HttpStatus.OK);
			}
			
			else {
				return new ResponseEntity("Can't Approve Booking, Slot already used", HttpStatus.BAD_REQUEST);

			}
			
		}

		if (BookingStatus.PENDING.value().equals(request.getStatus())) {
			return new ResponseEntity("Can't update Booking status to Pending", HttpStatus.BAD_REQUEST);
		}

		// cancel status
		book.setStatus(request.getStatus());
		this.bookingService.addBooking(book);

		return new ResponseEntity("Booking " + book.getStatus() + "Successfully", HttpStatus.OK);

	}

	@GetMapping("/fetch/batch")
	@ApiOperation(value = "Api to fetch all booking batch")
	public ResponseEntity<?> fetchAllBookingTimeSlot() {
		LOG.info("Recieved request for fetch all batch");

		List<String> response = new ArrayList<>();

		for (Batch batch : Batch.values()) {
			response.add(batch.value());
		}

		return new ResponseEntity(response, HttpStatus.OK);

	}

}
