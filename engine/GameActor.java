/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author redblast71
 */

public class GameActor extends GameBattler {   
    
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
    private boolean twoSwords;
    
    public GameActor(){
        super();
        spriteName = "default";
    }
    
    public GameActor(String bSprite){
        super(bSprite+".png");
        spriteName = bSprite;
    }
    
    @Override
    public boolean isActor(){
        return true;
    }
    
    @Override
    public void performCollapse(){
        Sounds.playActorCollapse();
    }
    
    public List<Weapon> getWeapons(){
        List<Weapon> weps = new ArrayList<>();
        if(GameData.items.get(weaponID) instanceof Weapon){
            weps.add((Weapon)GameData.items.get(weaponID));
        }
        if(twoSwordsStyle() && GameData.items.get(armor1_ID) instanceof Weapon){
            weps.add((Weapon)GameData.items.get(armor1_ID));
        }
        return weps; 
    }
    
    public void setTwoSwords(boolean twoSwords){
        this.twoSwords = twoSwords;
    }
    
    public boolean twoSwordsStyle(){
        return twoSwords;
    }
    
    public GameClass getGameClass(){
        return GameData.classes.get(classID);
    }
    
}
