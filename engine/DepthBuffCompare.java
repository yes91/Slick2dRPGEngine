/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.Comparator;

/**
 *
 * @author Kieran
 */
public class DepthBuffCompare implements Comparator<GameActor>{
    
    @Override
    public int compare(GameActor g1, GameActor g2) {
        return (int)((g1.basePosZ + g1.moveZ) - (g2.basePosZ + g2.moveZ));
    }
}
