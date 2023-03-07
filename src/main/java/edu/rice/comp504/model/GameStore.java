package edu.rice.comp504.model;

import edu.rice.comp504.model.movingelements.Character;
import edu.rice.comp504.model.movingelements.*;
import edu.rice.comp504.model.stationaryelements.*;
import edu.rice.comp504.model.cmd.*;
import edu.rice.comp504.model.strategy.collision.*;
import edu.rice.comp504.model.strategy.movement.*;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

import edu.rice.comp504.util.JsonUtil;

/**
 * A store containing current Game.
 */
public class GameStore {

    private PropertyChangeSupport pcs;
    private static Point characterPos;
    private static Point[] ghostPos;
    private static Point[] ghostVelDir;
    private static int[] ghostStartTime;
    private static int[] fruitPeriod;
    private static int ghostBonusDuration;
    private static int exitY;
    public static Point dims;
    public static int[][] staticmap; // 0: dot, 1: wall, 2: nothing
    public static int gridSize = 50;
    public static int score = 0;
    public static int level = 1;
    public static int life = 3;
    public static int timer = 0;
    public static int ghostBonusTimes = 0; // 0 for no ghost bonus, [1, 2, 4, 8] times for points [200, 400, 800, 1600]
    public static int ghostBonusTimer = 0; //
    public static boolean gameStart = false;

    /**
     * Constructor.
     */
    public GameStore() {
        this.pcs = new PropertyChangeSupport(this);
        loadGame(1);
    }

    /**
     * Get current level.
     * @return Current level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Get PropertyChangeSupport.
     * @return PropertyChangeSupport
     */
    public PropertyChangeSupport getPcs() {
        return pcs;
    }

    /**
     * Get number of lives.
     * @return life Number of lives
     */
    public int getlife() {
        return life;
    }

    /**
     * Set game start status.
     * @param start Whether the game starts
     */
    public void setGameStart(boolean start) {
        gameStart = true;
    }

    /**
     * Set timer.
     * @param t timer
     */
    public void setTimer(int t) {
        timer = t;
    }

    /**
     * Load map and game settings from Json file.
     */
    public void loadGame(int level) {
        Map<?, ?> jsonMap = JsonUtil.loadJsonFile("target/classes/public/json/level" + String.valueOf(level) + ".json");
        ArrayList<Double> oneDimArr;
        ArrayList<ArrayList<Double>> twoDimArr;
        twoDimArr = (ArrayList<ArrayList<Double>>) jsonMap.get("staticMap");
        staticmap = new int[twoDimArr.size()][twoDimArr.get(0).size()];
        for (int i = 0; i < twoDimArr.size(); i++) {
            ArrayList<Double> row = twoDimArr.get(i);
            for (int j = 0; j < row.size(); j++) {
                staticmap[i][j] = row.get(j).intValue();
            }
        }
        dims = new Point((staticmap.length * gridSize), staticmap[0].length * gridSize);
        oneDimArr = (ArrayList<Double>) jsonMap.get("characterPos");
        characterPos = new Point(oneDimArr.get(0).intValue(), oneDimArr.get(1).intValue());
        staticmap[(int) characterPos.getX()][(int) characterPos.getY()] = 2;
        twoDimArr = (ArrayList<ArrayList<Double>>) jsonMap.get("ghostPos");
        ghostPos = new Point[4];
        for (int i = 0; i < 4; i++) {
            ArrayList<Double> row = twoDimArr.get(i);
            ghostPos[i] = new Point(row.get(0).intValue(), row.get(1).intValue());
        }
        twoDimArr = (ArrayList<ArrayList<Double>>) jsonMap.get("ghostVelDir");
        ghostVelDir = new Point[4];
        for (int i = 0; i < 4; i++) {
            ArrayList<Double> row = twoDimArr.get(i);
            ghostVelDir[i] = new Point(row.get(0).intValue(), row.get(1).intValue());
        }
        oneDimArr = (ArrayList<Double>) jsonMap.get("ghostStartTime");
        ghostStartTime = new int[4];
        for (int i = 0; i < 4; i++) {
            ghostStartTime[i] = oneDimArr.get(i).intValue();
        }
        oneDimArr = (ArrayList<Double>) jsonMap.get("fruitPeriod");
        fruitPeriod = new int[3];
        for (int i = 0; i < 3; i++) {
            fruitPeriod[i] = oneDimArr.get(i).intValue();
        }
        ghostBonusDuration = ((Double) jsonMap.get("ghostBonusDuration")).intValue();
        exitY = ((Double) jsonMap.get("exitY")).intValue();
    }

