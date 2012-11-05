/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 *
 * @author Kieran
 */
@Deprecated
public class Menu{
    
    private Window main;
    private Window opts;
    private Window portr;
    private boolean notInSub;
    private boolean inInv;
    private boolean inEquip;
    private Image skin;
    private int y;
    private int yInv;
    private int xOff;
    private int count;
    private int countInv;
    private int itemIndex;
    private Item itemSelected;
    private WorldPlayer player;
    
    //Deprected; however, notable code within commented body.
    /*
    public Menu(WorldPlayer p){
        player = p;
        skin = Cache.getImage("Window.png");
        main = new Window(0+(int)(SceneMap.B_WIDTH*0.1), 0+(int)(SceneMap.B_HEIGHT*0.1), (SceneMap.B_WIDTH-(int)(SceneMap.B_WIDTH*0.2))-(int)(SceneMap.B_WIDTH*0.15), SceneMap.B_HEIGHT-(int)(SceneMap.B_HEIGHT*0.2));
        opts = new Window(0+((SceneMap.B_WIDTH-(int)(SceneMap.B_WIDTH*0.2))-(int)(SceneMap.B_WIDTH*0.05)), 0+(main.getHeight()-(int)(SceneMap.B_HEIGHT*0.4)), (int)(SceneMap.B_WIDTH*0.15), (int)(SceneMap.B_HEIGHT*0.5));
        portr = new Window(0+((SceneMap.B_WIDTH-(int)(SceneMap.B_WIDTH*0.2))-(int)(SceneMap.B_WIDTH*0.05)), 0+((int)(SceneMap.B_HEIGHT*0.1)), (int)(SceneMap.B_WIDTH*0.15), (int)(SceneMap.B_HEIGHT*0.3));
        notInSub = true;
        inInv = false;
        inEquip = false;
        y = 0;
        yInv = main.getY()+32;
        player.getInv().setY(0);
        count = 0;
        countInv = 0;
        itemIndex = 0;
        SceneMap.addUIElement(portr);
        SceneMap.addUIElement(main);
        SceneMap.addUIElement(opts);
        
    }
    
    public void render(Graphics g){
        
        Cache.getFont().drawString(opts.getX()+((int)(opts.getWidth()*0.2)), opts.getY()+((int)(opts.getHeight()*0.2)), "Item");
        Cache.getFont().drawString(opts.getX()+((int)(opts.getWidth()*0.2)), opts.getY()+((int)(opts.getHeight()*0.3)), "Skill");
        Cache.getFont().drawString(opts.getX()+((int)(opts.getWidth()*0.2)), opts.getY()+((int)(opts.getHeight()*0.4)), "Equip");
        Cache.getFont().drawString(opts.getX()+((int)(opts.getWidth()*0.2)), opts.getY()+((int)(opts.getHeight()*0.5)), "Status");
        Cache.getFont().drawString(opts.getX()+((int)(opts.getWidth()*0.2)), opts.getY()+((int)(opts.getHeight()*0.6)), "Save");
        Cache.getFont().drawString(opts.getX()+((int)(opts.getWidth()*0.2)), opts.getY()+((int)(opts.getHeight()*0.7)), "Options");
        if(notInSub){
            
            Sprite.drawSpriteFrame(player.getImage(), g, main.getX()+40, main.getY()+40, 4, 12, 64, 96);
            Cache.getFont().drawString(main.getX()+40, main.getY()+10, "Atticus");
            Cache.getFont().drawString(main.getX()+110, main.getY()+40, "HP: "+player.currentHP);
        }
        else if(inInv){
            
            player.renderInv(g, main, 32, main.getX()+16, main.getY());
            //g.setClip(main.getX()+6, main.getY()+6, main.getWidth()-6, main.getHeight()-12);
            if(itemSelected != null){
            skin.draw(main.getX()+16, yInv-4, main.getX()+24+32+Cache.getFont().getWidth(itemSelected.getName())+Cache.getFont().getWidth(""+player.getInv().getItemAmount(itemSelected)), yInv+((int)(main.getHeight()*0.05))-8, 64, 64, 96, 96);
            }
            //g.clearClip();
        }
        else if(inEquip){
            
            player.renderInv(g, main, 32, main.getX()+16, main.getY());
            //g.setClip(main.getX()+6, main.getY()+6, main.getWidth()-6, main.getHeight()-12);
            if(itemSelected != null){
            skin.draw(main.getX()+16, yInv-4, main.getX()+24+32+Cache.getFont().getWidth(itemSelected.getName())+Cache.getFont().getWidth(""+player.getInv().getItemAmount(itemSelected)), yInv+((int)(main.getHeight()*0.05))-8, 64, 64, 96, 96);
            }
            //g.clearClip();
        }
        
        skin.draw(opts.getX()+((int)(opts.getWidth()*0.2))+12+(xOff), y-4, opts.getX()-12+((int)(opts.getWidth()*0.2)), y+((int)(opts.getHeight()*0.1))-8, 64, 64, 96, 96);
    }
    
    public void update(Input input){
        
        if(SceneMap.inMenu == true){
       if (inEquip){
            if(itemIndex < 0){
                itemIndex = 0;
            }
            if(itemIndex >= (player.getInv().getCurrLength()-1)){
                itemIndex = (player.getInv().getCurrLength()-1);
            }
            if(itemIndex >= 0){
            itemSelected = player.getInv().allitems.get(itemIndex);
            System.out.println(itemSelected.toString());
            }
            if(input.isKeyPressed(Input.KEY_K)){
                Sounds.cancel.play();
                inEquip = false;
                notInSub = true;
            }
            if(itemSelected != null){
            if(input.isKeyPressed(Input.KEY_W)){
                Sounds.cursor.play();
                countInv--;
                itemIndex--;
                yInv -= 32;
            }
            if(input.isKeyPressed(Input.KEY_S)){
                if(itemIndex < (player.getInv().getCurrLength()-1)){
                Sounds.cursor.play();
                countInv++;
                itemIndex++;
                yInv += 32;
                }
            }
            /*if(!itemSelected.isUseable() & input.isKeyPressed(Input.KEY_J)){
                player.equip(itemSelected);
            }*/
            /*}
            if(countInv > 15){
            
            countInv = 15;
            yInv = main.getY()+(16*32);
            
            player.getInv().scroll(-32);
            }
            if(countInv < 0){
            
            countInv = 0;
            if(itemSelected.equals(player.getInv().allitems.get(0))){
            player.getInv().setY(0);
            }
            else { player.getInv().scroll(32); }
            yInv = main.getY()+32;
            }
            
        }
        
        if (inInv){
            if(itemIndex < 0){
                itemIndex = 0;
            }
            if(itemIndex >= (player.getInv().allitems.size()-1)){
                itemIndex = (player.getInv().allitems.size()-1);
            }
            if(itemIndex >= 0){
            itemSelected = player.getInv().allitems.get(itemIndex);
            System.out.println(itemSelected.toString());
            }
            if(input.isKeyPressed(Input.KEY_K)){
                Sounds.cancel.play();
                inInv = false;
                notInSub = true;
            }
            if(itemSelected != null){
            if(input.isKeyPressed(Input.KEY_W)){
                Sounds.cursor.play();
                countInv--;
                if(!(itemIndex-1<0)){
                itemIndex--;
                }
                yInv -= 32;
            }
            if(input.isKeyPressed(Input.KEY_S)){
                if(itemIndex < (player.getInv().getCurrLength()-1)){
                Sounds.cursor.play();
                countInv++;
                itemIndex++;
                yInv += 32;
                }
            }
            if(itemSelected.isUseable() && (input.isKeyPressed(Input.KEY_J))){
                ((Consumable)itemSelected).use(player, player.getInv());
            }
            }
            if(countInv > 15){
            
            countInv = 15;
            yInv = main.getY()+(16*32);
            
            player.getInv().scroll(-32);
            }
            if(countInv < 0){
            
            countInv = 0;
            if(itemSelected.equals(player.getInv().allitems.get(0))){
            player.getInv().setY(0);
            }
            else { player.getInv().scroll(32); }
            yInv = main.getY()+32;
            }
            
        }
        if(notInSub){
        if(input.isKeyPressed(Input.KEY_W)){
            Sounds.cursor.play();
            count--;
        }
        if(input.isKeyPressed(Input.KEY_S)){
            Sounds.cursor.play();
            count++;
        }
        }
        if(count > 5){
            
            count = 0;
        }
        if(count < 0){
            
            count = 5;
        }
        if(count == 0){
            y=opts.getY()+((int)(opts.getHeight()*0.2));
            xOff = Cache.getFont().getWidth("Item");
            if(input.isKeyPressed(Input.KEY_J) || input.isControlPressed(5)){
                    notInSub = false;
                    yInv = main.getY()+32;
                    itemIndex = 0;
                    countInv = 0;
                    inInv = true;
                }
        }
        if(count == 1){
            y=opts.getY()+((int)(opts.getHeight()*0.3));
            xOff = Cache.getFont().getWidth("Skill");
        }
        if(count == 2){
            y=opts.getY()+((int)(opts.getHeight()*0.4));
            xOff = Cache.getFont().getWidth("Equip");
            if(input.isKeyPressed(Input.KEY_J)){
                    notInSub = false;
                    yInv = main.getY()+32;
                    itemIndex = 0;
                    countInv = 0;
                    SceneMap.removeUIElement(main);
                    main.height = SceneMap.B_HEIGHT-(int)(SceneMap.B_HEIGHT*0.5);
                    main.initY = 0+(int)(SceneMap.B_HEIGHT*0.3);
                    SceneMap.addUIElement(main);
                    inEquip = true;
                }
        }
        if(count == 3){
            y=opts.getY()+((int)(opts.getHeight()*0.5));
            xOff = Cache.getFont().getWidth("Status");
        }
        if(count == 4){
            y=opts.getY()+((int)(opts.getHeight()*0.6));
            xOff = Cache.getFont().getWidth("Save");
        }
        if(count == 5){
            y=opts.getY()+((int)(opts.getHeight()*0.7));
            xOff = Cache.getFont().getWidth("Options");
        }
                
        
        }
        
    }
    
    public void destroy(){
        
        SceneMap.removeUIElement(portr);
        SceneMap.removeUIElement(main);
        SceneMap.removeUIElement(opts);
    }
    
    public boolean getNotSub(){
        
        return notInSub;
    }*/
    
}
