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
public class Dialog1 extends Activity implements Updater {

    private Main main = null;
    private int step = 1;

    public Dialog1(Main main) {
        super("dialog1", new ResourceManager(new World(new Vector2(0, 9.8f), true), 75, (1920 * 2), 1820 * 2), 1920, 1080);
        this.main = main;
        super.resources().addGameGraphic("background", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), Main.S_RES.getX(), Main.S_RES.getY(), ImagePath.DIALOG_ONE).setIndex(0);
    }

    public void displayText(String text) {
        super.resources().addText("text " + Integer.toString(step), Main.S_RES.getX() / 2, 70 * step, 10, 20, text, Color.ORANGE).setIndex(step + 1);
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
                displayText("Huck: Jim, how far we are from Cairo?");
            case 2:
                displayText("Jim: Well... We might already passed it.");
                break;
            case 3:
                displayText("Huck: How do we know?");
                break;
            case 4:
                displayText("Jim: We don’t know…");
                break;
            case 5:
                displayText("(Huck looks around)");
                break;
            case 6:
                displayText("Jim: Don’t worry about it, I’m sure we will be there soon, and you and I will be free!");
                break;
            case 7:
                displayText("Jim: I’m gonna make a lot of money, so I can buy my wife and my children back from their masters,");
                break;
            case 8:
                displayText("and you know what? If their masters refuse to give up my family, I’ll get some abolitionists to kidnap them!");
                break;
            case 9:
                displayText("(Huck looked at Jim but didn’t say anything)");
                break;
            case 10:
                displayText("Huck: Umm... Jim, I think we might be already at Cairo.");
                break;
            case 11:
                displayText("Huck: Let me get on this canoe to check around here...");
                break;
            case 12:
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
