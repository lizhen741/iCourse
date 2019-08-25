package com.iCourse.user.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public interface ScoreService {
	
	public void exportScore(Long cl_id);
	
}
