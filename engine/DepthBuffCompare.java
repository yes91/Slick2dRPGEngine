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
public class DepthBuffCompare implements Comparator<float[]>{
    
    @Override
    public int compare(float[] f1, float[] f2) {
        return (int)(f1[2] - f2[2]);
    }
}
