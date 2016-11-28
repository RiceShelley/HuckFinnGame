package base.engine.lonefly.game.engine.display;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import base.engine.lonefly.game.LoneflyGE;
import base.engine.lonefly.game.engine.util.ILevels;

/*
 * Author: Rice Shelley
 * Version: 3/26/2016
 * Purpose: uploading graphics  
 */
public class GraphicLoader {
    // upload graphics

    public BufferedImage loadImage(String path) {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            LoneflyGE.LOG.println("Falied to load image at " + path, ILevels.ERROR);
            return null;
        }
        LoneflyGE.LOG.println("Loaded image at " + path, ILevels.SMALLTALK);
        return image;
    }
}
