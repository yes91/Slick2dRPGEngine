/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Kieran
 */
public class Frame {
    public ArrayList<Cell> cellData;
    public int cellMax;

    public Frame() {
        cellMax = 0;
        cellData = new ArrayList<>();
    }
    
}
