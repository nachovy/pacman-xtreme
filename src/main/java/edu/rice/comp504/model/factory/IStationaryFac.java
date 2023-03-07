package edu.rice.comp504.model.factory;


import edu.rice.comp504.model.stationaryelements.AStationaryElement;

/**
 * Factory for stationary element.
 */
public interface IStationaryFac {

    /**
     * Make a new stationary element.
     * @return A new stationary element
     */
    public AStationaryElement make();
}
