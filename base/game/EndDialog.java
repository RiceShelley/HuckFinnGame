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
public class EndDialog extends Activity implements Updater {

    private Main main = null;
    private int step = 1;

    public EndDialog(Main main) {
        super("EndDialog", new ResourceManager(new World(new Vector2(0, 9.8f), true), 75, (1920 * 2), 1820 * 2), 1920, 1080);
        this.main = main;
        super.resources().addGameGraphic("background", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), Main.S_RES.getX(), Main.S_RES.getY(), ImagePath.DIALOG_END).setIndex(0);
    }

    public void displayText(String text) {
        super.resources().addText("text " + Integer.toString(step), Main.S_RES.getX() / 2, 60 * step, 20, 25, text, Color.MAGENTA).setIndex(step + 1);
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
                displayText("(Huck finds the bible and notices a peice of paper with \"half-past two\" writtin on it)");
                break;
            case 2:
                displayText("(he doesnt think much of it and returns it to Sophia. Sophia seams to be happy about the letter)");
                break;
            case 3:
                displayText("(Huck is lead into the swap by a slave and reunited with jim and their raft)");
                break;
            case 4:
                displayText("(The next day Huck learns that sophia Grangerford has run off with Harnery Sheperdson)");
                break;
            case 5:
                displayText("(A fight breaks out and two of the Grangerfords are killed one of them being buck)");
                break;
            case 6:
                displayText("(Huck and jim set off down stream and the chapter ends)");
                break;
            case 7:
                displayText("fin~");
            case 8:
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
                main.setStage(0);
                main.setRunStage(false);
                break;
            default:
                break;
        }
        super.update();
    }
}
