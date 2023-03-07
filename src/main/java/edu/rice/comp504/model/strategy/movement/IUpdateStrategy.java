package edu.rice.comp504.model.strategy.movement;

import edu.rice.comp504.model.movingelements.AMovingElement;
/**
 * The IUpdateStrategy interface is used to determine the behavior of a movingelements in the canvas over time.
 */
public interface IUpdateStrategy {

    /**
     * Update the state the movingelements.
     * @param context The movingelements to apply the strategy to.
     */
    public void updateState(AMovingElement context);


    /**
     * Update the state the movingelements.
     * @param character The pacman movingelements to compare to.
     * @param context The movingelements to apply the strategy to.
     */
    public void updateState(AMovingElement character, AMovingElement context);

    /**
     * Get the name of the strategy.
     * @return The strategy name.
     */
    public String getName();
}
