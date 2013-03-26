/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author redblast71
 */
public class WindowMenuCommand extends WindowCommand{
    
    public static String[] list = new String[]{  
        "Item",
        "Skill",
        "Equip",
        "Status",
        "Save",
        "Bestiary", 
        "Party", 
        "Default",
        "Options"
    };
    
    public static int[] icons = new int[]{  
        144,
        131,
        2,
        137,
        141,
        178,
        103, 
        103, 
        98,
    };
    
    public WindowMenuCommand(int width, int cMax, int rMax) throws SlickException{
        super(width, list, cMax, rMax);
    }
    
    @Override
    public void drawItem(int ind){
        Rectangle rect = getItemRect(ind);
        rect.setX(rect.getX() + 4);
        rect.setWidth(rect.getX() - 8);
        drawItemName(commands[ind], rect.getX(), rect.getY() - oy, true, icons[ind]);
    }
}
