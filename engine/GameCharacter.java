/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Image;

/**
 *
 * @author redblast71
 */
public class GameCharacter extends GameObject{
    
    public enum Direction {
        UP, DOWN, LEFT, RIGHT;
    }
    
    protected float dx;
    protected float dy;
    protected float lastX;
    protected float lastY;
    public SpriteCharacter sprite;
    public Direction facing;
    
    public GameCharacter(Image i){
        width = i.getWidth() / 4;
        height = i.getHeight() / 4;
        sprite = new SpriteCharacter(this, i);
    }
    
}
