package project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
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
        
        String[] permmited = {
                              "/api/user/signUp","/"
                             };
        // Setting cors, csrf 
        http.cors().and().csrf().disable();
        
        // Setting permission of end point and authorize
        http.authorizeRequests()
                .antMatchers(permmited).permitAll()
                .antMatchers("/professor").hasRole("PROFESSOR")
                .antMatchers("/student").hasAnyRole("STUDENT","PROFESSOR")
            .and()
                .formLogin()
                .loginPage("/")
                .defaultSuccessUrl("/student")
                .usernameParameter("userID") // Coincide loginform name
            .and()
                .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            .and()
                .exceptionHandling()
                .accessDeniedPage("/accessDenied");
        
        // Session management
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) 
                .sessionFixation()
                .changeSessionId()
                .maximumSessions(1) 
                .maxSessionsPreventsLogin(false) 
                .expiredUrl("/")
                .sessionRegistry(sessionRegistry())
            .and()
                .invalidSessionUrl("/"); 
    }
    
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    
}