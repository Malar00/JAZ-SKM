package pl.edu.pjatk.simulator.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/login", "/users/**", "/trains/**", "/compartments/**", "/people/**").permitAll()
                .antMatchers(HttpMethod.POST, "/login", "/users", "/trains", "/compartments", "/people").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.DELETE, "/login/**", "/users/**", "/trains/**", "/compartments/**", "/people/**").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT, "/login", "/users", "/trains", "/compartments", "/people").hasAnyAuthority("ROLE_MOD", "ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilter(new TokenAuthenticationFilter(authenticationManager()))
                .addFilter(new TokenAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
    }
}