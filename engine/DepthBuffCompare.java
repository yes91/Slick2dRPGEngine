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
public class DepthBuffCompare implements Comparator<GameBattler>{
    
    @Override
    public int compare(GameBattler g1, GameBattler g2) {
        return (int)(g1.posZ() - g2.posZ());
    }
}
