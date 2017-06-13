package com.siemens.hackathon.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siemens.hackathon.application.controllers.error.ResponseError;
import com.siemens.hackathon.application.repositories.UserRepository;
import com.siemens.hackathon.application.user.registration.entity.User;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin
public class UserController {

	@Autowired
	UserRepository userRepo;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		User postSave = userRepo.save(user);
		return new ResponseEntity<Long>(postSave.getId(), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json", value = "/{id}")
	public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User user) {
		if (!userRepo.exists(Long.parseLong(id)))
			return new ResponseEntity<ResponseError>(new ResponseError(HttpStatus.NOT_FOUND.value(), "User not found"),
					HttpStatus.NOT_FOUND);
		User postSave = userRepo.save(user);
		return new ResponseEntity<Long>(postSave.getId(), HttpStatus.ACCEPTED);

	}
	
	@GetMapping
	public ResponseEntity<List<User>> fetchUsers() {
		List<User> users = userRepo.findAll();
		return new ResponseEntity<List<User>>(users, HttpStatus.ACCEPTED);
	}

}
