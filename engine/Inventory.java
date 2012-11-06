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
<<<<<<< HEAD
    public ArrayList<Consumable> items;
    public ArrayList<Weapon> weapons;
    public ArrayList<Item> allitems;
    //public ArrayList<Armor> inven4;
    private int[] amountsI;
    private int[] amountsW;
=======
    public ArrayList<Item> items;
    //public ArrayList<Armor> inven4;
    private int[] amounts;
>>>>>>> upstream/master
    private int yOrigin;
    
    Inventory(){
        
        items = new ArrayList<>();
<<<<<<< HEAD
        allitems = new ArrayList<>();
        weapons = new ArrayList<>();
        amountsI = new int[INV_SIZE];
        amountsW = new int[INV_SIZE];
=======
        amounts = new int[INV_SIZE];
>>>>>>> upstream/master
        initInv();
        yOrigin = 0;
    }
    
  public boolean add(Item item, int a){
<<<<<<< HEAD
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
=======
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
>>>>>>> upstream/master
        return true;
    }
//Two options for items - draw amounts by item, pass in inventory<--- I did this. || Draw amounts by inventory, item 
//only draws icon & name
    
    public boolean remove(Item item, int amt){
<<<<<<< HEAD
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
    
=======
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
>>>>>>> upstream/master
    public void render(Graphics g2d, Window w, int spacing, int x, int y, String sort){
        
        if((getCurrLength() > 0)) {
        int itConst = spacing;
        for(int iter = yOrigin; iter < yOrigin+1;){
        g2d.setClip(w.getX()+6-(int)Camera.viewPort.getX(), w.getY()+6-(int)Camera.viewPort.getY(), w.getWidth()-6, w.getHeight()-12);
        if(sort.equals("all")){
<<<<<<< HEAD
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
=======
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
>>>>>>> upstream/master
        }
        g2d.clearClip();
        }
    }
    
    public int getCurrLength() {
<<<<<<< HEAD
        return weapons.size()+items.size();
    }

    private void initInv() {
        for(int i = 0; i < amountsW.length; i++){
            amountsW[i] = 0;
        }
        for(int i = 0; i < amountsI.length; i++){
            amountsI[i] = 0;
        }
        
=======
        return items.size();
    }

    private void initInv() {
        for(int i = 0; i < amounts.length; i++){
            amounts[i] = 0;
        }  
>>>>>>> upstream/master
    }
    
    public void scroll(int dy){
    
    yOrigin += dy;
    }
    
    public void setY(int y){
        
        yOrigin = y;
    }
    
<<<<<<< HEAD
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
        
=======
    public int getItemAmount(Item item){
        int amt = 0;
        if(items.contains(item)){
            amt = amounts[items.indexOf(item)];
        }
>>>>>>> upstream/master
        return amt;
    }
    
}
