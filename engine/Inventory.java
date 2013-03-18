/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author redblast71
 */
public class Inventory {
    
    public static final int INV_SIZE = 150;
    public static final int MAX_AMOUNT = 99;
    public ArrayList<Item> items;
    private HashMap<Item, Integer> amounts;
    
    public Inventory(){
        
        items = new ArrayList<>();
        amounts = new HashMap();
    }
    
  public boolean add(Item item, int amt){
            if(items.contains(item)){
                if(amounts.get(item).intValue() < MAX_AMOUNT){
                    amounts.put(item, amounts.get(item).intValue() + amt);
                }
            }
            else if(items.size() < INV_SIZE){
                items.add(item);
                amounts.put(item, amt);
            }
            else if(items.size() == INV_SIZE){
                return false;
            }
        return true;
    }
    
    public boolean remove(Item item, int amt){
            if(items.contains(item)){
                if(!(amounts.get(item).intValue() - amt <= 0)){
                    amounts.put(item, amounts.get(item).intValue() - amt);
                }
                else{
                    amounts.put(item, 0);
                    items.remove(item);
                }
            }
            else {
            return false;
        }
        return true;
    }
    
    public int getLength() {
        return items.size();
    }
    
    public int getItemAmount(Item item){
        Integer amt = 0;
        if(items.contains(item)){
            amt = amounts.get(item);
            if(amt == null){
                amt = 0;
            }
        }
        return amt.intValue();
    }
    
}
