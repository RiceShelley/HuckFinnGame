/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.game;

import base.engine.lonefly.game.ResourceManager;
import base.engine.lonefly.game.engine.entity.Graphic;
import base.engine.lonefly.game.Updater;
import base.engine.lonefly.game.engine.display.Activity;
import base.engine.lonefly.game.engine.entity.GameObject;
import base.engine.lonefly.game.engine.entity.Platform;
import base.engine.lonefly.game.engine.entity.Text;
import base.engine.lonefly.game.engine.environment.Direction;
import base.engine.lonefly.game.engine.environment.Point;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rootie
 */
public class RiverBoatGame extends Activity implements Updater, Runnable {

    private Graphic r0 = null;
    private Graphic r1 = null;
    // main 
    private Main main;
    // begin animation
    private boolean runBeginAnimation = true;
    // Start point of river graphics just off screen
    private Point startPoint = null;
    // private int sleep time
    private int sleepTime = 1000;
    private int accumSleepTime = 0;
    // booleans and friends
    boolean spawnObj = true;
    // rock var
    private Point rockP = new Point(0, -500);
    private boolean spawnR = false;
    private int rockNum = 0;
    // player 
    private GameObject player = null;
    // amt of hearts
    private int hearts = 3;
    // aproch raft scene 
    private boolean apRaft = false;
    private boolean spawnApRaft = false;
    private GameObject raft = null;
    // game clock stuff
    private Text clock = null;
    private int timeLeft = 60;

    public RiverBoatGame(Main main) {
        super("RiverBoatGame", new ResourceManager(new World(new Vector2(0, 4), true), 75, (1920 * 2), (1820 * 2)), 1920, 1080);
        this.main = main;
        startPoint = new Point((1500 / 2), (5000 / 2));
        r0 = super.resources().addGameGraphic("river0", startPoint.getX(), startPoint.getY(), 1500, 5000, ImagePath.RIVER);
        r1 = super.resources().addGameGraphic("river1", startPoint.getX(), (startPoint.getY() - 4999), 1500, 5000, ImagePath.RIVER);
        player = super.resources().addGameObject("player", 500, (float) (Main.S_RES.getY() - 50), 50, 50, ImagePath.CANOE);
        super.resources().addGameGraphic("heart1", Main.S_RES.getX() - 50, 50, 50, 50, ImagePath.HEART).setIndex(3);
        super.resources().addGameGraphic("heart2", Main.S_RES.getX() - 120, 50, 50, 50, ImagePath.HEART).setIndex(4);
        super.resources().addGameGraphic("heart3", Main.S_RES.getX() - 190, 50, 50, 50, ImagePath.HEART).setIndex(5);
        clock = super.resources().addText("clock", 50, 50, 20, 20, "T - 60", Color.BLACK);
        clock.setIndex(6);
        player.setProp(.5f, .5f, 1);
        player.setIndex(6);
        r0.setIndex(0);
        r1.setIndex(1);
        new Thread(this).start();
    }

