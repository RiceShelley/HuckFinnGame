package base.engine.lonefly.game;

import com.badlogic.gdx.utils.GdxNativesLoader;

import base.engine.lonefly.game.engine.display.GEdit;
import base.engine.lonefly.game.engine.display.GraphicLoader;
import base.engine.lonefly.game.engine.util.ConsoleOutput;
import base.engine.lonefly.game.engine.util.ILevels;

/*
 * Author: Rice Shelley
 * Version: 3/11/2016
 * Purpose: initiate lonefly engine 
 * assign class 
 */
public class LoneflyGE {

    // object for logging info to the console for the game engine
    public static ConsoleOutput LOG;
    // object for uploading graphics
    public static final GraphicLoader GRAPHIC_LOADER = new GraphicLoader();
    // object for editing graphics
    public static GEdit GEDIT;

    // default constructor
    public LoneflyGE() {
        GdxNativesLoader.load();
        LOG = new ConsoleOutput(true, ILevels.WARNING);
        GEDIT = new GEdit();
    }

    // Constructor with parameters <- refer to demos for use
    public LoneflyGE(boolean consoleShow, ILevels iLvls) {
        GdxNativesLoader.load();
        LOG = new ConsoleOutput(consoleShow, iLvls);
        GEDIT = new GEdit();
    }
}
