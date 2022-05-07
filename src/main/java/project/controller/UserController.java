package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.dto.UserDto;
import project.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController{
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(UserDto userDto) throws Exception{
        return ResponseEntity.ok().body(userService.createUser(userDto));
    } 
    
    
    
}