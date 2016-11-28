package base.engine.lonefly.game.engine.environment;

/*
 * Author: Rice Shelley
 * Version: 4/8/2016
 * Purpose: to define viewing point for an activity 
 */
public class Camera implements Coordinate {

    private float x;
    private float y;

    public Camera() {
        x = 0;
        y = 0;
    }

    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Camera(Point point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

}
