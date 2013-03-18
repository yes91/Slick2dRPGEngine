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
public class DepthBuffCompare implements Comparator<SpriteBattler>{
    
    @Override
    public int compare(SpriteBattler g1, SpriteBattler g2) {
        return (int)(g1.posZ() - g2.posZ());
    }
}
