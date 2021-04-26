package application.Models;

import javax.persistence.*;
import java.util.Objects;

/**
 * The type Trip segment.
 */
@Entity(name = "trip_segments")
@Table(name = "trip_segments")
public class TripSegment
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Boolean direction;
    private Integer points;
    private Double length;
    private Double height;

    @ManyToOne
    @JoinColumn
    private Route route;

    @ManyToOne
    @JoinColumn
    private Trip trip;

    /**
     * Instantiates a new Trip segment.
     */
    public TripSegment() {
    }

    /**
     * Instantiates a new Trip segment.
     *
     * @param direction the direction
     * @param points    the points
     * @param length    the length
     * @param height    the height
     * @param route     the route
     * @param trip      the trip
     */
    public TripSegment(Boolean direction, Integer points, Double length, Double height, Route route, Trip trip) {
        this.direction = direction;
        this.points = points;
        this.length = length;
        this.height = height;
        this.route = route;
        this.trip = trip;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets direction.
     *
     * @return the direction
     */
    public Boolean getDirection() {
        return direction;
    }

    /**
     * Sets direction.
     *
     * @param direction the direction
     */
    public void setDirection(Boolean direction) {
        this.direction = direction;
    }

    /**
     * Gets points.
     *
     * @return the points
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * Sets points.
     *
     * @param points the points
     */
    public void setPoints(Integer points) {
        this.points = points;
    }

    /**
     * Gets length.
     *
     * @return the length
     */
    public Double getLength() {
        return length;
    }

    /**
     * Sets length.
     *
     * @param length the length
     */
    public void setLength(Double length) {
        this.length = length;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public Double getHeight() {
        return height;
    }

    /**
     * Sets height.
     *
     * @param height the height
     */
    public void setHeight(Double height) {
        this.height = height;
    }

    /**
     * Gets route.
     *
     * @return the route
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Sets route.
     *
     * @param route the route
     */
    public void setRoute(Route route) {
        this.route = route;
    }

    /**
     * Gets trip.
     *
     * @return the trip
     */
    public Trip getTrip() {
        return trip;
    }

    /**
     * Sets trip.
     *
     * @param trip the trip
     */
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    /**
     * Equals boolean.
     *
     * @param o the o
     * @return the boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TripSegment)) return false;
        TripSegment that = (TripSegment) o;
        return Objects.equals(getDirection(), that.getDirection())
                && Objects.equals(getPoints(), that.getPoints())
                && Objects.equals(getLength(), that.getLength())
                && Objects.equals(getHeight(), that.getHeight())
                && Objects.equals(getRoute(), that.getRoute())
                && Objects.equals(getTrip(), that.getTrip());
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(getDirection(), getPoints(), getLength(), getHeight(), getRoute(), getTrip());
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return route.getStartingPoint().getLocation() + " - " + route.getEndingPoint().getLocation();
    }
}
