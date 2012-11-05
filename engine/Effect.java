/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

/**
 *
 * @author redblast71
 */
public abstract class Effect {
    String name;
    GameBattler[] target; // The target of a particular effect
    
    @Override
    public String toString(){
        return name;
    }
    
}
