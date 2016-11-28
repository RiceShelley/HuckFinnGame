package base.engine.lonefly.game.engine.display;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import base.engine.lonefly.game.LoneflyGE;
import base.engine.lonefly.game.engine.util.ILevels;

/*
 * Author: Rice Shelley
 * Version: 4/22/2016
 * Purpose: to manipulate graphics - mostly dealing with bufferedImages
 */
public class GEdit {

    public GEdit() {
        LoneflyGE.LOG.println("GEdit created", ILevels.SMALLTALK);
    }

    /*
	 * returns rotated buffered image NOTE: ANGLE IN RAIDIANS!!! 
     */
    public BufferedImage rotateGraphic(BufferedImage img, double oWidth, double oHeight, float angle) {
        // get diagonal length of rectangle 
        int diagonal = (int) Math.sqrt(Math.pow(oHeight, 2) + Math.pow(oWidth, 2));
        // create buffered image to display graphic inside of
        BufferedImage rotG = new BufferedImage(diagonal, diagonal, BufferedImage.TYPE_INT_ARGB);
        //get graphics 
        Graphics2D g2d = rotG.createGraphics();
        // set rotG buffered image to be transparent
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, diagonal, diagonal);
        // find x and y values for graphic inside of rotG
        int x = (int) ((diagonal / 2) - (oWidth / 2)), y = (int) ((diagonal / 2) - (oHeight / 2));
        // create new transformation
        AffineTransform rot = new AffineTransform();
        // rotate
        rot.setToRotation(angle, x + (oWidth / 2), y + (oHeight / 2));
        rot.translate(x, y);
        g2d.setTransform(rot);
        // set to no longer be transparent
        g2d.setComposite(AlphaComposite.Src);
        // draw graphic to rotG
        g2d.drawImage(img, 0, 0, (int) oWidth, (int) oHeight, null);
        //return rotated graphic 
        return rotG;
    }

}
