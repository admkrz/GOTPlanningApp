package application.Models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type Route.
 */
@Entity(name = "routes")
@Table(name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private Double length;
    private Double height;
    private Integer pointsRev;
    private String color;
    private Double heightRev;
    private Integer points;
    private LocalDateTime disablementTime;
    private boolean userOwnRoute;

    @ManyToOne
    @JoinColumn
    private MountainGroup group;

    @OneToMany(mappedBy = "route")
    private List<TripSegment> includingSegments = new ArrayList<>();

    @ManyToOne
    private Point startingPoint;

    @ManyToOne
    private Point endingPoint;

    /**
     * Instantiates a new Route.
     */
    public Route() {
    }

    /**
     * Instantiates a new Route.
     *
     * @param length        the length
     * @param height        the height
     * @param pointsRev     the points rev
     * @param color         the color
     * @param heightRev     the height rev
     * @param points        the points
     * @param group         the group
     * @param startingPoint the starting point
     * @param endingPoint   the ending point
     */
    public Route(Double length, Double height,
                 Integer pointsRev, String color,
                 Double heightRev, Integer points, MountainGroup group,
                 Point startingPoint, Point endingPoint) {
        this.length = length;
        this.height = height;
        this.pointsRev = pointsRev;
        this.color = color;
        this.heightRev = heightRev;
        this.points = points;
        this.group = group;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
    }

    /**
     * * Instantiates a new Route.
     *
     * @param length            the length
     * @param height            the height
     * @param pointsRev         the points rev
     * @param color             the color
     * @param heightRev         the height rev
     * @param points            the points
     * @param group             the group
     * @param startingPoint     the starting point
     * @param endingPoint       the ending point
     * @param userOwnRoute      the route status
     */
    public Route(Double length, Double height,
                 Integer pointsRev, String color, Double heightRev,
                 Integer points, MountainGroup group,
                 Point startingPoint, Point endingPoint, boolean userOwnRoute) {
        this.length = length;
        this.height = height;
        this.pointsRev = pointsRev;
        this.color = color;
        this.heightRev = heightRev;
        this.points = points;
        this.userOwnRoute = userOwnRoute;
        this.group = group;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
    }

    /**
     * Instantiates a new Route.
     *
     * @param length            the length
     * @param height            the height
     * @param pointsRev         the points rev
     * @param color             the color
     * @param heightRev         the height rev
     * @param points            the points
     * @param group             the group
     * @param includingSegments the including segments
     * @param startingPoint     the starting point
     * @param endingPoint       the ending point
     */
    public Route(Double length, Double height,
                 Integer pointsRev, String color,
                 Double heightRev, Integer points, MountainGroup group,
                 List<TripSegment> includingSegments,
                 Point startingPoint, Point endingPoint) {
        this.length = length;
        this.height = height;
        this.pointsRev = pointsRev;
        this.color = color;
        this.heightRev = heightRev;
        this.points = points;
        this.group = group;
        this.includingSegments = includingSegments;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.userOwnRoute = false;
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
     * Gets points rev.
     *
     * @return the points rev
     */
    public Integer getPointsRev() {
        return pointsRev;
    }

    /**
     * Sets points rev.
     *
     * @param pointsRev the points rev
     */
    public void setPointsRev(Integer pointsRev) {
        this.pointsRev = pointsRev;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets color.
     *
     * @param color the color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Gets height rev.
     *
     * @return the height rev
     */
    public Double getHeightRev() {
        return heightRev;
    }

    /**
     * Sets height rev.
     *
     * @param heightRev the height rev
     */
    public void setHeightRev(Double heightRev) {
        this.heightRev = heightRev;
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
     * Gets disablement time.
     *
     * @return the disablement time
     */
    public LocalDateTime getDisablementTime() {
        return disablementTime;
    }

    /**
     * Gets disablement time in date format.
     *
     * @return the disablement time
     */
    public String getDisablementDate() {
        return (DateTimeFormatter.ISO_DATE).format(disablementTime);
    }

    /**
     * Sets disablement time.
     *
     * @param disablementTime the disablement time
     */
    public void setDisablementTime(LocalDateTime disablementTime) {
        this.disablementTime = disablementTime;
    }

    /**
     * Gets group.
     *
     * @return the group
     */
    public MountainGroup getGroup() {
        return group;
    }

    /**
     * Sets group.
     *
     * @param group the group
     */
    public void setGroup(MountainGroup group) {
        this.group = group;
    }

    /**
     * Gets including segments.
     *
     * @return the including segments
     */
    public List<TripSegment> getIncludingSegments() {
        return includingSegments;
    }

    /**
     * Sets including segments.
     *
     * @param includingSegments the including segments
     */
    public void setIncludingSegments(List<TripSegment> includingSegments) {
        this.includingSegments = includingSegments;
    }

    /**
     * Gets starting point.
     *
     * @return the starting point
     */
    public Point getStartingPoint() {
        return startingPoint;
    }

    /**
     * Sets starting point.
     *
     * @param startingPoint the starting point
     */
    public void setStartingPoint(Point startingPoint) {
        this.startingPoint = startingPoint;
    }

    /**
     * Gets ending point.
     *
     * @return the ending point
     */
    public Point getEndingPoint() {
        return endingPoint;
    }

    /**
     * Sets ending point.
     *
     * @param endingPoint the ending point
     */
    public void setEndingPoint(Point endingPoint) {
        this.endingPoint = endingPoint;
    }

    /**
     * Gets route status.
     *
     * @return route status
     */
    public boolean isUserOwnRoute() {
        return userOwnRoute;
    }

    /**
     * Sets route status.
     *
     * @param userOwnRoute the route status
     */
    public void setUserOwnRoute(boolean userOwnRoute) {
        this.userOwnRoute = userOwnRoute;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return startingPoint.getLocation() + " - " + endingPoint.getLocation();
    }
}
