package com.sportsmanagement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sportsmanagement.entity.Booking;

@Repository
public interface BookingDao extends JpaRepository<Booking, Integer> {
	
	List<Booking> findByUserId(int userId);

	long countByDateAndSportsIdAndBatchAndStatus(String date,int sportsId, String timeSlot, String status);
	
	List<Booking> findBySportsId(int sportsId);
	
}
