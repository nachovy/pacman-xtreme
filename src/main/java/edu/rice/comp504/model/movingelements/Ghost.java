package edu.rice.comp504.model.movingelements;

import edu.rice.comp504.model.GameStore;
import edu.rice.comp504.model.strategy.movement.GhostMovementStrategy;
import edu.rice.comp504.model.strategy.movement.GhostReturningStrategy;
import edu.rice.comp504.model.strategy.movement.IUpdateStrategy;

import java.awt.*;

public class Ghost extends AMovingElement {

    private Point originLoc;
    private boolean faceDir; // false: left, true: right
    private int type;
    private int status; // 0: ghost, 1: dark blue ghost, 2: flashing dark blue ghost, 3: traveling eyes
    private int score;

    /**
     * Constructor.
     * @param loc The location of the ghost
     * @param vel The velocity of the ghost
     * @param type The type of the ghost
     */
    public Ghost(Point loc, Point vel, int type) {
        super("ghost", loc, vel, null, null, GameStore.gridSize);
        setVel(vel);
        this.originLoc = new Point((int) loc.getX(), (int) loc.getY());
        this.type = type;
        this.status = 0;
        this.score = 200;
    }

    /**
     * Get the location of the character.
     * @return The current location
     */
    public Point getLoc() {
        return loc;
    }

    /**
     * Get the origin location of the character.
     * @return The origin location
     */
    public Point getOriginLoc() {
        return originLoc;
    }

    /**
     * Get the status of the character.
     * @return The current status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Set the status of the character.
     * @param status The status to be set.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Get the score of the character.
     * @return The score
     */
    public int getScore() {
        return score;
    }

    /**
     * Set face direction.
     * @param faceDir Face direction.
     */
    public void setFaceDir(boolean faceDir) {
        this.faceDir = faceDir;
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

    /**
     * Move the character.
     * @param dims The canvas dims
     */
    public void move(Point dims) {
        if (dims == null) {
            return;
        }
        this.loc.setLocation(this.loc.getX() + this.vel.getX(), this.loc.getY() + this.vel.getY());
    }

    /**
     * Switch ghost movement strategy.
     * @param strategy The target strategy.
     */
    public void switchMovementStrategy(IUpdateStrategy strategy) {
        if (strategy.getName().equals("ghostreturning") && !this.movementStrategy.getName().equals("ghostreturning")) {
            ((GhostReturningStrategy) strategy).setPreviousStrategy(this.movementStrategy);
        }
        this.movementStrategy = strategy;
    }
}
