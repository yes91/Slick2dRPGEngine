/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author redblast71
 */
public class WindowMessage extends WindowSelectable{
    
    private GameMessage gameMessage;
    
    public final int MAX_LINE = 4;
    
    private String text;
    
    public WindowMessage(){
        super(0, (int)(SceneMap.B_HEIGHT*0.85), SceneMap.B_WIDTH, (int)(SceneMap.B_HEIGHT*0.15));
        this.index = -1;
        this.itemMax = 0;
        this.gameMessage = SceneBase.gameMessage;
        this.text = null;
    }
    
    @Override
    public void render(Graphics g, StateBasedGame sbg){
        super.render(g, sbg);
        for(int i = 0; i < gameMessage.texts.size(); i++){
        Cache.getFont().drawString(x+16, (y+16*(i+1)), gameMessage.texts.elementAt(i));
        }
    }
    
    @Override
    public void update(InputProvider input){
        super.update(input);
        if(text != null){
            updateMessage();
        }
        else{
            
        }
    }
    
    private void updateMessage(){
        
        
    }
}
