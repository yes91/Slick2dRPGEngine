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
class WindowBattleStatus extends WindowSelectable{
    
    public WindowBattleStatus(int x, int y, int width, int height) throws SlickException{
        super(x, y, width, height);
        columnMax = 10;
        index = -1;
    }
    
    @Override
    public void render(Graphics g, StateBasedGame sbg){
        super.render(g, sbg);
        itemMax = SceneBase.gameParty.getMembers().size();
        if(index > -1){
            drawCursorRect(g);
        }
        cg.clear();
        for(int i = 0; i < itemMax; i++){
            drawItem(i);
        }
        cg.flush();
        g.drawImage(contents, x + 16, y + 16);
    }
    
    public void drawItem(int ind){
        int xOff = ind * 120;
        GameActor actor = SceneBase.gameParty.getMembers().get(ind);
        drawActorFace(actor, xOff + 32*ind, 0);
        drawActorHP(actor, xOff + 32*ind, 2.8f * 24);
        drawActorMP(actor, xOff + 32*ind, 3.6f * 24);
    }
    
    @Override
    public void updateCursor(){
        if(index < 0){
            return;
        } else if(index < itemMax){
            cursorRect.setBounds(index * 120 + 32*index, 0, 120 + 16, 96);
        } else if(index >= 100){
            cursorRect.setBounds((index - 100) * 120 + 32*(index - 100), 0, 120 + 16, 96);
        } else {
            cursorRect.setBounds(0, 0, contents.getWidth(), 96);
        }
    }
    
    
}
