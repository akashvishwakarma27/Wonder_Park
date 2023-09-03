package com.masai.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.masai.Exception.ActivityException;
import com.masai.model.Activity;
import com.masai.model.Customer;
import com.masai.repository.ActivityRepository;
import com.masai.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ActivityServiceImpl implements ActivityService {
	
	@Autowired
	private ActivityRepository activityRepo;

	@Autowired
	private CustomerRepository customerRepo;
	@Override
	public Activity insertActivity(Activity activiy) throws ActivityException {
		if(activiy==null)throw new ActivityException("Activity is null");
		if(activiy.getPrice()<=0.0)throw new ActivityException("price needs to be greater than zero");
		log.debug("Calling save method from activityJpa Repository");
		Activity main=activityRepo.save(activiy);
		log.info("Activity added sucessfully");
		return main;
	}

	@Override
	public Activity updateActivity(Activity activity, Integer activityId) throws ActivityException {
		if(activity==null)throw new ActivityException("Activity is null");
		log.debug("Calling findById method from ActivityJpa Repository");
		Optional<Activity> temp=activityRepo.findById(activityId);
		if(temp.isPresent()) {
			Activity existingActivity=temp.get();
			existingActivity.setDistance(activity.getDistance());
			existingActivity.setPrice(activity.getPrice());
			existingActivity.setActivityName(activity.getActivityName());
			Activity main=activityRepo.save(existingActivity);
			log.info("Activity updated sucessfully");
			return main;
		}else {
			throw new ActivityException("No activity found");
		}
		
	}

	@Override
	public Activity deletedActivity(Integer activityId) throws ActivityException {
		log.debug("Calling findById method from ActivityJpa Repository");
		
		Optional<Activity> temp=activityRepo.findById(activityId);
		if(temp.isPresent()) {
			Activity existingActivity=temp.get();
			existingActivity.setIsDeleted(true);
			activityRepo.delete(existingActivity);
			log.info("Activity deleted sucessfully");
			return existingActivity;
		}else {
			throw new ActivityException("No activity found");
		}
	}

	@Override
	public List<Activity> viewAllActivities(Integer pageNumber,Integer pageSize) throws ActivityException {
		log.debug("Calling of method from PagingAndSorting Repository");
		Pageable p=PageRequest.of(pageNumber, pageSize);
		log.debug("Calling findAll method from ActivityJpa Repository");
		List<Activity> list = activityRepo.findAll(p).getContent();
		return list;
	}

	@Override
	public Activity getActivityById(Integer activityId) throws ActivityException {
		
		return activityRepo.findById(activityId).orElseThrow(()-> new ActivityException("Activity not found"));
	}

	@Override
	public List<Activity> getActivitiesByCharges(Double charge) throws ActivityException {
		if(charge<=0.0)throw new ActivityException("Charge cannot be less than or equal to zero");
		return activityRepo.findByCharge(charge);
	}

	@Override
	public Integer getCountNumberOfActivitesByCharge(Double charge)throws ActivityException {
		// TODO Auto-generated method stub
		Integer temp = activityRepo.findCountByCharge(charge);
		if(temp<=0)throw new ActivityException("No activity found");
		return temp;
	}

	@Override
	public List<Activity> getAllActivitiesByCustomerName() throws ActivityException {
		List<Activity> list= activityRepo.findAllSortedByCustomerName();
		if(list.isEmpty())throw new ActivityException("No Activity found");
		return list;
	}

	@Override
	public List<Activity> getAllActivitiesByDateWise() throws ActivityException {
		List<Activity> list= activityRepo.findAllByOrderByCreatedOnAsc();
		if(list.isEmpty())throw new ActivityException("No activity Found");
		return list;
	}

	@Override
	public List<Activity> getAllActivitiesForDays(Integer customerId, LocalDateTime start) throws ActivityException {
		Optional<Activity> temp= activityRepo.findById(customerId);
		if(temp.isPresent()) {
			List<Activity> list= activityRepo.findActivitiesByCustomerAndDate(customerId, start);
			if(list.isEmpty())throw new ActivityException("No activity found");
			return list;
		}else {
			throw new ActivityException("No customer found");
		}
		
	}

	@Override
	public List<Activity> getAllActivitiesOnBasisOfDate(LocalDateTime start, LocalDateTime end)
			throws ActivityException {
		
		if(end.isBefore(start))throw new ActivityException("start cannot be greater than end");
		List<Activity> list= activityRepo.findActivitiesForDays(start, end);
		if(list.isEmpty())throw new ActivityException("No activity found");
		return list;
	}

	@Override
	public List<Activity> getAllActivityByCustomerId(Integer customerId) throws ActivityException {
		Optional<Customer> cust=customerRepo.findById(customerId);
		if(cust.isPresent()) {
			List<Activity> list= activityRepo.findAllActivitiesByCustomerId(customerId);
			return list;
		}else {
			throw new ActivityException("No customer found");
		}
		
	}

}
