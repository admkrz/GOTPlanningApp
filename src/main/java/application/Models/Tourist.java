package application.Models;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * The type Tourist.
 */
@Entity(name = "tourists")
@Table(name = "tourists")
public class Tourist extends User
{
    private Integer sumOfPoints = 0;
    private Boolean isDisabled = false;

    @OneToMany(mappedBy = "tourist", cascade = CascadeType.ALL)
    private List<Badge> badges = new ArrayList<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Trip> plannedTrips = new ArrayList<>();

    @ManyToMany(mappedBy = "members", cascade = CascadeType.ALL)
    private List<Trip> tripsMember = new ArrayList<>();

    /**
     * Instantiates a new Tourist.
     */
    public Tourist() { super(); }

    /**
     * Instantiates a new Tourist.
     *
     * @param email       the email
     * @param name        the name
     * @param surname     the surname
     * @param passw       the passw
     * @param phoneNumber the phone number
     * @param address     the address
     * @param city        the city
     * @param zipCode     the zip code
     */
    public Tourist(String email, String name, String surname, String passw, Integer phoneNumber, String address, String city, String zipCode) {
        super(email, name, surname, passw, phoneNumber, address, city, zipCode, UserType.T);
    }

    /**
     * Instantiates a new Tourist.
     *
     * @param email        the email
     * @param name         the name
     * @param surname      the surname
     * @param passw        the passw
     * @param phoneNumber  the phone number
     * @param address      the address
     * @param city         the city
     * @param zipCode      the zip code
     * @param sumOfPoints  the sum of points
     * @param isDisabled   the is disabled
     * @param badges       the badges
     * @param plannedTrips the planned trips
     * @param tripsMember  the trips member
     */
    public Tourist(String email, String name, String surname,
                   String passw, Integer phoneNumber, String address,
                   String city, String zipCode,
                   Integer sumOfPoints, Boolean isDisabled,
                   List<Badge> badges, List<Trip> plannedTrips, List<Trip> tripsMember) {
        super(email, name, surname, passw, phoneNumber, address, city, zipCode, UserType.T);
        this.sumOfPoints = sumOfPoints;
        this.isDisabled = isDisabled;
        this.badges = badges;
        this.plannedTrips = plannedTrips;
        this.tripsMember = tripsMember;
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
     * Gets disabled.
     *
     * @return the disabled
     */
    public Boolean getDisabled() {
        return isDisabled;
    }

    /**
     * Sets disabled.
     *
     * @param disabled the disabled
     */
    public void setDisabled(Boolean disabled) {
        isDisabled = disabled;
    }

    /**
     * Gets badges.
     *
     * @return the badges
     */
    public List<Badge> getBadges() {
        return badges;
    }

    /**
     * Sets badges.
     *
     * @param badges the badges
     */
    public void setBadges(List<Badge> badges) {
        this.badges = badges;
    }

    /**
     * Gets planned trips.
     *
     * @return the planned trips
     */
    public List<Trip> getPlannedTrips() {
        return plannedTrips;
    }

    /**
     * Sets planned trips.
     *
     * @param plannedTrips the planned trips
     */
    public void setPlannedTrips(List<Trip> plannedTrips) {
        this.plannedTrips = plannedTrips;
    }

    /**
     * Gets trips member.
     *
     * @return the trips member
     */
    public List<Trip> getTripsMember() {
        return tripsMember;
    }

    /**
     * Sets trips member.
     *
     * @param tripsMember the trips member
     */
    public void setTripsMember(List<Trip> tripsMember) {
        this.tripsMember = tripsMember;
    }

    /**
     * Gets all trips.
     *
     * @return the all trips
     */
    public List<Trip> getAllTrips()
    {
        var trips = new ArrayList<Trip>(plannedTrips);
        trips.addAll(tripsMember);
        return trips;
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
        if (!(o instanceof Tourist)) return false;
        Tourist tourist = (Tourist) o;
        return Objects.equals(getSumOfPoints(), tourist.getSumOfPoints())
                && Objects.equals(isDisabled, tourist.isDisabled)
                && Objects.equals(getBadges(), tourist.getBadges())
                && Objects.equals(getPlannedTrips(), tourist.getPlannedTrips())
                && Objects.equals(getTripsMember(), tourist.getTripsMember());
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(getSumOfPoints(), isDisabled, getBadges(), getPlannedTrips(), getTripsMember());
    }
}
