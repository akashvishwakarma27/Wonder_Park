package com.masai.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.Exception.ActivityException;
import com.masai.model.Activity;
import com.masai.service.ActivityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/activities")
public class ActivityController {

	@Autowired
	private ActivityService activityService;
	
	@PostMapping("/add")
	public ResponseEntity<Activity> addActivity(@Valid @RequestBody Activity activity)throws ActivityException{
		activity.setIsDeleted(false);
		return new ResponseEntity<Activity>(activityService.insertActivity(activity),HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{activityId}")
	public ResponseEntity<Activity> updateActivity(@RequestBody Activity activity,@PathVariable Integer activityId)throws ActivityException{
		return new ResponseEntity<Activity>(activityService.updateActivity(activity, activityId),HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{activityId}")
	public ResponseEntity<Activity> deleteActivity(@PathVariable Integer activityId)throws ActivityException{
		return new ResponseEntity<Activity>(activityService.deletedActivity(activityId),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Activity>> viewAllActivity(@RequestParam(name = "pageNumber") Integer pageNumber,@RequestParam(name = "pageSize") Integer pageSize)throws ActivityException{
		return new ResponseEntity<List<Activity>>(activityService.viewAllActivities(pageNumber, pageSize),HttpStatus.OK);
	}
	
	@GetMapping("/{activityId}")
	public ResponseEntity<Activity> getActivityById(@PathVariable Integer activityId)throws ActivityException{
		return new ResponseEntity<Activity>(activityService.getActivityById(activityId),HttpStatus.OK);
	}
	
	@GetMapping("/filter")
	public ResponseEntity<List<Activity>> getActivitiesByCharge(@RequestParam Double charge)throws ActivityException{
		return new ResponseEntity<List<Activity>>(activityService.getActivitiesByCharges(charge),HttpStatus.OK);
	}
	
	@GetMapping("/filter/{charge}")
	public ResponseEntity<Integer> getNumOfActByCharge(@PathVariable Double charge)throws ActivityException{
		return new ResponseEntity<Integer>(activityService.getCountNumberOfActivitesByCharge(charge),HttpStatus.OK);
	}
	
	@GetMapping("/sortName")
	public ResponseEntity<List<Activity>> getAllActivitiesByCustomerName()throws ActivityException{
		return new ResponseEntity<List<Activity>>(activityService.getAllActivitiesByCustomerName(),HttpStatus.OK);
	}
	
	@GetMapping("/sortDateWise")
	public ResponseEntity<List<Activity>> getAllActivitiesByDateWise()throws ActivityException{
		return new ResponseEntity<List<Activity>>(activityService.getAllActivitiesByDateWise(),HttpStatus.OK);
	}
	
	@GetMapping("/filter/date")
	public ResponseEntity<List<Activity>> getAllActivitiesByDateTime(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end)throws ActivityException{
		return new ResponseEntity<List<Activity>>(activityService.getAllActivitiesOnBasisOfDate(start, end),HttpStatus.OK);
	}
	
	@GetMapping("/filter/{customerId}/date")
	public ResponseEntity<List<Activity>> getAllActivitiesOfCustomerByDate(@PathVariable Integer customerId,@RequestParam LocalDateTime start)throws ActivityException{
		return new ResponseEntity<List<Activity>>(activityService.getAllActivitiesForDays(customerId, start),HttpStatus.OK);
	}
}
