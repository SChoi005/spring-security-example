# Spring-Security

## Basic Terms
<ul>
    <li>Principal : Target to access to protected resource.</li>
    <li>Authentication : Process to check who access protected resource and subject being able to conduct tasks. => who?</li>
    <li>Authorize : After authentication, process to check which resources principal can access. => what authorities does principal have?</li>
</ul>

## Spring-Security feature and structure
<ul>
    <li>Conducting based on Filter apart from MVC.</li>
    <li>Basically, authentication through session and cookie</li>
    <li>Managing resource access through Authentication Manager(Authentication) and Access Decision Manager(Authorize).</li>
    <li>Authentication Manager is conducted by UsernamePasswordAuthenticationFilter.</li>
    <li>Access Decision Manager is conducted by FilterSecurityInterceptor.</li>
</ul>

## start Spring-Security
1. Add dependency

2. Java Configuration

3. Implementation UserDetails
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