/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 *
 * @author redblast71
 */
public class Sounds {
    
    public static Sound cursor;
    public static Sound cancel;
    
    public static void load(){
        try {
            cursor = new Sound("/src/res/Cursor2.wav");
            cancel = new Sound("/src/res/Cancel2.wav");  
        } catch (SlickException ex) {
            Logger.getLogger(Sounds.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
