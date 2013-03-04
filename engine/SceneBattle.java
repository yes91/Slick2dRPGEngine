/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import effectutil.CameraFadeInTransition;
import java.util.Arrays;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 *
 * @author Kieran
 */
public class SceneBattle extends SceneBase{
    private int stateID = -1;
    private static Image battleBack;
    private static Music BGM;
    private int xOffset = 0;
    private WindowCommand partyCommand;
    private WindowCommand actorCommand;
    private WindowCommand activeWindow;
    private WindowBattleStatus stat;
    //private DepthBuffCompare comparator = new DepthBuffCompare();
    private final int DEPTH_BUFFER_SIZE = 720/2;
    private int activePos = 0;
    private static float[][] position = new float[][]{
        new float[]{498.0f, 77.0f, 77.0f},
        new float[]{582.0f, 129.0f, 129.0f},
        new float[]{667.0f, 199.0f, 199.0f},
        new float[]{751.0f, 285.0f, 285.0f}
        
    };
    //private WindowBattleMessage bMessage;
    private GameParty party = SceneBase.gameParty;
            
    public SceneBattle(int stateID){
        this.stateID = stateID;
    }
    
    

    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        battleBack = null;
        BGM = null;
        createInfoView();
    }
    
    public void createInfoView() throws SlickException{
        partyCommand = new WindowCommand(120, 
            new String[]{ "Fight",
                          "Escape"
        }, 1, 4);
        partyCommand.initX = -120;
        partyCommand.initY = 720 - partyCommand.height;
        stat = new WindowBattleStatus(120, 720 - partyCommand.height, 1280 - 120 - 8, partyCommand.height);
        actorCommand = new WindowCommand(120, 
            new String[]{ "Attack", 
                          "Guard", 
                          "Skill",
                          "Item"
        }, 1, 0);
        actorCommand.initX = 1000;
        actorCommand.initY = 720 - actorCommand.height;
        actorCommand.index = -1;
        activeWindow = partyCommand;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        if(battleBack != null){
            battleBack.draw(0, 0, 1280, 720, 0, 0, 580, 444);
        }
        int i = 0;
        //Arrays.sort(position, comparator);
        for(GameActor ga: gameParty.getMembers()){
            ga.render(g, position[i][0], position[i][1], 1.0f + 1.0f * (position[i][2]/DEPTH_BUFFER_SIZE));
            i++;
        }
        /*for(int i = 0; i < gameParty.getMembers().size(); i++){
            gameParty.getMembers().get(i).battleSprite.getCurrentAni().draw(
                (!(i < 4) ? 800:700) + 100 * (!(i < 4) ? i - 4:i), (!(i < 4) ? 200:300) + 100 * (!(i < 4) ? i - 4:i));
        }*/
        partyCommand.render(g, sbg);
        actorCommand.render(g, sbg);
        stat.render(g, sbg);
        
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        if(BGM != null){
            if(!BGM.playing()){
                BGM.loop();
            }
        }
        if(input.isKeyPressed(Input.KEY_1)){
            activePos = 0;
        }
        if(input.isKeyPressed(Input.KEY_2)){
            activePos = 1;
        }
        if(input.isKeyPressed(Input.KEY_3)){
            activePos = 2;
        }
        if(input.isKeyPressed(Input.KEY_4)){
            activePos = 3;
        }
        if(input.isKeyPressed(Input.KEY_ENTER)){
            System.out.println(Arrays.deepToString(position));
        }
        if(input.isKeyDown(Input.KEY_UP)){
            position[activePos][1] -= 1f;
        } else if(input.isKeyDown(Input.KEY_DOWN)){
            position[activePos][1] += 1f;
        } else if(input.isKeyDown(Input.KEY_RIGHT)){
            position[activePos][0] += 1f;
        } else if(input.isKeyDown(Input.KEY_LEFT)){
            position[activePos][0] -= 1f;
        } else if(input.isKeyDown(Input.KEY_Z)){
            position[activePos][2] -= 1f;
        } else if(input.isKeyDown(Input.KEY_X)){
            position[activePos][2] += 1f;
        }
        stat.initX = xOffset;
        partyCommand.initX = xOffset - 120;
        actorCommand.initX = stat.width + xOffset;
        activeWindow.update(inputp, delta);
        if(activeWindow.equals(partyCommand)){
            if(xOffset < 128){
                xOffset += 16;
            }
            switch(partyCommand.index){
                case 0: 
                    if(inputp.isCommandControlPressed(action)){
                        actorCommand.index = 0;
                        activeWindow = actorCommand;
                        partyCommand.index = -1;
                    }
                    break;
                case 1:
                    if(inputp.isCommandControlPressed(action)){
                        sbg.enterState(1, new FadeOutTransition(), new CameraFadeInTransition());
                    }
                    break;
            }
        } else if(activeWindow.equals(actorCommand)){
            if(xOffset > 0){
                xOffset -= 16;
            }
            
            if(inputp.isCommandControlPressed(cancel)){
                actorCommand.index = -1;
                partyCommand.index = 0;
                gameParty.getMembers().get(0).currentHP = gameParty.getMembers().get(0).getMaxHP();
                activeWindow = partyCommand;
            }
            switch(actorCommand.index){
                case 0: 
                    if(inputp.isCommandControlPressed(action)){
                        gameParty.getMembers().get(0).currentHP -= 50;
                        if(gameParty.getMembers().get(0).currentHP <= 0){
                            gameParty.getMembers().get(0).currentHP = 0;
                        }
                    }
                    break;
            }
        }
    }

    public static Music getBGM() {
        return BGM;
    }

    public static void setBGM(String music) {
        BGM = Sounds.getMusic(music);
    }
    
    public void setBattleBack(Image b){
        battleBack = b;
    }
    
    public Image getBattleBack(){
        return battleBack;
    }
    
    
    
}
