package base.engine.lonefly.game.engine.util;

import java.util.Date;

/*
 * Author: Rice Shelley
 * Version: 3/11/2016
 * Purpose: standardizing output to the help user 
 * when debugging 
 */
public class ConsoleOutput {

    // toggles weather to show output or not
    private boolean show;
    // importance level - adjust the amount of output shown
    private int iLvl;
    // date object part of java.util used to stamp the time of outputs
    private Date date;

    public ConsoleOutput(boolean show, ILevels iLvls) {
        this.show = show;
        this.iLvl = iLvls.ordinal();
        date = new Date();
    }

    public void println(String out, ILevels iLvls) {
        if (show && iLvls.ordinal() <= this.iLvl) {
            System.out.println("[" + date + "] Lone Fly Engine: " + out);
        }
    }

    public void print(String out, ILevels iLvls) {
        if (show && iLvls.ordinal() <= this.iLvl) {
            System.out.print("[" + date + "] Lone Fly Engine: " + out);
        }
    }

    public void err(String out) {
        if (show) {
            System.err.println("[" + date + "] ERR: " + out);
        }
    }

}
