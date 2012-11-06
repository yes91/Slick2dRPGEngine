/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author redblast71
 */
public class Event extends GameObject{
    
    String dialog;
    String type;
    String maploc;
    String maploc2;
    
    public Event(float x, float y, int width, int height,String type1,String dg, String map, String map2){
        super.pos.x = x;
        super.pos.y = y;
        super.height = height;
        super.width = width;
        type = type1;
        dialog = dg;
        maploc = map;
        maploc2 = map2;
        
    }
    
    public String getText(){
        
        return dialog;
    }
    
    public String getType(){
        
        return type;
    }
    
    public Rectangle getActivationRect(){
        
        return new Rectangle(pos.x-4,pos.y-4,width+10,height+10);
    }
    
    
    
}
