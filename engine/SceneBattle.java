/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Kieran
 */
public class SceneBattle extends SceneBase{
    
    private int stateID = -1;
    private static Image battleBack;
    private static Music BGM;
    
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
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        if(battleBack != null){
            battleBack.draw(0, 0, 1280, 720, 0, 0, 580, 444);
        }
        
        
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if(BGM != null){
            if(!BGM.playing()){
                BGM.loop();
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
