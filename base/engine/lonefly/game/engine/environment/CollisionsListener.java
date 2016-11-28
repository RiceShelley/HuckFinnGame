package base.engine.lonefly.game.engine.environment;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import base.engine.lonefly.game.engine.display.Activity;
import base.engine.lonefly.game.engine.entity.Entity;
import base.engine.lonefly.game.engine.entity.GameObject;
import base.engine.lonefly.game.engine.entity.Platform;

/*
 * Author: Rice Shelley
 * Version: 4/8/2016
 * Purpose: to listen to Box2D collisions a figure out what objects are colliding 
 */
public class CollisionsListener implements ContactListener {

    private Activity activity;

    public CollisionsListener(Activity activity) {
        activity.resources().getWorld().setContactListener(this);
        this.activity = activity;
    }

    // when an object first touches another object this is called
    @Override
    public void beginContact(Contact c) {
        Fixture ab[] = {c.getFixtureA(), c.getFixtureB()};
        Entity cObj[] = new Entity[2];
        for (int i = 0; i < 2; i++) {
            switch (ab[i].getBody().getType()) {
                case DynamicBody:
                    for (GameObject gO : activity.resources().getGameObjects()) {
                        if (ab[i].getBody().equals(gO.getBody())) {
                            cObj[i] = gO;
                        }
                    }
                    break;
                case KinematicBody:
                    for (Platform p : activity.resources().getPlatforms()) {
                        if (ab[i].getBody().equals(p.getBody())) {
                            cObj[i] = p;
                        }
                    }
                case StaticBody:
                    break;
            }
        }
        if (cObj[0] != null && cObj[1] != null) {
            cObj[0].setTouching(cObj[1].getName());
            cObj[1].setTouching(cObj[0].getName());
            activity.gameCollision(cObj[0], cObj[1]);
        }
    }

    // when objects stop touching each other
    @Override
    public void endContact(Contact c) {
        Fixture ab[] = {c.getFixtureA(), c.getFixtureB()};
        for (int i = 0; i < 2; i++) {
            switch (ab[i].getBody().getType()) {
                case DynamicBody:
                    for (GameObject gO : activity.resources().getGameObjects()) {
                        if (ab[i].getBody().equals(gO.getBody())) {
                            gO.setTouching(null);
                        }
                    }
                    break;
                case KinematicBody:
                    for (Platform p : activity.resources().getPlatforms()) {
                        if (ab[i].getBody().equals(p.getBody())) {
                            p.setTouching(null);
                        }
                    }
                case StaticBody:
                    break;
            }
        }
    }

    // methods bellow not in use yet
    @Override
    public void postSolve(Contact c, ContactImpulse cI) {
    }

    @Override
    public void preSolve(Contact c, Manifold cI) {
    }

}
