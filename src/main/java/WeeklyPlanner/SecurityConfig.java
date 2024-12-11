package WeeklyPlanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


// Suojausasetukset.
// Määrittää kirjautumisen, uloskirjautumisen ja pääsyrajoitukset
@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {

        this.userDetailsService = userDetailsService;
    }

    // securityFilterChain on yhteensopiva Spring 5.7:ssä, joten
    // se on valittu tähän moocin materiaalin WebSecurityConfigurerAdapter sijaan
    // Käytössäni on Spring Boot 2.7.13 ja se Spring Security 5.7. joten tämä toteutettu
    // oli hyvä tapa saada Securitypuolikin toimimaan.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
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

    // Salasanojen salaamiseen käytetty enkooderi
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
