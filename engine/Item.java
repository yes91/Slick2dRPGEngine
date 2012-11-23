package engine;

import java.beans.PropertyChangeListener;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Item implements PropertyChangeListener {
	public static enum Targets {SINGLE_PLAYER, WHOLE_PARTY, SINGLE_ENEMY, WHOLE_ENEMY_PARTY}
    
    private boolean useable;
    private Image image;
    private String name;
    private String type;
    private int index;
    public GameBattler wielder;
    

    public Item(String name, boolean useable) {
      image = SceneMap.getImage();
      this.name = name;
      this.useable = useable;
    }

    public void render(Graphics g2d, Inventory inv, float x, float y) throws SlickException{ 
        Sprite.drawSpriteFrame(image, g2d, x, y, 16, index, 24, 24);
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
  