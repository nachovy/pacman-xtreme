package edu.rice.comp504.model.strategy.movement;

import edu.rice.comp504.model.movingelements.AMovingElement;
import edu.rice.comp504.model.movingelements.Character;
import edu.rice.comp504.model.movingelements.Ghost;
import edu.rice.comp504.util.GameUtil;
import edu.rice.comp504.util.PointUtil;

import java.awt.*;
import java.beans.PropertyChangeListener;

public class GhostReturningStrategy extends GhostMovementStrategy {

    private IUpdateStrategy previousStrategy;

    /**
     * Constructor.
     */
    public GhostReturningStrategy(Point dims, Character character, PropertyChangeListener[] walls, Point startVel, int startTime) {
        super(dims, character, walls, startVel, startTime);
    }

    /**
     * Set previous used strategy of ghost.
     * @param previousStrategy Previous used strategy.
     */
    public void setPreviousStrategy(IUpdateStrategy previousStrategy) {
        this.previousStrategy = previousStrategy;
    }

    /**
     * Update the state the movingelements.
     * @param context The movingelements to apply the strategy to.
     */
    public void updateState(AMovingElement context) {
        updateState(character, context);
    }


    /**
     * Update the state the movingelements.
     * @param character The pacman movingelements to compare to.
     * @param context The movingelements to apply the strategy to.
     */
    public void updateState(AMovingElement character, AMovingElement context) {
        Ghost ghost = (Ghost) context;
        Point st = ghost.getLoc();
        Point en = ghost.getOriginLoc();
        if (ghost.getStatus() != 3 || st.equals(en)) {
            ghost.switchMovementStrategy(previousStrategy);
            ghost.setVel(new Point(0, 0));
            ghost.setFaceDir(false);
            ghost.setStatus(0);
            return;
        }
        int dir = GameUtil.findPathDir(st, en, ghost.getSize(), ghost.getVel(), dims, walls);
        int speed = (int) Math.sqrt(context.getVel().getX() * context.getVel().getX() + context.getVel().getY() * context.getVel().getY());
        context.setVel(PointUtil.velDir(speed, dir));
        context.move(dims);
    }

    /**
     * Get the name of the strategy.
     * @return The strategy name.
     */
    public String getName() {
        return "ghostreturning";
    }
}
