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
    public Image skin;
    public final Image contents;
    public final Graphics cg;
    public Rectangle cursorRect;
    private double time;

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
        skin = Cache.getSystemImage("Window.png");
        cg = contents.getGraphics();
        cg.setFont(Cache.getFont());
    }

    public void render(Graphics g2d, StateBasedGame sbg) {
        if (sbg.getCurrentStateID() == 1) {
            x = initX + (int) Camera.viewPort.getX();
            y = initY + (int) Camera.viewPort.getY();
        } else {
            x = initX;
            y = initY;
        }

        skin.setAlpha(0.7f);
        skin.draw(x + 4, y + 4, x - 4 + width, y - 4 + height, 0, 0, 64, 64);
        skin.setAlpha(100f);

        Sprite.drawSpriteFrame(skin, g2d, x, y, 8, 4, 16, 16);
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
        g2d.drawImage(contents, x + 16, y + 16);
    }

    public void drawCursorRect(Graphics g2d) {
        int u = x + 16;
        int v = y + 16;
        if (time > 12.5) {
            time = 0;
        }
        time += .1;
        skin.setAlpha(Math.max(Math.abs((float) Math.sin(time)), 0.1f));
        g2d.drawImage(skin, u + 8 + cursorRect.getX(), v + 8 + cursorRect.getY(), u - 8 + cursorRect.getWidth() + cursorRect.getX(), v - 8 + cursorRect.getHeight() + cursorRect.getY(), 72, 72, 80, 80);
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
        skin.setAlpha(100f);
        g2d.flush();
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
    
    public void drawFace(String faceName, int faceIndex, float x, float y){
        Sprite.drawSpriteFrame(Cache.getRes(faceName+".png"), cg, x, y, 4, faceIndex, 96, 96);
    }
    
    public void drawActorFace(GameActor actor, float x, float y){
        drawFace(actor.faceName, actor.faceIndex, x , y);
    }

    public void drawActorHPGuage(GameActor actor, float x, float y) {
        float gw = 120 * ((float) actor.currentHP / (float) actor.getMaxHP());
        for (int i = 0; i < 10; i++) {
            cg.setColor(Color.darkGray);
            cg.drawLine(x + (10 - i), y + i, x + 120 + (10 - i), y + i);
            cg.drawGradientLine(x + (10 - i), y + i, Color.orange, x + gw + (10 - i), y + i, Color.red);
        }
    }
    
    public void drawActorHP(GameActor actor, float x, float y){
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
    
    public void drawActorMP(GameActor actor, float x, float y){
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

    public void drawActorMPGuage(GameActor actor, float x, float y) {
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
    
    public Color hpColor(GameActor actor){
        if(actor.currentHP <= 0){
            return textColor(18);
        } else if(actor.currentHP < actor.getMaxHP()/3f){
            return textColor(17);
        }
        return normalColor();
    }
}