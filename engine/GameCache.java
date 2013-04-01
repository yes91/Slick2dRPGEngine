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
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 *
 * @author redblast71
 */
public class GameCache {
    
    private static AngelCodeFont font;
    //private static AngelCodeFont font;
    private static final  HashMap<String, Image> images = new HashMap<>();
    private static final  HashMap<String, Music> music = new HashMap<>();
    private static final  HashMap<String, Sound> sounds = new HashMap<>();
    
    public static Image animation(String filename){
        return loadImage("res/animation/", filename);
    }
    
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
    
    public static Music bgm(String name){
        Music result = music.get(name);
        if(result == null){
            try {
                result = new Music("res/Audio/BGM/"+name);
                music.put(name, result);
            } catch (SlickException ex) {
                Logger.getLogger(GameCache.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    public static Sound se(String name){
        Sound result = sounds.get(name);
        if(result == null){
            try {
                result = new Sound("res/Audio/SE/"+name);
                sounds.put(name, result);
            } catch (SlickException ex) {
                Logger.getLogger(GameCache.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    public static Music me(String name){
        Music result = music.get(name);
        if(result == null){
            try {
                result = new Music("res/Audio/ME/"+name);
                music.put(name, result);
            } catch (SlickException ex) {
                Logger.getLogger(GameCache.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    public static void clear(){
        images.clear();
        music.clear();
    }
    
    public static AngelCodeFont getFont(){
        
        if(font == null){
            try {
                font = new AngelCodeFont("res/system/umeplusbold.fnt","res/system/umeplusbold.png");
                //font = new AngelCodeFont("res/system/mysterons.fnt","res/system/mysterons.png");
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