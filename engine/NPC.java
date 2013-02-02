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
        String words = "Hey, I'm an NPC. I'm aware I just broke the fourth wall,\n"
                                    + "but why does that matter in a tech demo? Kieran's pretty talented\n"
                                    + "for a novice programmer, eh? You know, he did spend far too long on\n"
                                    + " this particular feature, though. I'm having a really nice time on this\n"
                                    + " grassy field. How are you?";
        SceneBase.gameMessage.setText(words);
        WindowMessage text = new WindowMessage();
        SceneMap.removeUIElement(SceneMap.lastAdded);
        SceneMap.lastAdded = text;
        SceneMap.addUIElement(text);
        text.startMessage();
        SceneMap.uiFocus = true;
    }
    
    public void setFrame(int f){
        
        frame = f;
    }
}
