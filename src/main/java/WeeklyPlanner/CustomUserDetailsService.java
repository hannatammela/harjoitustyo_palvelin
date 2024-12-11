package WeeklyPlanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

// Vastaa käyttäjien lataamisesta tietokannasta autentikointia varten
// Käyttäjätiedot haetaan tietokannasta

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    public CustomUserDetailsService(AppUserRepository appUserRepository) {

        this.appUserRepository = appUserRepository;
    }

    // Hakee käyttäjänimen perusteella käyttäjän tiedot
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);

        if (appUser == null) {
            throw new UsernameNotFoundException("Käyttäjää" + username + "ei löydetty.");
        }
        return new User(appUser.getUsername(),
                        appUser.getPassword(),
                        true,
                true,
                true,
                true,
                List.of(new SimpleGrantedAuthority("USER"))
                );
    }
}
