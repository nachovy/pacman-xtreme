package edu.rice.comp504.model.factory;

import edu.rice.comp504.model.strategy.movement.IUpdateStrategy;

/**
 * Factory for movement strategy.
 */
public interface IUpdateFac {

    /**
     * Make a new movement strategy.
     * @return A new movement strategy
     */
    public IUpdateStrategy make();
}
