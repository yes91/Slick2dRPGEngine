/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Kieran
 */
public class WindowSystem extends WindowSelectable{
    
    public WindowSystem(int cMax, int rMax){
        super(0,0,SceneMap.B_WIDTH,SceneMap.B_HEIGHT);
        itemMax = 1;
        columnMax = cMax;
        index = 0;
    }
    
    @Override
    public void render(Graphics g, StateBasedGame sbg){
        super.render(g, sbg);
        for(int i = 0; i < itemMax; i++){
            drawItem(i);
        }
    }
    
    public void drawItem(int ind){
        switch(ind){
            case 0: Cache.getFont().drawString(x+16, y+16, "Configure Key/Controls"); break;
        }
    }
    
}
