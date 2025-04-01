package com.library.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.Entities.UserRole;
import com.library.Entities.Users;
import com.library.model.JWTImpl;
import com.library.model.UserLogin;
import com.library.service.UserService;

@RestController
@RequestMapping("/users")
public class UserRestApi {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JWTImpl jot;
	private String token=null;
	
	@GetMapping("/")
	public ResponseEntity<?> getAllUsers() {
	    // Validate token
	    if (!jot.validToken(token)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("10 minutes over! Please log in again.");
	    }

	    // Extract email from token
	    String email = jot.extractEmail(token);
	    System.out.println("Extracted email: " + email); // Debugging Log

	    // Fetch user by email
	    Users getUser = userService.findByEmail(email);
	    if (getUser == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for extracted email.");
	    }
	    
	    System.out.println("Fetched user: " + getUser.getName() + ", Role: " + getUser.getRole()); // Debugging Log

	    // Check if user is admin
	    if (getUser.getRole() == UserRole.admin) { // Use equalsIgnoreCase()
	        try {
	            List<Users> users = userService.findAllUsers();
	            if (users.isEmpty()) {
	                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No users found.");
	            }
	            return ResponseEntity.ok(users);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching users.");
	        }
	    } else {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot access! Please contact the admin.");
	    }
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable("id") long id) {
	    if (!jot.validToken(token)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("10 minutes over! Please log in again.");
	    }

	    String email = jot.extractEmail(token);
	    Users getUser = userService.findByEmail(email);
	    if (getUser == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for extracted email.");
	    }

	    if (getUser.getRole() == UserRole.admin) {
	        Optional<Users> userOptional = userService.findById(id); // ✅ FIXED

	        if (userOptional.isPresent()) {
	            return ResponseEntity.ok(userOptional.get());
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + id);
	        }
	    } else {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot access this! Please contact the admin.");
	    }
	}


	
	@PostMapping("/")
	public ResponseEntity<?> saveUser(@RequestBody Users user) {
	    try {
	        // Check if an email already exists
	        if (userService.findByEmail(user.getEmail()) != null) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists. Please use a different email.");
	        }

	        Users savedUser = userService.saveUser(user);
	        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while saving the user.");
	    }
	}

	
	@PutMapping("/")
	public ResponseEntity<?> updateUser(@RequestBody Users user) {
	    // Validate token
	    if (!jot.validToken(token)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("10 minutes over! Please login again.");
	    }

	    try {
	        Users updatedUser = userService.saveUser(user); // Save updated user
	        return ResponseEntity.ok(updatedUser);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the user.");
	    }
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser1(@PathVariable("id") long id, @RequestBody Users user) {
	    if (!jot.validToken(token)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("10 minutes over! Please log in again.");
	    }

	    Optional<Users> existingUserOptional = userService.findById(id); // ✅ FIXED

	    if (existingUserOptional.isPresent()) {
	        Users existingUser = existingUserOptional.get();

	        existingUser.setEmail(user.getEmail());
	        existingUser.setName(user.getName());
	        existingUser.setPassword(user.getPassword());
	        existingUser.setRole(user.getRole());

	        Users updatedUser = userService.saveUser(existingUser); // Save updated user

	        return ResponseEntity.ok(updatedUser);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + id);
	    }
	}


	
	@PatchMapping("/{id}")
	public ResponseEntity<?> updateUser2(@PathVariable("id") long id, @RequestBody Users user) {
	    // Validate token
	    if (!jot.validToken(token)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("10 minutes over! Please login again.");
	    }

	    try {
	        Optional<Users> existingUserOptional = userService.findById(id); // ✅ FIXED

	        if (existingUserOptional.isPresent()) {
	            Users existingUser = existingUserOptional.get();

	            // Update only the password field
	            existingUser.setPassword(user.getPassword());
	            Users updatedUser = userService.saveUser(existingUser); // Save changes

	            return ResponseEntity.ok(updatedUser);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + id);
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the user.");
	    }
	}


	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
	    if (!jot.validToken(token)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("10 minutes over! Please log in again.");
	    }

	    String email = jot.extractEmail(token);
	    Users requestingUser = userService.findByEmail(email);

	    if (requestingUser == null || requestingUser.getRole() != UserRole.admin) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to delete users. Please contact the admin.");
	    }

	    Optional<Users> userToDeleteOptional = userService.findById(id); // ✅ FIXED

	    if (userToDeleteOptional.isPresent()) {
	        userService.deleteUser(userToDeleteOptional.get()); // ✅ FIXED
	        return ResponseEntity.ok("User deleted successfully.");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + id);
	    }
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody UserLogin user) {
	    Users existingUser = userService.findByEmail(user.getEmail()); // Find user by email

	    if (existingUser == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
	    }

	    if (!existingUser.getPassword().equals(user.getPassword())) { // Basic password check
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password is Wrong");
	    }

	    token = jot.generateToken(user.getEmail()); // Generate token for valid user
	    // Format the token expiration time as HH:mm:ss
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	    String formattedTime = sdf.format(new Date(System.currentTimeMillis() + 600000));
	    return ResponseEntity.ok("Login successful\n Token valid is: "+formattedTime);
	}

}
