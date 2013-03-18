/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import engine.Effect.Place;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author redblast71
 */
public class WindowItem extends WindowSelectable {

    private Inventory inven;
    private ArrayList<Item> data;
    

    public WindowItem(int x, int y, int width, int height, Inventory inv) throws SlickException {
        super(x, y, width, height);
        this.columnMax = 4;
        this.index = 0;
        inven = inv;
        data = new ArrayList<>();
        data.addAll(inven.items);
        sortUseable();
    }

    public Item getItem() {
        return data.get(index);
    }

    @Override
    public void render(Graphics g, StateBasedGame sbg) {
        super.render(g, sbg);
        this.itemMax = data.size();
        if (itemMax > 0) {
            drawCursorRect(g);
        }
        cg.clear();
        for (int i = 0; i < itemMax; i++) {
            drawItem(i);
        }
        cg.flush();
        g.drawImage(contents, x + 16, y + 16);
    }

    public void drawItem(int ind) {
        Rectangle rect = getItemRect(ind);
        rect.setX(rect.getX() + 4);
        rect.setWidth(rect.getWidth() - 8);
        Item item = data.get(ind);
        int number = inven.getItemAmount(item);
        boolean enabled = item instanceof Consumable && ((Consumable)item).getPlace() != Place.BATTLE_ONLY
                                                     && ((Consumable)item).getPlace() != Place.NEVER;
        drawItemName((BaseItem)item, rect.getX() + 8, rect.getY() - oy, enabled);
        for(int i = 0; i < 4; i++){
            cg.drawString(String.format(":%2d", number), 
                    (rect.getWidth()) + 
                    rect.getX() -
                    GameCache.getFont().getWidth(String.format(":%2d", number)), 
                    rect.getY() + 2 - oy);
        }
    }

    public final void sortAlpha() {
        Collections.sort(data, new byName());
    }
    
    public final void sortUseable() {
        Collections.sort(data, new byType());
    }
    
    public void updateItems(){
        data.clear();
        data.addAll(inven.items);
        sortUseable();
    }

    public void setBattle() {
        data.clear();
        for (Item i : inven.items) {
            if (i instanceof Consumable && ((Consumable)i).getPlace() != Place.MENU_ONLY
                                        && ((Consumable)i).getPlace() != Place.NEVER) {
                data.add(i);
            }
        }
    }
    
    
    private static final class byName implements Comparator<Item> {
        @Override
        public int compare(Item i1, Item i2) {
            return i1.getName().compareTo(i2.getName());
        }
    }
    
    private static final class byType implements Comparator<Item> {
        @Override
        public int compare(Item i1, Item i2) {
            int result = 0;
            if(i1 instanceof Consumable && i2 instanceof Consumable){
                result = i1.getName().compareTo(i2.getName());
            }
            if(i1 instanceof Weapon && i2 instanceof Weapon){
                result = i1.getName().compareTo(i2.getName());
            }
            if(i1 instanceof Weapon && i2 instanceof Consumable){
                result = 1;
            }
            if(i1 instanceof Consumable && i2 instanceof Weapon){
                result = -1;
            }
            return result;
        }
    }
}


