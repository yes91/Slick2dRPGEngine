/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author redblast71
 */
public class GameCharacter extends GameObject{
    
    public enum Direction {
        UP, DOWN, LEFT, RIGHT;
    }
    
    protected Vector2f deltaPos = new Vector2f();
    protected Vector2f lastPos = new Vector2f();
    public SpriteCharacter sprite;
    public String characterName;
    public int characterIndex;
    public Direction facing;
    
    public GameCharacter(String image){
        characterName = image;
        Image i = GameCache.res(image+".png");
        width = i.getWidth() / 4;
        height = i.getHeight() / 4;
        sprite = new SpriteCharacter(this, i);
    }
    
}
