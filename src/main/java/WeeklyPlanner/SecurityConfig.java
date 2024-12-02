package WeeklyPlanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // final -> Vältetään silmukkavirhe
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/h2-console/**").permitAll() // H2-tietokanta sallittu
                        .anyRequest().authenticated() // Kaikki polut suojataan
                )
                .formLogin(form -> form
                .defaultSuccessUrl("/", true) // Onnistunut kirjautuminen -> siirrytään index.html
                .permitAll()
        )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login") // Uloskirjautuminen -> takaisin kirjautumissivulle
                        .invalidateHttpSession(true) // Suljetaan istunto
                        .deleteCookies("JSESSIONID") // Poistetaan evästeet
                        .permitAll()
                )
                .csrf().disable() // H2 Console tarvitsee CSRF:n poiston
                .headers().frameOptions().disable(); // H2 Console käyttämät kehykset

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public AuthenticationManagerBuilder authenticationManagerBuilder(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return auth;
    }
}
