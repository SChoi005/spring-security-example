package project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
    
    // static resource
    @Override
    public void configure(WebSecurity web) throws Exception {
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        String[] permmited = {"/login",
                              "/api/user/signUp"
                             };
        // Setting cors, csrf 
        http = http.cors().and().csrf().disable();
        
        // JWT => stateless
        //http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
        
        // Session
        http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).and();
        
        // Setting permission of end point
        http.authorizeRequests()
            .antMatchers(permmited).permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin();
           
        
    }
}