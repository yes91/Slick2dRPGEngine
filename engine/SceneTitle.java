/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import effectutil.CameraFadeInTransition;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.ControllerListener;
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
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 *
 * @author Kieran
 */
public class SceneTitle extends SceneBase{
    
    private KeyListener klistener;
    private ControllerListener clistener;
    private Image back;
    private WindowCommand wind;
    private WindowSystem options;
    private WindowMessage test;
    private int keyPressed;
    private boolean fullscreen;
    private char repChar;
    private int controller;
    private int buttonPressed;
    private boolean inSubMenu;
    private ArrayList<Window> uielements;
    private Window lastAdded;
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
        klistener = new KeyListener(){ 
            
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
        clistener = new ControllerListener(){

            @Override
            public void controllerLeftPressed(int i) {
                
            }

            @Override
            public void controllerLeftReleased(int i) {
                
            }

            @Override
            public void controllerRightPressed(int i) {
                
            }

            @Override
            public void controllerRightReleased(int i) {
                
            }

            @Override
            public void controllerUpPressed(int i) {
                
            }

            @Override
            public void controllerUpReleased(int i) {
                
            }

            @Override
            public void controllerDownPressed(int i) {
                
            }

            @Override
            public void controllerDownReleased(int i) {
                
            }

            @Override
            public void controllerButtonPressed(int i, int i1) {
                controller = i; buttonPressed = i1;
            }

            @Override
            public void controllerButtonReleased(int i, int i1) {
                
            }

            @Override
            public void setInput(Input input) {
                
            }

            @Override
            public boolean isAcceptingInput() {
                return true;
            }

            @Override
            public void inputEnded() {
                
            }

            @Override
            public void inputStarted() {
                
            }
        };
        //gc.getGraphics().setFont(Cache.getFont());
        input = gc.getInput();
        input.addKeyListener(klistener);
        input.addControllerListener(clistener);
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
        inputp.bindCommand(new KeyControl(Input.KEY_PRIOR), pUp);
        inputp.bindCommand(new KeyControl(Input.KEY_NEXT), pDown);
        inputp.bindCommand(new ControllerButtonControl(0, 5), pUp);
        inputp.bindCommand(new ControllerButtonControl(0, 6), pDown);
        ItemReader.populateItems();
        for(Item i: GameData.items){
            gameParty.giveItem(i, 99);
        }
        gameParty.setMembers(GameData.actors.subList(0, 4));
        gameTroop.setMembers(Demo.testEnemies);
        uielements = new ArrayList<>();
        back = GameCache.res("TitleBack.png");
        options = new WindowSystem(2, 1);
        String[] coms = new String[]{"New Game","Continue","Option","Exit"};
        wind = new WindowCommand(160, coms, 1, 0);
        gameMessage.faceName = "People1";
        gameMessage.faceIndex = 4;
        String text = "Hello, \\C[1]this\\C[0] is a test of the message system.\n"
        + "This is a new line. This is still the same line.\n"
        + "Habla espanol por favor, senor. No hablo ingles.\n"
        + "For sooth, this is yet another line!~\n"
        + "This is a fifth line, woot! YEAH!\n"
        + "This is a sixth line, it ~should work.\n"
        + "This is a seventh line, I really hope it works.\n"
        + "This is an eight line, the line after this better work.\n"
        + "Line #9 and it better work.\n"
        + "Ok, clearly it works no matter what.";
        gameMessage.setText(text);
        test = new WindowMessage();
        wind.initX = (1280/2) - 80;
        wind.initY = 500;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        back.draw(0, 0);
        GameCache.getFont().drawString(300, 100, "Debug Message: KeyListener "+repChar);
        GameCache.getFont().drawString(300, 150, "Debug Message: ContollerListener Button: "+buttonPressed+" on Controller: "+controller);
        if(!inSubMenu){
            wind.render(g, sbg);
        }
        else{
            GameCache.getFont().drawString(300, 50, "Debug Message: In Submenu");
            for(Window w: uielements){
            w.render(g, sbg);
            }
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        if (input.isKeyDown(Input.KEY_RALT) && input.isKeyPressed(Input.KEY_ENTER)) {
            fullscreen = gc.isFullscreen();
            gc.pause();
            gc.setFullscreen(!fullscreen);
            gc.resume();
        }
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            gc.exit();
        }
        if(!inSubMenu){
        ((WindowSelectable)wind).update(inputp, delta);
            if(inputp.isCommandControlDown(action)){
                switch(wind.index){
                    case 0:
                        input.removeKeyListener(klistener);
                        input.removeControllerListener(clistener);
                        input.clearKeyPressedRecord();
                        input.clearControlPressedRecord();
                        sbg.enterState(1, new FadeOutTransition(), new CameraFadeInTransition()); break;
                    case 1: lastAdded = test;
                        uielements.add(lastAdded); 
                        test.startMessage(); break;
                    case 2:
                        lastAdded = options;
                        uielements.add(lastAdded); break;
                    case 3: gc.exit(); break;
                }
                inSubMenu = true;
            }
        }
        else{
            for(Window w: uielements){
                if(w instanceof WindowSelectable){
                    ((WindowSelectable)w).update(inputp, delta);
                }
            }
            if(inputp.isCommandControlDown(cancel)){
                uielements.remove(lastAdded);
                inSubMenu = false;
            }
        }
    }
    
}
