/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

/**
 *
 * @author redblast71
 */
public class WindowMessage extends WindowSelectable{
    
    public GameMessage gameMessage;
    
    public final int MAX_LINE = 4;
    
    private String text;
    
    public WindowMessage(){
        super(0, (int)(SceneMap.B_WIDTH*1.89), SceneMap.B_WIDTH, (int)(SceneMap.B_HEIGHT*3.25));
        this.index = -1;
        this.gameMessage = new GameMessage();
        this.text = null;
        
    }
}
