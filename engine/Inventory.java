/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author redblast71
 */
public class Inventory {
    
    public static final int INV_SIZE = 50;
    public static final int MAX_AMOUNT = 99;
    public ArrayList<Item> items;
    //public ArrayList<Armor> inven4;
    private int[] amounts;
    private int yOrigin;
    
    Inventory(){
        
        items = new ArrayList<>();
        amounts = new int[INV_SIZE];
        initInv();
        yOrigin = 0;
    }
    
  public boolean add(Item item, int a){
            if(items.contains(item)){
                if(amounts[items.indexOf(item)] < MAX_AMOUNT){
                amounts[items.indexOf(item)] += a;
                }
            }
            else if(items.size() < INV_SIZE){
                items.add(item);
                amounts[items.indexOf(item)] = a;
            }
            else if(items.size() == INV_SIZE){
                return false;
            }
        return true;
    }
//Two options for items - draw amounts by item, pass in inventory<--- I did this. || Draw amounts by inventory, item 
//only draws icon & name
    
    public boolean remove(Item item, int amt){
            if(items.contains(item)){
                if(!(amounts[items.indexOf(item)]-amt <= 0)){
                amounts[items.indexOf(item)] -= amt;
                }
                else{
                    amounts[items.indexOf(item)] = 0;
                    items.remove(item);
                }
            }
            else return false;
        return true;
    }
    
    @Deprecated
    public void render(Graphics g2d, Window w, int spacing, int x, int y, String sort){
        
        if((getCurrLength() > 0)) {
        int itConst = spacing;
        for(int iter = yOrigin; iter < yOrigin+1;){
        g2d.setClip(w.getX()+6-(int)Camera.viewPort.getX(), w.getY()+6-(int)Camera.viewPort.getY(), w.getWidth()-6, w.getHeight()-12);
        if(sort.equals("all")){

        for(Item item: items){
                    try {
                    item.render(g2d, this, x, iter+spacing+y);
                    } catch (SlickException ex) {
                        Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    iter+=itConst;
                    if(iter > (itConst*(getCurrLength()-1))+yOrigin){ break; }
                }
            }
        }
        g2d.clearClip();
        }
    }
    
    public int getCurrLength() {
        return items.size();
    }

    private void initInv() {
        for(int i = 0; i < amounts.length; i++){
            amounts[i] = 0;
        }  
    }
    
    public void scroll(int dy){
    
    yOrigin += dy;
    }
    
    public void setY(int y){
        
        yOrigin = y;
    }
    
    public int getItemAmount(Item item){
        int amt = 0;
        if(items.contains(item)){
            amt = amounts[items.indexOf(item)];
        }
        return amt;
    }
    
}
