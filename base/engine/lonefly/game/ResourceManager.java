package base.engine.lonefly.game;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.World;

import base.engine.lonefly.game.engine.effects.Particle;
import base.engine.lonefly.game.engine.entity.GameObject;
import base.engine.lonefly.game.engine.entity.Graphic;
import base.engine.lonefly.game.engine.entity.Platform;
import base.engine.lonefly.game.engine.entity.Text;
import base.engine.lonefly.game.engine.environment.Camera;
import base.engine.lonefly.game.engine.util.ILevels;

public class ResourceManager implements Updater {

    private ArrayList<GameObject> gObjects;

    private ArrayList<Graphic> gGraphics;

    private ArrayList<Platform> platforms;

    private ArrayList<Graphic> fGraphics;

    private ArrayList<Text> textG;

    private ArrayList<Particle> particles;

    private World world;

    private Camera cam;

    private int pScale;

    private int gBoundsX;

    private int gBoundsY;

    public ResourceManager(World world, int pScale, int gBoundsX, int gBoundsY) {
        this.world = world;
        this.pScale = pScale;
        this.gBoundsX = gBoundsX;
        this.gBoundsY = gBoundsY;

        this.cam = new Camera();

        gGraphics = new ArrayList<Graphic>();
        gObjects = new ArrayList<GameObject>();
        platforms = new ArrayList<Platform>();
        fGraphics = new ArrayList<Graphic>();
        textG = new ArrayList<Text>();
        particles = new ArrayList<Particle>();
    }

    public GameObject addGameObject(String name, float x, float y, float width, float height, String resourcePath) {
        // create game object
        GameObject gO = new GameObject(name, pTom(x), pTom(y), width, height, pScale, world, resourcePath);
        // add to activities game object data structure
        gObjects.add(gO);
        // log game object creation
        LoneflyGE.LOG.println("Game Object " + gO + " added to " + this, ILevels.SMALLTALK);
        // return game object so user may have
        return gO;
    }

    public Graphic addGameGraphic(String name, float x, float y, float width, float height, String resourcePath) {
        // create new graphic
        Graphic g = new Graphic(name, x, y, width, height, 1, resourcePath);
        // add to game graphics data structure
        gGraphics.add(g);
        // log graphic creation
        LoneflyGE.LOG.println("Game graphic: " + g + " added to " + this, ILevels.SMALLTALK);
        // return newly created graphic object
        return g;
    }

    public Graphic addGameGraphic(String name, float x, float y, float width, float height, BufferedImage graphic) {
        // create graphic
        Graphic g = new Graphic(name, x, y, width, height, 1, graphic);
        // add to game graphics data structure
        gGraphics.add(g);
        // log graphic creation
        LoneflyGE.LOG.println("Game graphic: " + g + " added to " + this, ILevels.SMALLTALK);
        // return graphic
        return g;
    }

    public Graphic addFrameGraphic(String name, float x, float y, float width, float height, String resourcePath) {
        // create new graphic
        Graphic g = new Graphic(name, x, y, width, height, 1, resourcePath);
        // add to frame graphics data structure
        fGraphics.add(g);
        // log graphic creation
        LoneflyGE.LOG.println("Frame graphic: " + g + " added to " + this, ILevels.SMALLTALK);
        // return newly created graphic object
        return g;
    }

    public Graphic addFrameGraphic(String name, float x, float y, float width, float height, BufferedImage graphic) {
        // create graphic
        Graphic g = new Graphic(name, x, y, width, height, 1, graphic);
        // add to game graphics data structure
        fGraphics.add(g);
        // log graphic creation
        LoneflyGE.LOG.println("Frame graphic: " + g + " added to " + this, ILevels.SMALLTALK);
        // return graphic
        return g;
    }

    public Platform addPlatform(String name, float x, float y, float width, float height, String resourcePath) {
        // create new platform
        Platform p = new Platform(name, pTom(x), pTom(y), width, height, pScale, world, resourcePath);
        // add to platform data structure
        platforms.add(p);
        // log platform creation
        LoneflyGE.LOG.println("Platform: " + p + " added to " + this, ILevels.SMALLTALK);
        // return newly created platform
        // return p;
        return p;
    }

    public Text addText(String name, float x, float y, float width, float height, String text, Color color) {
        // create new text object
        Text gText = new Text(name, x, y, width, height, text, color);
        // add text object to gTextObjects
        textG.add(gText);
        // log text creation
        LoneflyGE.LOG.println("Text: " + gText + " added to " + this, ILevels.SMALLTALK);
        // return text object
        return gText;
    }

    public void addParticle(Particle particle) {
        particles.add(particle);
    }

    public void addParticleArray(ArrayList<Particle> particles) {
        for (int i = 0; i < particles.size(); i++) {
            this.particles.add(particles.get(i));
        }
    }

