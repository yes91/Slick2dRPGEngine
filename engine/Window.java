package engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;


public class Window {
    
    public final int WINDOW_LINE_HEIGHT = 24;
    public int width;
    public int height;
    public int x;
    public int y;
    public int initX;
    public int initY;
    public int oy;
    public Image skin;
    public Image contents;
    public Rectangle cursorRect;
    private double time;
    
    public Window(int x1, int y1, int w, int h) {
        
        skin = Cache.getImage("Window.png");
        cursorRect = new Rectangle(0,0,0,0);
        x = x1;
        y = y1;
        initX = x;
        initY = y;
        oy = y;
        time = 0;
        width = w;
        height = h;
    }
    
    public void render(Graphics g2d, StateBasedGame sbg){
        if(sbg.getCurrentStateID() == 0){
        x = initX+(int)Camera.viewPort.getX();
        y = initY+(int)Camera.viewPort.getY();
        }
        else{
        x = initX;
        y = initY;
        }
        
        
        skin.setAlpha(0.6f);
        skin.draw(x+4, y+4, x-4+width, y-4+height, 0, 0, 64, 64);
        skin.setAlpha(100f);
        
        Sprite.drawSpriteFrame(skin, g2d, x, y, 8, 4, 16, 16);
        if((width % 16) == 0){
        for(int i = 0; i<((width/16)-2); i++){
            Sprite.drawSpriteFrame(skin, g2d, x+(i*16)+16, y, 8, 5, 16, 16);
        }
        }
        else{
            for(int i = 0; i<((width/16)-1); i++){
            Sprite.drawSpriteFrame(skin, g2d, x+(i*16)+16, y, 8, 5, 16, 16);
        }
        }
        Sprite.drawSpriteFrame(skin, g2d, x+(width)-16, y, 8, 7, 16, 16);
        if((height % 16) == 0){
        for(int j = 0; j<((height/16)-2); j++){
            Sprite.drawSpriteFrame(skin, g2d, x, y+(j*16)+16, 8, 12, 16, 16);
        }
        }
        else{
            for(int j = 0; j<((height/16)-1); j++){
            Sprite.drawSpriteFrame(skin, g2d, x, y+(j*16)+16, 8, 12, 16, 16);
        }
        }
        Sprite.drawSpriteFrame(skin, g2d, x, y+(height)-16, 8, 28, 16, 16);
        if((width % 16) ==0){
        for(int k = 0; k<((width/16)-2); k++){
            Sprite.drawSpriteFrame(skin, g2d, x+(k*16)+16, y+(height)-16, 8, 29, 16, 16);
        }
        }
        else{
            for(int k = 0; k<((width/16)-1); k++){
            Sprite.drawSpriteFrame(skin, g2d, x+(k*16)+16, y+(height)-16, 8, 29, 16, 16);
        }
        }
        Sprite.drawSpriteFrame(skin, g2d, x+(width)-16, y+(height)-16, 8, 31, 16, 16);
        if((height % 16) == 0){
        for(int l = 0; l<((height/16)-2); l++){
            Sprite.drawSpriteFrame(skin, g2d, x+(width)-16, y+(l*16)+16, 8, 15, 16, 16);
        }
        }
        else{
            for(int l = 0; l<((height/16)-1); l++){
            Sprite.drawSpriteFrame(skin, g2d, x+(width)-16, y+(l*16)+16, 8, 15, 16, 16);
        }
        }
    }
    
    public void drawCursorRect(Graphics g2d){
        int u = x+16;
        int v = y+16;
        if(time > 12.5){
            time = 0;
        }
        time += .1;
        skin.setAlpha(Math.max(Math.abs((float)Math.sin(time)), 0.1f));
        skin.draw(u+8+cursorRect.getX(), v+8+cursorRect.getY(), u-8+cursorRect.getWidth()+cursorRect.getX(), v-8+cursorRect.getHeight()+cursorRect.getY(), 72, 72, 80, 80);
        Sprite.drawSpriteFrame(skin, g2d, u+cursorRect.getX(), v+cursorRect.getY(), 16, 136, 8, 8);
        if((cursorRect.getWidth() % 8) == 0){
        for(int i = 0; i<((cursorRect.getWidth()/8)-2); i++){
            Sprite.drawSpriteFrame(skin, g2d, u+cursorRect.getX()+(i*8)+8, v+cursorRect.getY(), 16, 137, 8, 8);
        }
        }
        else{
            for(int i = 0; i<((cursorRect.getWidth()/8)-2); i++){
            Sprite.drawSpriteFrame(skin, g2d, u+cursorRect.getX()+(i*8)+8, v+cursorRect.getY(), 16, 137, 8, 8);
        }
        }
        Sprite.drawSpriteFrame(skin, g2d, u+cursorRect.getX()+(cursorRect.getWidth())-8, v+cursorRect.getY(), 16, 139, 8, 8);
        if((cursorRect.getHeight() % 8) == 0){
        for(int j = 0; j<((cursorRect.getHeight()/8)-2); j++){
            Sprite.drawSpriteFrame(skin, g2d, u+cursorRect.getX(), v+cursorRect.getY()+(j*8)+8, 16, 152, 8, 8);
        }
        }
        else{
            for(int j = 0; j<((cursorRect.getHeight()/8)-2); j++){
            Sprite.drawSpriteFrame(skin, g2d, u+cursorRect.getX(), v+(j*8)+8+cursorRect.getY(), 16, 152, 8, 8);
        }
        }
        Sprite.drawSpriteFrame(skin, g2d, u+cursorRect.getX(), v+(cursorRect.getHeight())-8+cursorRect.getY(), 16, 184, 8, 8);
        if((cursorRect.getWidth() % 8) ==0){
        for(int k = 0; k<((cursorRect.getWidth()/8)-2); k++){
            Sprite.drawSpriteFrame(skin, g2d, u+(k*8)+8+cursorRect.getX(), v+(cursorRect.getHeight())-8+cursorRect.getY(), 16, 185, 8, 8);
        }
        }
        else{
            for(int k = 0; k<((cursorRect.getWidth()/8)-2); k++){
            Sprite.drawSpriteFrame(skin, g2d, u+(k*8)+8+cursorRect.getX(), v+(cursorRect.getHeight())-8+cursorRect.getY(), 16, 185, 8, 8);
        }
        }
        Sprite.drawSpriteFrame(skin, g2d, u+(cursorRect.getWidth())-8+cursorRect.getX(), v+(cursorRect.getHeight())-8+cursorRect.getY(), 16, 187, 8, 8);
        if((cursorRect.getHeight() % 8) == 0){
        for(int l = 0; l<((cursorRect.getHeight()/8)-2); l++){
            Sprite.drawSpriteFrame(skin, g2d, u+(cursorRect.getWidth())-8+cursorRect.getX(), v+(l*8)+8+cursorRect.getY(), 16, 155, 8, 8);
        }
        }
        else{
            for(int l = 0; l<((cursorRect.getHeight()/8)-2); l++){
            Sprite.drawSpriteFrame(skin, g2d, u+(cursorRect.getWidth())-8+cursorRect.getX(), v+(l*8)+8+cursorRect.getY(), 16, 155, 8, 8);
        }
        }
        skin.setAlpha(100f);
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
}