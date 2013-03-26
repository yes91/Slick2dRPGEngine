/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.List;
import java.util.Random;

/**
 *
 * @author redblast71
 */
public abstract class GameUnit<T extends GameBattler> {
    
    public Random chance = new Random();
    
    public abstract List<T> getMembers();
    
    public abstract List<T> getLivingMembers();
    
    public abstract boolean allDead();
    
    public abstract GameBattler getRandomTarget();
    
    public abstract GameBattler getRandomDeadTarget();
    
}
