/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.ArrayList;

/**
 *
 * @author Kieran
 */
public class EffectAnimation {
    
    public int id;
    public String name;
    public String animationName;
    public int animationHue;
    public int position;
    public int frameMax;
    public ArrayList<Frame> frames;
    public ArrayList<Timing> timings;
    
    public EffectAnimation(){
        id = 0;
        name = "";
        animationName = "";
        animationHue = 0;
        position = 1;
        frameMax = 1;
        frames = new ArrayList<>();
        frames.add(new Frame());
        timings = new ArrayList<>();
    }
}
