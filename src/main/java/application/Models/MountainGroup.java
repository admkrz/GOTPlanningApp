package application.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Mountain group.
 */
@Entity(name = "mountain_group")
@Table(name = "mountain_group")
public class MountainGroup
{
    @Id
    private String code;

    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn
    private MountainArea area;

    @OneToMany(mappedBy = "group")
    private List<Route> containingRoutes = new ArrayList<>();

    /**
     * Instantiates a new Mountain group.
     */
    public MountainGroup() {
    }

    /**
     * Instantiates a new Mountain group.
     *
     * @param code the code
     * @param name the name
     * @param area the area
     */
    public MountainGroup(String code, String name,
                         MountainArea area) {
        this.code = code;
        this.name = name;
        this.area = area;
    }

    /**
     * Instantiates a new Mountain group.
     *
     * @param code             the code
     * @param name             the name
     * @param area             the area
     * @param containingRoutes the containing routes
     */
    public MountainGroup(String code, String name,
                         MountainArea area, List<Route> containingRoutes) {
        this.code = code;
        this.name = name;
        this.area = area;
        this.containingRoutes = containingRoutes;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
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
     * Gets containing routes.
     *
     * @return the containing routes
     */
    public List<Route> getContainingRoutes() {
        return containingRoutes;
    }

    /**
     * Sets containing routes.
     *
     * @param containingRoutes the containing routes
     */
    public void setContainingRoutes(List<Route> containingRoutes) {
        this.containingRoutes = containingRoutes;
    }

    /**
     * Gets area.
     *
     * @return the area
     */
    public MountainArea getArea() {
        return area;
    }

    /**
     * Sets area.
     *
     * @param area the area
     */
    public void setArea(MountainArea area) {
        this.area = area;
    }
}
