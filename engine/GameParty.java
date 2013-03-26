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
public class GameParty extends GameUnit<GameActor>{
    
    private final int MAX_MEMBERS = 10;
    private List<GameActor> actors;
    private int gold;
    private Inventory inven;
    
    public GameParty(){
        inven = new Inventory();
    }
    
    public void setMembers(List l){
        this.actors = l;
    }
    
    @Override
    public List<GameActor> getMembers(){
        return actors;
    }
    
    @Override
    public List<GameActor> getLivingMembers(){
        List<GameActor> result = new ArrayList<>();
        for(GameActor a: actors){
            if(!a.isDead()){
                result.add(a);
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
        for(GameActor a: actors){
            if(a.isDead()){
               dead.add(a); 
            }
        }
        return dead.get(chance.nextInt(dead.size())); 
    }
    
    public void clearActions(){
        for(GameActor a: actors){
            a.action.clear();
        }
    }
    
    public Inventory getInv(){
        return inven;
    }
    
    public void giveItem(Item item, int a) {
        inven.add(item, a);
    }
    
    public void takeItem(Item item, int a) {
        inven.remove(item, a);
    }
    
}
