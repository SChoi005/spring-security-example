package project.dto;

import lombok.Getter;
import lombok.Setter;
import project.enumeration.Role;

@Getter
@Setter
public class UserDto{

    private Long id;
    
    private String username;
    
    private String password;
    
    private Role role;
}