/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

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
    public List<Frame> frames;
    public List<Timing> timings;
    
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
    
    public static class Timing {
        
        public int frame;
        public String se;
        public int flashScope;
        public Color flashColor;
        public int flashDuration;
        public int condition;
        
        
        public Timing(){
            frame = 0;
            se = "";
            flashScope = 0;
            flashColor = Color.white;
            flashDuration = 5;
            condition = 0;
        }
        
        
    }
    
    public static class Frame {
        
        public List<Cell> cellData;
        public int cellMax;
        
        public Frame(){
            cellMax = 0;
            cellData = new ArrayList<>();
        }
        
        public static class Cell {
            
            public Cell(){
                pattern = 0;
                x = 0; y = 0;
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
    }
}
