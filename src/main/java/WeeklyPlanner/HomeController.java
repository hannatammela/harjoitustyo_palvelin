package WeeklyPlanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.util.List;

// Controller aloitussivulle..
@Controller
public class HomeController {
    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppUserRepository appUserRepository;

    // Alustetaan kolme käyttäjää tietokantaan
    // ohjelman käynnistyessä
    @PostConstruct
    public void createDefaultUsers() {
        if (appUserRepository.count() == 0) {
            List<AppUser> defaultUsers = List.of(
                    new AppUser("maverick", passwordEncoder.encode("darkstar")),
                    new AppUser("cooper", passwordEncoder.encode("tars1")),
                    new AppUser("drhouse", passwordEncoder.encode("cane44"))
            );
            appUserRepository.saveAll(defaultUsers);
        }
    }
}
