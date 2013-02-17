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
public class GameUnit {
    
    public List<GameBattler> getMembers(){
        return null;
    }
    
    public List<GameBattler> getLivingMembers(){
        List<GameBattler> result = new ArrayList<>();
        for(GameBattler b: getMembers()){
            if(!b.isDead()){
                result.add(b);
            }
        }
        return result;
    }
    
    
}
