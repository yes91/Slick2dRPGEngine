/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.geom.Rectangle;


/**
 *
 * @author Kieran
 */
public class Physics {
    
    
    public static boolean checkCollisions(GameObject go1, GameObject go2) 
    {
        Rectangle r1 = go1.getBounds();
        
        Rectangle r2 = go2.getBounds();
        
        return r1.intersects(r2);
    }
    
    public static boolean checkCollisions(GameObject go1, Rectangle r) 
    {
        Rectangle r1 = go1.getBounds();
        
        return r1.intersects(r);
    }
    
}
