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
public abstract class GameUnit {
    
    public Random chance = new Random();
    
    public abstract List<GameBattler> getMembers();
    
    public abstract List<GameBattler> getLivingMembers();
    
    public abstract boolean allDead();
    
    public abstract GameBattler getRandomTarget();
    
    public abstract GameBattler getRandomDeadTarget();
    
}
