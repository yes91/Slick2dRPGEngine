/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Color;

/**
 *
 * @author Kieran
 */
public class Timing {
    public int frame;
    public String se;
    public int flashScope;
    public Color flashColor;
    public int flashDuration;
    public int condition;

    public Timing() {
        frame = 0;
        se = "";
        flashScope = 0;
        flashColor = Color.white;
        flashDuration = 5;
        condition = 0;
    }
    
}
