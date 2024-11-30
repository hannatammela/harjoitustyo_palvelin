package WeeklyPlanner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll() // Sallitaan pääsy kaikille sivustoille
                )
                .csrf().disable() // H2 Console tarvitsee CSRF:n poiston
                .headers().frameOptions().disable(); // Sallitaan kehykset (frames), joita H2 Console käyttää.
        return http.build();
    }
}
