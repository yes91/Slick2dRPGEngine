/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author Kieran
 */
public class Light {
		/** The position of the light */
		public float x, y;
		/** The RGB tint of the light, alpha is ignored */
		public Color tint; 
		/** The alpha value of the light, default 1.0 (100%) */
		public float intensity;
		/** The amount to scale the light (use 1.0 for default size). */
		public float scale;
                
                public final float[] attenuation = new float[]{ 0.4f, 3.0f, 20.0f};
		//original scale
 
		public Light(float x, float y, float scale, float intensity, Color tint) {
			this.x = x;
			this.y = y;
			this.scale = scale;
			this.intensity = intensity;
			this.tint = tint;
		}
 
		public Light(float x, float y, float scale) {
			this(x, y, scale, 1f, Color.white);
		}
                
                public float screenX(){
                    return x - Camera.viewPort.getX();
                }
                
                public float screenY(){
                    return (Camera.viewPort.getHeight() - y) + Camera.viewPort.getY();
                }
                
               /* public boolean isVisible(){
                    return (new Rectangle(Camera.viewPort.getX() 
                            - 20, Camera.viewPort.getY() 
                            - 20, Camera.viewPort.getWidth() 
                            + 40, Camera.viewPort.getHeight() 
                            + 40)).intersects(this.getBounds());
                }
                
                public Rectangle getBounds(){
                    return new Rectangle(x - 10, y - 10, 20, 20);
                }*/
	}
