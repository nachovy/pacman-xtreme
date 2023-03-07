package edu.rice.comp504.model.strategy.movement;

import edu.rice.comp504.model.GameStore;
import edu.rice.comp504.model.movingelements.AMovingElement;
import edu.rice.comp504.model.movingelements.Character;
import edu.rice.comp504.model.movingelements.Ghost;
import edu.rice.comp504.util.GameUtil;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class GhostPatrollingStrategy extends GhostMovementStrategy {
    private static final int[][][] patrolPath = {
            {{0, 0}, {200, 0}, {200, 200}, {0, 200}, {0, 600}, {200, 600}, {200, 800}, {0, 800}, {600, 600}, {600, 800}, {800, 800}, {800, 0}, {600, 0}, {600, 200}},
            {{0, 0}, {250, 0}, {250, 300}, {0, 300}, {0, 900}, {350, 900}, {350, 600}, {650, 600}, {650, 900}, {1000, 900}, {1000, 0}, {750, 0}, {750, 300}, {250, 300}},
            {{0, 0}, {200, 700}, {100, 0}, {900, 0}, {900, 500}, {200, 900}, {900, 900}, {0, 0}, {200, 700}, {100, 0}, {900, 0}, {900, 500}, {200, 900}, {900, 900}}
    };
    private static int idx;
    private Point nextPoint;
    /**
     * Constructor.
     */
    public GhostPatrollingStrategy(Point dims, Character character, PropertyChangeListener[] walls, Point startVel, int startTime) {
        super(dims, character, walls, startVel, startTime);
        idx = 0;
        setNextPoint(idx);
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
        if (context.getLoc().equals(nextPoint)) {
            idx = (idx + 1) % patrolPath[GameStore.level - 1].length;
            setNextPoint(idx);
        }
        Ghost ghost = (Ghost) context;
        Point st = new Point((int) ghost.getLoc().getX(), (int) ghost.getLoc().getY());
        int dir = GameUtil.findPathDir(st, nextPoint, ghost.getSize(), ghost.getVel(), dims, walls);
        int speed = (int) Math.sqrt(context.getVel().getX() * context.getVel().getX() + context.getVel().getY() * context.getVel().getY());
        context.setVel(new Point((int) Math.cos(Math.PI / 2 * dir) * speed, (int) Math.sin(Math.PI / 2 * dir) * speed));
        context.move(dims);
    }

    /**
     * Get the name of the strategy.
     * @return The strategy name.
     */
    public String getName() {
        return "ghost2movement";
    }

    /**
     * Set the next patrol point.
     * @param nextIdx The index of the next patrol point.
     */
    public void setNextPoint(int nextIdx) {
        int[] nextPatrol = patrolPath[GameStore.level - 1][nextIdx];
        nextPoint = new Point(nextPatrol[0], nextPatrol[1]);
    }
}
