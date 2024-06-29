package com.springboot.user.service.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.springboot.user.service.entities.Hotel;
import com.springboot.user.service.entities.Rating;
import com.springboot.user.service.entities.User;
import com.springboot.user.service.exception.ResourseNotFoundException;
import com.springboot.user.service.external.services.HotelService;
import com.springboot.user.service.repostories.UserRepository;

@Service
public class UserServiceImple implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService hotelService;
	
	@Override
	public User saveUser(User user) {
		String randomUserID = UUID.randomUUID().toString();
		user.setUserId(randomUserID);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		
		return userRepository.findAll();
	}

	@Override
	public User getUser(String id) {
		 // get user from database with help of user repository
		User user = userRepository.findById(id).orElseThrow(()-> new ResourseNotFoundException("User with given id is not found on server !!:"));
		//fetch rating of the above user rating service
        // http://localhost:8081/rating/hotel/8a6e7f54-b9bd-4578-b707-23196e8c05f2
		Rating[] forObject = restTemplate.getForObject("http://RatingService/rating/user/"+user.getUserId(), Rating[].class);
		List<Rating> ratings = Arrays.stream(forObject).toList();
//		 user.setRatings(forObject);
		    
		
		   List<Rating> ratingList = ratings.stream().map(rating -> {
	            //api call to hotel service to get the hotel
	            //http://localhost:8082/hotels/1cbaf36d-0b28-4173-b5ea-f1cb0bc0a791
	            //ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTELSERVICE/hotels/"+rating.getHotelId(), Hotel.class);
	            Hotel hotel = hotelService.getHotel(rating.getHotelId());
	     
	            //set the hotel to rating
	            rating.setHotel(hotel);
	    
	            return rating;
	        }).collect(Collectors.toList());

	        user.setRatings(ratingList);
		return user;
	}

	
}
