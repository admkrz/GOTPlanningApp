package application.Models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

/**
 * The type Badge.
 */
@Entity(name = "badges")
@Table(name = "badges")
public class Badge
{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @ManyToOne
    @JoinColumn
    private Tourist tourist;
    private BadgeDegree badge;
    private LocalDate dateOfAward;

    /**
     * Instantiates a new Badge.
     */
    public Badge() { }

    /**
     * Instantiates a new Badge.
     *
     * @param tourist     the tourist
     * @param badge       the badge
     * @param dateOfAward the date of award
     */
    public Badge(Tourist tourist, BadgeDegree badge, LocalDate dateOfAward) {
        this.tourist = tourist;
        this.badge = badge;
        this.dateOfAward = dateOfAward;
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
     * Gets tourist.
     *
     * @return the tourist
     */
    public Tourist getTourist() { return tourist; }

    /**
     * Sets tourist.
     *
     * @param tourist the tourist
     */
    public void setTourist(Tourist tourist) { this.tourist = tourist; }

    /**
     * Gets badge.
     *
     * @return the badge
     */
    public BadgeDegree getBadge() {
        return badge;
    }

    /**
     * Sets badge.
     *
     * @param badge the badge
     */
    public void setBadge(BadgeDegree badge) {
        this.badge = badge;
    }

    /**
     * Gets date of award.
     *
     * @return the date of award
     */
    public LocalDate getDateOfAward() {
        return dateOfAward;
    }

    /**
     * Sets date of award.
     *
     * @param dateOfAward the date of award
     */
    public void setDateOfAward(LocalDate dateOfAward) {
        this.dateOfAward = dateOfAward;
    }

    /**
     * How many points to the next badge float.
     *
     * @param current the current
     * @return the float
     */
    public static float howManyPointsToTheNextBadge(BadgeDegree current){
        float points;
        if (current == null) {
            points = 60;
        } else if (current == BadgeDegree.Popular) {
            points = 120;
        } else if (current == BadgeDegree.SmallBronze) {
            points = 360;
        } else if (current == BadgeDegree.SmallSilver) {
            points = 720;
        } else {
            points = 0;
        }
        return points;
    }
}
