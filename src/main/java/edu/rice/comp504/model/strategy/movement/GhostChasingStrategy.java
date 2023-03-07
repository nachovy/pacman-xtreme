package edu.rice.comp504.model.strategy.movement;

import edu.rice.comp504.model.GameStore;
import edu.rice.comp504.model.movingelements.AMovingElement;
import edu.rice.comp504.model.movingelements.Character;
import edu.rice.comp504.model.movingelements.Ghost;
import edu.rice.comp504.model.movingelements.NullElement;
import edu.rice.comp504.util.GameUtil;
import edu.rice.comp504.util.PointUtil;

import java.awt.*;
import java.beans.PropertyChangeListener;

public class GhostChasingStrategy extends GhostMovementStrategy {

    private Point chasingTarget; // switch target point based on the location of character every period of time
    private final int switchTargetInterval = 25; // switch every (0.2 * switchTargetInterval) seconds

    /**
     * Constructor.
     */
    public GhostChasingStrategy(Point dims, Character character, PropertyChangeListener[] walls, Point startVel, int startTime) {
        super(dims, character, walls, startVel, startTime);
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
        if (!GameStore.gameStart || GameStore.timer < startTime) {
            return;
        }
        if (context.getVel().getX() == 0 && context.getVel().getY() == 0 && GameStore.timer >= startTime) {
            context.setVel(startVel);
        }
        Ghost ghost = (Ghost) context;
        if (GameStore.timer % switchTargetInterval == 1) {
            chasingTarget = PointUtil.copy(character.getLoc());
        }
        int speed = (int) Math.sqrt(context.getVel().getX() * context.getVel().getX() + context.getVel().getY() * context.getVel().getY());
        int dir = 0;
        if (chasingTarget == null || ghost.getLoc().equals(chasingTarget)) { // reach target or no target: move along, switch direction if colliding with wall
            chasingTarget = null;
            NullElement ele = new NullElement(PointUtil.copy(ghost.getLoc()), ghost.getSize());
            ele.setVel(PointUtil.copy(ghost.getVel()));
            ele.move(dims);
            dir = ((int) (Math.atan2(ghost.getVel().getY(), ghost.getVel().getX()) / (Math.PI / 2)) + 4) % 4;
            if (GameUtil.detectWallCollision(ele, dims, walls)) {
                for (dir = 0; dir < 4; dir++) {
                    ele = new NullElement(PointUtil.copy(ghost.getLoc()), ghost.getSize());
                    ele.setVel(PointUtil.velDir(speed, dir));
                    ele.move(dims);
                    if (!GameUtil.detectWallCollision(ele, dims, walls)) {
                        break;
                    }
                }
            }
        } else { // does not reach target : chase target
            dir = GameUtil.findPathDir(ghost.getLoc(), chasingTarget, ghost.getSize(), ghost.getVel(), dims, walls);
        }
        context.setVel(PointUtil.velDir(speed, dir));
        context.move(dims);
    }

    /**
     * Get the name of the strategy.
     * @return The strategy name.
     */
    public String getName() {
        return "ghost0movement";
    }
}
