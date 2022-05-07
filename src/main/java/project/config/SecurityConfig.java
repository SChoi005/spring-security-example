package project.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{


    // static resource
    @Override
    public void configure(WebSecurity web) throws Exception {
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        String[] permmited = {"/api/user/signUp"};
        
        http.authorizeRequests()
            .antMatchers(permmited).permitAll()
            .anyRequest().authenticated();
        
    }
}