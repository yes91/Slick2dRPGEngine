package rpgslickport;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.UnicodeFont;


public class UIWindow {
    
    public int width;
    public int height;
    public int x;
    public int y;
    public Image skin;
    
    public UIWindow(Image i) {
        
        skin = i;
    }
    
    public void render(Graphics g2d){
        
        skin.setAlpha(0.5f);
        skin.draw(x+4, y+4, x-4+width, y-4+height, 0, 0, 64, 64);
        skin.setAlpha(100f);
        
        
        Sprite.drawSpriteFrame(skin, g2d, x, y, 8, 4, 16, 16);
        for(int i = 0; i<((width/16)-2); i++){
            Sprite.drawSpriteFrame(skin, g2d, x+(i*16)+16, y, 8, 5, 16, 16);
        }
        Sprite.drawSpriteFrame(skin, g2d, x+(width)-16, y, 8, 7, 16, 16);
        for(int j = 0; j<((height/16)-2); j++){
            Sprite.drawSpriteFrame(skin, g2d, x, y+(j*16)+16, 8, 12, 16, 16);
        }
        Sprite.drawSpriteFrame(skin, g2d, x, y+(height)-16, 8, 28, 16, 16);
        for(int k = 0; k<((width/16)-2); k++){
            Sprite.drawSpriteFrame(skin, g2d, x+(k*16)+16, y+(height)-16, 8, 29, 16, 16);
        }
        Sprite.drawSpriteFrame(skin, g2d, x+(width)-16, y+(height)-16, 8, 31, 16, 16);
        for(int l = 0; l<((height/16)-2); l++){
            Sprite.drawSpriteFrame(skin, g2d, x+(width)-16, y+(l*16)+16, 8, 15, 16, 16);
        }
 
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public void dispose(){
        
        
    }
    

}