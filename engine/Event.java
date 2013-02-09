/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author redblast71
 */
public class Event extends GameObject{
    
    String type;
    String maploc;
    String maploc2;
    public List<EventCommand> list;
    
    public Event(float x, float y, int width, int height,String type1,String dg, String map, String map2){
        super.pos.x = x;
        super.pos.y = y;
        super.height = height;
        super.width = width;
        list = new ArrayList<>();
        list.add(new EventCommand(new Object[]{"People1", 0, 0, 2, "Hey there, \\C[0]buddy\\R. How's life?"}, 0, 0));
        type = type1;
        maploc = map;
        maploc2 = map2;
        
    }
    
    public String getType(){
        
        return type;
    }
    
    public Rectangle getActivationRect(){
        
        return new Rectangle(pos.x-4,pos.y-4,width+10,height+10);
    }
    
    
    
}
