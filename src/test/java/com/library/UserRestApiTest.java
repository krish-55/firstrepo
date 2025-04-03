package com.library; 

import com.library.controller.UserRestApi;  
import com.library.Entities.Users;  
import com.library.Entities.UserRole; // Ensure this import is correct based on your UserRole implementation  
import com.library.service.UserService;  
import org.junit.jupiter.api.BeforeEach;  
import org.junit.jupiter.api.Test;  
import org.mockito.InjectMocks;  
import org.mockito.Mock;  
import org.mockito.MockitoAnnotations;  
import static org.junit.jupiter.api.Assertions.assertEquals;  

import java.util.Arrays;  
import java.util.List;  

import static org.mockito.ArgumentMatchers.any;  
import static org.mockito.Mockito.*;  

public class UserRestApiTest {  

    @InjectMocks  
    private UserRestApi userRestApi;  

    @Mock  
    private UserService userService;  

    private Users user1;  
    private Users user2;  

    @BeforeEach  
    public void setUp() {  
        MockitoAnnotations.openMocks(this);  

        // Set up test users  
        user1 = new Users(1, "Alice", "alice@example.com", "password", UserRole.user);  
        user2 = new Users(2, "Bob", "bob@example.com", "password", UserRole.user);  
    }  

    @Test  
    public void testGetAllUsers() {  
        when(userService.findAllUsers()).thenReturn(Arrays.asList(user1, user2));  

        List<Users> users = userRestApi.getAllUsers();  
        assertEquals(2, users.size());  
        verify(userService, times(1)).findAllUsers();  
    }  

    @Test  
    public void testGetUser() {  
        when(userService.findById(1L)).thenReturn(user1);  

        Users user = userRestApi.getUser(1L);  
        assertEquals("Alice", user.getName());  
        verify(userService, times(1)).findById(1L);  
    }  

    @Test  
    public void testSaveUser() {  
        when(userService.saveUser(any(Users.class))).thenReturn(user1);  

        Users savedUser = userRestApi.saveUser(user1);  
        assertEquals("Alice", savedUser.getName());  
        verify(userService, times(1)).saveUser(any(Users.class));  
    }  

    @Test  
    public void testUpdateUser() {  
        when(userService.saveUser(any(Users.class))).thenReturn(user1);  

        Users updatedUser = userRestApi.updateUser(user1);  
        assertEquals("Alice", updatedUser.getName());  
        verify(userService, times(1)).saveUser(any(Users.class));  
    }  

    @Test  
    public void testUpdateUserById() {  
        when(userService.findById(1L)).thenReturn(user1);  
        when(userService.saveUser(any(Users.class))).thenReturn(user1);  

        Users updatedUser = userRestApi.updateUser1(1L, user1);  
        assertEquals("Alice", updatedUser.getName());  
        verify(userService, times(1)).findById(1L);  
        verify(userService, times(1)).saveUser(any(Users.class));  
    }  

    @Test  
    public void testDeleteUser() {  
        when(userService.findById(1L)).thenReturn(user1);  
        doNothing().when(userService).deleteUser(user1);  

        userRestApi.deleteUser(1L);  
        verify(userService, times(1)).findById(1L);  
        verify(userService, times(1)).deleteUser(user1);  
    }  
}  