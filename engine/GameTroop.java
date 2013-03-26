/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author redblast71
 */
public class GameTroop extends GameUnit<GameEnemy>{
    
    private final int MAX_MEMBERS = 10;
    private List<GameEnemy> enemies;
    
    public void setMembers(List l){
        this.enemies = l;
    }

    @Override
    public List<GameEnemy> getMembers() {
       return enemies;
    }

    @Override
    public List<GameEnemy> getLivingMembers() {
        List<GameEnemy> result = new ArrayList<>();
        for(GameEnemy e: enemies){
            if(!e.isDead()){
                result.add(e);
            }
        }
        return result;
    }
    
    @Override
    public boolean allDead(){
        return getLivingMembers().isEmpty();
    }

    @Override
    public GameBattler getRandomTarget() {
        return getLivingMembers().get(chance.nextInt(getLivingMembers().size()));
    }

    @Override
    public GameBattler getRandomDeadTarget() {
        List<GameBattler> dead = new ArrayList<>();
        for(GameEnemy e: enemies){
            if(e.isDead()){
               dead.add(e); 
            }
        }
        return dead.get(chance.nextInt(dead.size())); 
    }
    
    public void makeActions(){
        for(GameEnemy e: enemies){
            e.makeAction();
        }
    }
    
}
