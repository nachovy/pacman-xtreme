package edu.rice.comp504.util;

import edu.rice.comp504.model.GameStore;
import edu.rice.comp504.model.movingelements.AMovingElement;
import edu.rice.comp504.model.movingelements.NullElement;
import edu.rice.comp504.model.stationaryelements.Wall;

import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Utility of canvas.
 */
public class GameUtil {

    /**
     * Detect if element collide with walls.
     * @param ele Element.
     * @param walls Walls.
     * @return True of false.
     */
    public static boolean detectWallCollision(AMovingElement ele, Point dims, PropertyChangeListener[] walls) {
        if (ele.getLoc().getX() < 0 || ele.getLoc().getX() > dims.getX() - ele.getSize() || ele.getLoc().getY() < 0 || ele.getLoc().getY() > dims.getY() - ele.getSize()) {
            return true;
        }
        for (PropertyChangeListener pcs : walls) {
            Wall wall = (Wall) pcs;
            if (ele.detectCollisionWithWall(wall)) {
                return true;
            }
        }
        int x = (int) ele.getLoc().getX() / GameStore.gridSize;
        int y = (int) ele.getLoc().getY() / GameStore.gridSize;
        if (GameStore.staticmap[x][y] == 1) {
            return true;
        }
        return false;
    }

    /**
     * Find the direction starting from one point to another.
     * @param st Starting point.
     * @param en Destination point.
     * @param size Size of element.
     * @param vel Initial velocity.
     * @param dims Canvas dims.
     * @return A number indicating direction. 0: right, 1: down, 2: left, 3: up
     */
    public static int findPathDir(Point st, Point en, int size, Point vel, Point dims, PropertyChangeListener[] walls) {
        int speed = (int) Math.sqrt(vel.getX() * vel.getX() + vel.getY() * vel.getY());
        int[] ansDis = {100000, 100000, 100000, 100000};
        int ansDir = 0;
        for (int stDir = 0; stDir < 4; stDir++) {
            NullElement init = new NullElement(PointUtil.copy(st), size);
            init.setVel(PointUtil.velDir(speed, stDir));
            init.move(dims);
            if (init.getLoc().getX() == en.getX() && init.getLoc().getY() == en.getY()) {
                return stDir;
            }
            if (detectWallCollision(init, dims, walls)) {
                continue;
            }
            LinkedList<Pair<NullElement, Integer>> queue = new LinkedList<>();
            HashSet<Pair<Double, Double>> used = new HashSet<>();
            queue.add(Pair.make_pair(init, 1));
            used.add(Pair.make_pair(init.getLoc().getX(), init.getLoc().getY()));
            while (!queue.isEmpty()) {
                Pair p = queue.getFirst();
                queue.removeFirst();
                NullElement ele = (NullElement) p.first;
                int depth = ((Integer) p.second).intValue();
                if (ele.getLoc().getX() == en.getX() && ele.getLoc().getY() == en.getY()) {
                    ansDis[stDir] = depth;
                    break;
                }
                for (int dir = 0; dir < 4; dir++) {
                    NullElement newEle = new NullElement(PointUtil.copy(ele.getLoc()), size);
                    newEle.setVel(PointUtil.velDir(speed, dir));
                    newEle.move(dims);
                    if (detectWallCollision(newEle, dims, walls) || used.contains(Pair.make_pair(newEle.getLoc().getX(), newEle.getLoc().getY()))) {
                        continue;
                    }
                    queue.addLast(Pair.make_pair(newEle, depth + 1));
                    used.add(Pair.make_pair(newEle.getLoc().getX(), newEle.getLoc().getY()));
                }
            }
        }
        for (int i = 1; i < 4; i++) {
            if (ansDis[i] < ansDis[ansDir]) {
                ansDir = i;
            }
        }
        return ansDir;
    }


    /**
     * Find a random empty point on the whole map.
     * @return Am empty point.
     */
    public static Point findRandomEmptyPoint() {
        int x = (int) Math.floor(Math.random() * GameStore.staticmap.length);
        int y = (int) Math.floor(Math.random() * GameStore.staticmap[0].length);
        while (GameStore.staticmap[x][y] != 0) {
            x = (int) Math.floor(Math.random() * GameStore.staticmap.length);
            y = (int) Math.floor(Math.random() * GameStore.staticmap[0].length);
        }
        return new Point(x * 50, y * 50);
    }

    /**
     * Find an empty target point near a given point.
     * @param init Element with starting point and velocity.
     * @param dims Canvas dims.
     * @param walls Array of walls.
     * @return Empty point found.
     */
    public static Point findNearbyEmptyPoint(NullElement init, Point dims, PropertyChangeListener[] walls) {
        if (!detectWallCollision(init, dims, walls)) {
            return init.getLoc();
        }
        int speed = (int) Math.sqrt(init.getVel().getX() * init.getVel().getX() + init.getVel().getY() * init.getVel().getY());
        LinkedList<NullElement> queue = new LinkedList<>();
        HashSet<Pair<Double, Double>> used = new HashSet<>();
        queue.add(init);
        used.add(Pair.make_pair(init.getLoc().getX(), init.getLoc().getY()));
        while (!queue.isEmpty()) {
            NullElement ele = queue.getFirst();
            queue.removeFirst();
            for (int dir = 0; dir < 4; dir++) {
                NullElement newEle = new NullElement(PointUtil.copy(ele.getLoc()), ele.getSize());
                newEle.setVel(PointUtil.velDir(speed, dir));
                newEle.move(dims);
                if (newEle.getLoc().getX() < 0 || newEle.getLoc().getX() > dims.getX()
                        || newEle.getLoc().getY() < 0 || newEle.getLoc().getY() > dims.getY()
                        || used.contains(Pair.make_pair(newEle.getLoc().getX(), newEle.getLoc().getY()))) {
                    continue;
                }
                if (!detectWallCollision(newEle, dims, walls)) {
                    return newEle.getLoc();
                }
                queue.addLast(newEle);
                used.add(Pair.make_pair(newEle.getLoc().getX(), newEle.getLoc().getY()));
            }
        }
        return null;
    }
}
