package base.engine.lonefly.game;

import java.util.TimerTask;

/*
 * Author: Rice Shelley
 * Version: 4/15/2016
 * Purpose: calculate frames per second 
 */
public class FramesPerSecond extends TimerTask {

    // Average amount of frames rendered in the last 2 seconds
    private float fps;
    // target fps
    private float target;
    // keeps track of how many frames are rendered in a second
    private int framesRendered;
    //resource manager 
    //private ResourceManager rMan;

    public FramesPerSecond(ResourceManager rMan) {
        //intialize instance varibles 
        fps = 0;
        target = 45;
        //this.rMan = rMan;
    }

    // Increment frames rendered
    public void tick() {
        framesRendered++;
    }

    // timer task overridden from super class
    @Override
    public void run() {
        if (fps == 0) {
            fps = framesRendered;
        } else {
            // amount of frames displayed from two second ago + amount of frames
            // rendered one second ago / 2
            fps = (fps + framesRendered) / 2;
        }
        framesRendered = 0;
    }

    // set target frames per second
    public void setTarget(float target) {
        this.target = target;
    }

    // get frames per second
    public float getFps() {
        return fps;
    }

    // get target fps
    public float getTarget() {
        return target;
    }

}
