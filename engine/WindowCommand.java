/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author redblast71
 */
public class WindowCommand extends WindowSelectable{
    
    public String[] commands;
    
    public WindowCommand(int width, String[] coms, int cMax, int rMax){
        super(0,0,0,0);
        if(rMax == 0){
            rMax = (coms.length + cMax - 1) / cMax;
        }
        super.width = width;
        super.height = rMax * WINDOW_LINE_HEIGHT + 32;
        commands = coms;
        itemMax = commands.length;
        columnMax = cMax;
        index = 0;
    }
    
    @Override
    public void render(Graphics g,StateBasedGame sbg){
        super.render(g,sbg);
        for(int i = 0; i < itemMax; i++){
            drawItem(i);
        }
    }
    
    public void drawItem(int ind){
        Rectangle rect = getItemRect(ind);
        rect.setX(rect.getX() + 4);
        rect.setWidth(rect.getX() - 8);
        Cache.getFont().drawString(x+16+rect.getX()+8, y+16+rect.getY()+4, commands[ind]);
    }
    
}
