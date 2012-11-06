<<<<<<< HEAD
/**
 * This class describes an item that the character can or cannot use
 */
=======
>>>>>>> upstream/master
package engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

<<<<<<< HEAD
public abstract class Item {
    
    private boolean useable; // Whether or not the character can use the item
    private Image image; // The image for the item
    private String name; // The name of the item
    private String type; // The item's type
    private int index;
    public static enum Targets {SINGLE_PLAYER, WHOLE_PARTY, SINGLE_ENEMY, WHOLE_ENEMY_PARTY}

    

    public Item(String name, boolean use) {
      this.image = SceneMap.getImage(); // ?
=======
public class Item {
    
    private boolean useable;
    private Image image;
    private String name;
    private String type;
    private int index;

    
    

    public Item(String name, boolean use) {
      image = SceneMap.getImage();
>>>>>>> upstream/master
      this.name = name;
      this.useable = use;
        
    }
    
    public void render(Graphics g2d, Inventory inv, float x, float y) throws SlickException{
        
<<<<<<< HEAD
        Sprite.drawSpriteFrame(getImage(), g2d, x, y, 16, index, 24, 24);
=======
        Sprite.drawSpriteFrame(image, g2d, x, y, 16, index, 24, 24);
>>>>>>> upstream/master
        Cache.getFont().drawString(x+24, y, name);
        String amountToDraw = "" + inv.getItemAmount(this);
        Cache.getFont().drawString((x+(Cache.getFont().getWidth(name)+32)), y,amountToDraw);
    }
    
    public Image getImage() {
     
      return image;
    }
    
    public String getName(){
        
        return name;
    }
    
    public void setType(String t){
        
        type = t;
    }
    
    public String getType(){
        
        return type;
    }
    
    public boolean isUseable(){
     
        return useable;
    }
    
    public void setIndex(int n){
        
        index = n;
    }
    
    @Override
    public String toString(){
        return name;
    }

}
  