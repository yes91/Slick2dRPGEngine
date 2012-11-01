/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rpgslickport;

/**
 *
 * @author redblast71
 */
public class Weapon extends Item {
    
    private int damage;
    private String subtype;
    
    public Weapon(String name,String type) {
        super(name, false);
        this.subtype = type;
        super.setType("weapon");
    }
    
    public void setDmg(int d){ 
        this.damage = d;
    }
    
    public int getDmg(){
        return damage;
    }
    
    public String getSubtype(){
        return subtype;   
    }    
}
