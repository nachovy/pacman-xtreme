package edu.rice.comp504.model.movingelements;

import edu.rice.comp504.model.strategy.collision.ICollisionStrategy;
import edu.rice.comp504.model.strategy.movement.IUpdateStrategy;

import java.awt.*;

/**
 * Empty moving element.
 */
public class NullElement extends AMovingElement {


    /**
     * Constructor.
     * @param loc  The location of the element
     * @param size  The size of the element
     */
    public NullElement(Point loc, int size) {
        super("null", loc, null, null, null, size);
    }
}
