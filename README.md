# Spring-Security

## Basic Terms
<ul>
    <li>Principal : Target to access to protected resource.</li>
    <li>Authentication : Process to check who access protected resource and subject being able to conduct tasks. => who?</li>
    <li>Authorization : After authentication, process to check which resources principal can access. => what authorities does principal have?</li>
    <li>Credential : Principal's password accessing resource.</li>
</ul>

## Spring-Security feature and structure
<ul>
    <li>Conducting based on Filter apart from MVC.</li>
    <li>Basically, authentication through session and cookie</li>
    <li>Managing resource access through Authentication Manager(Authentication) and Access Decision Manager(Authorization).</li>
    <li>Authentication Manager is conducted by UsernamePasswordAuthenticationFilter.</li>
    <li>Access Decision Manager is conducted by FilterSecurityInterceptor.</li>
</ul>

## start Spring-Security

### Working principle
<ul>
    <li>Authentication -> After Authentication succeeds -> Authorization</li>
    <li>Authentication information basically stores at in-memory session storage(SecurityContextHolder) as session-cookie way.</li>
</ul>
1. 

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
    }

```

3. Implementation UserDetails



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