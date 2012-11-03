/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Input;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.state.BasicGameState;

/**
 *
 * @author Kieran
 */
public abstract class SceneBase extends BasicGameState{
    
    public Input input;
    public static BasicCommand up = new BasicCommand("Up");
    public static BasicCommand down = new BasicCommand("Down");
    public static BasicCommand left = new BasicCommand("Left");
    public static BasicCommand right = new BasicCommand("Right");
    public static BasicCommand sprint = new BasicCommand("Sprint");
    public static BasicCommand action = new BasicCommand("Action");
    public InputProvider inputp;
    public static WorldPlayer worldPlayer;
    
}
