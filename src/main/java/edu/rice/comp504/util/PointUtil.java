package edu.rice.comp504.util;

import java.awt.*;

public class PointUtil {

    public static Point copy(Point p) {
        return new Point((int) p.getX(), (int) p.getY());
    }

    public static Point velDir(int speed, int dir) {
        return new Point((int) Math.cos(Math.PI / 2 * dir) * speed, (int) Math.sin(Math.PI / 2 * dir) * speed);
    }
}
