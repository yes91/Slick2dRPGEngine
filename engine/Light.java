/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author Kieran
 */
public class Light {

    protected final Image lightSprite;
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
        GL14.glBlendColor(tint.r * intensity, tint.g * intensity, tint.b * intensity, tint.a);
        //GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_CONSTANT_COLOR, GL11.GL_SRC_ALPHA);
        lightSprite.setRotation(0);
        float xOff = lightSprite.getWidth() / 2f * scale;
        float yOff = lightSprite.getHeight() / 2f * scale;
        lightSprite.draw(screenX() - xOff, screenY() - yOff, scale);
    }
    
    public void update(long elapsed){
        scale = scaleOrig + 0.5f * (float)Math.sin(elapsed /1000f);
    }

    public float screenX() {
        return x - Camera.viewPort.getX();
    }

    public float screenY() {
        return y - Camera.viewPort.getY();
    }

    public boolean isVisible() {
        return x > Camera.viewPort.getX() - 1024 && y > Camera.viewPort.getY() - 1024
                && x < Camera.viewPort.getX() + Camera.viewPort.getWidth() + 1024
                && y < Camera.viewPort.getY() + Camera.viewPort.getHeight() + 1024;
    }
}
