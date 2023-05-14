package com.sportsmanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sportsmanagement.entity.Sports;

@Repository
public interface SportsDao extends JpaRepository<Sports, Integer> {

}
