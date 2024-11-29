package WeeklyPlanner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Controller aloitussivulle
@Controller
public class HomeController {
    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }
}
