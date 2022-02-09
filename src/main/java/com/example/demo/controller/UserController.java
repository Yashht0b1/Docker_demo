package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/save")
	public ResponseEntity<User> createUser(@RequestBody User user){
		//User _user = userRepository.save(new User(user.getEmail(), user.getPassword()));
		User _user = userRepository.save(new User(user.getEmail(), user.getPassword()));
		return new ResponseEntity<>(_user, HttpStatus.CREATED);
	}
	
	//fetch All
	@GetMapping("/get_all_users")
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> users = new ArrayList<User>();
		userRepository.findAll().forEach(users::add);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	//Fetch by ID
	@GetMapping("/get_user_by_id/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") int id){
		Optional<User> userData = userRepository.findById(id);
		if(userData.isPresent()) {
			return new ResponseEntity<>(userData.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	// Update by ID
	@PutMapping("/update_user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") int id,@RequestBody User user){
		Optional<User> userdata = userRepository.findById(id);
		if(userdata.isPresent()) {
			User _user = userdata.get();
			_user.setEmail(user.getEmail());
			_user.setPassword(user.getPassword());
			return new ResponseEntity<>(userRepository.save(_user),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//Delete by ID
	@DeleteMapping("/delete_user/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id){
		userRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
