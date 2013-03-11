/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author redblast71
 */
public class GameClass {

    public static enum Position {

        FRONT(0),
        MIDDLE(1),
        BACK(2);
        
        public final int index;

        Position(int index) {
            this.index = index;
        }

        @Override
        public String toString() {
            switch (this.name()) {
                case "FRONT":
                    return "Front";
                case "MIDDLE":
                    return "Middle";
                case "BACK":
                    return "Back";
            }
            return "";
        }
    }
    
    public Position pos;
    public String className;
    public HashMap<Integer, Boolean> weapons; //Key-weapon item index Value-usable by class
    public HashMap<Integer, Boolean> armors; //Key-armor item index Value-usable by class
    public HashMap<Element, Float> eEfficiency;
    public HashMap<Integer /*level*/, List<Integer>> learnings;

    public GameClass() {
        pos = Position.FRONT;
        className = "";
        weapons = new HashMap();
        armors = new HashMap();
        eEfficiency = new HashMap();
        learnings = new HashMap();
    }
}
