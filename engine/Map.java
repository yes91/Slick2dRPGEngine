/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import engine.GameCharacter.Direction;
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
    public ArrayList<Event> events;
    public ArrayList<GameObject> objs;
    public ArrayList<Rectangle> listRect;
    public int boundsX;
    public int boundsY;
    
    public Map(TiledMap mp, WorldPlayer p){
        events = new ArrayList<>();
        listRect = new ArrayList<>();
        objs = new ArrayList<>();
        map = mp;
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
                                GameCharacter e = new GameCharacter("092-Monster06");
                                e.pos.x = (float)map.getObjectX(0, Object);
                                e.pos.y = (float)map.getObjectY(0, Object);
                                objs.add(e);
                            }
	        }
                objs.add(p);
                p.setX(Float.parseFloat(map.getMapProperty("startPosx", "1"))*64 + 32);
                p.setY(Float.parseFloat(map.getMapProperty("startPosy", "1"))*64 + 48);
    }
    
    public void render(WorldPlayer p, Graphics g){
        map.render(0, 0, 0);
        map.render(0, 0, 1);
    }
    
    public void renderUI(WorldPlayer p){
        
    }
    
    public void update(GameContainer container, WorldPlayer p) throws SlickException {
        for(GameObject go: objs){
            go.update();
        }
        for(Event e: events){
            if(Physics.checkCollisions(p, e.getActivationRect()) & p.getAction() == true){
                if(e.getType().equals("Actor") | e.getType().equals("Npc")){
                    if(e.getType().equals("Npc")){
                        if(p.facing.equals(Direction.DOWN)){
                        ((NPC)e).setFrame(12);
                        }
                        if(p.facing.equals(Direction.UP)){
                        ((NPC)e).setFrame(0);
                        }
                        if(p.facing.equals(Direction.LEFT)){
                        ((NPC)e).setFrame(8);
                        }
                        if(p.facing.equals(Direction.RIGHT)){
                        ((NPC)e).setFrame(4);
                        }
                        ((NPC)e).activate();
                    }
                break;
                }
                if(e.getType().equals("Teleport")){
                    container.pause();
                    SceneMap.map = new Map(new TiledMap(e.maploc, e.maploc2), p);
                    SceneMap.setCamera(new Camera(SceneMap.map.map, SceneMap.map.map.getWidth()*64, SceneMap.map.map.getHeight()*64));
                    container.resume();
                }
            }  
        }
        
    }
    
}
