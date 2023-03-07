package edu.rice.comp504.model.strategy.collision;

import edu.rice.comp504.model.APaintObj;

/**
 * An interface for element strategies that determine the element collision behavior with each other.
 */
public interface ICollisionStrategy {

    /**
     * Get the name of the strategy.
     * @return the strategy name
     */
    String getName();

    /**
     * Update the state of the object
     * @param context The current object.
     * @param other The collide object.
     */
    void collideState(APaintObj context, APaintObj other);
}
