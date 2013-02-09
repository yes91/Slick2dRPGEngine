/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

/**
 *
 * @author redblast71
 */
public class EventCommand {
    
    public int code;
    public Object[] parameters;
    public int indent;
    
    
    public EventCommand(Object[] params, int code, int indent){
        this.parameters = params;
        this.code = code;
        this.indent = indent;
    }
}
