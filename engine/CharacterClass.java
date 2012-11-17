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
public class CharacterClass {
    
    HashMap<Integer, Boolean> weapons;
    HashMap<Integer, Boolean> armors;
    
    public CharacterClass(int classID){
        weapons = new HashMap();
        armors = new HashMap();
    }
    
    
    
}
