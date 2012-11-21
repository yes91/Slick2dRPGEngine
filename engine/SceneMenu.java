/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author redblast71
 */
public class SceneMenu extends SceneBase{
    
    public static Image back;
    private WindowCommand wind;
    //private Input input;
    
    public int stateID = -1;
    
    public SceneMenu(int stateID){
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        //s = Shader.makeShader("/src/rpgslickport/blur.vrt", "/src/rpgslickport/blur.frg");
        //s2 = Shader.makeShader("/src/rpgslickport/blur2.vrt", "/src/rpgslickport/blur2.frg");
        //i = new Image(1280,720);
        input = gc.getInput();
        String[] coms = new String[6];
                coms[0] = "Item";
                coms[1] = "Skill";
                coms[2] = "Equip";
                coms[3] = "Status";
                coms[4] = "Save";
                coms[5] = "Options";
                wind = new WindowCommand(120,coms, 1, 0);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        grphcs.drawImage(back, 0, 0);
        wind.render(grphcs,sbg);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        ((WindowSelectable)wind).update(inputp);
        if(inputp.isCommandControlPressed(cancel)){
            Sounds.cancel.play();
            input.clearKeyPressedRecord();
            input.clearControlPressedRecord();
            sbg.getState(1).update(gc, sbg, delta);
            sbg.enterState(1);
        }
    }
    
}
