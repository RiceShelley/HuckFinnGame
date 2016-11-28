package base.game;

import java.awt.Dimension;

import base.engine.lonefly.game.LoneflyGE;
import base.engine.lonefly.game.engine.display.Display;
import base.engine.lonefly.game.engine.environment.Point;
import base.engine.lonefly.game.engine.input.LfGEKeyListener;
import base.engine.lonefly.game.engine.util.ILevels;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class Main {

    public static Point S_RES = new Point(1500, 900);

    private LoneflyGE glfGE = null;
    private LfGEKeyListener key = null;
    private MouseMHandle mouse = null;
    private Display display = null;
    // int var for representing stage of game
    private int stage = 0;
    // boolean state affecting weather to continue updating a stage or not
    private boolean runStage = true;
    // sound obj
    private PlayWav pW = null;

    public Main() {
        // start game engine - parameters
        glfGE = new LoneflyGE(true, ILevels.SMALLTALK);
        // get class path from main
        final File f = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String resPath = f.getAbsolutePath().substring(0, f.getAbsolutePath().lastIndexOf("\\")) + "\\base\\game\\";
        //String resPath = f.getAbsolutePath() + "/base/game/";
        ImagePath.PREFIX = resPath;
        ImagePath.LOAD_IMG_PATHS();
        System.out.println("Loaded PATH: " + ImagePath.PREFIX);
        // create new keyListener
        key = new LfGEKeyListener();
        // create mouse motion addapter
        mouse = new MouseMHandle();
        // create display frame pass it frame size, visibility, name, and a
        // key listener to bind to the frame
        display = new Display(new Dimension((int) S_RES.getX(), (int) S_RES.getY()), true, "Adventures of Huckleberry Finn the game", key);
        display.setResizable(false);
        display.addMouseMotionListener(mouse);
        // start game
        gamePathFlow();
    }

    private void gamePathFlow() {
        while (true) {
            switch (stage) {
                case 0:
                    pW = new PlayWav(ImagePath.PREFIX + "sound/Intro.wav");
                    // start screen
                    StartScreen ss = new StartScreen(this);
                    display.addMouseListener(ss);
                    display.addActivity(ss);
                    while (runStage) {
                        ss.update();
                        sleep(30);
                    }
                    runStage = true;
                    pW.stopStream();
                    break;
                case 1:
                    // post start screen chapter / quote
                    PostGameStart pgs = new PostGameStart(this);
                    display.addActivity(pgs);
                    while (runStage) {
                        pgs.update();
                        sleep(30);
                    }
                    runStage = true;
                    break;
                case 2:
                    pW = new PlayWav(ImagePath.PREFIX + "sound/Dialog_On_Raft_Music.wav");
                    Dialog1 d1 = new Dialog1(this);
                    display.addActivity(d1);
                    while (runStage) {
                        d1.update();
                        sleep(30);
                    }
                    sleep(3000);
                    runStage = true;
                    pW.stopStream();
                    break;
                case 3:
                    pW = new PlayWav(ImagePath.PREFIX + "sound/boat_game.wav");
                    // start river boat game
                    RiverBoatGame rbg = new RiverBoatGame(this);
                    display.addActivity(rbg);
                    while (runStage) {
                        rbg.update();
                        sleep(30);
                    }
                    runStage = true;
                    pW.stopStream();
                    break;
                case 4:
                    pW = new PlayWav(ImagePath.PREFIX + "sound/EscapedSlaves.wav");
                    Dialog2 d2 = new Dialog2(this);
                    display.addActivity(d2);
                    while (runStage) {
                        d2.update();
                        sleep(30);
                    }
                    runStage = true;
                    pW.stopStream();
                    break;
                case 5:
                    pW = new PlayWav(ImagePath.PREFIX + "sound/EscapedSlaves.wav");
                    Dialog3 d3 = new Dialog3(this);
                    display.addActivity(d3);
                    while (runStage) {
                        d3.update();
                        sleep(30);
                    }
                    runStage = true;
                    pW.stopStream();
                    break;
                case 6:
                    pW = new PlayWav(ImagePath.PREFIX + "sound/Gun_game.wav");
                    Shooter s = new Shooter(this);
                    display.addActivity(s);
                    display.addMouseListener(s);
                    while (runStage) {
                        s.update();
                        sleep(30);
                    }
                    runStage = true;
                    pW.stopStream();
                    break;
                case 7:
                    pW = new PlayWav(ImagePath.PREFIX + "sound/Intro.wav");
                    Dialog4 d4 = new Dialog4(this);
                    display.addActivity(d4);
                    while (runStage) {
                        d4.update();
                        sleep(30);
                    }
                    runStage = true;
                    pW.stopStream();
                    break;
                case 8:
                    pW = new PlayWav(ImagePath.PREFIX + "sound/Church_game.wav");
                    Church c = new Church(this);
                    display.addActivity(c);
                    while (runStage) {
                        c.update();
                        sleep(30);
                    }
                    runStage = true;
                    pW.stopStream();
                    break;
                case 9:
                    pW = new PlayWav(ImagePath.PREFIX + "sound/Intro.wav");
                    EndDialog endD = new EndDialog(this);
                    display.addActivity(endD);
                    while (runStage) {
                        endD.update();
                        sleep(30);
                    }
                    runStage = true;
                    pW.stopStream();
                    break;
                default:
                    System.out.println("ERROR UNKOWN STAGE!!!");
                    System.exit(-1);
                    break;
            }
        }
    }

    public void sleep(long amt) {
        try {
            Thread.sleep(amt);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changeDisplayActivity(JPanel obj) {
        display.addActivity(obj);
    }

    public void setRunStage(boolean runStage) {
        this.runStage = runStage;
    }

    public Display getDisplay() {
        return display;
    }

    public Point getMCords() {
        return mouse.getPoint();
    }

    public LfGEKeyListener getKeys() {
        return key;
    }

    public void tickStage() {
        stage++;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public static void main(String[] args) {
        Main main = new Main();
    }

    private class MouseMHandle extends MouseAdapter {

        private Point p;

        public MouseMHandle() {
            p = new Point(0, 0);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            update(e);
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            update(e);
        }

        private void update(MouseEvent e) {
            p.setX(e.getX());
            p.setY(e.getY());
        }

        public Point getPoint() {
            return p;
        }
    }

}
