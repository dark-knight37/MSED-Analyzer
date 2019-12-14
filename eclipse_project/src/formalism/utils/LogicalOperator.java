package formalism.utils;

/**
 * Operators
 * Date: Oct 26, 2004
 * Time: 11.32.37
 * This enumeration class points out the types of the operator for a fault tree.
 * @author Stefano Marrone
 * @version 0.1
 */
public class LogicalOperator {
    /**
     * logical "Unspecified"
     */
    public static final LogicalOperator UNSPEC = new LogicalOperator("UNSPEC");

    /**
     * logical "AND"
     */
    public static final LogicalOperator AND = new LogicalOperator("AND");

    /**
     * logical "OR"
     */
    public static final LogicalOperator OR = new LogicalOperator("OR");

    /**
     * logical "NOT"
     */
    public static final LogicalOperator NOT = new LogicalOperator("NOT");

    /**
     * logical "XOR"
     */
    public static final LogicalOperator XOR = new LogicalOperator("XOR");

    /**
     * logical "KooN"
     */
    public static final LogicalOperator KooN = new LogicalOperator("KooN");

    private final String myName; // for debug only

    /**
     * constructor.
     * @param name name.
     */
    private LogicalOperator(String name) {
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
