package com.sportsmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportsmanagement.dao.BookingDao;
import com.sportsmanagement.entity.Booking;

@Service
public class BookingService {

	@Autowired
	private BookingDao bookingDao;
	
	public Booking addBooking(Booking booking) {
		return bookingDao.save(booking);
	}
	
	public Booking getBookById(int id) {
		return bookingDao.findById(id).get();
	}
	
	public List<Booking> getAllBooking() {
		return bookingDao.findAll();
	}
	
	public Long getBookingByDateAndSportsIdAndBatchAndStatus(String date, int sprotsId, String batch, String status) {
		return bookingDao.countByDateAndSportsIdAndBatchAndStatus(date, sprotsId, batch, status);
	}
	 
	public List<Booking> getBookingsByUserId(int userId) {
		return bookingDao.findByUserId(userId);
	}
	
	public List<Booking> getBookingsBySportsId(int sportsId) {
		return bookingDao.findBySportsId(sportsId);
	}
	
}
