/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author redblast71
 */
public class WindowCommand extends WindowSelectable{
    
    public String[] commands;
    
    public WindowCommand(int width, String[] coms, int cMax, int rMax) throws SlickException{
        super(0,0,width,(rMax == 0 ? ((coms.length + cMax - 1) / cMax):rMax) * 24 + 32);
        commands = coms;
        itemMax = commands.length;
        columnMax = cMax;
        index = 0;
        SceneBase.gameMessage.proc = new Callback(){
            @Override
            public Object call(){
                itemMax = 4;
                return this;
            }
        };
    }
    
    @Override
    public void render(Graphics g,StateBasedGame sbg){
        //Graphics.setCurrent(g);
        super.render(g,sbg);
        //cg.clear();
        for(int i = 0; i < itemMax; i++){
            drawItem(i);
        }
        if(itemMax > 0 && index >= 0){
            drawCursorRect(g);
        }
    }
    
    public void drawItem(int ind){
        Rectangle rect = getItemRect(ind);
        rect.setX(rect.getX() + 4);
        rect.setWidth(rect.getX() - 8);
        //Graphics.setCurrent(cg);
        //for(int i = 0; i < 10; i++){
        Cache.getFont().drawString(x+16+rect.getX()+8, y+16+rect.getY()+2, commands[ind]);
        //}
    }
    
}
