/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author redblast71
 */
public class WindowItem extends WindowSelectable{
    
    private Inventory inven;
    
    public WindowItem(int x, int y, int width,int height, Inventory inv){
        super(x, y, width, height);
        this.columnMax = 2;
        this.index = 0;
        inven = inv;
    }
    
    @Override
    public void render(Graphics g, StateBasedGame sbg){
        super.render(g, sbg);
        this.itemMax = inven.getCurrLength()-1;
        for(int i = 0; i < itemMax; i++ ){
            drawItem(i, g);
        }
        if(itemMax > 0){
        drawCursorRect(g);
        }
    }
    
    public void drawItem(int ind, Graphics g){
        Rectangle rect = getItemRect(ind);
        rect.setX(rect.getX() + 4);
        rect.setWidth(rect.getWidth() - 8);
        Item item = inven.items.get(ind);
        int number = inven.getItemAmount(item);
        Sprite.drawSpriteFrame(Cache.getImage("IconSet.png"), g, x+16+rect.getX()+8, y+16+rect.getY(), 16, item.getIndex(), 24, 24);
        Cache.getFont().drawString(x+24+16+rect.getX()+8, y+16+rect.getY(), item.getName());
        Cache.getFont().drawString(x+(rect.getWidth())+rect.getX()+8-Cache.getFont().getWidth(String.format(":%2d", number)), y+16+rect.getY(), String.format(":%2d", number));
    }
    
    
    
}
