/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.ArrayList;
<<<<<<< HEAD
import org.newdawn.slick.Graphics;
=======
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
>>>>>>> upstream/master

/**
 *
 * @author redblast71
 */
public class InventoryBak {
    
    public static final int INV_SIZE = 50;
    public Item[] inven;
    public ArrayList<Item> inven2;
    public ArrayList<Weapon> inven3;
    //public ArrayList<Armor> inven4;
    private int[] amounts;
    private int yOrigin;
    
    InventoryBak(){
        
        inven2 = new ArrayList<>();
        inven3 = new ArrayList<>();
        inven = new Item[INV_SIZE];
        amounts = new int[INV_SIZE];
        initInv();
        yOrigin = 0;
    }
    
  public void add(Item item, int a){
        for(int i = 0; i<inven.length; i++) {
            if(inven[i] != null){
            if((inven[i] == item) & (item.getName().equals(inven[i].getName()))){
            if(amounts[i]< 99){
            amounts[i] += a;
            }
            break;
            }
            }
            else if(inven[i] == null){
            inven[i] = item;
            amounts[i] = 1;
            break;
            }
        }
    }
//Two options for items - draw amounts by item, pass in inventory<--- I did this. || Draw amounts by inventory, item 
//only draws icon & name

    
    public void remove(Item item, int amt){
        for(int i = 0; i <inven.length; i++) {
            if(inven[i] == item) {
            if((getItemAmount(item) - amt) <= 0){
            amounts[i] = 0;
            inven[i] = null;
            if((i+1) > 0){
                if(inven[i+1] != null & inven[i+2] == null){
                    inven[i] = inven[i+1];
                    amounts[i] = amounts[i+1];
                    inven[i+1] = null;
                    amounts[i+1] = 0;
                }
                else{  
                    for(int j = 2; j<inven.length; j++){
                        if(i+j+1 < inven.length){
                        if(inven[i+j] != null & inven[i+j+1] == null){
                        inven[i] = inven[i+j];
                        amounts[i] = amounts[i+j];
                        inven[i+j] = null;
                        amounts[i+j] = 0;
                            }
                        }
                    }
                }
            }
            }
            else{ amounts[i] -= amt; }
            break;
            }
            
        }
    }
    
    public void render(Graphics g2d, Window w, int spacing, int x, int y){
        
        if((getCurrLength() > 0)) {
        int itConst = spacing;
        for(int iter = yOrigin; iter < yOrigin+1;){
        g2d.setClip(w.getX()+6-(int)Camera.viewPort.getX(), w.getY()+6-(int)Camera.viewPort.getY(), w.getWidth()-6, w.getHeight()-12);
        for(Item item: inven){
                /*try {
                    item.render(g2d, this, x, iter+spacing+y);
                } catch (SlickException ex) {
                    Logger.getLogger(InventoryBak.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            iter+=itConst;
            if(iter > (itConst*(getCurrLength()-1))+yOrigin){ break; }
        }
        g2d.clearClip();
        }
        }
    }
    
    public int getCurrLength() {
        int i = 0;
        int res = 0;
        while(i <= 49) {
            if(inven[i] != null){
            res++;
            }
            i++;
        }
        return res;
    }

    private void initInv() {
        for(int i = 0; i < inven.length; i++){
            inven[i] = null;
        }
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
        for(int i = 0; i <inven.length; i++) {
        if(inven[i] != null) {
                if((inven[i] == item) & (item.getName().equals(inven[i].getName()))){
                    amt = amounts[i];
                    break;
                }
            }
        }
        return amt;
    }
    
}
