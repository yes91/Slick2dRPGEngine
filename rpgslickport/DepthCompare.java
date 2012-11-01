/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rpgslickport;

import java.util.Comparator;

/**
 *
 * @author redblast71
 */
public class DepthCompare implements Comparator<GameObject>{

    @Override
    public int compare(GameObject o1, GameObject o2) {
        return (int)(o1.pos.y - o2.pos.y);
    }
    
}
