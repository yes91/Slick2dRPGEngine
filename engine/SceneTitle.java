/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.ControllerButtonControl;
import org.newdawn.slick.command.ControllerDirectionControl;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Kieran
 */
public class SceneTitle extends SceneBase{
    
    private KeyListener listener;
    private Image back;
    private WindowCommand wind;
    private int keyPressed;
    private char repChar;
    private boolean inSubMenu;
    public int stateID = -1;
    
    public SceneTitle(int stateID){
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        inSubMenu = false;
        listener = new KeyListener(){ 
            
            @Override
            public void keyPressed(int index, char c){
            keyPressed = index; repChar = c;
            }
            
            @Override
            public void keyReleased(int index, char c){
            
            }
            
            @Override
            public void inputStarted(){
            
            }
            
            @Override
            public void inputEnded(){
            
            }
            
            @Override
            public boolean isAcceptingInput(){
                return true;
            }
            
            @Override
            public void setInput(Input input){
                
            }
        
        };
        input = gc.getInput();
        input.addKeyListener(listener);
        inputp = new InputProvider(input);
        inputp.bindCommand(new KeyControl(Input.KEY_W), up);
        inputp.bindCommand(new KeyControl(Input.KEY_S), down);
        inputp.bindCommand(new KeyControl(Input.KEY_A), left);
        inputp.bindCommand(new KeyControl(Input.KEY_D), right);
        inputp.bindCommand(new KeyControl(Input.KEY_E), menu);
        inputp.bindCommand(new KeyControl(Input.KEY_K), cancel);
        inputp.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.DOWN), down);
        inputp.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.UP), up);
        inputp.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.LEFT), left);
        inputp.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.RIGHT), right);
        inputp.bindCommand(new ControllerButtonControl(0, 1), sprint);
        inputp.bindCommand(new ControllerButtonControl(0, 3), action);
        inputp.bindCommand(new ControllerButtonControl(0, 4), menu);
        inputp.bindCommand(new ControllerButtonControl(0, 2), cancel);
        inputp.bindCommand(new KeyControl(Input.KEY_LSHIFT), sprint);
        inputp.bindCommand(new KeyControl(Input.KEY_J), action);
        back = Cache.getRes("TitleBack.png");
        String[] coms = new String[]{"New Game","Continue","Options","Exit"};
        wind = new WindowCommand(260, coms, 1, 0);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        back.draw(0, 0);
        wind.render(g, sbg);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        if(!inSubMenu){
        ((WindowSelectable)wind).update(inputp);
            if(inputp.isCommandControlDown(action)){
                switch(wind.index){
                    
                }
                inSubMenu = true;
            }
        }
        else{
            
        }
    }
    
}
