/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author Kieran
 */
public class NPC extends Event {


    public NPC(float x, float y, String image) {
        super(x, y, "Npc", image);
    }

    public void activate() {
        SceneBase.gameMessage.faceName = "People1";
        SceneBase.gameMessage.faceIndex = 2;
        SceneBase.gameMessage.setText(text);
        SceneMap.removeUIElement(SceneMap.lastAdded);
        SceneMap.lastAdded = SceneMap.message;
        SceneMap.addUIElement(SceneMap.message);
        SceneMap.message.startMessage();
        SceneMap.uiFocus = true;
        //SceneMap.interpreter.setup(list, 0);
    } 
}
