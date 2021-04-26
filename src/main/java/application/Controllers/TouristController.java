package application.Controllers;

import application.Models.TouristRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

/**
 * The type Tourist controller.
 */
@Controller
public class TouristController {

    private TouristRepository touristRepository;

    /**
     * Instantiates a new Tourist controller.
     *
     * @param touristRepository the tourist repository
     */
    @Autowired
    public TouristController(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    /**
     * Shows all tourist trips.
     *
     * @param session the session
     * @param model   the model
     * @return trips view
     */
    @RequestMapping(value = "/user/trips", method = RequestMethod.GET)
    public String showTouristTrips(HttpSession session, Model model)
    {
        var tourist = touristRepository.findById((Integer)session.getAttribute("userId"));
        if (tourist.isPresent()) {
            var trips = tourist.get().getPlannedTrips();
            trips.addAll(tourist.get().getTripsMember());
            if (trips.size() == 0) {
                //model.addAttribute("noTrips");
                return "redirect:/?noTrips";
            } else {
                model.addAttribute("allMyTrips", trips);
                return "trips";
            }
        } else {
            return "redirect:/login?error";
        }
    }

    /**
     * Selects trip.
     *
     * @param id      the id
     * @param session the session
     * @return redirects to single trip view
     */
    @RequestMapping(value = "/user/tripSelect/{id}", method = RequestMethod.GET)
    public String selectTrip(@PathVariable(value = "id") String id, HttpSession session)
    {
        try{
            int ident = Integer.parseInt(id);
            session.setAttribute("tripId", ident);
        }catch(NumberFormatException ignored){
            return "redirect:/user/trips";
        }
        return "redirect:/user/trip";
    }

}
