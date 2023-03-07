package edu.rice.comp504.model.strategy.movement;

import edu.rice.comp504.model.GameStore;
import edu.rice.comp504.model.movingelements.AMovingElement;
import edu.rice.comp504.model.movingelements.Character;
import edu.rice.comp504.model.movingelements.Ghost;
import edu.rice.comp504.util.GameUtil;

import java.awt.*;
import java.beans.PropertyChangeListener;

public class GhostRandomStrategy extends GhostMovementStrategy {
    private static Point randomPoint;
    /**
     * Constructor.
     */
    public GhostRandomStrategy(Point dims, Character character, PropertyChangeListener[] walls, Point startVel, int startTime) {
        super(dims, character, walls, startVel, startTime);
        randomPoint = GameUtil.findRandomEmptyPoint();
    }

    /**
     * Update the state the movingelements.
     * @param context The movingelements to apply the strategy to.
     */
    public void updateState(AMovingElement context) {
        if (!GameStore.gameStart || GameStore.timer < startTime) {
            return;
        }
        if (context.getVel().getX() == 0 && context.getVel().getY() == 0 && GameStore.timer >= startTime) {
            context.setVel(startVel);
        }
        if (context.getLoc().equals(randomPoint)) {
            randomPoint = GameUtil.findRandomEmptyPoint();
//            System.out.println(randomPoint.x+" "+randomPoint.y);
        }
        Ghost ghost = (Ghost) context;
        Point st = new Point((int) ghost.getLoc().getX(), (int) ghost.getLoc().getY());
        int dir = GameUtil.findPathDir(st, randomPoint, ghost.getSize(), ghost.getVel(), dims, walls);
        int speed = (int) Math.sqrt(context.getVel().getX() * context.getVel().getX() + context.getVel().getY() * context.getVel().getY());
        context.setVel(new Point((int) Math.cos(Math.PI / 2 * dir) * speed, (int) Math.sin(Math.PI / 2 * dir) * speed));
        context.move(dims);
    }


    /**
     * Update the state the movingelements.
     * @param character The pacman movingelements to compare to.
     * @param context The movingelements to apply the strategy to.
     */
    public void updateState(AMovingElement character, AMovingElement context) {}

    /**
     * Get the name of the strategy.
     * @return The strategy name.
     */
    public String getName() {
        return "ghost1movement";
    }
}
