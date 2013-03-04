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
    }
    
    @Override
    public void updateCursor(){
        
    }
    
    @Override
    public void render(Graphics g, StateBasedGame sbg){
        super.render(g, sbg);
        itemMax = SceneBase.gameParty.getMembers().size();
        cg.clear();
        for(int i = 0; i < itemMax; i++){
            drawItem(i);
        }
        cg.flush();
    }
    
    public void drawItem(int ind){
        int xOff = ind * 120;
        GameActor actor = SceneBase.gameParty.getMembers().get(ind);
        drawActorFace(actor, xOff + 32*ind, 0);
        drawActorHP(actor, xOff + 32*ind, 2.8f * 24);
        drawActorMP(actor, xOff + 32*ind, 3.6f * 24);
    }
    
    
}
