/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

/**
 *
 * @author redblast71
 */
public class StatusEffect extends Effect {
    
    public StatusEffect(String name, GameBattler[] target, int targetArraySize){
        this.target = new GameBattler[targetArraySize];
        this.name = name; 
    }
    
}
