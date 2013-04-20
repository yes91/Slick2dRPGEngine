/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
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
    public Rectangle bounds;
    
    public GameCharacter(String image){
        characterName = image;
        if(image != null){
            Image i = GameCache.res(image+".png");
            width = i.getWidth() / 4;
            height = i.getHeight() / 4;
            sprite = new SpriteCharacter(this, i);
        }
        bounds = new Rectangle(pos.x - width/2 + (width/8), pos.y, width - (width/4), height/2);
    }
    
    @Override
    public void render(Graphics g){
        if(sprite != null){
            sprite.getCurrentAni().draw((int) pos.x - width/2, (int) pos.y - height/2);
        }
    }
    
    @Override
    public void update(){
        if(sprite != null){
            sprite.update();
        }
        bounds.setLocation(pos.x - width/2 + (width/8), pos.y);
    }
    
    public void setFacing(GameCharacter.Direction dir) {
        facing = dir;
        switch(dir){
            case UP: sprite.setState(4); break;
            case DOWN: sprite.setState(5); break;
            case LEFT: sprite.setState(6); break;
            case RIGHT: sprite.setState(7); break;
        }
    }
}
