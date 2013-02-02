/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author redblast71
 */
public class Cache {
    
    //private static UnicodeFont font;
    private static AngelCodeFont font;
    private static HashMap<String, Image> images = new HashMap<>();
    
    public static Image getImage(String filename){
        Image result = images.get(filename);
        if(result == null){
            try {
                result = new Image("/src/res/"+filename);
                images.put(filename, result);
            } catch (SlickException ex) {
                Logger.getLogger(Cache.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    public static Image getSystemImage(String filename){
        Image result = images.get(filename);
        if(result == null){
            try {
                result = new Image("src/res/system/"+filename);
                images.put(filename, result);
            } catch (SlickException ex) {
                Logger.getLogger(Cache.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    public static Image getRes(String filename){
        try {
            return new Image("/src/res/"+filename); 
        } catch (SlickException ex) {
            Logger.getLogger(Cache.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static AngelCodeFont getFont(){
        
        if(font == null){
            try {
                font = new AngelCodeFont("src/res/system/umeplusbold.fnt","src/res/system/umeplusbold.png");
                    /*font = new UnicodeFont(new Font("VL Gothic Regular",Font.PLAIN,17));
                    font.addAsciiGlyphs();   //Add Glyphs
                    font.addGlyphs(400, 600); //Add Glyphs //Add Effects
                    font.getEffects().add(new ShadowEffect(Color.BLACK,1,1,0.9f));
                    font.getEffects().add(new ColorEffect(Color.WHITE));
            try {
                font.loadGlyphs();  //Load Glyphs
            } catch (SlickException ex) {
                Logger.getLogger(Cache.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            } catch (SlickException ex) {
                Logger.getLogger(Cache.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                return font;
    }
}