/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import util.MathHelper;

/**
 *
 * @author Kieran
 */
public class MiniMap {
    
    public float zoom = 3.0f;
    private Image miniMap;
    private boolean shape;
    private Graphics mg;
    
    public MiniMap() throws SlickException{
        miniMap = new Image(400, 400);
        miniMap.setFilter(Image.FILTER_LINEAR);
        mg = miniMap.getGraphics();
    }
    
    public void update(Input input){
        if(input.isKeyDown(Input.KEY_EQUALS)){
            zoom += 0.05f;
        } else if(input.isKeyDown(Input.KEY_MINUS)){
            zoom -= 0.05f;
        } else if(input.isKeyPressed(Input.KEY_BACKSLASH)){
            shape = !shape;
        }
        
        zoom = MathHelper.clamp(zoom, 1.0f, 10.0f);
    }
    
    public void render(Graphics g, GameMap map){
        drawMiniMap(mg, map, zoom);
        Image mini = miniMap.getScaledCopy(0.4f);
        mini.setAlpha(0.5f);
        if(shape){
            mini.draw(Camera.viewPort.getX() + 10, Camera.viewPort.getY() + SceneMap.B_HEIGHT - 10 - miniMap.getHeight()*0.4f);
        } else {
            mini.draw(Camera.viewPort.getX() + SceneMap.B_WIDTH - 10 - miniMap.getHeight()*0.4f, Camera.viewPort.getY() + 10 );
        }
    }
    
    public void drawRect(Graphics g){
        g.clear();
        g.setColor(Color.darkGray);
        int width = miniMap.getWidth();
        int height = miniMap.getHeight();
        g.setAntiAlias(true);
        g.drawRect(0, 0, width, height);
        g.setAntiAlias(false);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.white);
        g.setAntiAlias(true);
        g.drawRect(5, 5, width - 10, height - 10);
        g.setAntiAlias(false);
        g.fillRect(5, 5, width - 10, height - 10);
        //g.setDrawMode(Graphics.MODE_ALPHA_BLEND);
    }
    
    public void drawOval(Graphics g){
        g.clear();
        g.setColor(Color.darkGray);
        int width = miniMap.getWidth();
        int height = miniMap.getHeight();
        g.setAntiAlias(true);
        g.drawOval(0, 0, width, height);
        g.setAntiAlias(false);
        g.fillOval(0, 0, width, height);
        g.setColor(Color.white);
        g.setAntiAlias(true);
        g.drawOval(5, 5, width - 10, height - 10);
        g.setAntiAlias(false);
        g.fillOval(5, 5, width - 10, height - 10);
        g.setDrawMode(Graphics.MODE_ALPHA_BLEND);
    }
    
    public void drawMiniMap(Graphics g, GameMap map, float scale){
        int width = miniMap.getWidth();
        if(shape){
            drawOval(g);
        } else {
            drawRect(g);
        }
        g.setColor(Color.darkGray);
        Rectangle mapBounds = new Rectangle(0, 0, map.getPixelWidth(), map.getPixelHeight());
        Rectangle e = mapRect(mapBounds, 0, scale);
        g.setAntiAlias(true);
        g.setLineWidth(2);
        g.drawRect(e.getX(), e.getY(), e.getWidth(), e.getHeight());
        g.setLineWidth(1);
        g.setAntiAlias(false);
        
        for(Rectangle r: map.listRect){
            Rectangle t = mapRect(r, 0, scale);
            g.fillRect(t.getX(), t.getY(), t.getWidth(), t.getHeight());
        }
        
        for(GameObject o: map.objs){
            if(o instanceof GameCharacter){
                
            float scaleFix = 24;
            scaleFix = MathHelper.clamp(scaleFix*scale, scaleFix, scaleFix*10.0f);
            Rectangle r = new Rectangle(o.pos.x - scaleFix, o.pos.y - scaleFix, 2*scaleFix, 2*scaleFix);
            g.setColor(Color.darkGray);
            fillOval(g, mapRect(r, 0, scale));
                if(o instanceof WorldPlayer){
                    g.setColor(Color.cyan);
                } else if(o instanceof NPC){
                    g.setColor(Color.green);
                } else {
                    g.setColor(Color.red);
                }
                int offset = width/100;
                fillOval(g, mapRect(r, offset, scale));
            }
        }
        g.setDrawMode(Graphics.MODE_NORMAL);
        g.flush();
    }
    
    public void fillOval(Graphics g, Rectangle r){
        g.setAntiAlias(true);
        g.drawOval(r.getX(), r.getY(), r.getWidth(), r.getHeight());
        g.setAntiAlias(false);
        g.fillOval(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }
    
    public Rectangle mapRect(Rectangle r, int offset, float scale){
        int width = miniMap.getWidth();
        int height = miniMap.getHeight();
        int aspectWidth = 16 * (height/9);
        
        Rectangle cam = Camera.viewPort;
        Rectangle sample = new Rectangle(-(SceneMap.B_WIDTH/2f)*scale + cam.getCenterX(), -(SceneMap.B_HEIGHT/2f)*scale + cam.getCenterY(), SceneMap.B_WIDTH*scale, SceneMap.B_HEIGHT*scale);
        
        float x = MathHelper.scaleRange(r.getX() - sample.getX(), 0, sample.getWidth(), 0, aspectWidth) - ((aspectWidth - width)/2) + offset;
        float y = MathHelper.scaleRange(r.getY() - sample.getY(), 0, sample.getHeight(), 0, height) + offset;
        float rWidth = MathHelper.scaleRange(r.getWidth(), 0, sample.getWidth(), 0, aspectWidth) - 2*offset; 
        float rHeight = MathHelper.scaleRange(r.getHeight(), 0, sample.getHeight(), 0, height) - 2*offset;
        
        return new Rectangle(x, y, rWidth, rHeight);
    }
    
}
