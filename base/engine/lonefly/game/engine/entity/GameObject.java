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
import base.engine.lonefly.game.engine.environment.Direction;
import base.engine.lonefly.game.engine.util.ILevels;

/*
 * Author: Rice Shelley
 * Version: 3/11/2016
 * Purpose: creating graphical tangible objects with collisions,
 * positions, and depth.
 */
public class GameObject extends Entity implements HasGraphic, Destroy, Renderable {

    // GameObject graphic
    private BufferedImage graphic;

    public GameObject(String name, float x, float y, float width, float height, float pScale, World world,
            String gPath) {
        super(name, x, y, width, height, pScale, world, BodyType.DynamicBody);
        graphic = LoneflyGE.GRAPHIC_LOADER.loadImage(gPath);
        LoneflyGE.LOG.println("Object " + name + " created ", ILevels.SMALLTALK);
    }

    public GameObject(String name, float x, float y, float width, float height, float pScale, World world,
            BufferedImage graphic) {
        super(name, x, y, width, height, pScale, world, BodyType.DynamicBody);
        this.graphic = graphic;
        LoneflyGE.LOG.println("Object " + name + " created ", ILevels.SMALLTALK);
    }

    /*
	 * Applies a force in a direction - thx box2D
     */
    public void push(Direction dir, float force) {
        switch (dir) {
            case NORTH:
                super.getBody().applyForceToCenter(0, force * -1f, true);
                break;
            case EAST:
                super.getBody().applyForceToCenter(force, 0, true);
                break;
            case SOUTH:
                super.getBody().applyForceToCenter(0, force, true);
                break;
            case WEST:
                super.getBody().applyForceToCenter(force * -1f, 0, true);
                break;
            case NULL:
                break;
        }
    }

    // destroy game object
    @Override
    public void destroy() {
        Array<Fixture> fix = getBody().getFixtureList();
        for (Fixture f : fix) {
            getBody().destroyFixture(f);
        }
    }

    @Override
    public String toString() {
        return "Game Object: " + getName() + " At X: " + getX() + " Y: " + getY();
    }

    @Override
    public BufferedImage getGraphic() {
        return graphic;
    }

    @Override
    public void setGraphic(BufferedImage img) {
        graphic = img;
    }

    // render gameObjects graphics
    @Override
    public void render(Graphics g) {
        // draw game objects
        g.setColor(Color.BLUE);
        if (graphic != null) {
            // if game object is at angle
            if (super.getBody().getAngle() != 0.0) {
                // rotate graphic
                BufferedImage rotG = LoneflyGE.GEDIT.rotateGraphic(graphic, super.getWidth(), super.getHeight(),
                        super.getBody().getAngle());
                // draw rotated graphic
                g.drawImage(rotG, (int) (super.getX() - (rotG.getWidth() / 2)),
                        (int) (super.getY() - (rotG.getHeight() / 2)), rotG.getWidth(), rotG.getHeight(), null);
            } else {
                // draw non rotated graphic
                g.drawImage(graphic, (int) (super.getX() - (super.getWidth() / 2)),
                        (int) (super.getY() - (super.getHeight() / 2)), (int) super.getWidth(), (int) super.getHeight(),
                        null);
            }
        } else {
            // if no graphic available draw rectangle in its place NOTE:
            // these rectangle fillers will not rotate
            g.fillRect((int) (super.getX() - (super.getWidth() / 2)), (int) (super.getY() - (super.getHeight() / 2)),
                    (int) super.getWidth(), (int) super.getHeight());
        }
    }
}
