package application.Controllers;

import application.Models.Trip;
import application.Models.TripRepository;
import application.Models.TripStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * The type Trips controller.
 */
@Controller
public class TripsController {

    private TripRepository tripRepository;

    /**
     * Instantiates a new Trips controller.
     *
     * @param tripRepository the trip repository
     */
    @Autowired
    public TripsController(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    /**
     * Shows trip.
     *
     * @param session the session
     * @param model   the model
     * @return singleTrip view
     */
    @RequestMapping(value = "/user/trip", method = RequestMethod.GET)
    public String showTrip(HttpSession session, Model model)
    {
        Trip trip = TripUtils.getCurrentTrip(session, tripRepository);
        model.addAttribute("trip", trip);
        return "singleTrip";
    }

    /**
     * Ends trip.
     *
     * @param session the session
     * @param model   the model
     * @return redirects to /user/trip
     */
    @RequestMapping(value = "/user/trip/end", method = RequestMethod.GET)
    public String endTrip(HttpSession session, Model model)
    {
        session.setAttribute("endClicked", true);
        return "redirect:/user/trip";
    }

    /**
     * Sends trip to verification.
     *
     * @param id      the id
     * @param session the session
     * @param model   the model
     * @return redirects to /user/trips
     */
    @RequestMapping(value = "/user/trip/toverify/{id}", method = RequestMethod.GET)
    public String sendTripToVerification(@PathVariable(value = "id") Integer id, HttpSession session, Model model)
    {
        tripRepository.updateStatus(id, TripStatus.AwaitingVerification);
        return "redirect:/user/trips";
    }

    /**
     * Changes trip status to NotDone.
     *
     * @param id      the id
     * @param session the session
     * @param model   the model
     * @return singleTrip view
     */
    @RequestMapping(value = "/user/trip/cancel/{id}", method = RequestMethod.GET)
    public String cancelTrip(@PathVariable(value = "id") Integer id, HttpSession session, Model model)
    {
        tripRepository.updateStatus(id, TripStatus.NotDone);
        model.addAttribute("tripCanceled", true);
        return showTrip(session, model);
    }

}
