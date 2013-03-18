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
public class DepthBuffCompare1 implements Comparator<SpriteBattler1>{
    
    @Override
    public int compare(SpriteBattler1 g1, SpriteBattler1 g2) {
        return (int)(g1.posZ() - g2.posZ());
    }
}
