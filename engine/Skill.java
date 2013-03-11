/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

/**
 *
 * @author redblast71
 */
public class Skill extends Effect{
    
    private float hitRatio;
    
    public Skill(String name){
        super(name);
    }

    public float getHitRatio() {
        return hitRatio;
    }

    public void setHitRatio(float hitRatio) {
        this.hitRatio = hitRatio;
    }
    
}
