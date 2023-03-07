package edu.rice.comp504.model.movingelements;

import edu.rice.comp504.model.APaintObj;
import edu.rice.comp504.model.GameStore;
import edu.rice.comp504.model.movingelements.AMovingElement;

import java.awt.*;

public class Character extends AMovingElement {
    private boolean faceDir; // false: left, true: right

    /**
     * Constructor.
     * @param loc The location of the character
     * @param vel The velocity of the character
     */
    public Character(Point loc, Point vel) {
        super("character", loc, vel, null, null, GameStore.gridSize);
        setVel(vel);
    }

    /**
     * Get the location of the character.
     * @return the current location
     */
    public Point getLoc() {
        return loc;
    }

    /**
     * Set the velocity of the character.
     * @param vel The new character velocity
     */
    @Override
    public void setVel(Point vel) {
        if (vel.getX() < 0) {
            faceDir = false;
        } else if (vel.getX() > 0) {
            faceDir = true;
        }
        this.vel = vel;
    }
}
