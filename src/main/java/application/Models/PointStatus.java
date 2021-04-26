package application.Models;

/**
 * The enum Point status.
 */
public enum PointStatus
{
    /**
     * Bare point status.
     */
    Bare("Zwykly"),
    /**
     * The Route start.
     */
    RouteStart("Poczatek trasy"),
    /**
     * The Route mid.
     */
    RouteMid("Posredni trasy"),
    /**
     * The Route end.
     */
    RouteEnd("koncowy trasy");

    PointStatus(String status)
    {
        this.status = status;
    }
    private String status;

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }
}
