/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author redblast71
 */

public class Camera {
 
	private int transX, transY;
        private int lastTX, lastTY;
	private int mapWidth, mapHeight;
	public static Rectangle viewPort; 
        private GameObject target;
	/* We define a rectangle with the size of our screen, this represents our camera
	 * "range", so everything inside the viewport will be drawn on the screen, we will
	 * be able to move this rectangle across the map. */
 
 
	public Camera(GameMap map) {
		transX = 0;
		transY = 0;
		viewPort = new Rectangle(0, 0, SceneMap.B_WIDTH, SceneMap.B_HEIGHT);
		this.mapWidth = map.getPixelWidth();
		this.mapHeight = map.getPixelHeight();
                target = SceneBase.worldPlayer;
	}
        
        public void setMap(GameMap map){
            this.mapWidth = map.getPixelWidth();
            this.mapHeight = map.getPixelHeight();
        }
        
        public void setTarget(GameObject go){
            target = go;
        }
 
	public void translate (Graphics g) {
 
	if((int)target.getX()-SceneMap.B_WIDTH/2+16 < 0) {
                transX = 0;
            }
    	else if((int)target.getX()+SceneMap.B_WIDTH/2+16 > mapWidth) {
                transX = -mapWidth+SceneMap.B_WIDTH;
            }
    	else {
                transX = -(int)target.getX()+SceneMap.B_WIDTH/2-16;
            }
 
    	if((int)target.getY()-SceneMap.B_HEIGHT/2+16 < 0) {
                transY = 0;
            }
    	else if((int)target.getY()+SceneMap.B_HEIGHT/2+16 > mapHeight) {
                transY = -mapHeight+SceneMap.B_HEIGHT;
            }
    	else {
                transY = -(int)target.getY()+SceneMap.B_HEIGHT/2-16;
            }
 
        if(!target.collide()){
            g.translate(transX, transY);
            viewPort.setX(-transX);
            viewPort.setY(-transY);
            lastTX = transX;
            lastTY = transY;
        }
        else{
            g.translate(lastTX, lastTY);
            viewPort.setX(-lastTX);
            viewPort.setY(-lastTY);
        }
 
	}
}