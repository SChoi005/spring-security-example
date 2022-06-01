package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import project.dto.UserDto;
import project.entity.User;
import project.enumeration.Role;
import project.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;
    
    public UserDto createUser(UserDto userDto) throws Exception{
        
        if(userRepository.findByUsername(userDto.getUsername())!= null)
            throw new Exception("Duplicate username.");
        else{
            User user = new User();
            user.setUsername(userDto.getUsername());
            user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
            user.setRole(Role.valueOf(userDto.getRole()));
            userRepository.save(user);

            
            return userDto;
        }
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user != null)
            return user;
        else
            throw new UsernameNotFoundException(username);
    }
    
}