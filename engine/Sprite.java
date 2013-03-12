/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author Kieran
 */
public class Sprite {

    private static float frame;

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

    public static void animateSprite(Image source, Graphics g, float x, float y, int width, int height, int columns, int startFrame, int endFrame, float speed, boolean isMoving) {
        if (frame == 0.0f) {
            frame = startFrame;
        }
        int fCount = (int) (frame + speed / 16 * SceneMap.deltaG);
        if ((fCount < endFrame) & isMoving) {
            frame += speed / 16 * SceneMap.deltaG;
        } else {
            frame = startFrame;
        }
        drawSpriteFrame(source, g, x, y, columns, (int) frame, width, height);
    }
}
