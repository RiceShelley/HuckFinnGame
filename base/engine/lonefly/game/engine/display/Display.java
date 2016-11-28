package base.engine.lonefly.game.engine.display;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import base.engine.lonefly.game.engine.input.LfGEKeyListener;

/*
 * Author: Rice Shelley
 * Version: 3/14/2016
 * Purpose: To create a frame for Activity objects or (JPanels)
 * to exist in. Manages all of the panels it contains 
 */
public class Display extends JFrame {

    private static final long serialVersionUID = 1L;

    // activity - a JPanel that is added to super class to display stuff
    private JPanel activity = null;

    public Display(Dimension size, boolean visible, String title) {
        defaultActivity();
        setSize(size);
        setTitle(title);
        add(activity);
        setVisible(visible);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Display(Dimension size, boolean visible, String title, LfGEKeyListener key) {
        defaultActivity();
        addKeyListener(key);
        setSize(size);
        setTitle(title);
        add(activity);
        setVisible(visible);
    }

    // create a default activity so JFrame can have something in it
    private void defaultActivity() {
        activity = new JPanel();
        activity.setBackground(Color.BLACK);
    }

    // add keyListener to display
    public void addKeyListener(LfGEKeyListener key) {
        super.addKeyListener(key);
    }

    // add an activity to JFrame
    public void addActivity(JPanel activity) {
        remove(this.activity);
        this.activity = activity;
        add(this.activity);
        revalidate();
    }
}
