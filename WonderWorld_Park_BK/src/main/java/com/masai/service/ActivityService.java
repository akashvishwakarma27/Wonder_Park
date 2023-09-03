package com.masai.service;

import java.time.LocalDateTime;
import java.util.List;

import com.masai.Exception.ActivityException;
import com.masai.model.Activity;

public interface ActivityService {

	 Activity insertActivity(Activity  activiy)throws ActivityException;
	 
	 Activity updateActivity(Activity activity,Integer activityId)throws ActivityException;
	 
	 Activity deletedActivity(Integer activityId)throws ActivityException;
	 
	 List<Activity> viewAllActivities(Integer pageNumber,Integer pageSize)throws ActivityException;
	 
	 Activity getActivityById(Integer activityId)throws ActivityException;
	 
	 List<Activity> getActivitiesByCharges(Double charge)throws ActivityException;
	 
	 Integer getCountNumberOfActivitesByCharge(Double charge)throws ActivityException;
	 
	 List<Activity> getAllActivitiesByCustomerName()throws ActivityException;
	 
	 List<Activity> getAllActivitiesByDateWise()throws ActivityException;
	 
	 List<Activity> getAllActivitiesForDays(Integer customerId,LocalDateTime start)throws ActivityException;
	 
	 List<Activity> getAllActivitiesOnBasisOfDate(LocalDateTime start, LocalDateTime end)throws ActivityException;
	 
	 List<Activity> getAllActivityByCustomerId(Integer customerId)throws ActivityException;
	 
}
