/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

/**
 *
 * @author Kieran
 */
public class Consumable extends Item {
    
    private float HPrate;
    private int HPamount;
    private float MPrate;
    private int MPamount;
    
    public Consumable(){
        super("Default", true);
    }
    
    public Consumable(String name) {
        super(name, true);
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
    
}
