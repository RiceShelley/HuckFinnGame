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
import base.engine.lonefly.game.engine.entity.GameObject;
import base.engine.lonefly.game.engine.environment.Point;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author rootie
 */
public class StartScreen extends Activity implements Updater, Runnable, MouseInputListener {

    private boolean fadeBackIn = false;
    private Particle particle = null;
    private Main main = null;
    private boolean startGame = false;
    private boolean animationDone = false;
    private String lastTime = null;
    private boolean runHuckAndJimL = false;

    public StartScreen(Main main) {
        // Initialize activity 
        super("start screen", new ResourceManager(new World(new Vector2(0, 9.8f), true), 75, (1920 * 2), 1820 * 2), 1920, 1080);
        this.main = main;
        super.resources().addFrameGraphic("background", 0f, 0f, 1920f, 1080f, ImagePath.HD_BLACKBG).setIndex(0);
        super.resources().addText("name", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), 20, 80, "Adventures of Huckleberry Finn", Color.white).setIndex(1);
        BufferedImage texture = new GraphicLoader().loadImage(ImagePath.HD_BLACKBG);
        particle = new Particle(new Point(Main.S_RES.getX(), Main.S_RES.getY()), .3f, 0, 0, 0, 0, 5000, texture);
        particle.setIndex(5);
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
        if (runHuckAndJimL) {
            launchHuckAndJim();
            runHuckAndJimL = false;
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
                super.resources().removeTextGraphic("name");
                super.resources().addText("theGame", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), 20, 80, "The Game", Color.white).setIndex(2);
            } else if (i == 1) {
                try {
                    particle.bringBackToLife();
                    super.resources().removeTextGraphic("theGame");
                    super.resources().addGameGraphic("background", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), Main.S_RES.getX(), Main.S_RES.getY(), ImagePath.START_SCREEN).setIndex(1);
                    super.resources().addText("StartBut", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), 50, 100, "Play", Color.white).setIndex(2);
                    particle.setAlphaMin(.4f);
                    Thread.sleep(1500);
                    animationDone = true;
                    while (!startGame) {
                        Thread.sleep(30);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(StartScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        main.tickStage();
        main.setRunStage(false);
    }

    private void launchHuckAndJim() {
        String time = new Date().toString();
        if (time.equals(lastTime)) {
            return;
        }
        time += Double.toString(Math.random());
        GameObject huck = super.resources().addGameObject(time + " huck", (Main.S_RES.getX() + 100), (Main.S_RES.getY() + 100), 100, 170, ImagePath.HUCK);
        GameObject jim = super.resources().addGameObject(time + " jim", -100, (Main.S_RES.getY() + 100), 100, 170, ImagePath.JIM);
        huck.getBody().applyForceToCenter((float) (-450 * (Math.random() * 5) + 10), (float) (-500 * 3.5), true);
        huck.setIndex(3);
        huck.setProp(1f, .5f, .2f);
        huck.getBody().setAngularVelocity((float) Math.random());
        jim.getBody().applyForceToCenter((float) (450 * (Math.random() * 5) + 10), (float) (-500 * 3.5), true);
        jim.setProp(.9f, .5f, .2f);
        jim.getBody().setAngularVelocity((float) Math.random());
        jim.setIndex(4);
        lastTime = time;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (animationDone) {
            runHuckAndJimL = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if ((x > 657 && x < 858) && (y > 430 && y < 532) && animationDone) {
            startGame = true;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
