package formalism.utils;

/**
 * Distribution
 * Date: Oct 26, 2004
 * Time: 11.35.16
 * This interface contains the probability distribution of the basic event.
 * @author Stefano Marrone
 * @version 1.0 Oct 30, 2004
 */
public class DistributionKind {
    /**
     * exponential distribution
     */
    public static final DistributionKind EXP = new DistributionKind("EXP");

    /**
     * deterministic distribution
     */
    public static final DistributionKind DET = new DistributionKind("DET");

    /**
     * uniform distribution
     */
    public static final DistributionKind UNI = new DistributionKind("UNI");

    /**
     * normal distribution
     */
    public static final DistributionKind NORM = new DistributionKind("NORM");
    
    private final String myName; // for debug only

    /**
     * constructor.
     * @param name name.
     */
    private DistributionKind(String name) {
        myName = name;
    }

    /**
     * toString method.
     * @return string of the object.
     */
    public String toString() {
        return myName;
    }
}