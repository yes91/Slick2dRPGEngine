/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
/**
 *
 * @author Kieran
 */
public class NPC extends Event{
    
    private Image image;
    private int frame;
    
    public NPC(float x, float y, int width, int height, String dg, Image i){
        super(x, y, width, height, "Npc", dg, null, null);
        this.image = i;
        frame = 0;
    }
    
    @Override
    public void render(Graphics g){
        
        Sprite.drawSpriteFrame(image, g, pos.x, pos.y, 4, frame, 64, 96);
    }
    
    public void activate(){
        SceneBase.gameMessage.faceName = "People1";
        SceneBase.gameMessage.faceIndex = 5;
        String words = "Hey, I'm an NPC. I'm aware I just broke the fourth wall,\n"
                                    + "but why does that matter in a tech demo? Kieran's pretty talented\n"
                                    + "for a \\C[0]novice programmer\\R, eh? You know, he did spend far too long on \n"
                                    + "this particular feature, though. I'm having a really nice time on this \n"
                                    + "grassy field. How are you?";
        SceneBase.gameMessage.setText(words);
        SceneMap.removeUIElement(SceneMap.lastAdded);
        SceneMap.lastAdded = SceneMap.message;
        SceneMap.addUIElement(SceneMap.message);
        SceneMap.message.startMessage();
        SceneMap.uiFocus = true;
        //SceneMap.interpreter.setup(list, 0);
    }
    
    public void setFrame(int f){
        
        frame = f;
    }
}
