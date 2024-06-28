package com.springboot.user.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.springboot.user.service.entities.User;
import com.springboot.user.service.services.UserService;
import com.springboot.user.service.services.UserServiceImple;


@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	//create
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		User user1 = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
		
	}
    //get single user
	@GetMapping("/{userId}")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId){
		User user = userService.getUser(userId);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	//All user get
	@GetMapping
	public ResponseEntity<List<User>> getAllUser(){
		List<User> allUser = userService.getAllUser();
		return ResponseEntity.ok(allUser);
	}
	
}
