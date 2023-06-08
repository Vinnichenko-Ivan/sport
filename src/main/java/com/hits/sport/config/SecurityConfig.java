package com.hits.sport.config;

import com.hits.sport.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.hits.sport.utils.Path.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    public static final String X_TOKEN_FOR_API = "TOP_SECRET_API_KEY";
    private final JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .csrf().disable()
                .cors().configurationSource(corsConfiguration())
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .securityContext()
                .and()
                .authorizeHttpRequests(
                        authz -> {
                                authz
                                    .antMatchers("/swagger-ui/**").permitAll()
                                    .antMatchers("/swagger-resources/**").permitAll()
                                    .antMatchers("/v2/api-docs").permitAll()
                                    .antMatchers(USER_REGISTER).permitAll()
                                    .antMatchers(USER_SING_IN).permitAll()
                                    .antMatchers(USER_RESTORE_TOKEN).permitAll()
                                    .antMatchers(USER_PASSWORD).permitAll()
                                    .antMatchers(USER_PASSWORD_RESTORE).permitAll()
                                    .antMatchers(USER_EMAIL_CONFIRM).permitAll()
                                    .antMatchers("/user/confirm-all/").permitAll()
                                    .anyRequest().authenticated()
                                    .and()
                                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
                        }

                )
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfiguration() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.applyPermitDefaultValues();
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedOrigin("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }
}
