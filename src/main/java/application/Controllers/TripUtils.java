package application.Controllers;

import application.Models.Trip;
import application.Models.TripRepository;

import javax.servlet.http.HttpSession;

/**
 * The type Trip utils.
 */
public class TripUtils {

    /**
     * Gets trip related with picked tripId in current session.
     *
     * @param session        the session
     * @param tripRepository the trip repository
     * @return the current trip
     */
    public static Trip getCurrentTrip(HttpSession session, TripRepository tripRepository)
    {
        Trip trip = null;
        var tripIdOpt = session.getAttribute("tripId");
        if(tripIdOpt != null){
            var tripOpt = tripRepository.findById((Integer)tripIdOpt);
            if(tripOpt.isPresent()){
                trip = tripOpt.get();
            }
        }
        return trip;
    }
}
