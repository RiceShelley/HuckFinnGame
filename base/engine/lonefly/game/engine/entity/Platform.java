package base.engine.lonefly.game.engine.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import base.engine.lonefly.game.LoneflyGE;
import base.engine.lonefly.game.engine.display.HasGraphic;
import base.engine.lonefly.game.engine.util.ILevels;

/*
 * Author: Rice Shelley
 * Version: 3/17/2016
 * Purpose: "platforms" are entity's that are designed for Game
 * Objects to sit on top of
 */
public class Platform extends Entity implements HasGraphic, Destroy, Renderable {

    // graphic
    private BufferedImage graphic;

    public Platform(String name, float x, float y, float width, float height, float pScale, World world, String gPath) {
        super(name, x, y, width, height, pScale, world, BodyType.KinematicBody);
        graphic = LoneflyGE.GRAPHIC_LOADER.loadImage(gPath);
        LoneflyGE.LOG.println("New platform created! name <" + getName() + ">", ILevels.SMALLTALK);
    }

    public Platform(String name, float x, float y, float width, float height, float pScale, World world,
            BufferedImage graphic) {
        super(name, x, y, width, height, pScale, world, BodyType.KinematicBody);
        this.graphic = graphic;
        LoneflyGE.LOG.println("Object " + name + " created ", ILevels.SMALLTALK);
    }

    // destroy platform
    @Override
    public void destroy() {
        Array<Fixture> fix = getBody().getFixtureList();
        for (Fixture f : fix) {
            getBody().destroyFixture(f);
        }
    }

    @Override
    public BufferedImage getGraphic() {
        return graphic;
    }

    @Override
    public void setGraphic(BufferedImage img) {
        graphic = img;
    }

    @Override
    public String toString() {
        return "Game Object: " + getName() + " At X: " + getX() + " Y: " + getY();
    }

    @Override
    public void render(Graphics g) {
        // draw platforms
        g.setColor(Color.GREEN);
        if (graphic != null) {
            // if platform is at angle
            if (super.getBody().getAngle() != 0.0) {
                // get rotated graphic
                BufferedImage rotG = LoneflyGE.GEDIT.rotateGraphic(graphic, super.getWidth(), super.getHeight(),
                        super.getBody().getAngle());
                // draw rotated graphic
                g.drawImage(rotG, (int) (super.getX() - (rotG.getWidth() / 2)),
                        (int) (super.getY() - (super.getHeight() / 2)), rotG.getWidth(), rotG.getHeight(), null);
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
