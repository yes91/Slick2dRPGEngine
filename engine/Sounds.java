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
    public static Sound decision;
    public static Sound cancel;
    public static Sound buzzer;
    public static Sound itemUse;
    
    private static Sound actorCollapse;
    
    public static void playActorCollapse(){
        actorCollapse.play();
    }
    
    public static void load(){
        try {
            actorCollapse = new Sound("res/Audio/SE/011-System11.ogg");
            itemUse = new Sound("res/Audio/SE/105-Heal01.ogg");
            buzzer = new Sound("res/Audio/SE/004-System04.ogg");
            cursor = new Sound("res/Audio/SE/Cursor2.ogg");
            cancel = new Sound("res/Audio/SE/Cancel2.wav");
            decision = new Sound("res/Audio/SE/Decision1.ogg");
        } catch (SlickException ex) {
            Logger.getLogger(Sounds.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
