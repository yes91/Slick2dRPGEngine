/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.awt.Color;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.ShadowEffect;

/**
 *
 * @author redblast71
 */
public class Cache {
    
    private static UnicodeFont font;
    
    public static Image getImage(String filename){
        try {
            return new Image("/src/rpgslickport/"+filename); 
        } catch (SlickException ex) {
            Logger.getLogger(Cache.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static UnicodeFont getFont(){
        
        if(font == null){
                font = new UnicodeFont(new Font("VL Gothic Regular",Font.PLAIN,17));
                font.addAsciiGlyphs();   //Add Glyphs
                font.addGlyphs(400, 600); //Add Glyphs //Add Effects
                font.getEffects().add(new ShadowEffect(Color.BLACK,1,1,0.9f));
                font.getEffects().add(new ColorEffect(Color.WHITE));
        try {
            font.loadGlyphs();  //Load Glyphs
        } catch (SlickException ex) {
            Logger.getLogger(Cache.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
                return font;
    }
}