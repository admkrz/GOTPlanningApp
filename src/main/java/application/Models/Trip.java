package application.Models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Trip.
 */
@Entity(name = "trips")
@Table(name = "trips")
public class Trip
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Double length = 0.0D;
    private Double sumOfHeights = 0.0D;
    private Integer sumOfPoints = 0;
    private LocalDate startDate;
    private LocalDate endDate;
    private TripStatus status;

    @ManyToOne
    @JoinColumn
    private Tourist creator;

    @ManyToMany
    private List<Tourist> members = new ArrayList<>();

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<TripSegment> segments = new ArrayList<>();

    /**
     * Instantiates a new Trip.
     */
    public Trip() { }

    /**
     * Instantiates a new Trip.
     *
     * @param length       the length
     * @param sumOfHeights the sum of heights
     * @param sumOfPoints  the sum of points
     * @param startDate    the start date
     * @param endDate      the end date
     * @param status       the status
     * @param creator      the creator
     * @param members      the members
     * @param segments     the segments
     */
    public Trip(Double length,
                Double sumOfHeights, Integer sumOfPoints,
                LocalDate startDate, LocalDate endDate,
                TripStatus status, Tourist creator,
                List<Tourist> members, List<TripSegment> segments) {
        this.length = length;
        this.sumOfHeights = sumOfHeights;
        this.sumOfPoints = sumOfPoints;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.creator = creator;
        this.members = members;
        this.segments = segments;
    }

    public Trip(Double length, Double sumOfHeights, Integer sumOfPoints, LocalDate startDate, LocalDate endDate, TripStatus status, Tourist creator) {
        this.length = length;
        this.sumOfHeights = sumOfHeights;
        this.sumOfPoints = sumOfPoints;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.creator = creator;
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
     * Gets sum of heights.
     *
     * @return the sum of heights
     */
    public Double getSumOfHeights() {
        return sumOfHeights;
    }

    /**
     * Sets sum of heights.
     *
     * @param sumOfHeights the sum of heights
     */
    public void setSumOfHeights(Double sumOfHeights) {
        this.sumOfHeights = sumOfHeights;
    }

    /**
     * Gets sum of points.
     *
     * @return the sum of points
     */
    public Integer getSumOfPoints() {
        return sumOfPoints;
    }

    /**
     * Sets sum of points.
     *
     * @param sumOfPoints the sum of points
     */
    public void setSumOfPoints(Integer sumOfPoints) {
        this.sumOfPoints = sumOfPoints;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public TripStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(TripStatus status) {
        this.status = status;
    }

    /**
     * Gets creator.
     *
     * @return the creator
     */
    public Tourist getCreator() {
        return creator;
    }

    /**
     * Sets creator.
     *
     * @param creator the creator
     */
    public void setCreator(Tourist creator) {
        this.creator = creator;
    }

    /**
     * Gets members.
     *
     * @return the members
     */
    public List<Tourist> getMembers() {
        return members;
    }

    /**
     * Sets members.
     *
     * @param members the members
     */
    public void setMembers(List<Tourist> members) {
        this.members = members;
    }

    /**
     * Gets segments.
     *
     * @return the segments
     */
    public List<TripSegment> getSegments() {
        return segments;
    }

    /**
     * Sets segments.
     *
     * @param segments the segments
     */
    public void setSegments(List<TripSegment> segments) {
        this.segments = segments;
    }

    /**
     * Gets start date.
     *
     * @return the start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets start date.
     *
     * @param startDate the start date
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets end date.
     *
     * @param endDate the end date
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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
        if (!(o instanceof Trip)) return false;
        Trip trip = (Trip) o;
        return Objects.equals(getLength(), trip.getLength()) && Objects.equals(getSumOfHeights(), trip.getSumOfHeights()) && Objects.equals(getSumOfPoints(), trip.getSumOfPoints()) && Objects.equals(getStartDate(), trip.getStartDate()) && Objects.equals(getEndDate(), trip.getEndDate()) && getStatus() == trip.getStatus() && Objects.equals(getCreator(), trip.getCreator()) && Objects.equals(getMembers(), trip.getMembers()) && Objects.equals(getSegments(), trip.getSegments());
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(getLength(), getSumOfHeights(), getSumOfPoints(), getStartDate(), getEndDate(), getStatus(), getCreator(), getMembers(), getSegments());
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString()
    {
        if(segments.size() > 0) {
            return startDate.toString() + " - " + endDate.toString() + " "
                    + segments.get(0).getRoute().getStartingPoint().getLocation() + "-"
                    + segments.get(segments.size() - 1).getRoute().getStartingPoint().getLocation();
        }else{
            return startDate.toString() + " - " + endDate.toString();
        }
    }
}
