package application.Controllers;

import application.Models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * The type Login controller.
 */
@Controller
public class LoginController {

    private UserRepository userRepository;

    /**
     * Instantiates a new Login controller.
     *
     * @param userRepository the user repository
     */
    @Autowired
    public LoginController(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    /**
     * Shows login page.
     *
     * @return login view
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage()
    {
        return "login";
    }

    /**
     * Authenticates user.
     *
     * @param email   the email
     * @param passw   the password
     * @param session the session
     * @return redirects to user view
     */
    @RequestMapping(value = "/loginUser", method = RequestMethod.POST)
    public String login(@RequestParam String email, @RequestParam String passw, HttpSession session)
    {
        //todo: in future encode password
        var userOptional = userRepository.findByEmailAndPassw(email, passw);
        if(userOptional.isPresent()){
            var user = userOptional.get();
            session.setAttribute("userId", user.getId());
            return "redirect:/";
        }else{
            return "redirect:/login?error";
        }
    }

    /**
     * Invalidates session data. Logs user out.
     *
     * @param session the session
     * @return redirects to login view.
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session)
    {
        session.invalidate();
        return "redirect:/login";
    }

}
