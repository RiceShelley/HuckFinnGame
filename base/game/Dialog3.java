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
public class Dialog3 extends Activity implements Updater {

    private Main main = null;
    private int step = 1;

    public Dialog3(Main main) {
        super("dialog2", new ResourceManager(new World(new Vector2(0, 9.8f), true), 75, (1920 * 2), 1820 * 2), 1920, 1080);
        this.main = main;
        super.resources().addGameGraphic("background", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), Main.S_RES.getX(), Main.S_RES.getY(), ImagePath.DIALOG_THREE).setIndex(0);
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
                displayText("(Huck arrives on shore near a farmhouse a man calls out to him)");
            case 2:
                displayText("Man: Who's out there");
                break;
            case 3:
                displayText("Huck: Uhh.. Sir, my name is Uhh... George, George Jackson, I fell down from a steam boat.");
                break;
            case 4:
                displayText("Man: follow me");
                break;
            case 5:
                displayText("(Huck follows the man to his house where the family seems to be on edge, and with guns)");
                break;
            case 6:
                displayText("Man: So... your name is George? Jackson?");
                break;
            case 7:
                displayText("Huck: Yes sir, my whole family was on a steam boat, and I fell down from it.");
                break;
            case 8:
                displayText("Man: Buck, go get some dry clothes for our guest George.");
                break;
            case 9:
                displayText("Buck: ...");
                break;
            case 10:
                super.resources().addGameGraphic("buckNoGun", 100, (Main.S_RES.getY() - 300), (231 / 2), (552 / 2), ImagePath.BUCK).setIndex(20);
                displayText("Buck: you better not let me know if you're a Shepherdson.");
                break;
            case 11:
                displayText("I would have killed a Shepherdson had there been any Shepherdsons...");
                break;
            case 12:
                displayText("(buck hands the clothes to Huck)");
                break;
            case 13:
                displayText("Buck: I reckon you're not a Shepherdsons, don't look like one.");
                break;
            case 14:
                displayText("Colonel Grangerford: Welcome George, I’m the owner of this house.");
                break;
            case 15:
                displayText("I'v owned this house for a long time, and I have a estate with over a hundred slaves");
                break;
            case 16:
                displayText("Huck: ...");
                break;
            case 17:
                displayText("(days pass and huck's stays around the house living with the Grangerfords)");
                break;
            case 18:
                displayText("One particular day huck and buck are out in the woods and see a SHEPHERDSON!");
                break;
            case 19:
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
