package com.Tunznews.example;

import com.Tunznews.dao.UserDAO;
import com.Tunznews.model.User;

public class UserDAOExample {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        
        // Example 1: Register a new user
        System.out.println("=== Registering a new user ===");
        User newUser = new User();
        newUser.setId(userDAO.getNextUserId()); // Get next available ID
        newUser.setUsername("john_doe");
        newUser.setPassword("password123");
        newUser.setFullname("John Doe");
        newUser.setEmail("john.doe@example.com");
        newUser.setRole(1); // 1 = regular user, 0 = admin (adjust as needed)
        
        if (!userDAO.checkUserExists(newUser.getEmail())) {
            boolean registered = userDAO.register(newUser);
            System.out.println("Registration successful: " + registered);
        } else {
            System.out.println("User with this email already exists!");
        }
        
        // Example 2: Login
        System.out.println("\n=== User Login ===");
        User loggedInUser = userDAO.login("john_doe", "password123");
        if (loggedInUser != null) {
            System.out.println("Login successful: " + loggedInUser.toString());
        } else {
            System.out.println("Login failed!");
        }
        
        // Example 3: Update profile (without password change)
        System.out.println("\n=== Update Profile (without password) ===");
        if (loggedInUser != null) {
            boolean updated = userDAO.updateProfile(
                loggedInUser.getId(), 
                "John Smith", // new fullname
                "john.smith@example.com", // new email
                null // no password change
            );
            System.out.println("Profile update successful: " + updated);
        }
        
        // Example 4: Update profile (with password change)
        System.out.println("\n=== Update Profile (with password) ===");
        if (loggedInUser != null) {
            boolean updated = userDAO.updateProfile(
                loggedInUser.getId(), 
                "John Smith", 
                "john.smith@example.com", 
                "newpassword456" // new password
            );
            System.out.println("Profile update with password change successful: " + updated);
        }
        
        // Example 5: Get user by ID
        System.out.println("\n=== Get User by ID ===");
        if (loggedInUser != null) {
            User retrievedUser = userDAO.getUserById(loggedInUser.getId());
            if (retrievedUser != null) {
                System.out.println("Retrieved user: " + retrievedUser.toString());
            }
        }
    }
}