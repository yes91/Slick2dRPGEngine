/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import effectutil.LightShader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import util.MathHelper;

/**
 *
 * @author Kieran
 */
public class Light {
    
    protected SGL GL = Renderer.get();

    protected final Image lightSprite;
    
    public static LightShader program = null;
    static {
        try {
            program = new LightShader("engine/lightV.glsl", "engine/lightF.glsl");
        } catch (SlickException ex) {
            Logger.getLogger(Light.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * The position of the light
     */
    public float x, y;
    /**
     * The RGB tint of the light, alpha is ignored
     */
    public Color tint;
    /**
     * The alpha value of the light, default 1.0 (100%)
     */
    public float intensity;
    /**
     * The amount to scale the light (use 1.0 for default size).
     */
    public float scale;
    public final float scaleOrig;
    public final float[] attenuation = new float[]{0.4f, 3.0f, 20.0f};
    //original scale

    public Light(float x, float y, float scale, float intensity, Color tint) {
        lightSprite = GameCache.res("lighting_sprites.png");
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.scaleOrig = scale;
        this.intensity = intensity;
        this.tint = tint;
    }
    
    public Light(float x, float y, float scale, float intensity, String image, Color tint) {
        this.lightSprite = GameCache.res(image+".png");
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.scaleOrig = scale;
        this.intensity = intensity;
        this.tint = tint;
    }

    public Light(float x, float y, float scale) {
        this(x, y, scale, 1f, Color.white);
    }

    public void render(Graphics g) {
        /*GL14.glBlendColor(tint.r * intensity, tint.g * intensity, tint.b * intensity, tint.a);
        //GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_CONSTANT_COLOR, GL11.GL_SRC_ALPHA);
        lightSprite.setRotation(0);
        float xOff = lightSprite.getWidth() / 2f * scale;
        float yOff = lightSprite.getHeight() / 2f * scale;
        lightSprite.draw(screenX(cam) - xOff, screenY(cam) - yOff, scale);*/
        
        g.setDrawMode(Graphics.MODE_ADD);
        program.bind();
        
        program.setUniformLight("light", this);
        
        GL.glPushMatrix();
        GL.glTranslatef(screenX(), 720-screenY(), 0);
        float radius = MathHelper.scaleRange(scale, 1f, 2f, 128, 256);
        GL.glScalef(radius, radius, 0f);
        GL.glBegin(SGL.GL_QUADS);
        GL.glVertex3f(-1.0f, -1.0f, 0.0f);
        GL.glVertex3f(1.0f, -1.0f, 0.0f);
        GL.glVertex3f(1.0f, 1.0f, 0.0f);
        GL.glVertex3f(-1.0f, 1.0f, 0.0f);
        GL.glEnd();
        GL.glPopMatrix();
        
        LightShader.unbind();
        g.setDrawMode(Graphics.MODE_NORMAL);
    }
    
    public void update(long elapsed){
        scale = scaleOrig + MathHelper.scaleRange((float)Math.sin(elapsed /1000f), -1.0f, 1.0f, 1f/4f, 4f);
    }

    public float screenX() {
        return x - Camera.viewPort.getX();
    }

    public float screenY() {
        return y - Camera.viewPort.getY();
    }

    public boolean isVisible(Camera c) {
        return x > c.viewPort.getX() - 1024 && y > c.viewPort.getY() - 1024
                && x < c.viewPort.getX() + c.viewPort.getWidth() + 1024
                && y < c.viewPort.getY() + c.viewPort.getHeight() + 1024;
    }
}
