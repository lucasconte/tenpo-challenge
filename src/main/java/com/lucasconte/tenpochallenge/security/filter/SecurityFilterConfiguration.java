package com.lucasconte.tenpochallenge.security.filter;

import com.lucasconte.tenpochallenge.actuator.CustomTraceRepository;
import com.lucasconte.tenpochallenge.repository.HistoryRepository;
import com.lucasconte.tenpochallenge.service.JWTService;
import com.lucasconte.tenpochallenge.service.TokenBlacklistedService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Set;

@Configuration
public class SecurityFilterConfiguration {

    @Bean
    public UserAuthenticationFilter userAuthenticationFilter(@Lazy AuthenticationManager authenticationManager,
                                                               JWTService jwtService) {
        return new UserAuthenticationFilter(authenticationManager, jwtService);
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(JWTService jwtService,
                                                           @Value("${security.jwtAuthenticationFilter.shouldNotFilter}")
                                                            Set<String> shouldNotFilterUris,
                                                           TokenBlacklistedService tokenBlacklistedService) {
        return new JWTAuthenticationFilter(jwtService, shouldNotFilterUris, tokenBlacklistedService);
    }

    @Bean
    public HistoryFilter historyFilter(HistoryRepository historyRepository,
                                        @Value("${security.historyFilter.shouldNotFilter}")
                                                 Set<String> shouldNotFilterUris) {
        return new HistoryFilter(new CustomTraceRepository(historyRepository), shouldNotFilterUris);
    }
}
