package application.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Pttk worker.
 */
@Entity(name = "pttk_workers")
@Table(name = "pttk_workers")
public class PTTKWorker extends User
{
    @OneToMany(mappedBy = "responsibleWorker")
    private List<MountainArea> mountainAreas = new ArrayList<>();

    /**
     * Instantiates a new Pttk worker.
     */
    public PTTKWorker() { super(); }

    /**
     * Instantiates a new Pttk worker.
     *
     * @param mountainAreas the mountain areas
     */
    public PTTKWorker(List<MountainArea> mountainAreas) {
        super();
        this.mountainAreas = mountainAreas;
    }

    /**
     * Instantiates a new Pttk worker.
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
    public PTTKWorker(String email, String name, String surname,
                      String passw, Integer phoneNumber, String address,
                      String city, String zipCode) {
        super(email, name, surname, passw, phoneNumber, address, city, zipCode, UserType.W);
    }

    /**
     * Instantiates a new Pttk worker.
     *
     * @param email         the email
     * @param name          the name
     * @param surname       the surname
     * @param passw         the passw
     * @param phoneNumber   the phone number
     * @param address       the address
     * @param city          the city
     * @param zipCode       the zip code
     * @param mountainAreas the mountain areas
     */
    public PTTKWorker(String email, String name, String surname,
                      String passw, Integer phoneNumber, String address,
                      String city, String zipCode, List<MountainArea> mountainAreas) {
        super(email, name, surname, passw, phoneNumber, address, city, zipCode, UserType.W);
        this.mountainAreas = mountainAreas;
    }

    /**
     * Gets mountain areas.
     *
     * @return the mountain areas
     */
    public List<MountainArea> getMountainAreas() {
        return mountainAreas;
    }

    /**
     * Sets mountain areas.
     *
     * @param mountainAreas the mountain areas
     */
    public void setMountainAreas(List<MountainArea> mountainAreas) {
        this.mountainAreas = mountainAreas;
    }
}
