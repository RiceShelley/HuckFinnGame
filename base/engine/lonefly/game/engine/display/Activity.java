package base.engine.lonefly.game.engine.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import base.engine.lonefly.game.LoneflyGE;
import base.engine.lonefly.game.ResourceManager;
import base.engine.lonefly.game.Updater;
import base.engine.lonefly.game.engine.effects.Particle;
import base.engine.lonefly.game.engine.entity.Entity;
import base.engine.lonefly.game.engine.entity.GameObject;
import base.engine.lonefly.game.engine.entity.Graphic;
import base.engine.lonefly.game.engine.entity.Platform;
import base.engine.lonefly.game.engine.entity.Text;
import base.engine.lonefly.game.engine.environment.CollisionsListener;
import base.engine.lonefly.game.engine.util.ILevels;

/*
 * Author: Rice Shelley
 * Version: 3/14/2016
 * Purpose: Handle displays and uploading graphics to them 
 */
public class Activity extends JPanel implements Updater {

    private static final long serialVersionUID = 1L;
    // name given to activity
    private String name;
    // buffered image that contains all the activities graphics
    private BufferedImage display;
    // width / height of display
    private int renderW;
    private int renderH;
    // render index range <- render index defines order that graphics are
    // displayed
    private int minIR;
    private int maxIR;
    // resource manager
    private ResourceManager rMan;

    public Activity(String name, ResourceManager rMan, int renderW, int renderH) {
        this.name = name;
        this.renderW = renderW;
        this.renderH = renderH;
        this.rMan = rMan;
        // default 0 - 10 <- automatically adjusted when range is broken
        maxIR = 0;
        maxIR = 10;
        // Initialize display to size defined by renderW and renderH
        display = new BufferedImage(renderW, renderH, BufferedImage.TYPE_INT_RGB);
        /*
		 * create collision listener - when collisions are made this object
		 * calls to gameCollision() <- method in this class. gameCollision() can
		 * be overridden by user to create events on certain collisions
         */
        new CollisionsListener(this);

        LoneflyGE.LOG.println("new Activity created!", ILevels.SMALLTALK);
    }

    // get display <- display being the main container for everything being
    // rendered (except fGraphics)
    public BufferedImage getDisplay() {
        return display;
    }

    // method of JPanel overridden to upload graphics to panel
    /*
	 * note if graphic not found for a game entity then it will be shown as a
	 * color coordinated cubes - Color key: Blue = game object / yellow =
	 * graphic / green = platform
     */
    @Override
    public void paintComponent(Graphics g) {
        // create new display
        display = new BufferedImage(renderW, renderH, BufferedImage.TYPE_INT_RGB);
        /*
		 * disG2D is now a reference to buffered image display's Graphic2D
		 * object so if we draw to disG2D since its a reference to displays
		 * graphics2D object we are drawing to the display buffered image
         */
        Graphics2D disG2D = display.createGraphics();
        // clear JPanel
        g.clearRect(0, 0, getWidth(), getHeight());
        // get temporary resources
        ArrayList<Graphic> gameGraphics = new ArrayList<Graphic>(rMan.getGameGraphics());
        ArrayList<Platform> platforms = new ArrayList<Platform>(rMan.getPlatforms());
        ArrayList<GameObject> gameObjects = new ArrayList<GameObject>(rMan.getGameObjects());
        ArrayList<Text> textGraphics = new ArrayList<Text>(rMan.getTextGraphics());
        ArrayList<Particle> particles = new ArrayList<Particle>(rMan.getParticles());
        // draw background black
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        for (int cIndex = minIR; cIndex <= maxIR; cIndex++) {
            // draw game graphics
            for (int i = 0; i < gameGraphics.size(); i++) {
                Graphic gG = gameGraphics.get(i);
                // adjust render index range if need be
                if (gG.getIndex() < minIR) {
                    minIR = gG.getIndex();
                } else if (gG.getIndex() > maxIR) {
                    maxIR = gG.getIndex();
                }
                if (gG.getIndex() == cIndex) {
                    gG.render(disG2D);
                    gameGraphics.remove(i);
                }
            }
            for (int i = 0; i < platforms.size(); i++) {
                Platform p = platforms.get(i);
                // adjust render index range if need be
                if (p.getIndex() < minIR) {
                    minIR = p.getIndex();
                } else if (p.getIndex() > maxIR) {
                    maxIR = p.getIndex();
                }
                if (p.getIndex() == cIndex) {
                    p.render(disG2D);
                    platforms.remove(i);
                }
            }
            for (int i = 0; i < gameObjects.size(); i++) {
                GameObject gO = gameObjects.get(i);
                // adjust render index range if need be
                if (gO.getIndex() < minIR) {
                    minIR = gO.getIndex();
                } else if (gO.getIndex() > maxIR) {
                    maxIR = gO.getIndex();
                }
                if (gO.getIndex() == cIndex) {
                    gO.render(disG2D);
                    gameObjects.remove(i);
                }
            }
            // draw graphical text
            for (int i = 0; i < textGraphics.size(); i++) {
                Text t = textGraphics.get(i);
                // adjust render index range if need be
                if (t.getIndex() < minIR) {
                    minIR = t.getIndex();
                } else if (t.getIndex() > maxIR) {
                    maxIR = t.getIndex();
                }
                if (t.getIndex() == cIndex) {
                    t.render(disG2D);
                    textGraphics.remove(i);
                }
            }
            for (int i = 0; i < particles.size(); i++) {
                Particle p = particles.get(i);
                if (p != null) {
                    // adjust render index range if need be
                    if (p.getIndex() < minIR) {
                        minIR = p.getIndex();
                    } else if (p.getIndex() > maxIR) {
                        maxIR = p.getIndex();
                    }
                    if (p.getIndex() == cIndex) {
                        p.render(disG2D);
                        particles.set(i, null);
                    }
                }
            }
        }
        // render display <- display being a container for all the graphics
        // above
        g.drawImage(display, (int) (rMan.getCamera().getX() * -1), (int) (rMan.getCamera().getY() * -1), null);
        // draw frame graphics directly to JPanel
        for (Graphic gG : rMan.getFrameGraphics()) {
            gG.render(disG2D);
        }
    }

    /*
	 * called by CollisionListener every time a game object or platform have
	 * collisions method is intended to be overridden by user via implementing
	 * the Collision interface in lonefly.game.engine.util
     */
    public void gameCollision(Entity a, Entity b) {
        LoneflyGE.LOG.println(a + " Collided with " + b, ILevels.SMALLTALK);
    }

    public ResourceManager resources() {
        return rMan;
    }

    // update Activity
    @Override
    public void update() {
        // repaint activitie's JPanel
        repaint();
        rMan.update();
    }

    // kind of self explanatory
    @Override
    public String toString() {
        return "Activity " + name + " ";
    }
}
