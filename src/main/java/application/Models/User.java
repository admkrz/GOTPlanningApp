package application.Models;

import javax.persistence.*;
import java.util.Objects;


/**
 * The type User.
 */
@Entity(name = "users")
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User
{
    /**
     * The enum User type.
     */
    public enum UserType{
        /**
         * T user type.
         */
        T,
        /**
         * W user type.
         */
        W
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String email;
    private String name;
    private String surname;
    private String passw;
    private Integer phoneNumber;
    private String address;
    private String city;
    private String zipCode;
    private UserType userType;

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param email       the email
     * @param name        the name
     * @param surname     the surname
     * @param passw       the passw
     * @param phoneNumber the phone number
     * @param address     the address
     * @param city        the city
     * @param zipCode     the zip code
     * @param userType    the user type
     */
    public User(String email, String name, String surname,
                String passw, Integer phoneNumber, String address,
                String city, String zipCode, UserType userType) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.passw = passw;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.userType = userType;
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
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets surname.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets surname.
     *
     * @param surname the surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets passw.
     *
     * @return the passw
     */
    public String getPassw() {
        return passw;
    }

    /**
     * Sets passw.
     *
     * @param passw the passw
     */
    public void setPassw(String passw) {
        this.passw = passw;
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets phone number.
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets zip code.
     *
     * @return the zip code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets zip code.
     *
     * @param zipCode the zip code
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Gets user type.
     *
     * @return the user type
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Sets user type.
     *
     * @param userType the user type
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
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
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getEmail(), user.getEmail())
                && Objects.equals(getName(), user.getName())
                && Objects.equals(getSurname(), user.getSurname())
                && Objects.equals(getPassw(), user.getPassw())
                && Objects.equals(getPhoneNumber(), user.getPhoneNumber())
                && Objects.equals(getAddress(), user.getAddress())
                && Objects.equals(getCity(), user.getCity())
                && Objects.equals(getZipCode(), user.getZipCode())
                && getUserType() == user.getUserType();
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getName(), getSurname(), getPassw(), getPhoneNumber(), getAddress(), getCity(), getZipCode(), getUserType());
    }
}