    @Override
    public void update() {
        int keys[] = main.getKeys().getKeys();
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == KeyEvent.VK_LEFT) {
                player.push(Direction.WEST, 2);
            } else if (keys[i] == KeyEvent.VK_RIGHT) {
                player.push(Direction.EAST, 2);
            } else if (keys[i] == KeyEvent.VK_UP) {
                player.push(Direction.NORTH, 4);
            } else if (keys[i] == KeyEvent.VK_DOWN) {
                player.push(Direction.SOUTH, 2);
            } else if (keys[i] == KeyEvent.VK_A) {
                player.getBody().setAngularVelocity(-1);
            } else if (keys[i] == KeyEvent.VK_D) {
                player.getBody().setAngularVelocity(1);
            }
        }

        // update clock
        clock.setText("T - " + Integer.toString((timeLeft - (int) (accumSleepTime / 1000))));

        if (player.isTouching().startsWith("rock")) {
            hearts--;
            ArrayList<GameObject> go = super.resources().getGameObjects();
            for (int i = 0; i < go.size(); i++) {
                if (go.get(i).getName().startsWith("rock")) {
                    super.resources().removeGameObject(go.get(i).getName());
                }
            }
            player.setTouching("");
            if (hearts == 2) {
                super.resources().removeGameGraphic("heart3");
            } else if (hearts == 1) {
                super.resources().removeGameGraphic("heart2");
            } else if (hearts == 0) {
                // game over
                super.resources().removeGameGraphic("heart1");
                super.resources().addText("gameOver", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), 30, 30, "You drowned... Restarting level", Color.red).setIndex(10);
                super.update();
                spawnObj = false;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(RiverBoatGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                main.setRunStage(false);
            }
        }

        if (player.getX() < 70) {
            player.getBody().setLinearVelocity(0, player.getBody().getLinearVelocity().y);
            player.push(Direction.EAST, 30);
        } else if (player.getX() > 1430) {
            player.getBody().setLinearVelocity(0, player.getBody().getLinearVelocity().y);
            player.push(Direction.WEST, 30);
        }
        if (player.getY() > 850) {
            player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, 0);
            player.push(Direction.NORTH, 30);
        } else if (player.getY() < 10) {
            player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, 0);
            player.push(Direction.SOUTH, 30);
        }

        if (spawnApRaft) {
            raft = super.resources().addGameObject("apRaft", (Main.S_RES.getX() / 2), 300, 100, 100, ImagePath.RAND_RAFT);
            raft.setIndex(11);
            spawnApRaft = false;
        }

        if (!apRaft) {
            r0.move(Direction.SOUTH, 10.5f);
            r1.move(Direction.SOUTH, 10.5f);
            if (r0.getY() > (1500 + 2510)) {
                r0.setY(r1.getY() - 2500);
            } else if (r1.getY() > (1500 + 2510)) {
                r1.setY(r0.getY() - 2500);
            }
        } else {
            if (player.isTouching().equals("apRaft")) {
                main.tickStage();
                main.setRunStage(false);
            }
            if (raft != null) {
                raft.push(Direction.NORTH, 4);
            }
        }

        // spawn shit
        if (spawnR) {
            float x = rockP.getX();
            float y = rockP.getY();
            GameObject go = super.resources().addGameObject("rock " + Integer.toString(rockNum), x, y, 100, 100, ImagePath.ROCK);
            go.setIndex(rockNum);
            rockNum++;
            spawnR = false;
        }

        super.update();

        if (runBeginAnimation) {
            // run begin animation with jim
            super.resources().addPlatform("jim", (Main.S_RES.getX() - 300), (Main.S_RES.getY() - 300), 80, 200, ImagePath.JIM).setIndex(10);
            super.resources().addText("jimSays0", (Main.S_RES.getX() - 300), (Main.S_RES.getY() - 500), 30, 30, "...", Color.yellow).setIndex(11);
            super.update();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RiverBoatGame.class.getName()).log(Level.SEVERE, null, ex);
            }
            super.resources().removeTextGraphic("jimSays0");
            super.resources().addText("jimSays1", (Main.S_RES.getX() - 350), (Main.S_RES.getY() - 500), 30, 30, "Huck Finn you're the best friend I ever had", Color.yellow).setIndex(11);
            super.update();
            try {
                Thread.sleep(3500);
            } catch (InterruptedException ex) {
                Logger.getLogger(RiverBoatGame.class.getName()).log(Level.SEVERE, null, ex);
            }
            super.resources().removePlatform("jim");
            super.resources().removeTextGraphic("jimSays1");
            super.update();
            runBeginAnimation = false;
        }
    }

    @Override
    public void run() {
        while (spawnObj) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(RiverBoatGame.class.getName()).log(Level.SEVERE, null, ex);
            }
            rockP.setX((float) (Math.random() * 1300) + 100);
            spawnR = true;
            accumSleepTime += sleepTime;
            if ((accumSleepTime / 1000) >= 60) {
                spawnObj = false;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(RiverBoatGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                super.resources().addText("win", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), 30, 30, "You survived the river. You see a raft approach it to continue", Color.PINK).setIndex(10);
                apRaft = true;
                spawnApRaft = true;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(RiverBoatGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                continue;
            }
            sleepTime = (int) (-Math.sqrt(5000 * (double) rockNum) + 1000.0);
        }
    }
}
