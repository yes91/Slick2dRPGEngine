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
    public List<EventCommand> commands;
    public String text = "Hey, I'm an NPC.\\| I'm aware I just broke the fourth wall,\n"
                + "but why does that matter in a tech demo?\\.\\. \\C[31]Kieran\\C[0]'s pretty talented\n"
                + "for a \\C[10]novice programmer\\C[0], eh? You know, \\.\\..\\.\\..\\.\\..\\| he did spend far too long on \n"
                + "this particular feature, though.\\.\\. I'm having a really nice time on this \n"
                + "grassy field. How are you?";
    
    public Event(float x, float y, int width, int height,String type1,String dg, String map, String map2){
        super.pos.x = x;
        super.pos.y = y;
        super.height = height;
        super.width = width;
        commands = new ArrayList<>();
        commands.add(new EventCommand("SHOW_TEXT", new Object[]{ "", 0, 0, 0, ""}));
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
