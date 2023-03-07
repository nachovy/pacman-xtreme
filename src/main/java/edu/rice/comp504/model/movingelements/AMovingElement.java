package edu.rice.comp504.model.movingelements;

import edu.rice.comp504.model.APaintObj;
import edu.rice.comp504.model.stationaryelements.Dot;
import edu.rice.comp504.model.stationaryelements.Fruit;
import edu.rice.comp504.model.stationaryelements.Wall;
import edu.rice.comp504.model.cmd.IPacmanCmd;
import edu.rice.comp504.model.strategy.collision.ICollisionStrategy;
import edu.rice.comp504.model.strategy.movement.IUpdateStrategy;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * AMovingElement is any given moving element in the pacManWorld.
 */
public abstract class AMovingElement extends APaintObj implements PropertyChangeListener {

    protected Point vel;
    protected IUpdateStrategy movementStrategy;
    protected ICollisionStrategy collisionStrategy;
    protected String color;
    protected int size;


    /**
     * Constructor.
     * @param name The name of the moving element
     * @param loc  The location of the moving element
     * @param vel  The velocity of the element
     * @param movementStrategy  The element movement strategy
     * @param collisionStrategy The element collision strategy
     */
    public AMovingElement(String name, Point loc, Point vel, IUpdateStrategy movementStrategy,
                          ICollisionStrategy collisionStrategy, int size) {
        super(name, loc);
        this.vel = vel;
        this.movementStrategy = movementStrategy;
        this.collisionStrategy = collisionStrategy;
        this.size = size;
    }

    /**
     * Get the element color.
     * @return element color
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Set the element color.
     * @param c The new element color
     */
    public void setColor(String c) {
        this.color = c;
    }

    /**
     * Get the velocity of the ACharacter.
     * @return The element velocity
     */
    public Point getVel() {
        return this.vel;
    }

    /**
     * Set the velocity of the element.
     * @param vel The new element velocity
     */
    public void setVel(Point vel) {
        this.vel = vel;
    }

    /**
     * Get the element movementStrategy.
     * @return The element movementStrategy.
     */
    public IUpdateStrategy getMovementStrategy() {
        return this.movementStrategy;
    }

    /**
     * Set the movementStrategy of the element.
     * @param movementStrategy  The new movementStrategy
     */
    public void setMovementStrategy(IUpdateStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    /**
     * Get the element collisionStrategy.
     * @return The element collisionStrategy.
     */
    public ICollisionStrategy getCollisionStrategy() {
        return collisionStrategy;
    }

    /**
     * Set the collisionStrategy of the element.
     * @param collisionStrategy  The new collisionStrategy
     */
    public void setCollisionStrategy(ICollisionStrategy collisionStrategy) {
        this.collisionStrategy = collisionStrategy;
    }

    /**
     * Get the size of the element.
     * @return The element size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Set the size of the element.
     * @param size The element size.
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Move the element.
     * @param dims The canvas dims
     */
    public Point nextLocation(Point dims) {
        return new Point((int) (this.loc.getX() + this.vel.getX()), (int) (this.loc.getY() + this.vel.getY()));
    }

    /**
     * Move the element.
     * @param dims The canvas dims
     */
    public void move(Point dims) {
        if (dims == null) {
            return;
        }
        this.loc.setLocation(this.loc.getX() + this.vel.getX(), this.loc.getY() + this.vel.getY());
    }

    /**
     * Detects collision between an element and a wall in the pacman world.
     * @return If the element has collided with the wall or not.
     */
    public boolean detectCollisionWithWall(Wall wall) {
        if (!wall.isHorizontal()) {
            return (loc.getX() < wall.getLoc().getX() && loc.getX() + size > wall.getLoc().getX()
                && !(loc.getY() >= wall.getLocEnd().getY()) && !(loc.getY() + size <= wall.getLoc().getY()));
        } else if (wall.isHorizontal()) {
            return (loc.getY() < wall.getLoc().getY() && loc.getY() + size > wall.getLoc().getY()
                && !(loc.getX() >= wall.getLocEnd().getX()) && !(loc.getX() + size <= wall.getLoc().getX()));
        }
        return false;
    }

    /**
     * Detects collision between an element and a dot.
     * @return If the element has collided with the dot or not.
     */
    public boolean detectCollisionWithDot(Dot dot) {
        return (dot.getLoc().getX() >= loc.getX() - dot.getSize() && dot.getLoc().getX() <= loc.getX() + size + dot.getSize())
                && (dot.getLoc().getY() >= loc.getY() - dot.getSize() && dot.getLoc().getY() <= loc.getY() + size + dot.getSize());
    }

    /**
     * Detects collision between an element and a fruit.
     * @return If the element has collided with the fruit or not.
     */
    public boolean detectCollisionWithFruit(Fruit fruit) {
        return Math.abs(loc.getX() + size / 2 - fruit.getLoc().getX()) < (size + fruit.getSize()) / 2
                && Math.abs(loc.getY() + size / 2 - fruit.getLoc().getY()) < (size + fruit.getSize()) / 2;
    }

    /**
     * Detects collision between an element and a ghost.
     * @return If the element has collided with the ghost or not.
     */
    public boolean detectCollisionWithGhost(Ghost ghost) {
        return Math.abs(loc.getX() + size / 2 - ghost.getLoc().getX() - ghost.getSize() / 2) < (size + ghost.getSize()) / 2
                && Math.abs(loc.getY() + size / 2 - ghost.getLoc().getY() - ghost.getSize() / 2) < (size + ghost.getSize()) / 2;
    }

    /**
     * Item respond to property change event.
     * @param evt changed event.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ((IPacmanCmd) evt.getNewValue()).execute(this, (APaintObj) evt.getOldValue());
    }
}

