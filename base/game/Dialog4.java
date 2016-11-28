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
public class Dialog4 extends Activity implements Updater {

    private Main main = null;
    private int step = 1;

    public Dialog4(Main main) {
        super("dialog4", new ResourceManager(new World(new Vector2(0, 9.8f), true), 75, (1920 * 2), 1820 * 2), 1920, 1080);
        this.main = main;
        super.resources().addGameGraphic("background", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), Main.S_RES.getX(), Main.S_RES.getY(), ImagePath.DIALOG_FOUR).setIndex(0);
    }

    public void displayText(String text) {
        super.resources().addText("text " + Integer.toString(step), Main.S_RES.getX() / 2, 40 * step, 15, 25, text, Color.MAGENTA).setIndex(step + 1);
    }

    public void displayText(String text, int index) {
        super.resources().addText("text " + Integer.toString(step), Main.S_RES.getX() / 2, 40 * index, 15, 25, text, Color.MAGENTA).setIndex(index + 1);
    }

    @Override
    public void update() {
        int keys[] = main.getKeys().getKeys();
        boolean keypress = false;
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == KeyEvent.VK_SPACE) {
                step++;
                keypress = true;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Dialog1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (!keypress && step != 1) {
            return;
        }
        switch (step) {
            case 1:
                displayText("Huck: Buck why did you shoot at that man");
                break;
            case 2:
                displayText("Buck: Well George... It’s all because the Grangerfords are in a feud with a");
                break;
            case 3:
                displayText("neighboring a family, the Shepherdsons.");
                break;
            case 4:
                displayText("Huck: What is a feud buck?");
                break;
            case 5:
                displayText("Buck: A feud is when two families fight don't you know that much?");
                break;
            case 6:
                displayText("Huck: but why?");
                break;
            case 7:
                displayText("Buck: No one can remember how or why the feud started,");
                break;
            case 8:
                displayText("Buck: but in the last year, two people have been killed, including a fourteen-year-old Grangerford.");
                break;
            case 9:
                displayText("(the family later goes to church with the Shepherdsons,");
                break;
            case 10:
                displayText("and they sit with rifles held between their knees as the minister preaches about brotherly love)");
                break;
            case 11:
                displayText("After church Sophia Grangerford realizes that she has left her bible and,");
                break;
            case 12:
                displayText("asks huck to go and retrieve the bible she left behind)");
                break;
            case 13:
                displayText("(huck goes and does so)");
                break;
            case 14:
                // fade out
                BufferedImage texture = new GraphicLoader().loadImage(ImagePath.HD_BLACKBG);
                Particle particle = new Particle(new Point(Main.S_RES.getX(), Main.S_RES.getY()), .3f, 0, 0, 0, 0, 5000, texture);
                particle.setAlpha(.01f);
                particle.setIndex(20);
                particle.setNoGbg(true);
                particle.setAlive(false);
                super.resources().addParticle(particle);
                while (true) {
                    particle.setAlpha((particle.getAlpha() + .01f));
                    super.update();
                    if (particle.getAlpha() >= 1) {
                        particle.setAlpha(1f);
                        break;
                    }
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Dialog1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                main.tickStage();
                main.setRunStage(false);
                break;
            default:
                break;
        }
        super.update();
    }
}
