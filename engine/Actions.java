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
public class Actions {
    
    public static final HashMap<String, String[]> ACTION = new HashMap();
    static{
        ACTION.put("BATTLE_START", new String[]{ "START_POSITION", "COORD_RESET" });
        ACTION.put("WAIT", new String[]{"WAIT"});
        ACTION.put("DEATH_POSITION", new String[]{"MOVE_DOWN"});
        ACTION.put("DEAD", new String[]{"DEAD_POSE"});
        ACTION.put("HURT", new String[]{"KNOCKBACK", "COORD_RESET"});
        ACTION.put("VICTORY", new String[]{ "WAIT", "16", "VICTORY_POSE", "Don't Wait" });
        ACTION.put("WAIT-CRITICAL", new String[]{ "NO_MOVE", "WAIT(FIXED)", "22"});
        ACTION.put("COMMAND_INPUT", new String[]{ "BEFORE_MOVE" });
        ACTION.put("COMMAND_SELECT", new String[]{ "COORD_RESET"});
        ACTION.put("NORMAL_ATTACK", new String[]{"16", "PREV_MOVING_TARGET","WPN_SWING_V",
                          "OBJ_ANIM_WAIT", "12","WPN_SWING_VL",
                          "OBJ_ANIM_L", "Two Wpn Only", "16",
                          "Can Collapse","FLEE_RESET"});
        ACTION.put("STATIONARY_ATTACK", new String[]{ "WPN_SWING_V",
                          "OBJ_ANIM_WAIT", "12","WPN_SWING_VL",
                          "OBJ_ANIM_L", "Two Wpn Only", "16",
                          "Can Collapse","FLEE_RESET" });
        ACTION.put("SKILL_USE", new String[]{ "BEFORE_MOVE","WAIT(FIXED)","START_MAGIC_ANIM",
                          "SKILL_POSE", "WAIT(FIXED)",
                          "OBJ_ANIM_WAIT","Can Collapse","24","COORD_RESET"});
    }
    
}
