package com.kyc_ledger.demo.config;
import com.kyc_ledger.demo.repository.UserRepository;
import com.kyc_ledger.demo.security.UserDetailsServiceImpl;
import com.kyc_ledger.demo.security.JwtTokenProvider;
import com.kyc_ledger.demo.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfig implements UserDetailsServiceImpl {

    private final UserRepository userRepository;


}
