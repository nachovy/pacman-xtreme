package edu.rice.comp504.model.stationaryelements;

import edu.rice.comp504.model.APaintObj;
import edu.rice.comp504.model.cmd.IPacmanCmd;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * AItem is any given stationary stationaryelements in the pacManWorld.
 */
public abstract class AStationaryElement extends APaintObj implements PropertyChangeListener {

    protected int score;
    protected transient boolean isEaten;

    /**
     * Constructor.
     * @param name The name of the stationaryelements
     * @param loc  The location of the stationaryelements on the canvas
     */
    public AStationaryElement(String name, Point loc, int score) {
        super(name, loc);
        this.score = score;
        this.isEaten = false;
    }

    /**
     * Get the AItem score.
     * @return AItem score
     */
    public int getScore() {
        return score;
    }

    /**
     * Get if the AItem color is eaten.
     * @return If AItem is eaten
     */
    public boolean isEaten() {
        return isEaten;
    }

    /**
     * Set if the AItem color is eaten.
     * @param eaten If AItem is eaten
     */
    public void setEaten(boolean eaten) {
        isEaten = eaten;
    }

    /**
     * Item respond to property change event.
     * @param evt changed event.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ((IPacmanCmd) evt.getNewValue()).execute(this);
    }
}
