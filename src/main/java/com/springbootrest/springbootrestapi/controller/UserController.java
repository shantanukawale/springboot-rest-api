package com.springbootrest.springbootrestapi.controller;

import com.springbootrest.springbootrestapi.model.User;
import com.springbootrest.springbootrestapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@GetMapping("")
	public List<User> list() {
		logger.info("test log");
		return userService.listAllUser();
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> get(@PathVariable Integer id) {
		try {
			User user = userService.getUser(id);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/")
	public void add(@RequestBody User user) {
		userService.saveUser(user);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody User user, @PathVariable Integer id) {
		try {
			User existUser = userService.getUser(id);
			user.setId(id);
			userService.saveUser(user);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		userService.deleteUser(id);
	}
}