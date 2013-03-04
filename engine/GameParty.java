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
public class GameParty extends GameUnit{
    
    private final int MAX_MEMBERS = 10;
    public List<GameActor> actors;
    private int gold;
    private Inventory inven;
    
    public GameParty(){
        inven = new Inventory();
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
