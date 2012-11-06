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
    
    Image image;
    int frame;
    
    public NPC(float x, float y, int width, int height, String dg, Image i){
        super(x, y, width, height, "Npc", dg, null, null);
        this.image = i;
        frame = 0;
    }
    
    @Override
    public void render(Graphics g){
        
        Sprite.drawSpriteFrame(image, g, pos.x, pos.y, 4, frame, 64, 96);
    }
    
    public void setFrame(int f){
        
        frame = f;
    }
}
