package edu.rice.comp504.model.factory;

import edu.rice.comp504.model.strategy.collision.ICollisionStrategy;

public class CollisionStrategyFactory implements ICollisionFac {

    /**
     * make a collision strategy.
     * @return a new collision strategy
     */
    @Override
    public ICollisionStrategy make() {
        return null;
    }
}
