/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.Stack;

/**
 *
 * @author redblast71
 */
public class GameMessage {
    
    public final int MAX_LINE = 4;
    
    public Stack<String> texts;
    
    public GameMessage(){
        clear();
    }
    
    public final void clear(){
        texts = new Stack();
    }
    
    public boolean busy(){
        return texts.size() > 0;
    }
    
    public void newPage(){
        while(texts.size() % MAX_LINE > 0){
            texts.push("");
        }
    }
}
