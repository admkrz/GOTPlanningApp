package application.Controllers;

import application.Models.MountainGroupRepository;
import application.Models.Route;
import application.Models.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The type Route controller.
 */
@Controller
public class RouteController {
    private RouteRepository routeRepository;
    private MountainGroupRepository mountainGroupRepository;

    @Autowired
    public RouteController(RouteRepository routeRepository, MountainGroupRepository mountainGroupRepository) {
        this.routeRepository = routeRepository;
        this.mountainGroupRepository = mountainGroupRepository;
    }

    /**
     * Shows selected routes, fulfilling the criteria
     *
     * @param mountainGroup the mountain group of the routes
     * @param minPoints     the minimum points of the routes
     * @param maxPoints     the maximum points of the routes
     * @param minLength     the minimum length of the routes
     * @param maxLength     the maximum length of the routes
     * @param minHeight     the minimum height of the routes
     * @param maxHeight     the maximum height of the routes
     * @param disabled      the disablement state of the routes
     * @param model         the model
     * @return      routes view
     */
    @RequestMapping(value = "/routes", method = RequestMethod.GET)
    public String showRoutes(@RequestParam(value = "mountainGroup", required = false) String mountainGroup,
                             @RequestParam(value = "minPoints", required = false) Integer minPoints,
                             @RequestParam(value = "maxPoints", required = false) Integer maxPoints,
                             @RequestParam(value = "minLength", required = false) Double minLength,
                             @RequestParam(value = "maxLength", required = false) Double maxLength,
                             @RequestParam(value = "minHeight", required = false) Integer minHeight,
                             @RequestParam(value = "maxHeight", required = false) Integer maxHeight,
                             @RequestParam(value = "disabled", required = false) boolean disabled, Model model) {

        minPoints = (minPoints != null) ? minPoints : 0;
        maxPoints = (maxPoints != null) ? maxPoints : 20;
        minLength = (minLength != null) ? minLength : 0;
        maxLength = (maxLength != null) ? maxLength : 20;
        minHeight = (minHeight != null) ? minHeight : 0;
        maxHeight = (maxHeight != null) ? maxHeight : 1000;
        mountainGroup = (mountainGroup != null) ? mountainGroup : "empty";

        model.addAttribute("selectedMountainGroup", mountainGroup);
        model.addAttribute("minPoints", minPoints);
        model.addAttribute("maxPoints", maxPoints);
        model.addAttribute("minLength", minLength);
        model.addAttribute("maxLength", maxLength);
        model.addAttribute("minHeight", minHeight);
        model.addAttribute("maxHeight", maxHeight);
        model.addAttribute("mountainGroups", mountainGroupRepository.findAll());

        List<Route> selectedRoutes = getRoutes(disabled, mountainGroup, minPoints, maxPoints, minLength, maxLength, minHeight, maxHeight);
        model.addAttribute("routes", selectedRoutes);
        model.addAttribute("disabled", disabled);
        model.addAttribute("routesNotFound", selectedRoutes.size() <= 0);
        return "routes";
    }


    /**
     * Returns list of routes fulfilling the criteria
     *
     * @param disabled      the disablement state of the routes
     * @param mountainGroup the chosen mountain of the routes
     * @param minPoints     the minimum points of the routes
     * @param maxPoints     the maximum points of the routes
     * @param minLength     the minimum length of the routes
     * @param maxLength     the maximum length of the routes
     * @param minHeight     the minimum height of the routes
     * @param maxHeight     the maximum height of the routes
     * @return  the list of routes fulfilling the criteria
     */
    private List<Route> getRoutes(boolean disabled, String mountainGroup, int minPoints, int maxPoints,
                                  double minLength, double maxLength, int minHeight, int maxHeight) {
        List<Route> selectedRoutes = new ArrayList<>();
        for (var route : routeRepository.findAll())
            if (!disabled && route.getDisablementTime() == null && !route.isUserOwnRoute())
                selectedRoutes.add(route);
            else if (disabled && route.getDisablementTime() != null && !route.isUserOwnRoute())
                selectedRoutes.add(route);

        Predicate<Route> mountainGroupPredicate;
        if (!mountainGroup.equals("empty"))
            mountainGroupPredicate = r -> r.getGroup().getCode().equals(mountainGroup);
        else
            mountainGroupPredicate = r -> true;
        Predicate<Route> pointsPredicate = r -> r.getPoints() >= minPoints && r.getPoints() <= maxPoints;
        Predicate<Route> lengthPredicate = r -> r.getLength() >= minLength && r.getLength() <= maxLength;
        Predicate<Route> heightPredicate = r -> r.getHeight() >= minHeight && r.getHeight() <= maxHeight;
        selectedRoutes = selectedRoutes.stream().filter(mountainGroupPredicate.and(pointsPredicate.and(lengthPredicate.and(heightPredicate))))
                                                .collect(Collectors.toList());
        return selectedRoutes.stream().sorted((r1,r2) -> r1.getGroup().getCode().compareTo(r2.getGroup().getCode())).collect(Collectors.toList());
    }
}
