package edu.rice.comp504.model.factory;

import edu.rice.comp504.model.movingelements.AMovingElement;

/**
 * Factory for moving element.
 */
public interface IMovementFac {

    /**
     * Make a new moving element.
     * @return A new moving element
     */
    public AMovingElement make();
}
