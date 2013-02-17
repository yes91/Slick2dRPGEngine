/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

/**
 *
 * @author redblast71
 */

public class GameActor extends GameBattler{   
    
    public String name;
    public String characterName;
    public int characterIndex;
    public String faceName;
    public int faceIndex;
    public int classID;
    public int weaponID;
    public int armor1_ID;
    public int armor2_ID;
    public int armor3_ID;
    public int armor4_ID;
    public int lastSkillID;
    
    public GameActor(){
        super();
    }
    
    public GameClass getGameClass(){
        return GameData.classes.get(classID);
    }
    
}
