/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import engine.GameCharacter.Direction;
import java.util.ArrayList;
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
public class GameMap extends TiledMap {

    //public TiledMap map;
    public Music BGM;
    public Image battleback;
    public ArrayList<Event> events;
    public ArrayList<GameObject> objs;
    public ArrayList<Rectangle> listRect;

    public GameMap(String res, WorldPlayer p) throws SlickException {
        super(res);
        events = new ArrayList<>();
        listRect = new ArrayList<>();
        objs = new ArrayList<>();
        BGM = GameCache.bgm("fatefulencounter.wav");
        for (int xAxis = 0; xAxis < getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < getHeight(); yAxis++) {
                int tileID = getTileId(xAxis, yAxis, 2);
                String value = getTileProperty(tileID, "blocked", "false");
                if ("true".equals(value)) {
                    listRect.add(new Rectangle(xAxis * getTileWidth(), yAxis * getTileHeight(), getTileWidth(), getTileHeight()));
                }
            }
        }
        events = new ArrayList<>();
        for (int Object = 0; Object < getObjectCount(0); Object++) {
            String value = getObjectType(0, Object);
            if ("Actor".equals(value)) {
                events.add(new Event(getObjectX(0, Object) + getObjectWidth(0, Object)/2, getObjectY(0, Object) + getObjectHeight(0, Object)/2, value, getObjectProperty(0, Object, "text", "false")));
            }
            if ("Npc".equals(value)) {
                NPC npc = new NPC(getObjectX(0, Object) + getObjectWidth(0, Object)/2, getObjectY(0, Object) + getObjectHeight(0, Object)/2, getObjectProperty(0, Object, "image", "false"));
                events.add(npc);
                objs.add(npc);
                //listRect.add(new Rectangle(npc.getX()+8+2, npc.getY()+8+42, 61, 43));

            }
            if ("Teleport".equals(value)) {
                Event e = new Event(getObjectX(0, Object), getObjectY(0, Object), value, null);
                e.map = getObjectProperty(0, Object, "map", "");
                events.add(e);
            }

            if ("Enemy".equals(value)) {
                GameCharacter e = new GameCharacter("092-Monster06");
                e.pos.x = getObjectX(0, Object) + getObjectWidth(0, Object)/2;
                e.pos.y = getObjectY(0, Object) + getObjectHeight(0, Object)/2;
                objs.add(e);
            }
        }
        objs.add(p);
        p.tp(Float.parseFloat(getMapProperty("startPosx", "1")) * 64 + p.bounds.getWidth(), Float.parseFloat(getMapProperty("startPosy", "1")) * 64 + p.bounds.getHeight());
    }
    
    public int getPixelWidth(){
        return getWidth() * getTileWidth();
    }
    
    public int getPixelHeight(){
        return getHeight() * getTileHeight();
    }

    public void render(Graphics g) {
        render(0, 0, 0);
        render(0, 0, 1);
    }

    public void update(GameContainer container, WorldPlayer p) throws SlickException {
        for (GameObject go : objs) {
            go.update();
        }
        for (Event e : events) {
            if (p.bounds.intersects(e.getActivationRect()) && p.getAction() == true) {
                if (e.getType().equals("Actor") || e.getType().equals("Npc")) {
                    NPC npc = (NPC) e;
                    if (e.getType().equals("Npc")) {
                        switch (p.facing) {
                            case DOWN:
                                npc.setFacing(Direction.UP);
                                break;
                            case UP:
                                npc.setFacing(Direction.DOWN);
                                break;
                            case LEFT:
                                npc.setFacing(Direction.RIGHT);
                                break;
                            case RIGHT:
                                npc.setFacing(Direction.LEFT);
                                break;
                        }
                        npc.activate();
                    }
                    break;
                }
                if (e.getType().equals("Teleport")) {
                    container.pause();
                    SceneMap.setMap(new GameMap("res/data/map/"+e.map+".tmx", p));
                    container.resume();
                }
            }
        }

    }
}
