/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.ArrayList;

/**
 *
 * @author redblast71
 */
public class Weapon extends Item {
    
    private int damage;
    private ArrayList<Element> elements;
    private float hitPercent;
    
    public Weapon(String name){
        super(name, false);
        elements = new ArrayList<>();
    }

    public Element[] getElements() {
        return elements.toArray(new Element[]{});
    }

    public void addElement(Element e) {
        this.elements.add(e);
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
