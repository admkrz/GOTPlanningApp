package application.Controllers;

import application.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * The type Plan Trip controller.
 */
@Controller
@RequestMapping("/planTrip")
public class PlanTripController {
    private RouteRepository routeRepository;
    private TripRepository tripRepository;
    private TripSegmentRepository tripSegmentRepository;
    private TouristRepository touristRepository;
    private PointRepository pointRepository;
    private MountainGroupRepository mountainGroupRepository;

    @Autowired
    public PlanTripController(RouteRepository routeRepository, TripRepository tripRepository,
                              TripSegmentRepository tripSegmentRepository, TouristRepository touristRepository,
                              PointRepository pointRepository, MountainGroupRepository mountainGroupRepository) {
        this.routeRepository = routeRepository;
        this.tripRepository = tripRepository;
        this.tripSegmentRepository = tripSegmentRepository;
        this.touristRepository = touristRepository;
        this.pointRepository = pointRepository;
        this.mountainGroupRepository = mountainGroupRepository;
    }

    /**
     * Shows current planned trip with all segments
     *
     * @param model     the model
     * @param session   the session
     * @return  planTrip view
     */
    @RequestMapping
    public String planTrip(Model model, HttpSession session) {
        if (session.getAttribute("userId") != null) {
            TripSegmentsList tripSegmentsList = (TripSegmentsList) model.getAttribute("tripSegments");
            tripSegmentsList = (tripSegmentsList != null) ? tripSegmentsList : new TripSegmentsList(new ArrayList<>());
            List<Route> possibleSegments = new ArrayList<>();
            List<Route> possibleSegmentsReverse = new ArrayList<>();
            int points = 0;
            double length = 0;
            int height = 0;
            Point lastPoint = null;

            if (tripSegmentsList.getTripSegments().size() == 0) {
                for (var route : routeRepository.findAll()) {
                    if (route.getDisablementTime() == null && !route.isUserOwnRoute()) {
                        possibleSegments.add(route);
                        possibleSegmentsReverse.add(route);
                    }
                }
            } else {
                TripSegment lastSegment = tripSegmentRepository.findById(Integer.valueOf(tripSegmentsList.getTripSegments()
                                                        .get(tripSegmentsList.getTripSegments().size() - 1))).get();
                lastPoint = (lastSegment.getDirection()) ? lastSegment.getRoute().getEndingPoint() : lastSegment.getRoute().getStartingPoint();
                for (var route : routeRepository.findAll()) {
                    if (route.getDisablementTime() == null && !route.isUserOwnRoute()) {
                        if (route.getStartingPoint().getLocation().equals(lastPoint.getLocation()))
                            possibleSegments.add(route);
                        if (route.getEndingPoint().getLocation().equals(lastPoint.getLocation()))
                            possibleSegmentsReverse.add(route);
                    }
                }
            }

            List<TripSegment> tripSegments = new ArrayList<>();
            for (var id : tripSegmentsList.getTripSegments()) {
                TripSegment segment = tripSegmentRepository.findById(Integer.valueOf(id)).get();
                tripSegments.add(segment);
                points += segment.getPoints();
                length += segment.getLength();
                height += segment.getHeight();
            }

            Iterable<MountainGroup> mountainGroups = mountainGroupRepository.findAll();

            model.addAttribute("possibleSegments", possibleSegments);
            model.addAttribute("possibleSegmentsReverse", possibleSegmentsReverse);
            model.addAttribute("tripSegmentsObjects", tripSegments);
            model.addAttribute("tripSegmentsList", tripSegmentsList);
            model.addAttribute("points", points);
            model.addAttribute("length", Math.round(length * 100) / 100.0);
            model.addAttribute("height", height);
            model.addAttribute("lastPoint", (lastPoint != null) ? lastPoint.getLocation() : "");
            model.addAttribute("mountainGroups", mountainGroups);
            model.addAttribute("ownSegmentForm", new OwnSegmentForm());
            if (model.getAttribute("removed") == null) {
                model.addAttribute("removed", false);
                model.addAttribute("removedText", "");
            }
            return "planTrip";
        } else
            return "redirect:/login?error";
    }

