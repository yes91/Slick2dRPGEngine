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
public class WindowMenuStatus extends WindowSelectable{
    
    public WindowMenuStatus(int x, int y) throws SlickException{
        super(x, y, SceneMap.B_WIDTH - x, SceneMap.B_HEIGHT - y);
        this.index = -1;
    }
    
    @Override
    public void render(Graphics g, StateBasedGame sbg){
        super.render(g, sbg);
        
    }
    
}
