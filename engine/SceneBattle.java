/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import effectutil.CameraFadeInTransition;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
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
    private int currentActor;
    private WindowCommand partyCommand;
    private WindowCommand actorCommand;
    private WindowCommand activeWindow;
    private Window canvas;
    private WindowBattleStatus stat;
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
        partyCommand.render(g, sbg);
        actorCommand.render(g, sbg);
        stat.render(g, sbg);
        /*int index = 0;
        for(GameActor ga :Demo.testActors){
            canvas.drawActorFace(ga, (120 * index) + 32*index, 0);
            canvas.drawActorHP(ga, (120 * index) + 32*index, 2.8f * 24);
            canvas.drawActorMP(ga, (120 * index)  + 32*index, 3.6f * 24);
            index++;
        }
        canvas.cg.flush();
        canvas.render(g, sbg); */
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        if(BGM != null){
            if(!BGM.playing()){
                BGM.loop();
            }
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
                Demo.init();
                activeWindow = partyCommand;
            }
            switch(actorCommand.index){
                case 0: 
                    if(inputp.isCommandControlPressed(action)){
                        Demo.testActors.get(0).currentHP -= 50;
                        if(Demo.testActors.get(0).currentHP <= 0){
                            Demo.testActors.get(0).currentHP = 0;
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