    /**
     * Initialize all Wall Objects.
     */
    public void initializeWalls() {
        int n = staticmap.length;
        int m = staticmap[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (staticmap[i][j] == 1) {
                    boolean u = (j > 0 && staticmap[i][j - 1] != 1);
                    boolean d = (j < m - 1 && staticmap[i][j + 1] != 1);
                    boolean l = (i > 0 && staticmap[i - 1][j] != 1);
                    boolean r = (i < n - 1 && staticmap[i + 1][j] != 1);
                    if (u) {
                        Wall wall = new Wall(new Point(i * gridSize, j * gridSize), new Point((i + 1) * gridSize, j * gridSize));
                        pcs.addPropertyChangeListener("wall", wall);
                    }
                    if (d) {
                        Wall wall = new Wall(new Point(i * gridSize, (j + 1) * gridSize), new Point((i + 1) * gridSize, (j + 1) * gridSize));
                        pcs.addPropertyChangeListener("wall", wall);
                    }
                    if (l) {
                        Wall wall = new Wall(new Point(i * gridSize, j * gridSize), new Point(i * gridSize, (j + 1) * gridSize));
                        pcs.addPropertyChangeListener("wall", wall);
                    }
                    if (r) {
                        Wall wall = new Wall(new Point((i + 1) * gridSize, j * gridSize), new Point((i + 1) * gridSize, (j + 1) * gridSize));
                        pcs.addPropertyChangeListener("wall", wall);
                    }
                } else {
                    var u = (j == 0);
                    var d = (j == m - 1);
                    var l = (i == 0);
                    var r = (i == n - 1);
                    if (u) {
                        Wall wall = new Wall(new Point(i * gridSize, j * gridSize), new Point((i + 1) * gridSize, j * gridSize));
                        pcs.addPropertyChangeListener("wall", wall);
                    }
                    if (d) {
                        Wall wall = new Wall(new Point(i * gridSize, (j + 1) * gridSize), new Point((i + 1) * gridSize, (j + 1) * gridSize));
                        pcs.addPropertyChangeListener("wall", wall);
                    }
                    if (l) {
                        Wall wall = new Wall(new Point(i * gridSize, j * gridSize), new Point(i * gridSize, (j + 1) * gridSize));
                        if (i == 0 && j == exitY) {
                            wall.setExit();
                        }
                        pcs.addPropertyChangeListener("wall", wall);
                    }
                    if (r) {
                        Wall wall = new Wall(new Point((i + 1) * gridSize, j * gridSize), new Point((i + 1) * gridSize, (j + 1) * gridSize));
                        if (i + 1 == n && j == exitY) {
                            wall.setExit();
                        }
                        pcs.addPropertyChangeListener("wall", wall);
                    }
                }
            }
        }
    }

    /**
     * Initialize all Dot Objects.
     */
    public void initializeDots() {
        int n = staticmap.length;
        int m = staticmap[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (staticmap[i][j] == 0) {
                    Dot dot;
                    if ((i == 0 || i == n - 1) && (j == 0 || j == m - 1)) {
                        dot = new Dot(new Point(i * gridSize + gridSize / 2, j * gridSize + gridSize / 2), 10, 50, true);
                    } else {
                        dot = new Dot(new Point(i * gridSize + gridSize / 2, j * gridSize + gridSize / 2), 5, 10, false);
                    }
                    pcs.addPropertyChangeListener("dot", dot);
                }
            }
        }
    }

    /**
     * Initialize the main character.
     */
    public void initializeCharacter() {
        Character character = new Character(new Point((int) characterPos.getX() * gridSize, (int) characterPos.getY() * gridSize), new Point(0, 0));
        character.setMovementStrategy(new CharacterMovementStrategy(dims));
        character.setCollisionStrategy(new CharacterCollisionStrategy(dims));
        pcs.addPropertyChangeListener("character", character);
    }

    /**
     * Initialize all Ghost Objects.
     */
    public void initializeGhosts() {
        Character character = (Character) pcs.getPropertyChangeListeners("character")[0];
        PropertyChangeListener[] wallArr = pcs.getPropertyChangeListeners("wall");
        Ghost ghost0 = new Ghost(new Point((int) ghostPos[0].getX() * gridSize, (int) ghostPos[0].getY() * gridSize), new Point(0, 0), 0);
        ghost0.setMovementStrategy(new GhostChasingStrategy(dims, character, wallArr, new Point(gridSize / 2 * (int) ghostVelDir[0].getX(), gridSize / 2 * (int) ghostVelDir[0].getY()), ghostStartTime[0]));
        ghost0.setCollisionStrategy(new GhostCollisionStrategy(dims));
        pcs.addPropertyChangeListener("ghost", ghost0);
        Ghost ghost1 = new Ghost(new Point((int) ghostPos[1].getX() * gridSize, (int) ghostPos[1].getY() * gridSize), new Point(0, 0), 1);
        ghost1.setMovementStrategy(new GhostRandomStrategy(dims, character, wallArr, new Point(gridSize / 2 * (int) ghostVelDir[1].getX(), gridSize / 2 * (int) ghostVelDir[1].getY()), ghostStartTime[1]));
        ghost1.setCollisionStrategy(new GhostCollisionStrategy(dims));
        pcs.addPropertyChangeListener("ghost", ghost1);
        Ghost ghost2 = new Ghost(new Point((int) ghostPos[2].getX() * gridSize, (int) ghostPos[2].getY() * gridSize), new Point(0, 0), 2);
        ghost2.setMovementStrategy(new GhostPatrollingStrategy(dims, character, wallArr, new Point(gridSize / 2 * (int) ghostVelDir[2].getX(), gridSize / 2 * (int) ghostVelDir[2].getY()), ghostStartTime[2]));
        ghost2.setCollisionStrategy(new GhostCollisionStrategy(dims));
        pcs.addPropertyChangeListener("ghost", ghost2);
        Ghost ghost3 = new Ghost(new Point((int) ghostPos[3].getX() * gridSize, (int) ghostPos[3].getY() * gridSize), new Point(0, 0), 3);
        ghost3.setMovementStrategy(new GhostEscapingStrategy(dims, character, wallArr, new Point(gridSize / 2 * (int) ghostVelDir[3].getX(), gridSize / 2 * (int) ghostVelDir[3].getY()), ghostStartTime[3]));
        ghost3.setCollisionStrategy(new GhostCollisionStrategy(dims));
        pcs.addPropertyChangeListener("ghost", ghost3);
    }

    /**
     * Get the canvas dimensions.
     * @return The canvas dimensions
     */
    public static Point getCanvasDims() {
        return dims;
    }

    /**
     * Switch the velocity of the character.
     * @param velx the x component of the new velocity
     * @param vely the y component of the new velocity
     */
    public void switchVel(int velx, int vely) {
        if (pcs.getPropertyChangeListeners("character").length == 0) {
            return;
        }
        Character character = (Character) pcs.getPropertyChangeListeners("character")[0];
        if (character.getVel().getX() == 0 && character.getVel().getY() == 0) {
            gameStart = true;
        }
        character.setVel(new Point(velx, vely));
    }

    /**
     * Initialize the map.
     * @return All walls of the map.
     */
    public PropertyChangeListener[] initMap() {
        pcs = new PropertyChangeSupport(this);
        initializeWalls();
        initializeCharacter();
        initializeGhosts();
        initializeDots();
        PropertyChangeListener[] wallArr = pcs.getPropertyChangeListeners("wall");
        return pcs.getPropertyChangeListeners("wall");
    }


    /**
     * Reset the elements to starting locations when hitting a ghost.
     */
    public void resetElements() {
        for (PropertyChangeListener pcl : pcs.getPropertyChangeListeners("character")) {
            pcs.removePropertyChangeListener("character", pcl);
        }
        for (PropertyChangeListener pcl : pcs.getPropertyChangeListeners("ghost")) {
            pcs.removePropertyChangeListener("ghost", pcl);
        }
        initializeCharacter();
        initializeCharacter();
        initializeGhosts();
    }

    /**
     * Replace any dot on the map by a fruit.
     * @type The type of fruit
     * @score The score of fruit
     */
    public void placeFruit(int type, int score) {
        PropertyChangeListener[] fruitArr = pcs.getPropertyChangeListeners("fruit");
        for (PropertyChangeListener pcs : fruitArr) {
            if (((Fruit) pcs).getType() == type) {
                return; // Only place one for each kind of fruit
            }
        }
        PropertyChangeListener[] dotArr = pcs.getPropertyChangeListeners("dot");
        if (dotArr.length == 0) {
            return;
        }
        ArrayList<Dot> dotList = new ArrayList<Dot>();
        for (PropertyChangeListener pcs : dotArr) {
            Dot dot = (Dot) pcs;
            if (!dot.isLarge()) {
                dotList.add(dot);
            }
        }
        if (dotList.size() == 0) {
            return;
        }
        Dot dotToReplace = (Dot) dotList.get((int) Math.floor(Math.random() * dotList.size()));
        Fruit fruit = new Fruit(new Point((int) dotToReplace.loc.getX(), (int) dotToReplace.loc.getY()), type, gridSize, score);
        pcs.removePropertyChangeListener("dot", dotToReplace);
        pcs.addPropertyChangeListener("fruit", fruit);
    }

    /**
     * Switch status of ghosts when a large dot is eaten.
     */
    public void startGhostBonus() {
        if (ghostBonusTimes == 0) {
            ghostBonusTimes = 1;
            ghostBonusTimer = 0;
            PropertyChangeListener[] ghostArr = pcs.getPropertyChangeListeners("ghost");
            for (PropertyChangeListener pcl : ghostArr) {
                Ghost ghost = (Ghost) pcl;
                ghost.setStatus(1);
            }
        }
    }

    /**
     * Switch status of ghosts when ghost bonus is about to finish.
     */
    public void warnGhostBonus() {
        if (ghostBonusTimes != 0) {
            PropertyChangeListener[] ghostArr = pcs.getPropertyChangeListeners("ghost");
            for (PropertyChangeListener pcl : ghostArr) {
                Ghost ghost = (Ghost) pcl;
                if (ghost.getStatus() == 1) {
                    ghost.setStatus(2);
                }
            }
        }
    }

    /**
     * Switch status of ghosts when ghost bonus is finished.
     */
    public void endGhostBonus() {
        if (ghostBonusTimes != 0) {
            ghostBonusTimes = 0;
            ghostBonusTimer = 0;
            PropertyChangeListener[] ghostArr = pcs.getPropertyChangeListeners("ghost");
            for (PropertyChangeListener pcl : ghostArr) {
                Ghost ghost = (Ghost) pcl;
                if (ghost.getStatus() != 3) {
                    ghost.setStatus(0);
                }
            }
        }
    }

    /**
     * Restart game.
     */
    public void gameRestart() {
        if (level == 4) {
            level = 1;
        }
        level = 1;
        life = 3;
        score = 0;
        timer = 0;
        ghostBonusTimes = 0;
        ghostBonusTimer = 0;
        gameStart = false;
        loadGame(level);
        initMap();
    }

    /**
     * Move to next level of the game.
     */
    public void nextLevel() {
        if (level >= 3) {
            level = 4;
            return;
        }
        level++;
        life = 3;
        timer = 0;
        ghostBonusTimes = 0;
        ghostBonusTimer = 0;
        loadGame(level);
        initMap();
    }

    /**
     * Switch level.
     * @param levelNum Level number
     */
    public void switchLevel(int levelNum) {
        level = levelNum;
        life = 3;
        score = 0;
        timer = 0;
        ghostBonusTimes = 0;
        ghostBonusTimer = 0;
        loadGame(level);
        initMap();
    }

    /**
     * Update the game.
     * @return All game objects.
     */
    public PropertyChangeListener[] updateGame() {
        if (pcs.getPropertyChangeListeners("character").length == 0) {
            return new PropertyChangeListener[0];
        }
        if (pcs.getPropertyChangeListeners("dot").length == 0 && pcs.getPropertyChangeListeners("fruit").length == 0) {
            nextLevel();
        }
        Character character = (Character) pcs.getPropertyChangeListeners("character")[0];
        if (gameStart) {
            timer++;
            if (ghostBonusTimes != 0) {
                ghostBonusTimer++;
            }
        }
        MovementCmd movementCmd = MovementCmd.make();
        CollisionCmd collisionCmd = CollisionCmd.make();
        pcs.firePropertyChange("character", null, movementCmd);
        PropertyChangeListener[] wallArr = pcs.getPropertyChangeListeners("wall");
        for (PropertyChangeListener pcl : wallArr) {
            Wall wall = (Wall) pcl;
            pcs.firePropertyChange("character", wall, collisionCmd);
        }

        if (timer > 0 && timer % fruitPeriod[0] == 0) {
            placeFruit(0, 100);
        }
        if (timer > 0 && timer % fruitPeriod[1] == 0) {
            placeFruit(1, 100);
        }
        if (timer > 0 && timer % fruitPeriod[2] == 0) {
            placeFruit(2, 100);
        }

        PropertyChangeListener[] ghostArr = pcs.getPropertyChangeListeners("ghost");
        pcs.firePropertyChange("ghost", null, movementCmd);
        for (PropertyChangeListener pcl : wallArr) {
            Wall wall = (Wall) pcl;
            pcs.firePropertyChange("ghost", wall, collisionCmd);
        }
        for (PropertyChangeListener pcl : ghostArr) {
            Ghost ghost = (Ghost) pcl;
            if (character.detectCollisionWithGhost(ghost)) {
                if (ghost.getStatus() == 0) {
                    life--;
                    timer = 0;
                    ghostBonusTimes = 0;
                    ghostBonusTimer = 0;
                    gameStart = false;
                    resetElements();
                    break;
                } else if (ghost.getStatus() == 1 || ghost.getStatus() == 2) {
                    ghost.setStatus(3);
                    ghost.switchMovementStrategy(new GhostReturningStrategy(dims, character, wallArr, new Point(0, -gridSize / 2), 0));
                    score += ghost.getScore() * ghostBonusTimes;
                    ghostBonusTimes *= 2;
                    if (ghostBonusTimes > 8) {
                        endGhostBonus();
                    }
                }
            }
        }

        PropertyChangeListener[] dotArr = pcs.getPropertyChangeListeners("dot");
        for (PropertyChangeListener pcl : dotArr) {
            Dot dot = (Dot) pcl;
            pcs.firePropertyChange("character", dot, collisionCmd);
            if (dot.getEaten()) {
                pcs.removePropertyChangeListener("dot", pcl);
                if (dot.isLarge()) {
                    startGhostBonus();
                }
            }
        }
        // Do not update dotArr here. Eaten dots will disappear from canvas during next update to compensate for the inconsistency between character size and actual character width.

        if (ghostBonusTimer == ghostBonusDuration - 10) {
            warnGhostBonus();
        }

        if (ghostBonusTimer == ghostBonusDuration) {
            endGhostBonus();
        }

        PropertyChangeListener[] fruitArr = pcs.getPropertyChangeListeners("fruit");
        for (PropertyChangeListener pcl : fruitArr) {
            Fruit fruit = (Fruit) pcl;
            pcs.firePropertyChange("character", fruit, collisionCmd);
            if (fruit.getEaten()) {
                pcs.removePropertyChangeListener("fruit", pcl);
            }
        }

        PropertyChangeListener[] pclArr = new PropertyChangeListener[dotArr.length + fruitArr.length + ghostArr.length + 1];
        for (int i = 0; i < dotArr.length; i++) {
            pclArr[i] = dotArr[i];
        }
        for (int i = 0; i < fruitArr.length; i++) {
            pclArr[dotArr.length + i] = fruitArr[i];
        }
        for (int i = 0; i < ghostArr.length; i++) {
            pclArr[dotArr.length + fruitArr.length + i] = ghostArr[i];
        }
        pclArr[dotArr.length + fruitArr.length + ghostArr.length] = character;
        return pclArr;
    }
}
