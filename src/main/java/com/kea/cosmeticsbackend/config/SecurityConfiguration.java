package com.kea.cosmeticsbackend.config;

import com.kea.cosmeticsbackend.JwtAuthenticationEntryPoint;
import com.kea.cosmeticsbackend.JwtFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class SecurityConfiguration implements WebMvcConfigurer {
    private JwtAuthenticationEntryPoint authenticationEntryPoint;
    private JwtFilter filter;
    private static PasswordEncoder passwordEncoder;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        if (passwordEncoder == null) {
            passwordEncoder = new BCryptPasswordEncoder();
        }
        return passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests(requests -> requests
                        .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()  // Allow all requests to /login
                        .requestMatchers(new AntPathRequestMatcher("/signup")).permitAll()  // Allow all requests to /signup
                        .requestMatchers(new AntPathRequestMatcher("/booking")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/booking/create")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/treatment")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/treatment")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/user/list/*")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/customer")).permitAll()
                       // .requestMatchers(new AntPathRequestMatcher("/api/quote**")).permitAll()
                       // .requestMatchers(new AntPathRequestMatcher("/api/quote/*")).permitAll()
                       // .requestMatchers(new AntPathRequestMatcher("/api/quote")).permitAll()
                       // .requestMatchers(new AntPathRequestMatcher("/api/subgenre**")).permitAll()
                       // .requestMatchers(new AntPathRequestMatcher("/api/subgenre/*")).permitAll()
                       // .requestMatchers(new AntPathRequestMatcher("/api/subgenre")).permitAll()


                        .anyRequest().authenticated()
                )
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                .allowCredentials(true);
    }
}