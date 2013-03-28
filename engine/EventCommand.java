/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

/**
 *
 * @author redblast71
 */
public final class EventCommand {
    
    public final String ID;
    public final Object[] params;
    
    public EventCommand(String ID, Object[] params){
        this.ID = ID;
        this.params = params;
    }
    
}
