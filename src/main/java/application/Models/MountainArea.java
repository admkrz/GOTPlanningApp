package application.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Mountain area.
 */
@Entity(name = "mountain_areas")
@Table(name = "mountain_areas")
public class MountainArea
{
    @Id
    private String name;

    @ManyToOne
    private PTTKWorker responsibleWorker;

    @OneToMany(mappedBy = "area")
    private List<MountainGroup> containingGroups = new ArrayList<>();

    /**
     * Instantiates a new Mountain area.
     */
    public MountainArea() {
    }

    /**
     * Instantiates a new Mountain area.
     *
     * @param name the name
     */
    public MountainArea(String name) {
        this.name = name;
    }

    /**
     * Instantiates a new Mountain area.
     *
     * @param name              the name
     * @param responsibleWorker the responsible worker
     * @param containingGroups  the containing groups
     */
    public MountainArea(String name, PTTKWorker responsibleWorker, List<MountainGroup> containingGroups) {
        this.name = name;
        this.responsibleWorker = responsibleWorker;
        this.containingGroups = containingGroups;
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
     * Gets containing groups.
     *
     * @return the containing groups
     */
    public List<MountainGroup> getContainingGroups() {
        return containingGroups;
    }

    /**
     * Sets containing groups.
     *
     * @param containingGroups the containing groups
     */
    public void setContainingGroups(List<MountainGroup> containingGroups) {
        this.containingGroups = containingGroups;
    }

    /**
     * Gets responsible worker.
     *
     * @return the responsible worker
     */
    public PTTKWorker getResponsibleWorker() {
        return responsibleWorker;
    }

    /**
     * Sets responsible worker.
     *
     * @param responsibleWorker the responsible worker
     */
    public void setResponsibleWorker(PTTKWorker responsibleWorker) {
        this.responsibleWorker = responsibleWorker;
    }
}
