/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

/**
 *
 * @author redblast71
 */
public class Weapon extends Item {
    
    private int damage;
    private float hitPercent;
    
    public Weapon(){
        super("Default", false);
    }
    
    public Weapon(String name){
        super(name, false);
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
}
