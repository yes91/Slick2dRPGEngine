/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

/**
 *
 * @author redblast71
 */
public class Weapon extends BaseItem implements Item {
    
    private int price;
    private int damage;
    private float hitPercent;
    
    public Weapon(){
        super("Default");
    }
    
    public Weapon(String name){
        super(name);
    }

    public float getHitPercent() {
        return hitPercent;
    }

    public void setHitPercent(float hitPercent) {
        this.hitPercent = hitPercent;
    }
    
    public void setDmg(int d){ 
        this.damage = d;
    }
    
    public int getDmg(){
        return damage;
    } 
    
    @Override
    public int getPrice(){
        return price;
    }
    
    @Override
    public void setPrice(int price){
        this.price = price;
    }
}
