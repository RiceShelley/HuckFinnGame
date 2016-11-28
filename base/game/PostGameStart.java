/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.game;

import base.engine.lonefly.game.ResourceManager;
import base.engine.lonefly.game.Updater;
import base.engine.lonefly.game.engine.display.Activity;
import base.engine.lonefly.game.engine.display.GraphicLoader;
import base.engine.lonefly.game.engine.effects.Particle;
import base.engine.lonefly.game.engine.environment.Point;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rootie
 */
public class PostGameStart extends Activity implements Updater, Runnable {

    private boolean fadeBackIn = false;
    private Particle particle = null;
    private Main main = null;

    public PostGameStart(Main main) {
        // Initialize activity 
        super("postGameStart", new ResourceManager(new World(new Vector2(0, 9.8f), true), 75, (1920 * 2), 1820 * 2), 1920, 1080);
        this.main = main;
        super.resources().addFrameGraphic("background", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), 1920f, 1080f, ImagePath.HD_BLACKBG).setIndex(0);
        super.resources().addText("chapterTXT", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), 20, 80, "Chapters 16, 17, 18", Color.white).setIndex(3);
        BufferedImage texture = new GraphicLoader().loadImage(ImagePath.HD_BLACKBG);
        particle = new Particle(new Point(Main.S_RES.getX(), Main.S_RES.getY()), .3f, 0, 0, 0, 0, 5000, texture);
        particle.setIndex(10);
        particle.setNoGbg(true);
        super.resources().addParticle(particle);
        new Thread(this).start();
    }

    @Override
    public void update() {
        if (fadeBackIn) {
            particle.setAlpha((particle.getAlpha() + .01f));
            if (particle.getAlpha() >= 1) {
                particle.setAlpha(1f);
                fadeBackIn = false;
            }
        }
        super.update();
    }

    @Override
    public void run() {
        for (int i = 0; i < 2; i++) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(StartScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (i == 1) {
                while (true) {
                    int keys[] = main.getKeys().getKeys();
                    boolean exitLoop = false;
                    for (int i2 = 0; i2 < keys.length; i2++) {
                        if (keys[i2] == KeyEvent.VK_SPACE) {
                            exitLoop = true;
                        }
                    }
                    if (exitLoop) {
                        break;
                    }
                }
            }
            fadeBackIn = true;
            while (fadeBackIn) {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException ex) {
                    Logger.getLogger(StartScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (i == 0) {
                particle.bringBackToLife();
                super.resources().removeTextGraphic("chapterTXT");
                super.resources().addText("quote0", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), 20, 30, "\"...I reckoned I wouldn't bother no more about it,", Color.white).setIndex(1);
                super.resources().addText("quote1", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2) + 50, 20, 30, "but after this always do whichever comes handiest at the time.\"", Color.white).setIndex(2);
            }
        }
        main.tickStage();
        main.setRunStage(false);
    }

}
