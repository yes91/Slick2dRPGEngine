/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.List;

/**
 *
 * @author redblast71
 */
public class GameInterpreter {
    public int mapID;
    public int initEventID;
    public int eventID;
    public List<EventCommand> list;
    public int index;
    public boolean messageWaiting;
    public Object movingCharacter;
    public int waitCount;
    public GameInterpreter childInterpreter;
    public Object branch;
    public int depth;
    public boolean main;
    private Object[] params;
    
    public GameInterpreter(){
        depth = 0;
        main = false;
        clear();
    }
    
    public GameInterpreter(int depth, boolean main){
        this.depth = depth;
        this.main = main;
        if(depth > 100){
            System.err.println("Common event call has exceeded maximum limit.");
            System.exit(1);
        }
        clear();
    }
    
    public final void clear(){
        mapID = 0;
        initEventID = 0;
        eventID = 0;
        list = null;
        index = 0;
        messageWaiting = false;
        movingCharacter = null;
        waitCount = 0;
        childInterpreter = null;
        branch = null;
    }
    
    public void setup(List list, int eventID){
        this.list = list;
        this.initEventID = eventID;
        this.eventID = eventID;
        
    }
    
    public void update(){
        if(list == null){
            return;
        }
        if(!executeCommands()){
            return;
        }
        index++;
    }
    
    public boolean executeCommands(){
        params = list.get(index).parameters;
        int indent = list.get(index).indent;
        switch(list.get(index).code){
            case 0: return showText();
        }
        return true;
    }
    
    public boolean showText(){
        SceneBase.gameMessage.faceName = (String)params[0];
        SceneBase.gameMessage.faceIndex = (int)params[1];
        SceneBase.gameMessage.background = (int)params[2];
        SceneBase.gameMessage.position = (int)params[3];
        SceneBase.gameMessage.setText((String)params[4]);
        SceneMap.removeUIElement(SceneMap.lastAdded);
        SceneMap.lastAdded = SceneMap.message;
        SceneMap.addUIElement(SceneMap.message);
        SceneMap.message.startMessage();
        SceneMap.uiFocus = true;
        return false;
    }
}
