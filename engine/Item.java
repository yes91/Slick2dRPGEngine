package engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Item {
    
    public static enum Targets {SINGLE_PLAYER, WHOLE_PARTY, SINGLE_ENEMY, WHOLE_ENEMY_PARTY}
    private static Image image = Cache.getImage("IconSet.png");
    private boolean useable;
    private String name;
    private String desc;
    private int price;
    private int gid;

    
    

    public Item(String name, boolean use) {
      this.name = name;
      this.useable = use;
      this.price = 0;
      this.desc = "Default item description.";
        
    }

    @Deprecated
    public void render(Graphics g2d, Inventory inv, float x, float y) throws SlickException{ 
        Sprite.drawSpriteFrame(image, g2d, x, y, 16, gid, 24, 24);
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
    
    
    public boolean isUseable(){
     
        return useable;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public int getIndex(){
        return gid;
    }
    
    public void setIndex(int n){ 
        gid = n;
    }
    
    @Override
    public String toString(){
        return name;
    }

}
  