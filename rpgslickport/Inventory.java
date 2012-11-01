/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rpgslickport;

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
    public ArrayList<Consumable> items;
    public ArrayList<Weapon> weapons;
    public ArrayList<Item> allitems;
    //public ArrayList<Armor> inven4;
    private int[] amountsI;
    private int[] amountsW;
    private int yOrigin;
    
    Inventory(){
        
        items = new ArrayList<>();
        allitems = new ArrayList<>();
        weapons = new ArrayList<>();
        amountsI = new int[INV_SIZE];
        amountsW = new int[INV_SIZE];
        initInv();
        yOrigin = 0;
    }
    
  public boolean add(Item item, int a){
        if(item instanceof Weapon){
            if(weapons.contains((Weapon)item)){
                if(amountsW[weapons.indexOf((Weapon)item)] < MAX_AMOUNT){
                amountsW[weapons.indexOf((Weapon)item)] += a;
                }
            }
            else if(weapons.size()+items.size() < INV_SIZE){
                weapons.add((Weapon)item);
                amountsW[weapons.indexOf((Weapon)item)] = a;
            }
            else if(weapons.size()+items.size() == INV_SIZE){
                return false;
            }
        }
        else if(item instanceof Consumable){
            if(items.contains((Consumable)item)){
                if(amountsI[items.indexOf((Consumable)item)] < MAX_AMOUNT){
                amountsI[items.indexOf((Consumable)item)] += a;
                }
            }
            else if(weapons.size()+items.size() < INV_SIZE){
                items.add((Consumable)item);
                amountsI[items.indexOf((Consumable)item)] = a;
            }
            else if(weapons.size()+items.size() == INV_SIZE){
                return false;
            }
        }
        update();
        return true;
    }
//Two options for items - draw amounts by item, pass in inventory<--- I did this. || Draw amounts by inventory, item 
//only draws icon & name
    
    public boolean remove(Item item, int amt){
        if(item instanceof Weapon){
            if(weapons.contains((Weapon)item)){
                if(!(amountsW[weapons.indexOf((Weapon)item)]-amt <= 0)){
                amountsW[weapons.indexOf((Weapon)item)] -= amt;
                }
                else{
                    amountsW[weapons.indexOf((Weapon)item)] = 0;
                    weapons.remove((Weapon)item);
                }
            }
            else return false;
        }
        if(item instanceof Consumable){
            if(items.contains((Consumable)item)){
                if(!(amountsI[items.indexOf((Consumable)item)]-amt <= 0)){
                amountsI[items.indexOf((Consumable)item)] -= amt;
                }
                else{
                    amountsI[items.indexOf((Consumable)item)] = 0;
                    items.remove((Consumable)item);
                }
            }
            else return false;
        }
        update();
        return true;
    }
    
    public void render(Graphics g2d, Window w, int spacing, int x, int y, String sort){
        
        if((getCurrLength() > 0)) {
        int itConst = spacing;
        for(int iter = yOrigin; iter < yOrigin+1;){
        g2d.setClip(w.getX()+6-(int)Camera.viewPort.getX(), w.getY()+6-(int)Camera.viewPort.getY(), w.getWidth()-6, w.getHeight()-12);
        if(sort.equals("all")){
        for(Item item: allitems){
                try {
                    item.render(g2d, this, x, iter+spacing+y);
                } catch (SlickException ex) {
                    Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
                }
            iter+=itConst;
            if(iter > (itConst*(getCurrLength()-1))+yOrigin){ break; }
        }
        }
        if(sort.equals("weapon")){
            for(Weapon item: weapons){
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
        return weapons.size()+items.size();
    }

    private void initInv() {
        for(int i = 0; i < amountsW.length; i++){
            amountsW[i] = 0;
        }
        for(int i = 0; i < amountsI.length; i++){
            amountsI[i] = 0;
        }
        
    }
    
    public void scroll(int dy){
    
    yOrigin += dy;
    }
    
    public void setY(int y){
        
        yOrigin = y;
    }
    
    public void update(){
        allitems = new ArrayList<>();
        allitems.addAll(items);
        allitems.addAll(weapons);
    }
    
    public int getItemAmount(Item item){
        int amt = 0;
         if(item instanceof Weapon){
             if(weapons.contains((Weapon)item)){
                 amt = amountsW[weapons.indexOf((Weapon)item)];
             }
         }
         if(item instanceof Consumable){
             if(items.contains((Consumable)item)){
                 amt = amountsI[items.indexOf((Consumable)item)];
             }
         }
        
        return amt;
    }
    
}
