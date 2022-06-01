# Spring-Security

## Http Protocol Characteristic 
1. <strong>Connectionless</strong>
   * When a client and server exchange a request and a response once, disconnect.
2. <strong>Stateless</strong>
   * After communication, not stay state information
* <strong>So, due to these characteristics, can stay information of user authentication, using Session&#38;Cookie or jwt. </strong>  

## Login ways

* <strong>Session&#38;Cookie</strong>
* <strong>JWT(Json Web Token) token</strong>
* <strong>OAuth(Open Authentication)</strong>

## Basic Terms
<ul>
<li><strong>Principal</strong> : Target to access to protected resource.</li>
<li><strong>Authentication</strong> : Process to check who access protected resource and subject being able to conduct tasks. => who?</li>
<li><strong>Authorization</strong> : After authentication, process to check which resources principal can access. => what authorities does principal have?</li>
<li><strong>Credential</strong> : Principal's password accessing resource.</li>
</ul>

## Spring-Security feature and structure
<ul>
    <li>Conducting based on Filter apart from MVC.</li>
    <li>Basically, authentication through session and cookie</li>
    <li>Managing resource access through <strong>Authentication Manager(Authentication)</strong> and <strong>Access Decision Manager(Authorization).</strong></li>
    <li>Authentication Manager is conducted by <strong>UsernamePasswordAuthenticationFilter.</strong></li>
    <li>Access Decision Manager is conducted by <strong>FilterSecurityInterceptor.</strong></li>
</ul>

## Start Spring-Security

### Working principle
<ul>
    <li>Authentication -> After Authentication succeeds -> Authorization</li>
    <li>Authentication information basically stores at in-memory session storage(<strong>SecurityContextHolder</strong>) as session-cookie way.</li>
</ul>
<ol>
    <li>Basically, if request about authentication based on ID, PASSWORD comes about, it reaches <strong>UsernamePasswordAuthenticationFilter</strong> of Authentication Filters.</li>
    <em>* If not ID, PASSWORD, but OAuth2.0 or JWT is used, other filter is used. (ex : OAuth2ClientAuthenticationProcessingFilter)</em>
    <li>If the request reaches <strong>UsernamePasswordAuthenticationFilter</strong>, <strong>attemptAuthentication(request,response)</strong> method works. This method creates <strong>UsernamePasswordAuthenticationToken(Authentication)</strong> by bringing username and password from the request.</li>
    <li><strong>AuthenticationManager(ProviderManager)</strong> authorize by using <strong>UsernamePasswordAuthentciationToken(Authentication)</strong></li>
</ol>

### Code

1. Add dependency

    ```groovy

        compile('org.springframework.boot:spring-boot-starter-security')

    ```

2. Java Configuration

    ```java

        @EnableWebSecurity // Automatically, contain SpringSecurityFilterChain
        public class SecurityConfig extends WebSecurityConfigurerAdapter{
            ...   
            
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

    ```
    * <strong>``` sessionCreationPolicy(SessionCreationPolicy.ALWAYS) ```</strong> => Setting session creation policy
      * <strong>SessionCreationPolicy.ALWAYS </strong>      => Always create session
      * <strong>SessionCreationPolicy.IF_REQUIRED</strong>  => (Basic)Create when be required
      * <strong>SessionCreationPolicy.NEVER</strong>        => Not create, but if session is existed, use it
      * <strong>SessionCreationPolicy.STATELESS</strong>    => Not create, use existed session(JWT)
    * <strong>``` sessionFixation() ```</strong> => Setting about session-fixation attack
      * <strong>changeSessionId()</strong> => Issue new sessionId, and capable of using prior session
      * <strong>newSession()</strong>      => Issue new sessionId, and not capable of using prior session
      * <strong>none()</strong>            => Nothing
    * <strong>``` maximumSessions(1) ```</strong> => Restrict maximum session 
    * <strong>``` maxSessionsPreventsLogin(false) ```</strong>
      * <strong>True</strong> => Later user's session is blocked 
        * If don't normally logout and close browser, occur anyone doesn't login
      * <strong>False</strong> => Prior user's session is expired
    * <strong>``` expiredUrl("/") ```</strong> => Url when dupliciate login
    * <strong>``` sessionRegistry(sessionRegistry()) ```</strong>
      * If not add sessionRegistry(sessionRegistry()), when user login again after logout, occur error "Maximum sessions of 1 for this principal exceeded".
    * <strong>``` invalidSessionUrl("/") ```</strong> => Url when session is expired
    
3. Implementation UserDetails

    ```java
        
        public class User implements UserDetails {
    
            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;

            private String username;

            private String password;

            @Column(name = "role")
            @Enumerated(EnumType.STRING)
            private Role role;

            @Override
            public String getPassword() {
                return password;
            }

            @Override
            public String getUsername() {
                return username;
            }

            // Return Authority lists user has
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                ArrayList<GrantedAuthority> auth = new ArrayList<>();
                auth.add(new SimpleGrantedAuthority(role.toString()));
                return auth;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }
        }
    
    ```
4. Implementation UserDetailsService

    ```java
        
        @Service
        public class UserService implements UserDetailsService{

            ...

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.findByUsername(username);
                if(user != null)
                    return user;
                else
                    throw new UsernameNotFoundException(username);
            }

        }
        
    ```
