/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;

/**
 *
 * @author Kieran
 */
public class Sprite {
    
    private static final int MODE_ADD = 5; 
    
    protected SGL GL = Renderer.get();
    protected SpriteSheet image;
    protected Vector2f pos;
    protected int blendType;
    protected boolean visible;
    protected Rectangle srcRect;
    protected Color color;
    protected float opacity;
    
    public Sprite(){
        pos = new Vector2f(0, 0);
        srcRect = new Rectangle(0, 0, 0, 0);
        blendType = Graphics.MODE_NORMAL;
        opacity = 1f;
        color = new Color(0, 0, 0, 0);
    }
    
    public void render(Graphics g, float scale){
        if (visible) {
            if(blendType == MODE_ADD){
                modeAdd();
            } else {
                g.setDrawMode(blendType);
            }
            
            //float sx = pos.x - (srcRect.getWidth() * scale / 2);
            //float sy = pos.y - (srcRect.getHeight() * scale / 2);
            
            image.setAlpha(opacity);
            image.draw(
                    pos.x,
                    pos.y,
                    pos.x + (srcRect.getWidth() * scale),
                    pos.y + (srcRect.getHeight() * scale),
                    srcRect.getX(),
                    srcRect.getY(),
                    srcRect.getX() + srcRect.getWidth(),
                    srcRect.getY() + srcRect.getHeight());
            image.setAlpha(1f);
            g.setDrawMode(Graphics.MODE_NORMAL);
        }
    }
    
    public void modeAdd(){
        GL.glEnable(SGL.GL_BLEND);
        GL.glColorMask(true, true, true, true);
        GL.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE);
    }
    

    public static void drawSpriteFrame(Image source, Graphics g2d, float x, float y,
            int columns, int frame, int width, int height) {
        int frameX = (frame % columns) * width;
        int frameY = (frame / columns) * height;
        g2d.drawImage(source, x, y, x + width, y + height,
                frameX, frameY, frameX + width, frameY + height);
    }
    
    public static void drawSpriteFrame(Image source, float x, float y,
            int columns, int frame, int width, int height) {
        int frameX = (frame % columns) * width;
        int frameY = (frame / columns) * height;
        source.draw(x, y, x + width, y + height,
                frameX, frameY, frameX + width, frameY + height);
    }
    
    public static void drawSpriteFrame(Image source, Graphics g2d, float x, float y,
            int columns, int frame, int width, int height, Color filter) {
        int frameX = (frame % columns) * width;
        int frameY = (frame / columns) * height;
        g2d.drawImage(source, x, y, x + width, y + height,
                frameX, frameY, frameX + width, frameY + height, filter);
    }
}
