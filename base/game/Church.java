/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.game;

import base.engine.lonefly.game.ResourceManager;
import base.engine.lonefly.game.Updater;
import base.engine.lonefly.game.engine.display.Activity;
import base.engine.lonefly.game.engine.entity.GameObject;
import base.engine.lonefly.game.engine.environment.Direction;
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
public class Church extends Activity implements Updater {

    private Main main = null;
    private GameObject pig1 = null;
    private GameObject pig2 = null;
    private GameObject pig3 = null;
    private GameObject player = null;
    private ArrayList<GameObject> pigs = new ArrayList();

    public Church(Main main) {
        super("Church", new ResourceManager(new World(new Vector2(0, 0), true), 75, (1920 * 2), 1820 * 2), 1920, 1080);
        super.resources().addGameGraphic("background", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), Main.S_RES.getX(), Main.S_RES.getY(), ImagePath.CHURCH).setIndex(0);
        pig1 = super.resources().addGameObject("pig1", (Main.S_RES.getX() - 300), 135, (int) (261 / 2.9), (int) (196 / 2.9), ImagePath.PIG);
        pig1.setIndex(1);
        pig2 = super.resources().addGameObject("pig2", (Main.S_RES.getX() - 300), 335, (int) (261 / 2.9), (int) (196 / 2.9), ImagePath.PIG);
        pig2.setIndex(2);
        pig3 = super.resources().addGameObject("pig3", (Main.S_RES.getX() - 300), 510, (int) (261 / 2.9), (int) (196 / 2.9), ImagePath.PIG);
        pig3.setIndex(3);
        pigs.add(pig1);
        pigs.add(pig2);
        pigs.add(pig3);
        player = super.resources().addGameObject("player", (Main.S_RES.getX() / 2) - 30, (Main.S_RES.getY() - 240), 40, 70, ImagePath.HUCK);
        super.resources().addGameObject("bible", (Main.S_RES.getX() / 2), 40, 50, 50, ImagePath.BIBLE).setIndex(5);
        this.main = main;
    }

    @Override
    public void update() {

        int keys[] = main.getKeys().getKeys();

        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == KeyEvent.VK_UP) {
                player.push(Direction.NORTH, 5);
            } else if (keys[i] == KeyEvent.VK_DOWN) {
                player.push(Direction.SOUTH, 5);
            } else if (keys[i] == KeyEvent.VK_LEFT) {
                player.push(Direction.WEST, 5);
            } else if (keys[i] == KeyEvent.VK_RIGHT) {
                player.push(Direction.EAST, 5);
            }
        }

        if (player.getX() < ((Main.S_RES.getX() / 2) - 165)) {
            player.getBody().setLinearVelocity(0, player.getBody().getLinearVelocity().y);
            player.push(Direction.EAST, 30);
        } else if (player.getX() > ((Main.S_RES.getX() / 2) + 120)) {
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

        if (player.isTouching().startsWith("pig")) {
            super.resources().addGameGraphic("background2", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), 1920f, 1080f, ImagePath.HD_BLACKBG).setIndex(9);
            super.resources().addText("gameOver", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), 30, 30, "You have tripped over a pig and died", Color.red).setIndex(10);
            super.update();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Church.class.getName()).log(Level.SEVERE, null, ex);
            }
            main.setRunStage(false);
        }
        if (player.isTouching().equals("bible")) {
            super.resources().addGameGraphic("background2", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), 1920f, 1080f, ImagePath.HD_BLACKBG).setIndex(9);
            super.resources().addText("gameOver", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), 30, 30, "you retrieve the bible", Color.red).setIndex(10);
            super.update();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Church.class.getName()).log(Level.SEVERE, null, ex);
            }
            main.tickStage();
            main.setRunStage(false);
        }

        int middleS = (int) (Main.S_RES.getX() / 2);
        for (int i = 0; i < pigs.size(); i++) {
            if (pigs.get(i).getX() > middleS) {
                pigs.get(i).push(Direction.WEST, (float) (60 * Math.random()));
            } else {
                pigs.get(i).push(Direction.EAST, (float) (60 * Math.random()));
            }
        }
        super.update();
    }

}
