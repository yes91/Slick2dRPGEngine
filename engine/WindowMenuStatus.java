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
        itemMax = SceneBase.gameParty.getMembers().size();
        cg.clear();
        for(GameActor ga: SceneBase.gameParty.getMembers()){
            drawActorFace(ga, 2, SceneBase.gameParty.getMembers().indexOf(ga) * 96 + 2, 92);
            float x = 104;
            float y = SceneBase.gameParty.getMembers().indexOf(ga) * 96 + WINDOW_LINE_HEIGHT / 2;
            drawActorName(ga, x, y);
            drawActorLevel(ga, x, y + WINDOW_LINE_HEIGHT);
            drawActorHP(ga, x + 120, y + WINDOW_LINE_HEIGHT);
            drawActorMP(ga, x + 120, y + WINDOW_LINE_HEIGHT * 2);
        }
        cg.flush();
        if(itemMax > 0 && index >= 0){
            drawCursorRect(g);
        }
        g.drawImage(contents, x + 16, y + 16);
    }
    
    @Override
    public void updateCursor(){
        if(index < 0){
            return;
        } else if(index < itemMax){
            cursorRect.setBounds(0, index * 96, contents.getWidth(), 96);
        } else if(index >= 100){
            cursorRect.setBounds(0, (index - 100) * 96, contents.getWidth(), 96);
        } else {
            cursorRect.setBounds(0, 0, contents.getWidth(), itemMax * 96);
        }
    }
    
}
