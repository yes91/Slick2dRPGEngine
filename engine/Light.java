/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kieran
 */
public class Light {

    private final Image lightSprite = Cache.getRes("lighting_sprites.png");
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
    public final float[] attenuation = new float[]{0.4f, 3.0f, 20.0f};
    //original scale

    public Light(float x, float y, float scale, float intensity, Color tint) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.intensity = intensity;
        this.tint = tint;
        try {
            lightSprite.setFilter(Image.FILTER_LINEAR);
        } catch (SlickException ex) {
            Logger.getLogger(Light.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Light(float x, float y, float scale) {
        this(x, y, scale, 1f, Color.white);
    }

    public void render(Graphics g) {
        GL14.glBlendColor(tint.r * intensity, tint.g * intensity, tint.b * intensity, intensity);
        GL11.glBlendFunc(GL11.GL_CONSTANT_COLOR, GL11.GL_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        float xOff = lightSprite.getWidth() / 2f * scale;
        float yOff = lightSprite.getHeight() / 2f * scale;
        lightSprite.draw(screenX() - xOff, screenY() - yOff, scale);
    }

    public float screenX() {
        return x - Camera.viewPort.getX();
    }

    public float screenY() {
        return y - Camera.viewPort.getY();
    }

    public boolean isVisible() {
        return x > Camera.viewPort.getX() - 512 && y > Camera.viewPort.getY() - 512
                && x < Camera.viewPort.getX() + Camera.viewPort.getWidth() + 512
                && y < Camera.viewPort.getY() + Camera.viewPort.getHeight() + 512;
    }
}