    /**
     * Adds new user defined segment to the current trip
     *
     * @param ownSegmentForm    the form from the view
     * @param errors            the form errors
     * @param redirectAttributes    the redirect attributes
     * @return  redirect to planTrip view
     */
    @RequestMapping(params = "addOwnSegment")
    public RedirectView addOwnSegment(@Valid OwnSegmentForm ownSegmentForm,
                                      Errors errors, RedirectAttributes redirectAttributes) {
        if (null != errors && errors.getErrorCount() > 0) {
            System.out.println("Data incorrect");
            return new RedirectView("/planTrip");
        } else {
            System.out.println("Data correct!");
            System.out.println(ownSegmentForm);
            Point firstPoint;
            if (ownSegmentForm.getFirstPoint() != null) {
                var firstPointOption = pointRepository.findById(ownSegmentForm.getFirstPoint());
                if (!firstPointOption.isPresent()) {
                    firstPoint = new Point(ownSegmentForm.getFirstPoint(), PointStatus.Bare);
                    pointRepository.save(firstPoint);
                } else {
                    firstPoint = firstPointOption.get();
                }
            } else {
                TripSegment previousTripSegment = tripSegmentRepository.findById(Integer.valueOf(ownSegmentForm.getTripSegmentsList().get(ownSegmentForm.getTripSegmentsList().size() - 1))).get();
                firstPoint = previousTripSegment.getDirection() ? previousTripSegment.getRoute().getEndingPoint() : previousTripSegment.getRoute().getStartingPoint();
            }
            var lastPointOption = pointRepository.findById(ownSegmentForm.getEndPoint());
            Point lastPoint;
            if (!lastPointOption.isPresent()) {
                lastPoint = new Point(ownSegmentForm.getEndPoint(), PointStatus.Bare);
                pointRepository.save(lastPoint);
            } else {
                lastPoint = lastPointOption.get();
            }
            MountainGroup mountainGroup = mountainGroupRepository.findById(ownSegmentForm.getMountainGroup()).get();
            Integer points = Math.toIntExact((int) Math.round(ownSegmentForm.getLength()) + (Math.round(Double.valueOf(ownSegmentForm.getHeight()) / 100.0) * 100) / 100);
            Route ownRoute = new Route(ownSegmentForm.getLength(), Double.valueOf(ownSegmentForm.getHeight()), null, ownSegmentForm.getColor(), null, points, mountainGroup, firstPoint, lastPoint, true);
            TripSegment ownSegment = new TripSegment(true, ownRoute.getPoints(), ownRoute.getLength(), ownRoute.getHeight(), ownRoute, null);
            routeRepository.save(ownRoute);
            tripSegmentRepository.save(ownSegment);
            ownSegmentForm.getTripSegmentsList().add(ownSegment.getId().toString());
            redirectAttributes.addFlashAttribute("tripSegments", new TripSegmentsList(ownSegmentForm.getTripSegmentsList()));
            return new RedirectView("/planTrip");
        }
    }

    /**
     * Adds new segment to the current trip
     *
     * @param tripSegmentsList  the current trip segments list
     * @param newSegmentId      the id of the segment to add
     * @param direction         the direction of the segment to add
     * @param redirectAttributes    the redirect attributes
     * @return  redirect to planTrip view
     */
    @RequestMapping(params = "add")
    public RedirectView addSegment(@ModelAttribute("tripSegmentsList") TripSegmentsList tripSegmentsList,
                                   @RequestParam(value = "newSegmentId") Integer newSegmentId,
                                   @RequestParam(value = "direction") boolean direction, RedirectAttributes redirectAttributes) {
        List<Integer> tripSegmentsIds = tripSegmentsList.getTripSegments().stream().map(Integer::valueOf).collect(Collectors.toList());
        boolean repeatedRoute = false;
        for (Integer id : tripSegmentsIds) {
            TripSegment tripSegment = tripSegmentRepository.findById(id).get();
            if (tripSegment.getRoute().getId() == newSegmentId && tripSegment.getDirection() == direction) {
                repeatedRoute = true;
                break;
            }
        }
        Route usedRoute = routeRepository.findById(newSegmentId).get();
        Integer points = 0;
        if (!repeatedRoute)
            points = (direction) ? usedRoute.getPoints() : usedRoute.getPointsRev();

        TripSegment newSegment = new TripSegment(direction, points,
                usedRoute.getLength(), (direction) ? usedRoute.getHeight() : usedRoute.getHeightRev(), usedRoute, null);
        tripSegmentRepository.save(newSegment);
        tripSegmentsList.getTripSegments().add(newSegment.getId().toString());
        redirectAttributes.addFlashAttribute("tripSegments", tripSegmentsList);
        return new RedirectView("/planTrip");
    }


