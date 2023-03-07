package edu.rice.comp504.model.stationaryelements;

import edu.rice.comp504.model.APaintObj;

import java.awt.*;

public class Dot extends AStationaryElement {

    private int size;
    private boolean isLarge;
    private boolean isEaten;

    /**
     * Constructor.
     * @param loc  The location of the dot
     * @param size  The radius of the dot
     * @param score  The score of the dot
     * @param isLarge  Whether the dot is large
     */
    public Dot(Point loc, int size, int score, boolean isLarge) {
        super("dot", loc, score);
        this.size = size;
        this.isLarge = isLarge;
        this.isEaten = false;
    }

    /**
     * Whether the dot is eaten.
     * @return Whether the dot is eaten.
     */
    public boolean getEaten() {
        return isEaten;
    }

    /**
     * Set the dot to be eaten.
     */
    public void setEaten() {
        isEaten = true;
    }

    /**
     * Get the size of the dot.
     * @return The dot size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Whether the dot is large.
     * @return Whether the dot is large.
     */
    public boolean isLarge() {
        return isLarge;
    }
}
