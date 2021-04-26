package application.Models;

/**
 * The enum Trip status.
 */
public enum TripStatus
{
    /**
     * Planned trip status.
     */
    Planned("Zaplanowana"),
    /**
     * Done trip status.
     */
    Done("Przebyta"),
    /**
     * Not done trip status.
     */
    NotDone("Niezrealizowana"),
    /**
     * The Awaiting verification.
     */
    AwaitingVerification("Oczekuje na weryfikacje");

    TripStatus(String status){
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
