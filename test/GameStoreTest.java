import edu.rice.comp504.model.GameStore;
import edu.rice.comp504.model.movingelements.Character;
import edu.rice.comp504.model.movingelements.Ghost;
import edu.rice.comp504.model.stationaryelements.Fruit;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class GameStoreTest {

    @Test
    public void loadGame() {
    }

    @Test
    public void initializeWalls() {
    }

    @Test
    public void initializeDots() {
    }

    @Test
    public void initializeCharacter() {
    }

    @Test
    public void initializeGhosts() {
    }

    @Test
    public void getCanvasDims() {
    }

    @Test
    public void switchVel() {
    }

    @Test
    public void initMap() {
    }

    @Test
    public void resetElements() {
    }

    @Test
    public void placeFruit() {
    }

    @Test
    public void startGhostBonus() {
    }

    @Test
    public void warnGhostBonus() {
    }

    @Test
    public void endGhostBonus() {
    }

    @Test
    public void gameRestart() {
        GameStore store = new GameStore();
        store.gameRestart();
        assertEquals("testMapLevel: ", store.getLevel(), 1);
        store.nextLevel();
        assertEquals("testMapLevel: ", store.getLevel(), 2);
        store.nextLevel();
        assertEquals("testMapLevel: ", store.getLevel(), 3);
        store.nextLevel();
        assertEquals("testMapLevel: ", store.getLevel(), 4);


        int gridSize = 50;
        // test collicion ghost
        store.gameRestart();
        Character c = (Character) store.getPcs().getPropertyChangeListeners("character")[0];
        Ghost g = (Ghost) store.getPcs().getPropertyChangeListeners("ghost")[0];
        c.setLoc(g.getLoc());
        store.setGameStart(true);
        store.updateGame();
        assertEquals("test collision ghost: ", 2,  store.getlife());
        // test collision pac
        store.gameRestart();
        c = (Character) store.getPcs().getPropertyChangeListeners("character")[0];
        c.setLoc(new Point(0 * gridSize, 0 * gridSize));
        store.setGameStart(true);
        store.updateGame();
        g = (Ghost) store.getPcs().getPropertyChangeListeners("ghost")[0];
        assertEquals("testEatLargeDot: ", 1,  g.getStatus());
        // test eat ghost
//        c.setLoc(new Point(2, 2));
//        g.setLoc(new Point(2, 2));
//        store.setTimer(11);
//        store.updateGame();

        c.setLoc(new Point(0, 0));
        g.setLoc(new Point(0, 0));
        store.setTimer(11);
        store.updateGame();
        assertEquals("testEatGhost: ", 3,  g.getStatus());

        for (int i = 0; i < 200; i++){
            store.updateGame();
        }
        //System.out.println(g.getStatus());
        // eat fruit
        store.gameRestart();
        c = (Character) store.getPcs().getPropertyChangeListeners("character")[0];
        store.setGameStart(true);
        store.placeFruit(1, 100);
        Fruit f = (Fruit) store.getPcs().getPropertyChangeListeners("fruit")[0];
        // System.out.println(f.getLoc().x + g.getLoc().y);
        c.setLoc(new Point(f.getLoc().x, f.getLoc().y));
        // System.out.println(c.detectCollisionWithFruit(f));
        store.updateGame();
        assertEquals("testEatFruit: ", true,  f.getEaten());

        for (int i = 0; i < 100; i++){
            store.updateGame();
        }
        // just go
        store.gameRestart();
        c = (Character) store.getPcs().getPropertyChangeListeners("character")[0];
        store.setGameStart(true);
        c.setLoc(new Point(0, 0));
        for (int i = 0; i < 100; i++){
            store.updateGame();
        }






    }

    @Test
    public void nextLevel() {
    }

    @Test
    public void updateGame() {
    }
}