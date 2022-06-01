package project.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto{

    private Long id;
    
    private String username;
    
    private String password;
    
    private String role;
}