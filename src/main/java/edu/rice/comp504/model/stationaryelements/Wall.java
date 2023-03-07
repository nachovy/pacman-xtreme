package edu.rice.comp504.model.stationaryelements;

import java.awt.*;

public class Wall extends edu.rice.comp504.model.stationaryelements.AStationaryElement {

    private Point locEnd;
    private boolean exit;

    /**
     * Constructor.
     * @param loc  The location of the starting endpoint
     * @param locEnd  The location of the ending endpoint
     */
    public Wall(Point loc, Point locEnd) {
        super("wall", loc, 0);
        this.locEnd = locEnd;
        this.exit = false;
    }

    /**
     * Get the location of the ending endpoint.
     * @return The location of the ending endpoint.
     */
    public Point getLocEnd() {
        return locEnd;
    }


    /**
     * Judge the direction of wall.
     * @return Whether the wall is horizontal.
     */
    public boolean isHorizontal() {
        return loc.getY() == locEnd.getY();
    }

    /**
     * Judge if the wall is an exit.
     * @return Whether the wall is an exit.
     */
    public boolean isExit() {
        return exit;
    }

    /**
     * Set the wall as an exit.
     */
    public void setExit() {
        this.exit = true;
    }
}