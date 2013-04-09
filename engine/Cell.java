/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Graphics;

/**
 *
 * @author Kieran
 */
public class Cell {

    public Cell() {
        pattern = 0;
        x = 0;
        y = 0;
        mirror = false;
        angle = 0;
        zoom = 1f;
        opacity = 1f;
        blendType = Graphics.MODE_NORMAL;
    }
    public int pattern;
    public int x;
    public int y;
    public boolean mirror;
    public float angle;
    public float zoom;
    public float opacity;
    public int blendType;
    
}
