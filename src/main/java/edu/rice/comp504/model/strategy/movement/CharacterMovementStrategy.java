package edu.rice.comp504.model.strategy.movement;

import edu.rice.comp504.model.movingelements.AMovingElement;
import edu.rice.comp504.model.movingelements.Character;

import java.awt.*;

public class CharacterMovementStrategy implements IUpdateStrategy {

    private Point dims;

    /**
     * Constructor.
     */
    public CharacterMovementStrategy(Point dims) {
        this.dims = dims;
    }

    /**
     * Update the state the movingelements.
     * @param context The movingelements to apply the strategy to.
     */
    public void updateState(AMovingElement context) {
        Character character = (Character) context;
        character.move(dims);
    }

    /**
     * Update the state the movingelements.
     * @param character The pacman movingelements to compare to.
     * @param context The movingelements to apply the strategy to.
     */
    public void updateState(AMovingElement character, AMovingElement context) {
        updateState(context);
    }

    /**
     * Get the name of the strategy.
     * @return The strategy name.
     */
    public String getName() {
        return "charactermovement";
    }
}
