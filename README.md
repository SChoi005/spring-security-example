# Spring-Security

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
        }

    ```
3. Implementation UserDetails

//signup 완료 sign in 작성 해야함


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