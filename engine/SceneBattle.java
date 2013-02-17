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
    private int currentActor;
    private boolean isPlayerTurn;
    private WindowCommand fightFlee;
    private WindowCommand command;
    private WindowCommand activeWindow;
    private Window canvas;
    //private WindowBattleStatus stat;
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
        fightFlee = new WindowCommand(120, 
            new String[]{ "Fight",
                          "Escape"
        }, 1, 4);
        fightFlee.initX = 280 - 120;
        fightFlee.initY = 720 - fightFlee.height;
        canvas = new Window(280, 720 - fightFlee.height, 1000 - 280, fightFlee.height);
        command = new WindowCommand(120, 
            new String[]{ "Attack", 
                          "Guard", 
                          "Skill",
                          "Item"
        }, 1, 0);
        command.initX = 1000;
        command.initY = 720 - command.height;
        command.index = -1;
        activeWindow = fightFlee;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        if(battleBack != null){
            battleBack.draw(0, 0, 1280, 720, 0, 0, 580, 444);
        }
        fightFlee.render(g, sbg);
        command.render(g, sbg);
        canvas.drawActorHPGuage(Demo.battleTester, 0, 32);
        canvas.drawActorMPGuage(Demo.battleTester, 0, 32+16);
        canvas.render(g, sbg); 
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        if(BGM != null){
            if(!BGM.playing()){
                BGM.loop();
            }
        }
        activeWindow.update(inputp, delta);
        if(activeWindow.equals(fightFlee)){
            switch(fightFlee.index){
                case 0: 
                    if(inputp.isCommandControlPressed(action)){
                        command.index = 0;
                        activeWindow = command;
                        fightFlee.index = -1;
                    }
                    break;
                case 1:
                    if(inputp.isCommandControlPressed(action)){
                        sbg.enterState(1, new FadeOutTransition(), new CameraFadeInTransition());
                    }
                    break;
            }
        } else if(activeWindow.equals(command)){
            if(inputp.isCommandControlPressed(cancel)){
                command.index = -1;
                fightFlee.index = 0;
                Demo.init();
                activeWindow = fightFlee;
            }
            switch(command.index){
                case 0: 
                    if(inputp.isCommandControlPressed(action)){
                        Demo.battleTester.currentHP -= 50;
                        if(Demo.battleTester.currentHP <= 0){
                            Demo.battleTester.currentHP = 0;
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
