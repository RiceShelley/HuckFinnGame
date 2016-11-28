package base.engine.lonefly.game.engine.environment;

/*
 * Author: Rice Shelley
 * Version: 3/19/2016
 * Purpose: to store a point on a 2D grid implements coordinate 
 */
public class Point implements Coordinate {

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double[] getPointD() {
        double[] point = {x, y};
        return point;
    }

    public int[] getPointI() {
        int[] point = {(int) x, (int) y};
        return point;
    }

    @Override
    public float getX() {
        return (float) x;
    }

    @Override
    public float getY() {
        return (float) y;
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
    public String toString() {
        return "Point X: " + x + " Y: " + y;
    }
}
