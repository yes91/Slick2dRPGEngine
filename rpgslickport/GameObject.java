/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rpgslickport;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;


/**
 *
 * @author Kieran
 */
public abstract class GameObject {
    
    public Vector2f pos = new Vector2f(0,0);
    public int width;
    public int height;
    //private Animation anim;
    
    
    
    public void update() {
        
        
    }
    
    public void render(Graphics g2d) {
        
        
    }
    
    public float getX() {
        
        return pos.x;
    }
    
    public float getY() {
        
        return pos.y;
    }
    
    public int getWidth() {
        
        return width;
    }
    
    public int getHeight() {
        
        return height;
    }
    
    public Rectangle getBounds() {
     return new Rectangle(pos.x, pos.y, width, height);
}
}
