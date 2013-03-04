/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author redblast71
 */
public class WindowHelp extends Window {
    
    public String text;
    
    public WindowHelp() throws SlickException{
        super(0,0,SceneMap.B_WIDTH, 24 + 32);
    }
    
    @Override
    public void render(Graphics g, StateBasedGame sbg){
        super.render(g, sbg);
        if(text != null){
            GameCache.getFont().drawString(x+16, y+16, text);
        }
    }
    
    public void setText(String text){
        this.text = text;
    }
    
}
