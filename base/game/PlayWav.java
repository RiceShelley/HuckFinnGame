/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.game;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author rootie
 */
public class PlayWav {

    private String path;
    private AudioInputStream aS = null;
    private Clip clip = null;

    public PlayWav(String path) {
        this.path = path;
        playSound();
    }

    private void playSound() {
        Toolkit.getDefaultToolkit().beep();
        File wav = new File(path);
        try {
            aS = AudioSystem.getAudioInputStream(wav);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(PlayWav.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlayWav.class.getName()).log(Level.SEVERE, null, ex);
        }
        AudioFormat format = aS.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        try {
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(aS);
            clip.loop(10);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(PlayWav.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlayWav.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stopStream() {
        clip.close();
        try {
            aS.close();
        } catch (IOException ex) {
            Logger.getLogger(PlayWav.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
