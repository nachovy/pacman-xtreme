package edu.rice.comp504.adapter;

import com.google.gson.JsonObject;
import edu.rice.comp504.model.GameStore;
import edu.rice.comp504.model.movingelements.Character;

import java.beans.PropertyChangeListener;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This adapter interfaces with the view (paint objects) and the controller.
 */
public class DispatchAdapter {

    private GameStore store;

    /**
     * Constructor call.
     */
    public DispatchAdapter() {
        store = new GameStore();
    }

    /**
     * Switch velocity.
     * @param velx Velocity x
     * @param vely Velocity y
     */
    public void switchVel(int velx, int vely) {
        store.switchVel(velx, vely);
    }

    /**
     * Switch level.
     * @param level Level number
     */
    public void switchLevel(int level) {
        store.switchLevel(level);
    }

    /**
     * Start game.
     */
    public void gameRestart() {
        store.gameRestart();
    }

    /**
     * Initialize the map.
     * @return All walls of the map.
     */
    public PropertyChangeListener[] initMap() {
        return store.initMap();
    }

    /**
     * Update the game.
     * @return All game objects.
     */
    public PropertyChangeListener[] updateGame() {
        return store.updateGame();
    }

    /**
     * Get canvas dims.
     * @return Dims.
     */
    public Point getDims() {
        return GameStore.dims;
    }

    /**
     * Get current game score.
     * @return Game score.
     */
    public JsonObject getStatistics() {
        JsonObject obj = new JsonObject();
        obj.addProperty("level", store.level);
        obj.addProperty("life", store.life);
        obj.addProperty("score", store.score);
        return obj;
    }

}
