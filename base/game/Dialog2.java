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
public class Dialog2 extends Activity implements Updater {

    private Main main = null;
    private int step = 1;

    public Dialog2(Main main) {
        super("dialog2", new ResourceManager(new World(new Vector2(0, 9.8f), true), 75, (1920 * 2), 1820 * 2), 1920, 1080);
        this.main = main;
        super.resources().addGameGraphic("background", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), Main.S_RES.getX(), Main.S_RES.getY(), ImagePath.DIALOG_TWO).setIndex(0);
    }

    public void displayText(String text) {
        super.resources().addText("text " + Integer.toString(step), Main.S_RES.getX() / 2, 40 * step, 10, 20, text, Color.ORANGE).setIndex(step + 1);
    }

    public void displayText(String text, int index) {
        super.resources().addText("text " + Integer.toString(step), Main.S_RES.getX() / 2, 40 * index, 10, 20, text, Color.ORANGE).setIndex(index + 1);
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
                displayText("(Huck begins talking with the men on the raft)");
            case 2:
                displayText("Raft man: We are looking for run away slaves, have you seen any slaves around? Whos on that raft over there may we search it?");
                break;
            case 3:
                displayText("press 1 to lie / press 2 to tell the truth");
                super.resources().addGameGraphic("raftMan0", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), 546, 666, ImagePath.RAFTMAN_0).setIndex(20);
                super.update();
                boolean cM = false;
                while (true) {
                    keys = main.getKeys().getKeys();
                    for (int i = 0; i < keys.length; i++) {
                        if (keys[i] == KeyEvent.VK_1) {
                            displayText("(you lie to the raft man)", 4);
                            super.resources().removeGameGraphic("raftMan0");
                            super.resources().addGameGraphic("raftMan1", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), 546, 666, ImagePath.RAFTMAN_1).setIndex(20);
                            super.update();
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Dialog2.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            super.resources().removeGameGraphic("raftMan1");
                            cM = true;
                            step++;
                        } else if (keys[i] == KeyEvent.VK_2) {
                            displayText("jim was caught, and you are a horrible person game over.", 4);
                            super.resources().removeGameGraphic("raftMan0");
                            super.resources().addGameGraphic("raftMan2", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), 546, 666, ImagePath.RAFTMAN_2).setIndex(20);
                            super.update();
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Dialog2.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            main.setRunStage(false);
                            cM = true;
                            step++;
                        }
                    }
                    if (cM) {
                        break;
                    }
                }
                break;
            case 5:
                displayText("Huck: Sir, I don’t think that’s a good idea, my family is suffering from smallpox, ");
                break;
            case 6:
                displayText("and they are all on the raft right now, I’m sure you don’t want to touch them.");
                break;
            case 7:
                displayText("raft man: OH Jesus, here is something that might help you, poor young boy. (raft man gives huck 40$)");
                break;
            case 8:
                displayText("(huck meets back up with jim)");
                break;
            case 9:
                displayText("Huck: Jim we need to leave this place right now!, I just saw some people that are looking for escaped slaves.");
                break;
            case 10:
                displayText("(Huck and Jim get back on the raft and drift down stream they soon fall asleep)");
                break;
            case 11:
                displayText("(upon waking up they realize their canoe has been stolen)");
                break;
            case 12:
                displayText("Huck: ?????, Where is the canoe?");
                break;
            case 13:
                displayText("Huck: Jim, Wake up, our canoe is gone!");
                break;
            case 14:
                displayText("Jim: It was just there last night, someone must've stole it from us!");
                break;
            case 15:
                displayText("(A steam boat soon hits their raft they jump off just in time huck swims to shore where he is meet by barking dogs.)");
                break;
            case 16:
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
