package application.Controllers;

import application.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * The type Home controller.
 */
@Controller
public class HomeController {
    private TouristRepository touristRepository;

    /**
     * Instantiates a new Home controller.
     *
     * @param touristRepository the tourist repository
     */
    @Autowired
    public HomeController(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    /**
     * Shows tourist or worker main page depending on currently logged user.
     *
     * @param session the session
     * @param model   the model
     * @return main view for logged user.
     */
    @RequestMapping(value = "/")
    public String index(HttpSession session, ModelMap model) {
        var id = session.getAttribute("userId");
        if (id == null) {
            return "redirect:/login";
        } else {
            var tourist = touristRepository.findById((Integer) id);
            if (tourist.isPresent()) {
                model.addAttribute("tourist", tourist.get());
                var tour = tourist.get();
                if (tour.getBadges().size() > 0) {
                    var currBadge = tour.getBadges().get(tour.getBadges().size() - 1).getBadge();
                    model.addAttribute("badge", currBadge.getValue());
                    if (currBadge.getNext() != null) {
                        model.addAttribute("nextBadge", currBadge.getNext().getValue());
                    } else {
                        model.addAttribute("nextBadge", "Brak");
                    }
                    model.addAttribute("nextPoints", (int) Badge.howManyPointsToTheNextBadge(currBadge.getNext()));
                    model.addAttribute("nextPointsPercent", 100 * tour.getSumOfPoints() / Badge.howManyPointsToTheNextBadge(null));
                } else {
                    model.addAttribute("badge", "Brak");
                    model.addAttribute("nextBadge", BadgeDegree.Popular.getValue());
                    model.addAttribute("nextPoints", (int) Badge.howManyPointsToTheNextBadge(null));
                    model.addAttribute("nextPointsPercent", 100 * tour.getSumOfPoints() / Badge.howManyPointsToTheNextBadge(null));
                }
                var allTrips = tour.getAllTrips();
                if (allTrips.size() > 0) {
                    model.addAttribute("lastTrip", allTrips.get(allTrips.size() - 1).toString());
                } else {
                    model.addAttribute("lastTrip", "Brak");
                }
                if (model.get("savedTrip") == null) {
                    model.addAttribute("savedTrip", false);
                    model.addAttribute("saved", "");
                }
                return "index";
            } else {
                return "redirect:/worker";
            }
        }
    }
}
