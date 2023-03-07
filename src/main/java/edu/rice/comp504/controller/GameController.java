package edu.rice.comp504.controller;

import com.google.gson.Gson;
import edu.rice.comp504.adapter.DispatchAdapter;
import edu.rice.comp504.model.movingelements.NullElement;

import static spark.Spark.*;
import java.awt.*;
import java.beans.PropertyChangeListener;


/**
 * The game controller creates the adapter(s) that communicate with the view.
 * The controller responds to requests from the view after contacting the adapter(s).
 */
public class GameController {

    /**
     * The main entry point into the program.
     * @param args  The program arguments normally specified on the cmd line
     */
    public static void main(String[] args) {
        staticFiles.location("/public");
        port(getHerokuAssignedPort());
        Gson gson = new Gson();
        DispatchAdapter dis = new DispatchAdapter();

        get("/canvas/dims", (request, response) -> {
            return gson.toJson(dis.getDims());
        });

        post("/restart", (request, response) -> {
            dis.gameRestart();
            return gson.toJson(null);
        });

        post("/initmap", (request, response) -> {
            return gson.toJson(dis.initMap());
        });

        get("/getstatistics", (request, response) -> {
            return dis.getStatistics();
        });

        get("/update", (request, response) -> {
            return gson.toJson(dis.updateGame());
        });

        post("/switchvel", (request, response) -> {
            int velx = Integer.parseInt(request.queryParams("velx"));
            int vely = Integer.parseInt(request.queryParams("vely"));
            dis.switchVel(velx, vely);
            return gson.toJson(null);
        });

        post("/switchlevel/:level", (request, response) -> {
            int level = Integer.parseInt(request.params(":level"));
            dis.switchLevel(level);
            return gson.toJson(null);
        });

        notFound((request, response) -> {
            response.redirect("/");
            return gson.toJson(null);
        });
    }

    /**
     * Get the heroku assigned port number.
     * @return The port number
     */
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
