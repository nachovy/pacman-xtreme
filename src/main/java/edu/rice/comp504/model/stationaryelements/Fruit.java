package edu.rice.comp504.model.stationaryelements;

import edu.rice.comp504.model.APaintObj;

import java.awt.*;

public class Fruit extends AStationaryElement {

    private int type;
    private int size;
    private boolean isEaten;

    /**
     * Constructor.
     * @param loc  The location of the fruit
     * @param type  The type of the fruit
     * @param size  The size of the fruit
     * @param score  The score of the fruit
     */
    public Fruit(Point loc, int type, int size, int score) {
        super("fruit", loc, score);
        this.type = type;
        this.size = size;
        this.isEaten = false;
    }

    /**
     * Whether the fruit is eaten.
     * @return Whether the fruit is eaten.
     */
    public boolean getEaten() {
        return isEaten;
    }

    /**
     * Set the fruit to be eaten.
     */
    public void setEaten() {
        isEaten = true;
    }

    /**
     * Get the size of the fruit.
     * @return The fruit size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Get the type of the fruit.
     * @return The fruit type.
     */
    public int getType() {
        return type;
    }
}
