/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author redblast71
 */
public class Enemy extends Event{
    
    private Animation ani;
    
    public Enemy(Image i,String n,int hp, int mp,int atk, int def,int matk,int mdef){   
        super(0,0,"Enemy", n);
        SpriteSheet sheet = new SpriteSheet(i, 4, 4);
        this.width = sheet.getWidth()/sheet.getHorizontalCount();
        this.height = sheet.getHeight()/sheet.getVerticalCount();
        ani = new Animation(sheet, 0, 1, 3, 1, true, 120, true);
        
    }
    
    @Override
    public void render(Graphics g){
        ani.draw(pos.x + width/2, pos.y + height/2, width, height);
    }
    
}
