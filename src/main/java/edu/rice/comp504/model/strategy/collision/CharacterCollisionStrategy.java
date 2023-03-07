package edu.rice.comp504.model.strategy.collision;

import edu.rice.comp504.model.APaintObj;
import edu.rice.comp504.model.GameStore;
import edu.rice.comp504.model.movingelements.AMovingElement;
import edu.rice.comp504.model.movingelements.Character;
import edu.rice.comp504.model.stationaryelements.Dot;
import edu.rice.comp504.model.stationaryelements.Fruit;
import edu.rice.comp504.model.stationaryelements.Wall;
import edu.rice.comp504.model.strategy.collision.ICollisionStrategy;

import java.awt.*;

public class CharacterCollisionStrategy implements ICollisionStrategy {

    private String name;
    private Point dims;

    /**
     * Constructor.
     */
    public CharacterCollisionStrategy(Point dims) {
        this.name = "charactercollision";
        this.dims = dims;
    }

    /**
     * Update the state of the object
     * @param context The current object.
     * @param other The collide object.
     */
    public void collideState(APaintObj context, APaintObj other) {
        AMovingElement ele = (AMovingElement) context;
        if (other.getName().equals("wall")) {
            Wall wall = (Wall) other;
            if (!wall.isHorizontal()) {
                if (ele.getVel().getX() < 0) {
                    if (wall.isExit()) {
                        ele.setLoc(new Point((int) dims.getX() - ele.getSize(), (int) ele.getLoc().getY()));
                    } else {
                        ele.setLoc(new Point((int) wall.getLoc().getX(), (int) ele.getLoc().getY()));
                    }
                } else if (ele.getVel().getX() > 0) {
                    if (wall.isExit()) {
                        ele.setLoc(new Point(0, (int) ele.getLoc().getY()));
                    } else {
                        ele.setLoc(new Point((int) (wall.getLoc().getX() - ele.getSize()), (int) ele.getLoc().getY()));
                    }
                }
            } else {
                if (ele.getVel().getY() < 0) {
                    ele.setLoc(new Point((int) ele.getLoc().getX(), (int) wall.getLoc().getY()));
                } else if (ele.getVel().getY() > 0) {
                    ele.setLoc(new Point((int) ele.getLoc().getX(), (int) (wall.getLoc().getY() - ele.getSize())));
                }
            }
        } else if (other.getName().equals("dot")) {
            Dot dot = (Dot) other;
            dot.setEaten();
            GameStore.score += dot.getScore();
        } else if (other.getName().equals("fruit")) {
            Fruit fruit = (Fruit) other;
            fruit.setEaten();
            GameStore.score += fruit.getScore();
        }
    }

    /**
     * Get the name of the strategy.
     * @return The strategy name.
     */
    public String getName() {
        return name;
    }
}
