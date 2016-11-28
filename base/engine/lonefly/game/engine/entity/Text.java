package base.engine.lonefly.game.engine.entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import base.engine.lonefly.game.LoneflyGE;

/*
 * Author: Rice Shelley
 * Version: 4/22/2016
 * Purpose: to graphically display text 
 */
public class Text extends Entity implements Renderable {

    // text the text object displays
    private String text;
    // color of the text
    private Color color;

    // Default constructor - set text to "Undefined" - set color to blue - put
    // text at x=0, y=0
    public Text() {
        super("Undefined", 0, 0, 50, "Undefined".length() * 10, 1, null, null);
        text = super.getName();
        color = Color.BLUE;
    }

    // constructor with parameters
    public Text(String name, float x, float y, float width, float height, String text, Color color) {
        super(name, x, y, width, height, 1, null, null);
        this.text = text;
        this.color = color;
    }

    @Override
    public String toString() {
        return super.getName();
    }

    // setters
    // set text
    public void setText(String text) {
        this.text = text;
    }

    // set color
    public void setColor(Color color) {
        this.color = color;
    }

    // getters
    // get text
    public String getText() {
        return text;
    }

    // get color
    public Color getColor() {
        return color;
    }

    @Override
    public void render(Graphics g) {
        // set color of text
        g.setColor(color);
        // define font
        g.setFont(new Font(null, (int) super.getWidth(), (int) super.getHeight()));
        // create buffered image container for text <- makes text easer to
        // rotate if need be
        BufferedImage tContainer = new BufferedImage(g.getFontMetrics().stringWidth(text),
                g.getFontMetrics().getHeight(), BufferedImage.TYPE_INT_ARGB);
        // get graphics
        Graphics2D tCG = tContainer.createGraphics();
        // make background transparent
        tCG.setComposite(AlphaComposite.Clear);
        tCG.fillRect(0, 0, tContainer.getWidth(), tContainer.getHeight());
        // set buffered image back to non transparency
        tCG.setComposite(AlphaComposite.Src);
        // set font according to that of disG2D
        tCG.setFont(g.getFont());
        // set color according to that of disG2D
        tCG.setColor(g.getColor());
        // draw text
        tCG.drawString(text, 0, (int) (tContainer.getHeight() / 1.35));
        // render
        // if at angle
        if (super.getAngle() != 0.0) {
            // rotate graphic
            BufferedImage rotG = LoneflyGE.GEDIT.rotateGraphic(tContainer, tContainer.getWidth(),
                    tContainer.getHeight(), super.getAngle());
            // draw rotated graphic
            g.drawImage(rotG, (int) super.getX() - (rotG.getWidth() / 2), (int) super.getY() - (rotG.getHeight() / 2),
                    rotG.getWidth(), rotG.getHeight(), null);
        } else {
            // if graphic not rotated
            g.drawImage(tContainer, (int) super.getX() - (tContainer.getWidth() / 2),
                    (int) super.getY() - (tContainer.getHeight() / 2), tContainer.getWidth(), tContainer.getHeight(),
                    null);
        }
    }

}
