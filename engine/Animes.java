/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.HashMap;

/**
 *
 * @author Kieran
 */
public class Animes {
    
    public static final HashMap<String, Object[]> ANIME = new HashMap();
    static {
        ANIME.put("WAIT",           new Object[]{ 1,  0,  12,   0,   0,  -1,  0,  false,  ""});
        ANIME.put("WAIT(FIXED)",    new Object[]{ 1,  0,  12,   2,   0,   1,  0,  false,  ""});
        ANIME.put("DEAD_POSE",      new Object[]{ 1, 10,  12,   1,   8,   0,  0,   true,  ""});
        ANIME.put("VICTORY_POSE",   new Object[]{ 1,  9,  12,   0,   0,  -1,  0,  false,  ""});
        ANIME.put("SKILL_POSE",     new Object[]{ 1,  8,  16,   2,   0,  -1,  0,  false,  ""});
        ANIME.put("MOVE_TO",        new Object[]{ 1,  4,   6,   1,   0,  -1,  0,  false,  ""});
        ANIME.put("MOVE_AWAY",      new Object[]{ 1,  5,   6,   1,   0,  -1,  0,  false,  ""});
        ANIME.put("WPN_SWING_V",    new Object[]{ 1,  6,   4,   2,   0,  -1,  0,  false,  ""});
        ANIME.put("WPN_SWING_VL",   new Object[]{ 1,  6,   1,   2,   0,  -1,  0,  false,  ""});
        
        ANIME.put("COORD_RESET", new Object[]{"reset", 16,  0,   0,  "MOVE_TO"});
        ANIME.put("FLEE_RESET",  new Object[]{"reset", 16,  0,   0,  "MOVE_AWAY"});
        
        ANIME.put("Two Wpn Only", new Object[]{"Two Wpn Only"});
        ANIME.put("One Wpn Only", new Object[]{"One Wpn Only"});
        ANIME.put("Can Collapse", new Object[]{"Can Collapse"});
        ANIME.put("Don't Wait",   new Object[]{"Don't Wait"});
        ANIME.put("End", new Object[]{"End"});
        
        ANIME.put("OBJ_ANIM_WAIT", new Object[]{"anime",  -1,  1,   false, true, false});
        ANIME.put("OBJ_ANIM_L", new Object[]{"anime",  -1,  1,   false,  false,  true});
        
        ANIME.put("START_MAGIC_ANIM", new Object[]{"m_a", 44,   4,  0,  52,   0,  0,  0,  2, false, ""});
        
        ANIME.put("NO_MOVE",            new Object[]{ 0,   0,   0,  1,   0,   0,  "WAIT(FIXED)"});
        ANIME.put("MOVE_DOWN",          new Object[]{ 3,   0,  50,  2,  -1,   0,  "DEAD_POSE"});
        ANIME.put("PREV_MOVING_TARGET", new Object[]{ 1,  24,   0, 12,  -1,   0,  "MOVE_TO"});
        ANIME.put("START_POSITION",     new Object[]{ 0,  54,   0,  1,   0,   0,  "MOVE_TO"});
        ANIME.put("BEFORE_MOVE",        new Object[]{ 3, -32,   0, 18,  -1,   0,  "MOVE_TO"});
        
    }
    
}
