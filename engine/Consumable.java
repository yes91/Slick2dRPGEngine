/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

/**
 *
 * @author Kieran
 */
public class Consumable extends Effect implements Item {
    
    private float HPrate;
    private int HPamount;
    private float MPrate;
    private int MPamount;
    private int price;
    
    public Consumable(){
        super("Default");
    }
    
    public Consumable(String name) {
        super(name);
    }
    
    public float getHPrate() {
        return HPrate;
    }

    public void setHPrate(float HPrate) {
        this.HPrate = HPrate;
    }

    public int getHPamount() {
        return HPamount;
    }

    public void setHPamount(int HPamount) {
        this.HPamount = HPamount;
    }

    public float getMPrate() {
        return MPrate;
    }

    public void setMPrate(float MPrate) {
        this.MPrate = MPrate;
    }

    public int getMPamount() {
        return MPamount;
    }

    public void setMPamount(int MPamount) {
        this.MPamount = MPamount;
    }
    
    public int getPrice(){
        return price;
    }
    
    public void setPrice(int price){
        this.price = price;
    }
    
    @Override
    public String toString() {
        String list = 
                "Name: "+getName()+"\n"+
                "Scope: "+getScope().toString()+"\n"+
                "Description: "+getDesc();
        return list;
    }

}
