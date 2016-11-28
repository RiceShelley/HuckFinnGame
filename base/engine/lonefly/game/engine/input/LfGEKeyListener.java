package base.engine.lonefly.game.engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * Author: Rice Shelley
 * Version: 3/15/2016
 * Purpose: KeyListener with intent of being added to the Display
 * class. Stores what keys are being held down (keyCode = key held down)
 * when no key is down keyCode = -1
 * }
 */
public class LfGEKeyListener implements KeyListener {

    // keyCode length based on how many keys the player should be able to press
    // down at once
    private int[] keyCode = new int[10];

    public LfGEKeyListener() {
        for (int i = 0; i < keyCode.length; i++) {
            keyCode[i] = -1;
        }
    }

    public int[] getKeys() {
        // create new array with appropriate size because efficiency thats why
        int realLength = 0;
        for (int i = 0; i < keyCode.length; i++) {
            if (keyCode[i] != -1) {
                realLength++;
            }
        }
        int[] keysPressed = new int[realLength];
        // copy values to new array
        for (int i = 0; i < realLength; i++) {
            keysPressed[i] = keyCode[i];
        }
        return keysPressed;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // make sure this key is not already being pressed
        for (int i = 0; i < keyCode.length; i++) {
            if (keyCode[i] == e.getKeyCode()) {
                return;
            } else if (keyCode[i] == -1) {
                // break because if the index value of i is -1 so is everything
                // else after it.
                break;
            }
        }
        int unUsedKey = 0;
        while (keyCode[unUsedKey] != -1) {
            unUsedKey++;
        }
        keyCode[unUsedKey] = e.getKeyCode();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // remove key that was released from keyCode
        int keyRelesed = e.getKeyCode();
        for (int i = 0; i < keyCode.length; i++) {
            if (keyCode[i] == keyRelesed) {
                /*
				 * if found shift all the other values in the array forward
				 * deleting this one
                 */
                for (int ii = i; ii < keyCode.length; ii++) {
                    if (keyCode[ii] != -1) {
                        if (keyCode[ii + 1] == -1) {
                            keyCode[ii] = -1;
                            break;
                        } else {
                            keyCode[ii] = keyCode[ii + 1];
                        }
                    }
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Nothing to see hear folks
    }

    @Override
    public String toString() {
        String output = "[";
        for (int i : keyCode) {
            output += " " + i + ", ";
        }
        return output + "\b\b]";
    }

}
