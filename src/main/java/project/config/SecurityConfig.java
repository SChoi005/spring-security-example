package project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserDetailsService userDetailsService;
    
    // static resource
    @Override
    public void configure(WebSecurity web) throws Exception {
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        String[] permmited = {"/login",
                              "/api/user/signUp"
                             };
        
        http = http.cors().and().csrf().disable();
        
        // Setting permission of end point
        http.authorizeRequests()
            .antMatchers(permmited).permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().disable();
           
        
    }
}