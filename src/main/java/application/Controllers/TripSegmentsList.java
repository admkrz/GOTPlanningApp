package application.Controllers;

import java.util.List;

/**
 * Helper class to pass the segments list from the view to the controller
 */
public class TripSegmentsList {

    public TripSegmentsList(List<String> tripSegments) {
        this.tripSegments = tripSegments;
    }

    private List<String> tripSegments;

    public List<String> getTripSegments() {
        return tripSegments;
    }

    public void setTripSegments(List<String> tripSegments) {
        this.tripSegments = tripSegments;
    }
}
