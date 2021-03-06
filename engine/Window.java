package engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
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
    public Camera camera;
    public Image skin;
    public final Image contents;
    public final Graphics cg;
    public Rectangle cursorRect;
    private double time;
    private static final Color transp = new Color(1.0f, 1.0f, 1.0f, 0.8f);
    
    public Window(int x, int y, int w, int h) throws SlickException {

        this.cursorRect = new Rectangle(0, 0, 0, 0);
        this.x = x;
        this.y = y;
        this.initX = x;
        this.initY = y;
        this.oy = y;
        this.time = 0;
        this.width = w;
        this.height = h;
        contents = new Image(width - 32, height - 32);
        skin = GameCache.system("Neo Classic.png");
        cg = contents.getGraphics();
        cg.setFont(GameCache.getFont());
    }

    public void render(Graphics g2d, StateBasedGame sbg) {
        if (camera != null) {
            x = initX + (int) Camera.viewPort.getX();
            y = initY + (int) Camera.viewPort.getY();
        } else {
            x = initX;
            y = initY;
        }

        skin.setAlpha(0.7f);
        skin.draw(x + 4, y + 4, x - 4 + width, y - 2 + height, 2, 2, 62, 62);
        g2d.fillRect(x + 4, y + 4, width - 4, height - 4, skin.getSubImage(0, 64, 64, 64), 0, 0);
        skin.setAlpha(100f);
        
        Sprite.drawSpriteFrame(skin, g2d, x, y, 8, 4, 16, 16);
        g2d.fillRect(x + 16, y, width - 32, 16, skin.getSubImage(64+16, 0, 16, 16), 0, 0);
        Sprite.drawSpriteFrame(skin, g2d, x + (width) - 16, y, 8, 7, 16, 16);
        g2d.fillRect(x, y + 16, 16, height - 32, skin.getSubImage(64, 32, 16, 16), 0, 0);
        Sprite.drawSpriteFrame(skin, g2d, x, y + (height) - 16, 8, 28, 16, 16);
        g2d.fillRect(x + 16, y + height - 16, width - 32, 16, skin.getSubImage(64+16, 64-16, 16, 16), 0, 0);
        Sprite.drawSpriteFrame(skin, g2d, x + (width) - 16, y + (height) - 16, 8, 31, 16, 16);
        g2d.fillRect(x + width - 16, y + 16, 16, height - 32, skin.getSubImage(128-16, 32, 16, 16), 0, 0);
        
        
        /*Sprite.drawSpriteFrame(skin, g2d, x, y, 8, 4, 16, 16);
        if ((width % 16) == 0) {
            for (int i = 0; i < ((width / 16) - 2); i++) {
                Sprite.drawSpriteFrame(skin, g2d, x + (i * 16) + 16, y, 8, 5, 16, 16);
            }
        } else {
            for (int i = 0; i < ((width / 16) - 1); i++) {
                Sprite.drawSpriteFrame(skin, g2d, x + (i * 16) + 16, y, 8, 5, 16, 16);
            }
        }
        Sprite.drawSpriteFrame(skin, g2d, x + (width) - 16, y, 8, 7, 16, 16);
        if ((height % 16) == 0) {
            for (int j = 0; j < ((height / 16) - 2); j++) {
                Sprite.drawSpriteFrame(skin, g2d, x, y + (j * 16) + 16, 8, 12, 16, 16);
            }
        } else {
            for (int j = 0; j < ((height / 16) - 1); j++) {
                Sprite.drawSpriteFrame(skin, g2d, x, y + (j * 16) + 16, 8, 12, 16, 16);
            }
        }
        Sprite.drawSpriteFrame(skin, g2d, x, y + (height) - 16, 8, 28, 16, 16);
        if ((width % 16) == 0) {
            for (int k = 0; k < ((width / 16) - 2); k++) {
                Sprite.drawSpriteFrame(skin, g2d, x + (k * 16) + 16, y + (height) - 16, 8, 29, 16, 16);
            }
        } else {
            for (int k = 0; k < ((width / 16) - 1); k++) {
                Sprite.drawSpriteFrame(skin, g2d, x + (k * 16) + 16, y + (height) - 16, 8, 29, 16, 16);
            }
        }
        Sprite.drawSpriteFrame(skin, g2d, x + (width) - 16, y + (height) - 16, 8, 31, 16, 16);
        if ((height % 16) == 0) {
            for (int l = 0; l < ((height / 16) - 2); l++) {
                Sprite.drawSpriteFrame(skin, g2d, x + (width) - 16, y + (l * 16) + 16, 8, 15, 16, 16);
            }
        } else {
            for (int l = 0; l < ((height / 16) - 1); l++) {
                Sprite.drawSpriteFrame(skin, g2d, x + (width) - 16, y + (l * 16) + 16, 8, 15, 16, 16);
            }
        }
        g2d.drawImage(contents, x + 16, y + 16);*/
    }

    public void drawCursorRect(Graphics g2d) {
        float u = x + 16 + cursorRect.getX();
        float v = y + 16 + cursorRect.getY();
        float cWidth = cursorRect.getWidth();
        float cHeight = cursorRect.getHeight();
        if (time > 12.5) {
            time = 0;
        }
        time += .1;
        float alpha = scaleRange((float)Math.sin(time*2), -1.0f, 1.0f, 0.6f, 1.0f);
        skin.setAlpha(alpha);
        
        Image topLeft = skin.getSubImage(64, 64, 2, 2);
        topLeft.setAlpha(alpha);
        Image topRight = skin.getSubImage(94, 64, 2, 2);
        topRight.setAlpha(alpha);
        Image bottomLeft = skin.getSubImage(64, 94, 2, 2);
        bottomLeft.setAlpha(alpha);
        Image bottomRight = skin.getSubImage(94, 94, 2, 2);
        bottomRight.setAlpha(alpha);
        
        Image topStrip = skin.getSubImage(66, 64, 28, 2);
        topStrip.setAlpha(alpha);
        Image leftStrip = skin.getSubImage(64, 66, 2, 28);
        leftStrip.setAlpha(alpha);
        Image rightStrip = skin.getSubImage(94, 66, 2, 28);
        rightStrip.setAlpha(alpha);
        Image bottomStrip = skin.getSubImage(66, 94, 28, 2);
        bottomStrip.setAlpha(alpha);
                
        g2d.drawImage(skin, u, v, u + cWidth - 2, v + cHeight - 2, 66, 66, 94, 94);
        
        topLeft.draw(u, v);
        
        g2d.fillRect(u + 2, v, cWidth - 6, 2, topStrip, 0, 0);
        
        topRight.draw(u + cWidth - 4, v);
        
        g2d.fillRect(u, v + 2, 2, cHeight - 6, leftStrip, 0, 0);
        
        bottomLeft.draw(u, v + cHeight - 4);
        
        g2d.fillRect(u + cWidth - 4, v + 2, 2, cHeight - 6, rightStrip, 0, 0);
        
        bottomRight.draw(u + cWidth - 4, v + cHeight - 4);
        
        g2d.fillRect(u + 2, v + cHeight - 4, cWidth - 6, 2, bottomStrip, 0, 0);
        
        
        /*g2d.drawImage(skin, u + 8 + cursorRect.getX(), v + 8 + cursorRect.getY(), u - 8 + cursorRect.getWidth() + cursorRect.getX(), v - 8 + cursorRect.getHeight() + cursorRect.getY(), 72, 72, 80, 80);
        Sprite.drawSpriteFrame(skin, g2d, u + cursorRect.getX(), v + cursorRect.getY(), 16, 136, 8, 8);
        if ((cursorRect.getWidth() % 8) == 0) {
            for (int i = 0; i < ((cursorRect.getWidth() / 8) - 2); i++) {
                Sprite.drawSpriteFrame(skin, g2d, u + cursorRect.getX() + (i * 8) + 8, v + cursorRect.getY(), 16, 137, 8, 8);
            }
        } else {
            for (int i = 0; i < ((cursorRect.getWidth() / 8) - 2); i++) {
                Sprite.drawSpriteFrame(skin, g2d, u + cursorRect.getX() + (i * 8) + 8, v + cursorRect.getY(), 16, 137, 8, 8);
            }
        }
        Sprite.drawSpriteFrame(skin, g2d, u + cursorRect.getX() + (cursorRect.getWidth()) - 8, v + cursorRect.getY(), 16, 139, 8, 8);
        if ((cursorRect.getHeight() % 8) == 0) {
            for (int j = 0; j < ((cursorRect.getHeight() / 8) - 2); j++) {
                Sprite.drawSpriteFrame(skin, g2d, u + cursorRect.getX(), v + cursorRect.getY() + (j * 8) + 8, 16, 152, 8, 8);
            }
        } else {
            for (int j = 0; j < ((cursorRect.getHeight() / 8) - 2); j++) {
                Sprite.drawSpriteFrame(skin, g2d, u + cursorRect.getX(), v + (j * 8) + 8 + cursorRect.getY(), 16, 152, 8, 8);
            }
        }
        Sprite.drawSpriteFrame(skin, g2d, u + cursorRect.getX(), v + (cursorRect.getHeight()) - 8 + cursorRect.getY(), 16, 184, 8, 8);
        if ((cursorRect.getWidth() % 8) == 0) {
            for (int k = 0; k < ((cursorRect.getWidth() / 8) - 2); k++) {
                Sprite.drawSpriteFrame(skin, g2d, u + (k * 8) + 8 + cursorRect.getX(), v + (cursorRect.getHeight()) - 8 + cursorRect.getY(), 16, 185, 8, 8);
            }
        } else {
            for (int k = 0; k < ((cursorRect.getWidth() / 8) - 2); k++) {
                Sprite.drawSpriteFrame(skin, g2d, u + (k * 8) + 8 + cursorRect.getX(), v + (cursorRect.getHeight()) - 8 + cursorRect.getY(), 16, 185, 8, 8);
            }
        }
        Sprite.drawSpriteFrame(skin, g2d, u + (cursorRect.getWidth()) - 8 + cursorRect.getX(), v + (cursorRect.getHeight()) - 8 + cursorRect.getY(), 16, 187, 8, 8);
        if ((cursorRect.getHeight() % 8) == 0) {
            for (int l = 0; l < ((cursorRect.getHeight() / 8) - 2); l++) {
                Sprite.drawSpriteFrame(skin, g2d, u + (cursorRect.getWidth()) - 8 + cursorRect.getX(), v + (l * 8) + 8 + cursorRect.getY(), 16, 155, 8, 8);
            }
        } else {
            for (int l = 0; l < ((cursorRect.getHeight() / 8) - 2); l++) {
                Sprite.drawSpriteFrame(skin, g2d, u + (cursorRect.getWidth()) - 8 + cursorRect.getX(), v + (l * 8) + 8 + cursorRect.getY(), 16, 155, 8, 8);
            }
        }
        g2d.flush();*/
    }
    
    private float scaleRange(float in, float oldMin, float oldMax, float newMin, float newMax){
        return (in / ((oldMax - oldMin) / (newMax - newMin))) + newMin;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void drawItemName(BaseItem item, float x, float y, boolean enabled){
        cg.setColor(enabled ? Color.white:transp);
        Sprite.drawSpriteFrame(GameCache.system("IconSet.png"), cg, x, y - 2, 16, item.getIndex(), 24, 24, cg.getColor());
        for (int i = 0; i < 4; i++) {
            cg.drawString(item.getName(), 24 + x, y + 2);
        }
    }
    
    public void drawItemName(String text, float x, float y, boolean enabled, int icon){
        cg.setColor(enabled ? Color.white:transp);
        if(icon >= 0){
            Sprite.drawSpriteFrame(GameCache.system("IconSet.png"), cg, x, y - 2, 16, icon, 24, 24, cg.getColor());
            for (int i = 0; i < 4; i++) {
                cg.drawString(text, 24 + x, y + 2);
            }
        } else {
            for (int i = 0; i < 4; i++) {
                cg.drawString(text, x, y + 2);
            }
        }
        
    }
    
    public void drawFace(String faceName, int faceIndex, float x, float y, int size){
        Image face = GameCache.res(faceName+".png");
        int frameX = (faceIndex % 4) * 96;
        int frameY = (faceIndex / 4) * 96;
        cg.drawImage(face.getSubImage(frameX, frameY, 96, 96), x, y, x+size, y+size, 0, 0, 96, 96);
    }
    
    public void drawActorFace(GameActor actor, float x, float y){
        drawFace(actor.faceName, actor.faceIndex, x , y, 96);
    }
    
    public void drawActorFace(GameActor actor, float x, float y, int size){
        drawFace(actor.faceName, actor.faceIndex, x , y, size);
    }
    
    public void drawActorName(GameActor ga, float x, float y){
        cg.setColor(hpColor(ga));
        for(int i = 0; i < 4; i++){
            cg.drawString(ga.name, x, y);
        }
    }
    
    public void drawActorLevel(GameActor ga, float x, float y){
        cg.setColor(systemColor());
        for(int i = 0; i < 4; i++){
            cg.drawString("Lv", x, y);
        }
        cg.setColor(normalColor());
        for(int i = 0; i < 4; i++){
            cg.drawString(""+ga.stats.level, x + 32, y);
        }
    }

    public void drawActorHPGuage(GameBattler actor, float x, float y) {
        float gw = 120 * ((float) actor.currentHP / (float) actor.getMaxHP());
        for (int i = 0; i < 10; i++) {
            cg.setColor(Color.darkGray);
            cg.drawLine(x + (10 - i), y + i, x + 120 + (10 - i), y + i);
            cg.drawGradientLine(x + (10 - i), y + i, Color.orange, x + gw + (10 - i), y + i, Color.red);
        }
        /*cg.setColor(Color.white);
        cg.drawLine(x + 10, y, x, y + 10);
        cg.drawLine(x - 1 + 10, y, x - 1 + 10 + 120, y);
        cg.drawLine(x - 1, y + 10, x - 1 + 120, y + 10);
        cg.drawLine(x + 10 + 120, y, x + 120, y + 10);*/
    }
    
    public void drawActorHP(GameBattler actor, float x, float y){
        drawActorHPGuage(actor, x, y);
        float xr = x + 120;
        for(int i = 0; i < 4; i++){
            cg.setColor(systemColor());
            cg.drawString("HP", x, y - 10);
            cg.setColor(hpColor(actor));
            cg.drawString(""+actor.currentHP, xr - 88, y - 10);
            cg.setColor(normalColor());
            cg.drawString("/", xr - 44, y - 10);
            cg.drawString(""+actor.getMaxHP(), xr - 33, y - 10);
        }
    }
    
    public void drawActorMP(GameBattler actor, float x, float y){
        drawActorMPGuage(actor, x, y);
        float xr = x + 120;
        for(int i = 0; i < 4; i++){
            cg.setColor(systemColor());
            cg.drawString("MP", x, y - 10);
            cg.setColor(normalColor());
            cg.drawString(""+actor.currentMP, xr - 88, y - 10);
            cg.drawString("/", xr - 44, y - 10);
            cg.drawString(""+actor.getMaxMP(), xr - 33, y - 10);
        }
    }

    public void drawActorMPGuage(GameBattler actor, float x, float y) {
        float gw = 120 * ((float) actor.currentMP / (float) actor.getMaxMP());
        for (int i = 0; i < 10; i++) {
            cg.setColor(Color.darkGray);
            cg.drawLine(x + (10 - i), y + i, x + 120 + (10 - i), y + i);
            cg.drawGradientLine(x + (10 - i), y + i, Color.cyan, x + gw + (10 - i), y + i, Color.blue);
        }
    }
    
    public Color textColor(int n){
        int cx = 64 + (n % 8) * 8;
        int cy = 96 + (n / 8) * 8;
        return skin.getColor(cx, cy);
    }
    
    public Color normalColor(){
        return textColor(0);
    }
    
    public Color systemColor(){
        return textColor(16);
    }
    
    public Color hpColor(GameBattler actor){
        if(actor.currentHP <= 0){
            return textColor(18);
        } else if(actor.currentHP < actor.getMaxHP()/3f){
            return textColor(17);
        }
        return normalColor();
    }
}