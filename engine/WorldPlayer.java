package engine;

import org.lwjgl.input.Controllers;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class WorldPlayer extends GameObject {

    private float dx;
    private float dy;
    private float lastX;
    private float lastY;
    private float frame;
    private Image image;
    private int lastFrame;
    private Inventory inven;
    private boolean action;
    public String name;
    public Item hand;
    public  Item head;
    public  Item chest;
    public  Item legs;
    public  Item boots;
    public int baseHP;
    public int maxHP;
    public int currentHP;
    public int baseATK;
    public int maxATK;
    public int baseDEF;
    public int maxDEF;

    public WorldPlayer(Image i) {
        image = i;
        inven = new Inventory();
        pos.x = 40;
        pos.y = 60;
        baseHP = 100;
        maxHP = baseHP;
        currentHP = maxHP;
    }


    public void update(Input in, int delta) {
        getInput(in, delta);
        if(SceneMap.isBlocked() == true || SceneMap.stopPlayer() == true)
        {
            pos.x = lastX;
            pos.y = lastY;
        }
        lastX = pos.x;
        lastY = pos.y;
        pos.x += dx;
        pos.y += dy;
        
    }
    @Override
    public void render(Graphics g2d) {
     if(currentHP > 0)
        {
        int fCount = (int)(frame + .0078125f * SceneMap.deltaG);
        if(fCount < 4 & isMoving() == true)
        {
          frame += .0078125f * SceneMap.deltaG;
        }
        else
        {
          frame = 0;
        }
        }
     if(!SceneMap.blocked){
     Sprite.drawSpriteFrame(getImage(), g2d, (int)pos.x, (int)pos.y, 4, getFrame()+(int)frame, 64, 96);
     }
     else{
     Sprite.drawSpriteFrame(getImage(), g2d, (int)lastX, (int)lastY, 4, getFrame()+(int)frame, 64, 96);
     }
     //Sprite.animateSprite(image, g2d, x, y, 64, 96, 4, getFrame(), getFrame()+4, .125f, isMoving());
        
   }
    
    public void setX(float x1) {
        pos.x = x1;
    }

    public void setY(float y1) {
        pos.y = y1;
    }
    
    public int getTileX(TiledMap map) {
        return (int)(getBounds().getX()/map.getTileWidth());
    }

    public int getTileY(TiledMap map) {
        return (int)(getBounds().getY()/map.getTileHeight());
    }

    public Image getImage() {
        return image;
    }
    
    public boolean isMoving() {
      
      if(((dx != 0) | (dy != 0)) & !SceneMap.blocked){
        return true;
      }
      else {
        return false;
      }
    }
    
    public void giveItem(Item item, int a){
        
        inven.add(item, a);
    }
    
    public void renderInv(Graphics g2d, Window w, int sp, int x, int y){
        inven.render(g2d, w, sp, x, y, "all");
    }

    public void getInput(Input input, int delta) {
        

        if(SceneMap.uiFocus == false){
        
        if (input.isKeyPressed(Input.KEY_J)) {
            
            action = true;
        }
        else { action = false; } 
        
        //if (input.isKeyPressed(Input.KEY_E)) {
            
            //new Menu(this);
        //}
   
        if (input.isKeyPressed(Input.KEY_Q)){
            
            giveItem(ItemReader.getItems().get(1), 1);
        }
        
        if(input.isKeyPressed(Input.KEY_U)){
            
            for(Item item : ItemReader.getItems()){
                giveItem(item, 1);
            }
        }
        
        if (input.isKeyPressed(Input.KEY_R)) {
            
            currentHP -= 10;
        }
        

        if (input.isKeyDown(Input.KEY_A)) {
            if(input.isKeyDown(Input.KEY_LSHIFT)){
                dx = -(0.48f * delta);
            }
            else{
            dx = -(0.24f * delta);
            }
        }
        
        if (input.isKeyDown(Input.KEY_D)) {
            if(input.isKeyDown(Input.KEY_LSHIFT)){
                dx = 0.48f * delta;
            }
            else{
            dx = 0.24f * delta;
            }
        }
        
        if((!input.isKeyDown(Input.KEY_D)) && (!input.isKeyDown(Input.KEY_A))) 
        
        { dx = 0; }

        if (input.isKeyDown(Input.KEY_W)) {
            if(input.isKeyDown(Input.KEY_LSHIFT)){
                dy = -(0.48f * delta);
            }
            else{
            dy = -(0.24f * delta);
            }
        }

        if (input.isKeyDown(Input.KEY_S)) {
            if(input.isKeyDown(Input.KEY_LSHIFT)){
                dy = 0.48f * delta;
            }
            else{
            dy = 0.24f * delta;
            }
        }
        
        if((!input.isKeyDown(Input.KEY_W)) && (!input.isKeyDown(Input.KEY_S)))
            
        { dy = 0; }
        
        }
        else {action = false; dx = 0; dy = 0;}
    }
    
    public void equip(Item i){
        if(!i.isUseable()){
          if(i.getType().equals("weapon")){
              hand = i;
              System.out.println(hand.toString());
          }
        }
        if(hand != null){
            maxATK = baseATK + ((Weapon)hand).getDmg();
        }
    }
    
    public int getFrame()
    {
      
      if(dx > 0)
        {
        lastFrame = 0;
          return 0;
        }
        if(dx < 0)
        {
          lastFrame = 8;
          return 8;
        }
        if(dy < 0)
        {
          lastFrame = 4;
          return 4;
        }
        if(dy > 0)
        {
          lastFrame = 12;
          return 12;
        }
        else
        {
          return lastFrame;
        }
    }
    
    public boolean getAction(){
        
        return action;
    }
    
    @Override
     public Rectangle getBounds() {
     return new Rectangle(pos.x+10, pos.y+40, 52, 52);
 }
     public Inventory getInv(){
     
     return inven;
    }
} 