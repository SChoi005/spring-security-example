package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import project.dto.UserDto;
import project.service.UserService;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController{
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody UserDto userDto) throws Exception{
        return ResponseEntity.ok().body(userService.createUser(userDto));
    } 
    
    
    
}