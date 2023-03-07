package edu.rice.comp504.model.factory;

import edu.rice.comp504.model.strategy.collision.ICollisionStrategy;

/**
 * Factory for collision strategy.
 */
public interface ICollisionFac {

    /**
     * Make a new collision strategy.
     * @return A new collision strategy
     */
    public ICollisionStrategy make();
}
