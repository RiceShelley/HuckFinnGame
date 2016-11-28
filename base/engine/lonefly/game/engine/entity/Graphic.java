package base.engine.lonefly.game.engine.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.engine.lonefly.game.LoneflyGE;
import base.engine.lonefly.game.engine.display.HasGraphic;
import base.engine.lonefly.game.engine.util.ILevels;

/*
 * Author: Rice Shelley
 * Version: 3/14/2016
 * Purpose: class for just uploading graphics such as images 
 * that can be moved around but have no effect on other game objects 
 */
public class Graphic extends Entity implements HasGraphic, Renderable {

    // BufferedImage graphic
    private BufferedImage graphic;

    // create Graphic via resource path
    public Graphic(String name, float x, float y, float width, float height, float pScale, String gPath) {
        super(name, x, y, width, height, pScale, null, null);
        graphic = LoneflyGE.GRAPHIC_LOADER.loadImage(gPath);
        // log graphic creation
        LoneflyGE.LOG.println("New Graphic created! name <" + getName() + ">", ILevels.SMALLTALK);
    }

    // create graphic via Buffered Image
    public Graphic(String name, float x, float y, float width, float height, float pScale, BufferedImage graphic) {
        super(name, x, y, width, height, pScale, null, null);
        this.graphic = graphic;
        // log graphic creation
        LoneflyGE.LOG.println("New Graphic created! name <" + getName() + ">", ILevels.SMALLTALK);
    }

    // change graphic via image path
    public void changeGraphic(String gPath) {
        graphic = LoneflyGE.GRAPHIC_LOADER.loadImage(gPath);
    }

    // change graphic via bufferdImage
    @Override
    public void setGraphic(BufferedImage img) {
        graphic = img;
    }

    @Override
    public BufferedImage getGraphic() {
        return graphic;
    }

    @Override
    public String toString() {
        return "Graphic: " + getName() + " At X: " + getX() + " Y: " + getY();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        if (graphic != null) {
            // if game graphic is at angle
            if (super.getAngle() != 0.0) {
                // rotate graphic
                BufferedImage rotG = LoneflyGE.GEDIT.rotateGraphic(graphic, super.getWidth(), super.getHeight(),
                        super.getAngle());
                // render rotated graphic
                g.drawImage(rotG, (int) (super.getX() - (rotG.getWidth() / 2)),
                        (int) (super.getY() - (rotG.getHeight() / 2)), rotG.getWidth(), rotG.getHeight(), null);
            } else {
                // draw non rotated graphic
                g.drawImage(graphic, (int) (super.getX() - (super.getWidth() / 2)),
                        (int) (super.getY() - (super.getHeight() / 2)), (int) super.getWidth(), (int) super.getHeight(), null);
            }
        } else {
            // if no graphic available draw rectangle in its place NOTE:
            // these rectangle fillers will not rotate
            g.fillRect((int) (super.getX() - (super.getWidth() / 2)), (int) (super.getY() - (super.getHeight() / 2)),
                    (int) super.getWidth(), (int) super.getHeight());
        }
    }
}
