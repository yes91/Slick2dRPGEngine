/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import engine.Effect.Place;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author redblast71
 */
public class SceneMenu extends SceneBase {

    public static Image back;
    private WindowCommand command;
    private WindowMenuStatus menuStatus;
    private WindowMenuStatus target;
    private Consumable toUse;
    private WindowItem inventory;
    private WindowHelp invHelp;
    private Window activeWindow;
    //private Input input;
    public int stateID = -1;

    public SceneMenu(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        input = gc.getInput();
        String[] coms = new String[]{
            "Item",
            "Skill",
            "Equip",
            "Status",
            "Save",
            "Options"
        };
        command = new WindowCommand(120, coms, 1, 0);
        menuStatus = new WindowMenuStatus(command.width, 0);
        target = new WindowMenuStatus(0, 0);
        target.index = 0;
        invHelp = new WindowHelp();
        inventory = new WindowItem(command.x, command.y + invHelp.height, SceneMap.B_WIDTH, SceneMap.B_HEIGHT - invHelp.height, gameParty.getInv());
        activeWindow = command;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        back.draw();
        if (activeWindow != null) {
            if (activeWindow.equals(inventory)) {
                invHelp.render(grphcs, sbg);
            }
            activeWindow.render(grphcs, sbg);
            if (activeWindow.equals(command)) {
                menuStatus.render(grphcs, sbg);
            }
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        ((WindowSelectable) activeWindow).update(inputp, delta);
        if (activeWindow.equals(command)) {
            switch (command.index) {
                case 0:
                    if (inputp.isCommandControlDown(action)) {
                        activeWindow = inventory;
                        clearPressedRecord(action);
                    }
                    break;
            }
        } else if (activeWindow.equals(inventory) && !gameParty.getInv().items.isEmpty()) {
            invHelp.setText(inventory.getItem().getDesc());
            if (inventory.getItem() instanceof Consumable 
                    && ((Consumable)inventory.getItem()).getPlace() != Place.BATTLE_ONLY
                    && ((Consumable)inventory.getItem()).getPlace() != Place.NEVER) {
                if (inputp.isCommandControlPressed(action)) {
                    activeWindow = target;
                    toUse = (Consumable)inventory.getItem();
                }
            }
        } else if (activeWindow.equals(target)) {
            if (inputp.isCommandControlPressed(action)) {
                if (gameParty.getMembers().get(target.index).isEffective(toUse) && gameParty.getInv().getItemAmount(toUse) > 0){
                    gameParty.getMembers().get(target.index).itemEffect(toUse);
                    gameParty.takeItem(toUse, 1);
                    Sounds.itemUse.play();
                } else {
                    Sounds.buzzer.play();
                    inventory.updateItems();
                }
            }
        }
        if (inputp.isCommandControlPressed(cancel)) {
            if (activeWindow.equals(command)) {
                Sounds.cancel.play();
                input.clearKeyPressedRecord();
                input.clearControlPressedRecord();
                sbg.getState(1).update(gc, sbg, delta);
                sbg.enterState(1);
            } else if (activeWindow.equals(inventory)) {
                Sounds.cancel.play();
                activeWindow = command;
            } else if (activeWindow.equals(target)) {
                Sounds.cancel.play();
                toUse = null;
                activeWindow = inventory;
            }
        }
        clearPressedRecord(action);
    }
}
