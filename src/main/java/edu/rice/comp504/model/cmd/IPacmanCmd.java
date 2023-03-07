package edu.rice.comp504.model.cmd;

import edu.rice.comp504.model.APaintObj;
import edu.rice.comp504.model.movingelements.AMovingElement;
import edu.rice.comp504.model.stationaryelements.Wall;

/**
 * The IPacmanCmd is an interface used to pass commands to objects in the Pacman.  The
 * movingelements must execute the command.
 */
public interface IPacmanCmd {

    /**
     * Execute the command.
     * @param context The receiver paintobj on which the command is executed.
     */
    public void execute(APaintObj context);

    /**
     * Execute the command.
     * @param context The receiver moving element on which the command is executed.
     * @param other The paintobj to be collided with
     */
    public void execute(APaintObj context, APaintObj other);
}
