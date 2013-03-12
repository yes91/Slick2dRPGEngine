/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Color;

/**
 *
 * @author Kieran
 */
public class SpriteSheet {
    
    public String image;
    
    private int rows;
    private int columns;
    
    public SpriteSheet(String image, int rows, int columns){
        this.image = image;
        this.rows = rows;
        this.columns = columns;
    }
    
    public void drawSprite(int sx, int sy, float x, float y){
        int width = GameCache.res(image).getWidth()/rows;
        int height = GameCache.res(image).getHeight()/columns;
        GameCache.res(image).draw(x, y, x + width, y + height,
                sx * width, sy * height, (sx * width) + width, (sy * height) + height);
    }
    
    public void drawSprite(int sx, int sy, float x, float y, float width, float height){
        int swidth = GameCache.res(image).getWidth()/rows;
        int sheight = GameCache.res(image).getHeight()/columns;
        GameCache.res(image).draw(x, y, x + width, y + height,
                sx * swidth, sy * sheight, (sx * swidth) + width, (sy * sheight) + sheight);
    }
    
    public void drawSprite(int sx, int sy, float x, float y, float width, float height, Color col){
        int swidth = GameCache.res(image).getWidth()/rows;
        int sheight = GameCache.res(image).getHeight()/columns;
        GameCache.res(image).draw(x, y, x + width, y + height,
                sx * swidth, sy * sheight, (sx * swidth) + swidth, (sy * sheight) + sheight, col);
    }
    
    public int getRows(){
        return rows;
    }
    
    public int getColumns(){
        return columns;
    }
    
}
