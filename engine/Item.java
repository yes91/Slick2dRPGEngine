package engine;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Item implements PropertyChangeListener{
	public static enum Targets {SINGLE_PLAYER, WHOLE_PARTY, SINGLE_ENEMY, WHOLE_ENEMY_PARTY}
    
    private boolean useable;
    private Image image;
    private String name;
    private String type;
    private int index;
    public GameBattler wielder;
    public AttackStatEffect attackBoost;

    
    

    public Item(String name, boolean use) {
      image = SceneMap.getImage();
      this.name = name;
      this.useable = use;
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

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if(e.getPropertyName() == "amount") {
			GameBattler[] targets = {wielder};
			attackBoost.deactivate(Arrays.asList(targets), (int)e.getOldValue());
			attackBoost.activate(Arrays.asList(targets));
		}
	}

}
  