    // garbage collect if a entity goes out of world delete it <- i'm harsh ._.
    private void garbageCollect() {
        ArrayList<GameObject> gOMarked = new ArrayList<GameObject>();
        for (GameObject gO : gObjects) {
            if (gO.getX() > (gBoundsX * 2) || gO.getX() < (gBoundsX * -1) || gO.getY() > (gBoundsX * 2)
                    || gO.getY() < (gBoundsX * -1)) {
                // mark for deletion
                gOMarked.add(gO);
            }
        }
        // remove game objects marked for removal
        for (GameObject gO : gOMarked) {
            if (removeGameObject(gO)) {
                // log removal
                LoneflyGE.LOG.println(this + gO.toString() + " was deleted by garbage collect!!!", ILevels.MSG);
            } else {
                // log failed deletion
                LoneflyGE.LOG.println(this + " garbage collect failed to delete <" + gO.toString() + ">",
                        ILevels.ERROR);
            }
        }
        // check platforms
        ArrayList<Platform> platMarked = new ArrayList<Platform>();
        for (Platform p : platforms) {
            if (p.getX() > (gBoundsX * 2) || p.getX() < (gBoundsX * -1) || p.getY() > (gBoundsX * 2)
                    || p.getY() < (gBoundsX * -1)) {
                // mark for deletion
                platMarked.add(p);
            }
        }
        // remove platforms marked for removal
        for (Platform p : platMarked) {
            if (removePlatform(p)) {
                // log removal
                LoneflyGE.LOG.println(this + p.toString() + " was deleted by garbage collect!!!", ILevels.MSG);
            } else {
                // log failed removal 
                LoneflyGE.LOG.println(this + " garbage collect failed to delete <" + p.toString() + ">",
                        ILevels.ERROR);
            }
        }

        /* check particles for dead particles that should be removed
		ArrayList<Particle> partMarked = new ArrayList<Particle>();
		for (Particle p : particles) {
			if (!p.getState()) {
				partMarked.add(p);
			}
		}
		// remove particles marked for removal
		for (Particle p : partMarked) {
			if (removeParticle(p)) {
				// log removal
				LoneflyGE.LOG.println(this + p.toString() + " was deleted by garbage collect!!!", ILevels.MSG);
			} else {
				// log failed removal
				LoneflyGE.LOG.println(this + " garbage collect failed to delete <" + p.toString() + ">",
						ILevels.ERROR);
			}
		}*/
        for (int i = 0; i < particles.size(); i++) {
            if (particles.get(i) != null) {
                if (!particles.get(i).getState() && !particles.get(i).getNoGbg()) {
                    particles.remove(i);
                }
            }
        }
    }

    /*
	 * convert pixel values to box2D meters with pScale
     */
    public float pTom(float val) {
        return val / pScale;
    }

    public void addGameObject(GameObject gO) {
        gObjects.add(gO);
    }

    public void addPlatform(Platform p) {
        platforms.add(p);
    }

    public void addGameGraphic(Graphic g) {
        gGraphics.add(g);
    }

    public void addFrameGraphic(Graphic g) {
        fGraphics.add(g);
    }

    public void addTextGraphic(Text t) {
        textG.add(t);
    }

    public boolean removeGameObject(String name) {
        for (int i = 0; i < gObjects.size(); i++) {
            if (gObjects.get(i).getName().equals(name)) {
                gObjects.get(i).destroy();
                gObjects.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean removeGameObject(GameObject gO) {
        for (int i = 0; i < gObjects.size(); i++) {
            if (gObjects.get(i).equals(gO)) {
                gObjects.get(i).destroy();
                gObjects.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean removePlatform(String name) {
        for (int i = 0; i < platforms.size(); i++) {
            if (platforms.get(i).getName().equals(name)) {
                platforms.get(i).destroy();
                platforms.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean removePlatform(Platform p) {
        for (int i = 0; i < platforms.size(); i++) {
            if (platforms.get(i).equals(p)) {
                platforms.get(i).destroy();
                platforms.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean removeGameGraphic(String name) {
        for (int i = 0; i < gGraphics.size(); i++) {
            if (gGraphics.get(i).getName().equals(name)) {
                gGraphics.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean removeGameGraphic(Graphic g) {
        for (int i = 0; i < gGraphics.size(); i++) {
            if (gGraphics.get(i).equals(g)) {
                gGraphics.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean removeFrameGraphic(String name) {
        for (int i = 0; i < fGraphics.size(); i++) {
            if (fGraphics.get(i).getName().equals(name)) {
                fGraphics.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean removeFrameGraphic(Graphic g) {
        for (int i = 0; i < fGraphics.size(); i++) {
            if (fGraphics.get(i).equals(g)) {
                fGraphics.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean removeTextGraphic(String name) {
        for (int i = 0; i < textG.size(); i++) {
            if (textG.get(i).getName().equals(name)) {
                textG.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean removeTextGraphic(Text t) {
        for (int i = 0; i < textG.size(); i++) {
            if (textG.get(i).equals(t)) {
                textG.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean removeParticle(Particle p) {
        for (int i = 0; i < particles.size(); i++) {
            if (particles.get(i).equals(p)) {
                particles.remove(i);
                return true;
            }
        }
        return false;
    }

    // getters
    public World getWorld() {
        return world;
    }

    public ArrayList<GameObject> getGameObjects() {
        return gObjects;
    }

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }

    public ArrayList<Graphic> getGameGraphics() {
        return gGraphics;
    }

    public ArrayList<Graphic> getFrameGraphics() {
        return fGraphics;
    }

    public ArrayList<Text> getTextGraphics() {
        return textG;
    }

    public ArrayList<Particle> getParticles() {
        return particles;
    }

    public Camera getCamera() {
        return cam;
    }

    @Override
    public void update() {
        // update Box2D
        world.step(1 / 45f, 6, 2);
        if (gBoundsX > 0 && gBoundsY > 0) {
            garbageCollect();
        }
    }
}
