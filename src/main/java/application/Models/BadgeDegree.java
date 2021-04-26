package application.Models;

/**
 * The enum Badge degree.
 */
public enum BadgeDegree
{
    /**
     * Popular badge degree.
     */
    Popular("Popularna"),
    /**
     * Small bronze badge degree.
     */
    SmallBronze("MalaBrazowa"),
    /**
     * Small silver badge degree.
     */
    SmallSilver("MalaSrebrna"),
    /**
     * Small golden badge degree.
     */
    SmallGolden("MalaZlota"),
    /**
     * Grand bronze badge degree.
     */
    GrandBronze("DuzaBrazowa"),
    /**
     * Grand silver badge degree.
     */
    GrandSilver("DuzaSrebrna"),
    /**
     * Grand golden badge degree.
     */
    GrandGolden("DuzaZlota");

    BadgeDegree(String value){
        this.value = value;
    }
    static{
        Popular.setNext(SmallBronze);
        SmallBronze.setNext(SmallSilver);
        SmallSilver.setNext(SmallGolden);
        SmallGolden.setNext(GrandBronze);
        GrandBronze.setNext(GrandSilver);
        GrandSilver.setNext(GrandGolden);
        GrandGolden.setNext(null);
    }
    private String value;
    private BadgeDegree next;

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() { return value; }
    private void setNext(BadgeDegree next){ this.next = next; }

    /**
     * Get next badge degree.
     *
     * @return the badge degree
     */
    public BadgeDegree getNext(){ return next; }
}
