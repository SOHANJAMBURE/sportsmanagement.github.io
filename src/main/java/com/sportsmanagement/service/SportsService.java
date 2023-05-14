package com.sportsmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportsmanagement.dao.SportsDao;
import com.sportsmanagement.entity.Sports;

@Service
public class SportsService {
	
	@Autowired
	private SportsDao sportsDao;
	
	public Sports addSports(Sports sports) {
		return sportsDao.save(sports);
	}
	 
	public Sports getSportsById(int sportsId) {
		return sportsDao.findById(sportsId).get();
	}
	
	public List<Sports> getAllSports() {
		return this.sportsDao.findAll();
	}

}
