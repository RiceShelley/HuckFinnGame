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
import base.engine.lonefly.game.engine.environment.Direction;
import base.engine.lonefly.game.engine.environment.Point;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author rootie
 */
public class Shooter extends Activity implements Updater, MouseInputListener {

    private Main main;

    private GameObject sight = null;
    private GameObject shep = null;
    private int bulletC = 15;
    private boolean fireGun = false;

    private boolean introAnimation = true;
    private boolean runEndAnimation = false;

    public Shooter(Main main) {
        super("shooter", new ResourceManager(new World(new Vector2(0, 0), true), 75, (1920 * 2), 1820 * 2), 1920, 1080);
        this.main = main;
        super.resources().addGameGraphic("background", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), Main.S_RES.getX(), Main.S_RES.getY(), ImagePath.FOREST).setIndex(0);
        sight = super.resources().addGameObject("sight", 0, 0, 100, 100, ImagePath.SIGHTS);
        sight.setIndex(3);
        shep = super.resources().addGameObject("sheperdson", (Main.S_RES.getX() / 2), (Main.S_RES.getY() / 2), 135, 300, ImagePath.SHEPERDSON);
        shep.setIndex(2);
        shep.setProp(.2f, 1f, 1f);
        // draw bullets count
        for (int i = 0; i < bulletC; i++) {
            super.resources().addGameGraphic("bullet " + (i + 1), ((Main.S_RES.getX() - 30) - (i * 35)), (Main.S_RES.getY() - 50), 25, 25, ImagePath.BULLET).setIndex(i + 10);
        }
    }

    public void displayText(String text, int index) {
        super.resources().removeTextGraphic("text " + index);
        super.resources().addText("text " + index, ((Main.S_RES.getX() / 2) + 400), (Main.S_RES.getY() / 2) - 100, 30, 30, text, Color.WHITE).setIndex(index);
        super.update();
    }

    @Override
    public void update() {

        if (introAnimation) {
            super.update();
            super.resources().addGameGraphic("buck", (Main.S_RES.getX() / 2) + 500, (Main.S_RES.getY() / 2) + 100, 135, 300, ImagePath.BUCK_GUN).setIndex(7);
            displayText("....", 8);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Shooter.class.getName()).log(Level.SEVERE, null, ex);
            }
            displayText("Theres! that sheperdson!", 8);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Shooter.class.getName()).log(Level.SEVERE, null, ex);
            }
            displayText("We have only got 15 shots! make 'em count!", 8);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Shooter.class.getName()).log(Level.SEVERE, null, ex);
            }
            super.resources().removeTextGraphic("text " + 8);
            super.resources().removeGameGraphic("buck");
            super.update();
            introAnimation = false;
        }

        if (runEndAnimation) {
            super.resources().removeGameObject("sheperdson");
            super.update();
            super.resources().addGameGraphic("buck", (Main.S_RES.getX() / 2) + 500, (Main.S_RES.getY() / 2) + 100, 135, 300, ImagePath.BUCK_GUN).setIndex(7);
            displayText("rats he got away George", 8);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Shooter.class.getName()).log(Level.SEVERE, null, ex);
            }
            displayText("we'd best be geting out of here", 8);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Shooter.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Shooter.class.getName()).log(Level.SEVERE, null, ex);
            }
            main.tickStage();
            main.setRunStage(false);
        }

        Point mP = main.getMCords();
        if (mP != null) {
            sight.setX(mP.getX() - 3);
            sight.setY(mP.getY() - 30);
        }
        // get dist to shep <- thx mrs roland
        double dist = Math.sqrt(Math.pow((shep.getX() - sight.getX()), 2) + Math.pow((shep.getY() - sight.getY()), 2));
        if (dist < 500.0) {
            if (shep.getX() < sight.getX()) {
                shep.push(Direction.WEST, 100f);
            } else if (shep.getX() > sight.getX()) {
                shep.push(Direction.EAST, 100f);
            }
            if (shep.getY() < sight.getY()) {
                shep.push(Direction.NORTH, 100f);
            } else if (shep.getY() > sight.getY()) {
                shep.push(Direction.SOUTH, 100f);
            }
        }

        if (shep.getY() < -200) {
            shep.setY(Main.S_RES.getY() + 200);
        } else if (shep.getY() > Main.S_RES.getY() + 200) {
            shep.setY(-100);
        }
        if (shep.getX() < -100) {
            shep.setX(Main.S_RES.getX() + 100);
        } else if (shep.getX() > Main.S_RES.getX() + 100) {
            shep.setX(-100);
        }

        // pull shep back to middle
        int cX = (int) (Main.S_RES.getX() / 2);
        int cY = (int) (Main.S_RES.getY() / 2);
        if (shep.getX() < cX) {
            shep.push(Direction.EAST, 60f);
        } else {
            shep.push(Direction.WEST, 60f);
        }

        if (shep.getY() < cY) {
            shep.push(Direction.SOUTH, 60f);
        } else {
            shep.push(Direction.NORTH, 60f);
        }

        // regulate speed
        if (shep.getBody().getLinearVelocity().x > 10) {
            shep.getBody().setLinearVelocity(9, shep.getBody().getLinearVelocity().y);
        } else if (shep.getBody().getLinearVelocity().x < -10) {
            shep.getBody().setLinearVelocity(-9, shep.getBody().getLinearVelocity().y);
        }
        if (shep.getBody().getLinearVelocity().y > 10) {
            shep.getBody().setLinearVelocity(shep.getBody().getLinearVelocity().x, 9);
        } else if (shep.getBody().getLinearVelocity().y < -10) {
            shep.getBody().setLinearVelocity(shep.getBody().getLinearVelocity().x, -9);
        }

        // fire gun if need b
        if (fireGun) {
            super.resources().removeGameGraphic("bullet " + bulletC);
            BufferedImage texture = null;
            Random randNum = new Random();
            int rand = randNum.nextInt((2 - 0) + 1) + 0;
            switch (rand) {
                case 0:
                    texture = new GraphicLoader().loadImage(ImagePath.GUNSOUND0);
                    break;
                case 1:
                    texture = new GraphicLoader().loadImage(ImagePath.GUNSOUND1);
                    break;
                case 2:
                    texture = new GraphicLoader().loadImage(ImagePath.GUNSOUND2);
                    break;
                default:
                    break;
            }
            Particle particle = new Particle(new Point(100, 100), .3f, (sight.getX() - 3) - (70 / 2), (sight.getY() - 30) - (70 / 2), 0, 0, 5000, texture);
            particle.setIndex(20 + bulletC);
            particle.setNoGbg(true);
            super.resources().addParticle(particle);
            bulletC--;
            fireGun = false;
        }
        if (bulletC == 0) {
            //game over
            runEndAnimation = true;
        }
        super.update();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        fireGun = true;
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }
}
