package application.Controllers;

import application.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * The type Pttk worker controller.
 */
@Controller
public class PTTKWorkerController {

    private PTTKWorkerRepository pttkWorkerRepository;
    private TripRepository tripRepository;

    /**
     * Instantiates a new Pttk worker controller.
     *
     * @param workerRepository the worker repository
     * @param tripRepository   the trip repository
     */
    @Autowired
    public PTTKWorkerController(PTTKWorkerRepository workerRepository, TripRepository tripRepository) {
        this.pttkWorkerRepository = workerRepository;
        this.tripRepository = tripRepository;
    }

    /**
     * Shows worker main view.
     *
     * @param session the session
     * @param model   the model
     * @return worker view
     */
    @RequestMapping(value = "/worker", method = RequestMethod.GET)
    public String showWorkerMainView(HttpSession session, Model model)
    {
        var workerOpt = pttkWorkerRepository.findById((int) session.getAttribute("userId"));
        if (workerOpt.isPresent()) {
            var worker = workerOpt.get();
            var tripsToVerify = tripRepository.findTripsByStatusEquals(TripStatus.AwaitingVerification);
            model.addAttribute("tripsToVerify", tripsToVerify);
            model.addAttribute("worker", worker);
            return "worker";
        } else {
            return "redirect:/login?error";
        }
    }

    /**
     * Selects trip.
     *
     * @param id      the id
     * @param session the session
     * @return redirects to worker view
     */
    @RequestMapping(value = "/worker/tripverify/{id}", method = RequestMethod.GET)
    public String selectTrip(@PathVariable(value = "id") String id, HttpSession session)
    {
        try{
            int ident = Integer.parseInt(id);
            session.setAttribute("tripId", ident);
        }catch(NumberFormatException ignored){
            return "redirect:/worker";
        }
        return "redirect:/worker/tripverify";
    }

    /**
     * Shows picked trip for worker.
     *
     * @param session the session
     * @param model   the model
     * @return trip verification view
     */
    @RequestMapping(value = "/worker/tripverify", method = RequestMethod.GET)
    public String showTripForWorker(HttpSession session, Model model)
    {
        Trip trip = TripUtils.getCurrentTrip(session, tripRepository);
        model.addAttribute("trip", trip);
        return "workerTripVerify";
    }

    /**
     * Saves verified trip.
     *
     * @param ids     the ids
     * @param session the session
     * @param model   the model
     * @return redirects to worker view
     */
    @RequestMapping(value = {"/worker/tripverify/save/{ids}", "/worker/tripverify/save"}, method = RequestMethod.GET)
    public String saveVerified(@PathVariable(value = "ids", required = false) String ids, HttpSession session, Model model)
    {
        var idOpt = session.getAttribute("tripId");
        try{
            var id = (Integer)idOpt;
            var tripOpt = tripRepository.findById(id);
            if(tripOpt.isPresent()) {
                var trip = tripOpt.get();
                if(ids != null) {
                    var idss = ids.split(",");
                    for (var segm : trip.getSegments()) {
                        if (Arrays.stream(idss).noneMatch(ident -> ident.equals(String.valueOf(segm.getId())))) {
                            segm.setPoints(0);
                        }
                    }
                }
                trip.setStatus(TripStatus.Done);
                addPointsToMembers(trip);
                tripRepository.save(trip);
                model.addAttribute("tripVerified", true);
                return showTripForWorker(session, model);
            }
        }catch(NumberFormatException ignored){
            return "redirect:/worker";
        }
        return "redirect:/worker/tripverify";
    }

    /**
     * Adds points to users accounts
     * @param trip
     */
    private void addPointsToMembers(Trip trip)
    {
        var tripMembers = new ArrayList<Tourist>();
        tripMembers.add(trip.getCreator());
        tripMembers.addAll(trip.getMembers());
        int tripSumOfPoints = trip.getSegments().stream().mapToInt(TripSegment::getPoints).sum();
        for(var member: tripMembers){
            int memberSumOfPoints = member.getSumOfPoints();
            memberSumOfPoints += tripSumOfPoints;
            var memberBadges = member.getBadges();
            float pointsToTheNextBadge = Badge.howManyPointsToTheNextBadge(null);
            BadgeDegree nextBadge;
            if(memberBadges.size()>0){
                var badge = memberBadges.get(memberBadges.size()-1);
                pointsToTheNextBadge = Badge.howManyPointsToTheNextBadge(badge.getBadge());
                nextBadge = badge.getBadge().getNext();
            }else{
                nextBadge = BadgeDegree.Popular;
            }
            if(memberSumOfPoints >= pointsToTheNextBadge){
                memberBadges.add(new Badge(member, nextBadge, LocalDate.now()));
                memberSumOfPoints -= pointsToTheNextBadge;
                if(memberSumOfPoints > 0.5*Badge.howManyPointsToTheNextBadge(nextBadge)){
                    memberSumOfPoints = (int)(0.5*Badge.howManyPointsToTheNextBadge(nextBadge));
                }
            }
            member.setSumOfPoints(memberSumOfPoints);
        }
    }

}
