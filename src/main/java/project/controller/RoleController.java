package project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class RoleController{
    @GetMapping("/student")
    public String student(){
        return "student";
    }

    @GetMapping("/professor")
    public String professor(){
        return "professor";
    }
    
    @GetMapping("/accessDenied")
    public String accessDenied(){
        return "accessDenied";
    }
    
}