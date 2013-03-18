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
        contents.draw(x+16, y+16);
    }
    
    public void setText(String text){
        this.text = text;
        cg.clear();
        for(int i = 0;i < 4; i++){
            cg.drawString(text, 0, 0);
        }
        cg.flush();
    }
    
    public void setBattleText(GameBattler member){
        cg.clear();
        drawActorHP(member, 180, 12);
        for(int i = 0;i < 4; i++){
            cg.drawString(member.name, 40, 0);
        }
        cg.flush();
    }
    
}
