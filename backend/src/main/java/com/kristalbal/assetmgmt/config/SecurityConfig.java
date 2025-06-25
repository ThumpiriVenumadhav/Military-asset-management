package com.kristalbal.assetmgmt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kristalbal.assetmgmt.security.JwtAuthFilter;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
    	.cors(Customizer.withDefaults())
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/users/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/assets").hasAnyRole("ADMIN", "LOGISTICS", "COMMANDER")
            .requestMatchers("/api/assets/**").hasAnyRole("ADMIN","LOGISTICS")
            .requestMatchers("/api/assignments/**").hasAnyRole("ADMIN","COMMANDER")
            .requestMatchers("/api/purchases/**").hasAnyRole("ADMIN","LOGISTICS")
            .requestMatchers(HttpMethod.POST, "/api/purchases/**").hasAnyRole("ADMIN","LOGISTICS")
            .requestMatchers(HttpMethod.POST, "/api/transfers").hasAnyRole("ADMIN", "LOGISTICS")
            .requestMatchers("/api/transfers/**").hasAnyRole("ADMIN","COMMANDER", "LOGISTICS")
            .requestMatchers("/api/expenditures/**").hasRole("ADMIN")
            
            .requestMatchers(HttpMethod.GET, "/api/assignments").hasAnyRole("ADMIN", "LOGISTICS", "COMMANDER")
            .requestMatchers(HttpMethod.POST, "/api/assignments").hasAnyRole("ADMIN", "LOGISTICS", "COMMANDER")

            .requestMatchers("/api/dashboard/**").authenticated()
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();

    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> null; // disables Spring's default login
    }
    
    
@Bean
public WebMvcConfigurer corsConfigurer() {
	return new WebMvcConfigurer() {
           @Override
           public void addCorsMappings(CorsRegistry registry) {
               registry.addMapping("/**")
                    .allowedOrigins("http://localhost:5173")
                    .allowedMethods("*")
                    .allowedHeaders("*")
                    .allowCredentials(true); // Optional if you want cookies in future
            }
        };
    }

    
}
