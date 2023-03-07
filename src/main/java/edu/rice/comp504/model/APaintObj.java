package edu.rice.comp504.model;

import edu.rice.comp504.model.strategy.movement.IUpdateStrategy;

import java.awt.*;

/**
 * The APaintObj is any given element with a given position and name in the pacmanWorldStore.
 */
public abstract class APaintObj {
    protected Point loc;
    protected String name;

    /**
     * Constructor.
     *
     * @param loc   The location of the obj on the canvas
     * @param name  The name of the obj
     */
    public APaintObj(String name, Point loc) {
        this.loc = loc;
        this.name = name;
    }

    /**
     * Get the paint object location in the pacman world.
     * @return The paint object location.
     */
    public Point getLoc() {
        return loc;
    }

    /**
     * Set the paint object location in the canvas.  The origin (0,0) is the top left corner of the canvas.
     * @param loc  The paint object coordinate.
     */
    public void setLoc(Point loc) {
        this.loc = loc;
    }

    /**
     * Get the paint object name.
     * @return paint object name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the paint object name.
     * @param name The new paint object name
     */
    public void setName(String name) {
        this.name = name;
    }
}
