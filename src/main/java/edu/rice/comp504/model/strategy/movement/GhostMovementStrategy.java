package edu.rice.comp504.model.strategy.movement;

import edu.rice.comp504.model.movingelements.Character;

import java.awt.*;
import java.beans.PropertyChangeListener;

public abstract class GhostMovementStrategy implements IUpdateStrategy {

    protected Point dims;
    protected Character character;
    protected PropertyChangeListener[] walls;
    protected Point startVel;
    protected int startTime;

    /**
     * Constructor.
     */
    public GhostMovementStrategy(Point dims, Character character, PropertyChangeListener[] walls, Point startVel, int startTime) {
        this.dims = dims;
        this.character = character;
        this.walls = walls;
        this.startVel = startVel;
        this.startTime = startTime;
    }

    /**
     * Get starting velocity.
     * @return Starting velocity.
     */
    public Point getStartVel() {
        return startVel;
    }
}
