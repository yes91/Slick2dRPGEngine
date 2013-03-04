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
public class GameCache {
    
    private static AngelCodeFont font;
    //private static AngelCodeFont font;
    private static final  HashMap<String, Image> images = new HashMap<>();
    
    public static Image image(String filename){
        return loadImage("res/", filename);
    }
    
    public static Image system(String filename){
        return loadImage("res/system/", filename);
    }

    public static Image res(String filename){
        return loadImage("res/", filename);
    }
    
    private static Image loadImage(String folderName, String fileName){
        Image result = images.get(fileName);
        if(result == null){
            try {
                result = new Image(folderName+fileName); 
                images.put(fileName, result);
            } catch (SlickException ex) {
                Logger.getLogger(GameCache.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    public static AngelCodeFont getFont(){
        
        if(font == null){
            try {
                font = new AngelCodeFont("res/system/umeplusbold.fnt","res/system/umeplusbold.png");
                //font = new AngelCodeFont("res/system/mysterons.fnt","res/system/mysterons_0.png");
                    /*font = new UnicodeFont(new Font("VL Gothic Regular",Font.PLAIN,18));
                    font.addAsciiGlyphs();   //Add Glyphs
                    font.addGlyphs(400, 600); //Add Glyphs //Add Effects
                    font.getEffects().add(new ShadowEffect(Color.BLACK,1,1,0.9f));
                    font.getEffects().add(new ColorEffect(Color.WHITE));
                    System.out.println(font.getLineHeight());
            try {
                font.loadGlyphs();  //Load Glyphs*/
            } catch (SlickException ex) {
                Logger.getLogger(GameCache.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                return font;
    }

}