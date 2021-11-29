package com.lucasconte.tenpochallenge.security;

import com.lucasconte.tenpochallenge.security.filter.HistoryFilter;
import com.lucasconte.tenpochallenge.security.filter.UserAuthenticationFilter;
import com.lucasconte.tenpochallenge.security.filter.JWTAuthenticationFilter;
import com.lucasconte.tenpochallenge.security.handler.SimpleAccessDeniedHandler;
import com.lucasconte.tenpochallenge.security.handler.SimpleAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthenticationFilter userAuthenticationFilter;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final HistoryFilter historyFilter;

    public SecurityConfiguration(UserDetailsService userDetailsService,
                                 PasswordEncoder passwordEncoder,
                                 UserAuthenticationFilter userAuthenticationFilter,
                                 JWTAuthenticationFilter jwtAuthenticationFilter,
                                 HistoryFilter historyFilter) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userAuthenticationFilter = userAuthenticationFilter;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.historyFilter = historyFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers(HttpMethod.POST,"/login").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/signUp").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/history").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/logout").permitAll();

        http.addFilter(userAuthenticationFilter);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(historyFilter, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling().accessDeniedHandler(new SimpleAccessDeniedHandler())
                .authenticationEntryPoint(new SimpleAuthenticationEntryPoint());

        http.authorizeRequests().anyRequest().authenticated();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST, "/logout");
    }

}
