package edu.rice.comp504.model.cmd;

import edu.rice.comp504.model.APaintObj;
import edu.rice.comp504.model.movingelements.AMovingElement;
import edu.rice.comp504.model.movingelements.Character;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The command updating status of moving elements.
 */
public class MovementCmd implements IPacmanCmd {

    private static MovementCmd singleton;

    /**
     * Constructor.
     */
    public MovementCmd() {}

    /**
     * Singleton constructor.
     * @return The only command.
     */
    public static MovementCmd make() {
        if (singleton == null) {
            singleton = new MovementCmd();
        }
        return singleton;
    }


    /**
     * Execute the command.
     * @param context The receiver paintobj on which the command is executed.
     */
    public void execute(APaintObj context) {
        if (context instanceof AMovingElement) {
            ((AMovingElement) context).getMovementStrategy().updateState((AMovingElement) context);
        }
    }

    /**
     * Execute the command.
     * @param context The receiver moving element on which the command is executed.
     * @param other The paintobj to be collided with
     */
    public void execute(APaintObj context, APaintObj other) {
        execute(context);
    }
}
