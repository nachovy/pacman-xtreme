package edu.rice.comp504.model.cmd;

import edu.rice.comp504.model.APaintObj;
import edu.rice.comp504.model.movingelements.AMovingElement;
import edu.rice.comp504.model.stationaryelements.Dot;
import edu.rice.comp504.model.stationaryelements.Fruit;
import edu.rice.comp504.model.stationaryelements.Wall;

import java.awt.*;
import java.beans.PropertyChangeListener;

/**
 * The command handling collision of moving elements.
 */
public class CollisionCmd implements IPacmanCmd {

    private static CollisionCmd singleton;

    /**
     * Constructor.
     */
    public CollisionCmd() {}

    /**
     * Singleton constructor.
     * @return The only command.
     */
    public static CollisionCmd make() {
        if (singleton == null) {
            singleton = new CollisionCmd();
        }
        return singleton;
    }


    /**
     * Execute the command.
     * @param context The receiver paintobj on which the command is executed.
     */
    public void execute(APaintObj context) {}

    /**
     * Execute the command.
     * @param context The receiver moving element on which the command is executed.
     * @param other The paintobj to be collided with
     */
    public void execute(APaintObj context, APaintObj other) {
        if (context instanceof AMovingElement) {
            AMovingElement ele = (AMovingElement) context;
            if (other.getName().equals("wall")) {
                Wall wall = (Wall) other;
                if (ele.detectCollisionWithWall(wall)) {
                    ele.getCollisionStrategy().collideState(ele, other);
                }
            } else if (other.getName().equals("dot")) {
                Dot dot = (Dot) other;
                if (ele.detectCollisionWithDot(dot)) {
                    ele.getCollisionStrategy().collideState(ele, other);
                }
            } else if (other.getName().equals("fruit")) {
                Fruit fruit = (Fruit) other;
                if (ele.detectCollisionWithFruit(fruit)) {
                    ele.getCollisionStrategy().collideState(ele, other);
                }
            }
        }
    }
}
