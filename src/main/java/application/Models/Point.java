package application.Models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Point.
 */
@Entity(name = "points")
@Table(name = "points")
public class Point
{
    @Id
    private String location;
    private PointStatus status;

    @OneToMany(mappedBy = "startingPoint")
    private List<Route> containingRoutesStarting = new ArrayList<>();

    @OneToMany(mappedBy = "endingPoint")
    private List<Route> containingRoutesEnding = new ArrayList<>();

    /**
     * Instantiates a new Point.
     */
    public Point() {
    }

    /**
     * Instantiates a new Point.
     *
     * @param location the location
     * @param status   the status
     */
    public Point(String location, PointStatus status) {
        this.location = location;
        this.status = status;
    }

    /**
     * Instantiates a new Point.
     *
     * @param location                 the location
     * @param status                   the status
     * @param containingRoutesStarting the containing routes starting
     * @param containingRoutesEnding   the containing routes ending
     */
    public Point(String location, PointStatus status,
                 List<Route> containingRoutesStarting, List<Route> containingRoutesEnding) {
        this.location = location;
        this.status = status;
        this.containingRoutesStarting = containingRoutesStarting;
        this.containingRoutesEnding = containingRoutesEnding;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public PointStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(PointStatus status) {
        this.status = status;
    }

    /**
     * Gets containing routes starting.
     *
     * @return the containing routes starting
     */
    public List<Route> getContainingRoutesStarting() {
        return containingRoutesStarting;
    }

    /**
     * Sets containing routes starting.
     *
     * @param containingRoutesStarting the containing routes starting
     */
    public void setContainingRoutesStarting(List<Route> containingRoutesStarting) {
        this.containingRoutesStarting = containingRoutesStarting;
    }

    /**
     * Gets containing routes ending.
     *
     * @return the containing routes ending
     */
    public List<Route> getContainingRoutesEnding() {
        return containingRoutesEnding;
    }

    /**
     * Sets containing routes ending.
     *
     * @param containingRoutesEnding the containing routes ending
     */
    public void setContainingRoutesEnding(List<Route> containingRoutesEnding) {
        this.containingRoutesEnding = containingRoutesEnding;
    }
}
