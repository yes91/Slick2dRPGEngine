/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;


public class BeamLight extends Light {
    
    public float angle;

    public BeamLight(float x, float y, float scale, float angle, float intensity, String image, Color tint) {
        super(x, y, scale, intensity, image, tint);
        this.angle = angle;
        lightSprite.setCenterOfRotation(lightSprite.getWidth() / 2f, lightSprite.getHeight() / 2f);
        lightSprite.setRotation(angle);
    }
    
    @Override
    public void render(Graphics g) {
        GL14.glBlendColor(tint.r * intensity, tint.g * intensity, tint.b * intensity, tint.a);
        GL11.glBlendFunc(GL11.GL_CONSTANT_COLOR, GL11.GL_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        float xOff = lightSprite.getWidth() / 2f;
        float yOff = lightSprite.getHeight() / 2f;
        lightSprite.draw(screenX() - xOff, screenY() - yOff, screenX() + lightSprite.getWidth() * scale, screenY() + lightSprite.getHeight(), 
                0, 0, lightSprite.getWidth(), lightSprite.getHeight());
    }
    
}
