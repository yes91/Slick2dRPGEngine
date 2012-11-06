/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Kieran
 */
public class Map {
    
    public TiledMap map;
    public Music BGM;
    public Image battleback;
    public Camera camera;
    public ArrayList<Event> events;
    public ArrayList<GameObject> objs;
    public ArrayList<Rectangle> listRect;
    public int boundsX;
    public int boundsY;
    
    public Map(TiledMap mp, WorldPlayer p){
        events = new ArrayList();
        listRect = new ArrayList();
        objs = new ArrayList();
        map = mp;
        camera = new Camera(map, map.getWidth()*map.getTileWidth(),map.getHeight()*map.getTileHeight());
        boundsX = map.getWidth()*map.getTileWidth();
        boundsY = map.getHeight()*map.getTileHeight();
        objs = new ArrayList<>();
                listRect = new ArrayList<>();
                for (int xAxis=0;xAxis<map.getWidth(); xAxis++)
	        {
	             for (int yAxis=0;yAxis<map.getHeight(); yAxis++)
	             {
	                 int tileID = map.getTileId(xAxis, yAxis, 2);
	                 String value = map.getTileProperty(tileID, "blocked", "false");
	                 if ("true".equals(value))
	                 {
	                     listRect.add(new Rectangle(xAxis*64, yAxis*64, map.getTileWidth(), map.getTileHeight()));
	                 }
	             }
	        }
                events = new ArrayList<>();
                for (int Object=0;Object<map.getObjectCount(0); Object++)
                {
	                     String value = map.getObjectType(0, Object);
                             if("Actor".equals(value)){
	                     events.add(new Event((float)map.getObjectX(0, Object), (float)map.getObjectY(0, Object), map.getObjectWidth(0, Object), map.getObjectHeight(0, Object), value,map.getObjectProperty(0, Object, "text", "false"),null,null));
                             }
                             if("Npc".equals(value)){
                                 NPC npc = null;
                                     try {
                                         npc = new NPC((float)map.getObjectX(0, Object), (float)map.getObjectY(0, Object), map.getObjectWidth(0, Object), map.getObjectHeight(0, Object),map.getObjectProperty(0, Object, "text", "false"), new Image(map.getObjectProperty(0, Object, "image", "false")));
                                } catch (SlickException ex) {
                                    Logger.getLogger(SceneMap.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                 events.add(npc);
                                 objs.add(npc);
                                 //listRect.add(new Rectangle(npc.getX()+8+2, npc.getY()+8+42, 61, 43));
                                 listRect.add(new Rectangle(npc.pos.x, npc.pos.y+32, 64, 64));
                                 
                             }
                             if("Teleport".equals(value)){
                                 events.add(new Event(map.getObjectX(0, Object), map.getObjectY(0, Object), map.getObjectWidth(0, Object), map.getObjectHeight(0, Object), value, map.getObjectProperty(0, Object, "text", "false"),map.getObjectProperty(0, Object, "map", "false"),map.getObjectProperty(0, Object, "map2", "false")));
                             }
                             
                             if("Enemy".equals(value)){
                            Enemy e = EnemyReader.getEnemies().get(Integer.parseInt(map.getObjectProperty(0, Object, "Index", "0")));
                             e.pos.x = (float)map.getObjectX(0, Object);
                             e.pos.y = (float)map.getObjectY(0, Object);
                             e.width = map.getObjectWidth(0, Object);
                             e.height = map.getObjectHeight(0, Object);
                            events.add(e);
                            objs.add(e);
                            }
	        }
                objs.add(p);
                p.setX(Float.parseFloat(map.getMapProperty("startPosx", "1"))*64);
                p.setY(Float.parseFloat(map.getMapProperty("startPosy", "1"))*64);
        
    }
    
    public void render(WorldPlayer p, Graphics g){
        map.render(0, 0, 0);
        map.render(0, 0, 1);
    }
    
    public void renderUI(WorldPlayer p){
        
    }
    
    public void update(GameContainer container, WorldPlayer p) throws SlickException {
        
         SceneMap.blocked = false;
        if(p.getX()+p.getBounds().getWidth() > boundsX | p.getY()+p.getHeight() > boundsY | p.getX() < 0 | p.getY() < 0){
            SceneMap.blocked = true;
        }
        for(Rectangle o: listRect){
            if (Physics.checkCollisions(p, o)) {
                
                SceneMap.blocked = true;
            }
        }
        for(Event e: events){
            if(Physics.checkCollisions(p, e.getActivationRect()) & p.getAction() == true){
                if(e.getType().equals("Actor") | e.getType().equals("Npc")){
                    if(e.getType().equals("Npc")){
                        if(p.getFrame() == 12){
                        ((NPC)e).setFrame(12);
                        }
                        if(p.getFrame() == 4){
                        ((NPC)e).setFrame(0);
                        }
                        if(p.getFrame() == 8){
                        ((NPC)e).setFrame(8);
                        }
                        if(p.getFrame() == 0){
                        ((NPC)e).setFrame(4);
                        }
                    }
                break;
                }
                if(e.getType().equals("Teleport")){
                    container.pause();
                    SceneMap.map = new Map(new TiledMap(e.maploc, e.maploc2), p);
                    container.resume();
                }
            }  
        }
        
    }
    
}