    /**
     * Removes the last segment from the current trip
     *
     * @param tripSegmentsList      the current trip segments list
     * @param redirectAttributes    the redirect attributes
     * @return  redirect to planTrip view
     */
    @RequestMapping(params = "remove")
    public RedirectView removeLastSegment(@ModelAttribute("tripSegmentsList") TripSegmentsList tripSegmentsList, RedirectAttributes redirectAttributes) {
        if (tripSegmentsList.getTripSegments().size() > 0) {
            Integer idToRemove = Integer.valueOf(tripSegmentsList.getTripSegments().get(tripSegmentsList.getTripSegments().size() - 1));
            var segmentOption = tripSegmentRepository.findById(idToRemove);
            if ((segmentOption).isPresent()) {
                TripSegment segment = segmentOption.get();
                String firstPoint = segment.getDirection() ? segment.getRoute().getStartingPoint().getLocation() : segment.getRoute().getEndingPoint().getLocation();
                String lastPoint = segment.getDirection() ? segment.getRoute().getEndingPoint().getLocation() : segment.getRoute().getStartingPoint().getLocation();
                tripSegmentRepository.delete(segment);
                tripSegmentsList.getTripSegments().remove(tripSegmentsList.getTripSegments().size() - 1);
                redirectAttributes.addFlashAttribute("removed", true);
                redirectAttributes.addFlashAttribute("removedText", "Usunięto odcinek " + firstPoint + " - " + lastPoint);
            } else {
                tripSegmentsList.getTripSegments().remove(tripSegmentsList.getTripSegments().size() - 1);
                redirectAttributes.addFlashAttribute("removed", true);
                redirectAttributes.addFlashAttribute("removedText", "Odcinek został usunięty już wcześniej");
            }
            redirectAttributes.addFlashAttribute("tripSegments", tripSegmentsList);
        }
        return new RedirectView("/planTrip");
    }

    /**
     * Saves the current trip to the repository
     *
     * @param tripSegmentsList      the current trip segments list
     * @param session               the session
     * @param redirectAttributes    the redirect attributes
     * @return  redirect to planTrip view
     */
    @RequestMapping(params = "save")
    public RedirectView saveTrip(@ModelAttribute("tripSegmentsList") TripSegmentsList tripSegmentsList, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("userId") != null) {
            var tourist = touristRepository.findById((Integer) session.getAttribute("userId"));
            if (tourist.isPresent()) {
                Trip newTrip = new Trip(0., 0., 0, LocalDate.now().plusDays(1), LocalDate.now().plusDays(1), TripStatus.Planned, tourist.get(),
                        new ArrayList<>(), new ArrayList<>());
                double length = 0;
                double height = 0;
                int points = 0;

                TripSegment firstSegment = tripSegmentRepository.findById(Integer.valueOf(tripSegmentsList.getTripSegments().get(0))).get();
                TripSegment lastSegment = tripSegmentRepository.findById(Integer.valueOf(tripSegmentsList.getTripSegments().get(tripSegmentsList.getTripSegments().size() - 1))).get();
                String firstPoint = firstSegment.getDirection() ? firstSegment.getRoute().getStartingPoint().getLocation() : firstSegment.getRoute().getEndingPoint().getLocation();
                String lastPoint = lastSegment.getDirection() ? lastSegment.getRoute().getEndingPoint().getLocation() : lastSegment.getRoute().getStartingPoint().getLocation();

                for (var id : tripSegmentsList.getTripSegments()) {
                    TripSegment currentSegment = tripSegmentRepository.findById(Integer.valueOf(id)).get();
                    length += currentSegment.getLength();
                    height += currentSegment.getHeight();
                    points += currentSegment.getPoints();
                    currentSegment.setTrip(newTrip);
                }
                newTrip.setLength(length);
                newTrip.setSumOfHeights(height);
                newTrip.setSumOfPoints(points);
                tripRepository.save(newTrip);
                redirectAttributes.addFlashAttribute("savedTrip", true);
                redirectAttributes.addFlashAttribute("saved", "Zapisano trasę " + firstPoint + " - " + lastPoint);
                return new RedirectView("/");
            } else
                return new RedirectView("/login?error");
        } else
            return new RedirectView("/login?error");
    }
}


/**
 * Helper class with validated form for user defined trip segments
 */
class OwnSegmentForm {
    @NotNull
    private List<String> tripSegmentsList;

    @NotNull
    private String firstPoint;

    @NotNull
    private String endPoint;

    @NotNull
    private String color;

    @NotNull
    private String mountainGroup;

    @NotNull
    @Min(0)
    private Double length;

    @NotNull
    @Min(0)
    private Integer height;

    public List<String> getTripSegmentsList() {
        return tripSegmentsList;
    }

    public void setTripSegmentsList(List<String> tripSegmentsList) {
        this.tripSegmentsList = tripSegmentsList;
    }

    public String getFirstPoint() {
        return firstPoint;
    }

    public void setFirstPoint(String firstPoint) {
        this.firstPoint = firstPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMountainGroup() {
        return mountainGroup;
    }

    public void setMountainGroup(String mountainGroup) {
        this.mountainGroup = mountainGroup;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "OwnSegmentForm{" +
                "tripSegmentsList=" + tripSegmentsList +
                ", firstPoint='" + firstPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", color='" + color + '\'' +
                ", mountainGroup='" + mountainGroup + '\'' +
                ", length=" + length +
                ", height=" + height +
                '}';
    }
}
