package base.engine.lonefly.game.engine.effects;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import base.engine.lonefly.game.Updater;
import base.engine.lonefly.game.engine.entity.Renderable;
import base.engine.lonefly.game.engine.environment.Point;

public class Particle extends BufferedImage implements Updater, Renderable {

    // texture for particle
    private BufferedImage texture;
    // Buffered image graphics
    private Graphics2D graph;
    // width and height of particle
    private Point size;
    // rate particle increases in width and height expansion
    private float expansionRate;
    // particle state false if particle is no longer visible
    private boolean alive;
    // boolean to prevent garbage collect from removing
    private boolean noGbg = false;
    // alpha value of buffered image
    private float alpha = 1;
    // life span in milliseconds of particle
    private float lifeSpan;
    // cords of particle
    private float x;
    private float y;
    // cords of particle origin
    private float oX;
    private float oY;
    // speed particle is moving away from the origin at
    private float vel;
    // distance particle is from origin
    private float rad;
    // direction particle is moving (in degrees)
    private float dir;
    // render index
    private int index;
    // lowest alpha val
    private float alphaMin = 0;

    public Particle(Point size, float expansionRate, float oX, float oY, float vel, float dir, float lifeSpan,
            BufferedImage texture) {
        // width / height = starting width / height + expansion rate * maximum
        // number of iterations of the fade thread
        super((int) (size.getX() + (expansionRate * (lifeSpan / (lifeSpan / 100)))),
                (int) (size.getY() + (expansionRate * (lifeSpan / (lifeSpan / 100)))), BufferedImage.TYPE_INT_ARGB);
        // super((int) size.getX(), (int) size.getY(),
        // BufferedImage.TYPE_INT_ARGB);
        alive = true;
        this.size = size;
        this.expansionRate = expansionRate;
        this.vel = vel;
        this.dir = dir;
        this.oX = oX;
        this.oY = oY;
        this.x = oX;
        this.y = oY;
        this.rad = 0;
        // life fade timing stuff
        this.lifeSpan = lifeSpan;
        double sleepTime = (double) (lifeSpan / 100);
        float alphaDVal = 1f / ((float) lifeSpan / (float) sleepTime);
        // graphical stuff
        this.texture = texture;
        graph = super.createGraphics();
        graph.setComposite(AlphaComposite.Clear);
        graph.drawRect(0, 0, super.getWidth(), super.getHeight());
        graph.setComposite(AlphaComposite.Src);
        graph.drawImage(texture, 0, 0, size.getPointI()[0], size.getPointI()[1], null);
        // start fade effect
        new Thread(new fade(sleepTime, alphaDVal)).start();
    }

    private class fade implements Runnable {

        // sleep time between alpha decreases
        private double sleepTime;
        // alpha decrease value
        private float alphaDVal;

        public fade(double sleepTime, float alphaDVal) {
            this.sleepTime = sleepTime;
            this.alphaDVal = alphaDVal;
        }

        @Override
        public void run() {
            float accumulativeTime = 0;
            while (alive) {
                if (accumulativeTime >= lifeSpan) {
                    alive = false;
                    break;
                }
                try {
                    Thread.sleep((long) sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                accumulativeTime += sleepTime;
                if (alpha > alphaMin + alphaDVal) {
                    alpha -= alphaDVal;
                    size.setX(size.getX() + expansionRate);
                    size.setY(size.getY() + expansionRate);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this, (int) x, (int) y, null);
        update();
    }

    @Override
    public void update() {
        // update particle position
        rad += vel;
        y = oY + ((float) Math.cos(Math.toRadians(dir)) * rad);
        x = oX + ((float) Math.sin(Math.toRadians(dir)) * rad);
        // redraw buffered image
        graph.setComposite(AlphaComposite.Clear);
        graph.fillRect(0, 0, super.getWidth(), super.getHeight());
        graph.setComposite(AlphaComposite.SrcOver.derive(alpha));
        graph.drawImage(texture, 0, 0, size.getPointI()[0], size.getPointI()[1], null);
    }

    public int bringBackToLife() {
        if (alive) {
            return -1;
        }
        graph = super.createGraphics();
        graph.setComposite(AlphaComposite.Clear);
        graph.drawRect(0, 0, super.getWidth(), super.getHeight());
        graph.setComposite(AlphaComposite.Src);
        graph.drawImage(texture, 0, 0, size.getPointI()[0], size.getPointI()[1], null);
        alive = true;
        alpha = 1f;
        double sleepTime = (double) (lifeSpan / 100);
        float alphaDVal = 1f / ((float) lifeSpan / (float) sleepTime);
        new Thread(new fade(sleepTime, alphaDVal)).start();
        return 0;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setNoGbg(boolean noGbg) {
        this.noGbg = noGbg;
    }

    public boolean getNoGbg() {
        return noGbg;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float val) {
        alpha = val;
    }

    public boolean getState() {
        return alive;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getAlphaMin() {
        return alphaMin;
    }

    public void setAlphaMin(float alphaMin) {
        this.alphaMin = alphaMin;
    }

    public int getIndex() {
        return index;
    }
}
