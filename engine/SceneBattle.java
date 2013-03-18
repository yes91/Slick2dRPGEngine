/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import effectutil.CameraFadeInTransition;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 *
 * @author Kieran
 */
public class SceneBattle extends SceneBase {

    public static final boolean DEBUG_UTIL = false;
    private float zoom = 1.0f;
    private Vector2f screenPos = new Vector2f(0, 0);
    private boolean playingAction = false;
    private int stateID = -1;
    private static Image battleBack;
    private static Music BGM;
    private int xOffset = 0;
    private boolean victory;
    private boolean defeat;
    private WindowCommand partyCommand;
    private WindowCommand actorCommand;
    private WindowItem itemWindow;
    private WindowHelp help;
    private WindowSelectable activeWindow;
    private WindowSelectable targetEnemy; 
    private WindowBattleStatus stat;
    private SpritesetBattle spriteset;
    private LinkedList<GameBattler> actionBattlers = new LinkedList<>();
    private int actorIndex;
    private GameBattler activeBattler;
    
    //private WindowBattleMessage bMessage;
    private GameParty party = SceneBase.gameParty;

    public SceneBattle(int stateID) {
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
    
    @Override
    public void enter(GameContainer container, StateBasedGame game)
         throws SlickException {
      super.enter(container, game);
      targetEnemy.itemMax = gameTroop.getMembers().size();
      targetEnemy.index = 0;
      victory = false;
      defeat = false;
      playingAction = false;
      for(GameEnemy e:gameTroop.getMembers()){
        e.currentHP = e.getMaxHP();
      }
      spriteset = new SpritesetBattle();
      actionBattlers.clear();
      actorIndex = 0;
      activeBattler = party.getMembers().get(actorIndex);
      setBGM("Trial_by_fire.ogg");
      BGM.loop();
    }

    public void createInfoView() throws SlickException {
        partyCommand = new WindowCommand(120,
                new String[]{"Fight",
                    "Escape"
                }, 1, 4);
        partyCommand.initX = -120;
        partyCommand.initY = SceneMap.B_HEIGHT - partyCommand.height;
        targetEnemy = new WindowSelectable(0, 0, 0, 0);
        stat = new WindowBattleStatus(120, SceneMap.B_HEIGHT - partyCommand.height, SceneMap.B_WIDTH - 120 - 8, partyCommand.height);
        actorCommand = new WindowCommand(120,
                new String[]{"Attack",
                    "Guard",
                    "Skill",
                    "Item"
                }, 1, 0);
        actorCommand.initX = 1000;
        actorCommand.initY = SceneMap.B_HEIGHT - actorCommand.height;
        actorCommand.index = -1;
        help = new WindowHelp();
        itemWindow = new WindowItem(0, help.height, SceneMap.B_WIDTH, SceneMap.B_HEIGHT - actorCommand.height - help.height, gameParty.getInv());
        itemWindow.setBattle();
        activeWindow = partyCommand;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.scale(zoom, zoom);
        g.translate(screenPos.x, screenPos.y);
        if (battleBack != null) {
            battleBack.draw(0, 0, SceneMap.B_WIDTH, SceneMap.B_HEIGHT, 0, 0, 580, 444);
        }
        //gameParty.getMembers().get(0).battleSprite.aSeq.play(768.0f, 273.0f);
        spriteset.render(g);
        partyCommand.render(g, sbg);
        actorCommand.render(g, sbg);
        stat.render(g, sbg);
        if(activeWindow == itemWindow){
            itemWindow.render(g, sbg);
            help.render(g, sbg);
        } else if(activeWindow == targetEnemy || activeWindow == stat){
            spriteset.cursor.render();
            help.render(g, sbg);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        
        if(DEBUG_UTIL){
            if(input.isKeyDown(Input.KEY_T)){
                screenPos.y -= 1f;
            }
            
            if(input.isKeyDown(Input.KEY_G)){
                screenPos.y += 1f;
            }
            
            if(input.isKeyDown(Input.KEY_F)){
                screenPos.x -= 1f;
            }
            
            if(input.isKeyDown(Input.KEY_H)){
                screenPos.x += 1f;
            }
            
            if(input.isKeyDown(Input.KEY_F) && input.isKeyDown(Input.KEY_T)){
                screenPos.y -= 1f; screenPos.x -= 1f;
            }
            
            if(input.isKeyDown(Input.KEY_H) && input.isKeyDown(Input.KEY_T)){
                screenPos.y -= 1f; screenPos.x += 1f;
            }
            
            if(input.isKeyDown(Input.KEY_F) && input.isKeyDown(Input.KEY_G)){
                screenPos.y += 1f; screenPos.x -= 1f;
            }
            
            if(input.isKeyDown(Input.KEY_H) && input.isKeyDown(Input.KEY_G)){
                screenPos.y += 1f; screenPos.x += 1f;
            }
            
            if (input.isKeyDown(Input.KEY_BACKSLASH)) {
                zoom /= 1.1;
            } else if(input.isKeyDown(Input.KEY_TAB)){
                zoom *= 1.1;
            }
        }

        spriteset.update(delta, input);
        if (!(victory || defeat)) {
            if (!playingAction) {
                if (judgeWinLoss()) {
                    return;
                }
                if (activeWindow != null) {
                    stat.initX = xOffset;
                    partyCommand.initX = xOffset - 120;
                    actorCommand.initX = stat.width + xOffset;
                    activeWindow.update(inputp, delta);
                }
                if (activeWindow == partyCommand) {
                    updatePartyCommandSelection(sbg);
                } else if (activeWindow == actorCommand) {
                    updateActorCommandSelection();
                } else if (activeWindow == targetEnemy) {
                    updateTargetEnemySelection();
                } else if (activeWindow == itemWindow) {
                    updateItemSelection();
                } else if (activeWindow == stat) {
                    updateTargetActorSelection();
                } else {
                    stat.initX = partyCommand.width / 2 + 4;
                    actorCommand.initX = SceneMap.B_WIDTH;
                    processAction();
                }
            } else {
                if (activeBattler.play != null) {
                    if (activeBattler.play[0].equals("End")) {
                        activeBattler.play = null;
                        if (activeBattler.isActor()) {
                            spriteset.setActive(activeBattler.isActor(), party.getMembers().indexOf(activeBattler), false);
                        } else {
                            spriteset.setActive(activeBattler.isActor(), gameTroop.getMembers().indexOf(activeBattler), false);
                        }
                        actionEnd();
                        playingAction = false;
                    } else if (activeBattler.play[0].equals("OBJ_ANIM")) {
                        damageAction(activeBattler.play[1]);
                    }
                }
            }
        } else if (victory) {
            if (inputp.isCommandControlDown(action) && inputp.isCommandControlPressed(action)) {
                GameCache.me(GameSystem.battleME).stop();
                battleEnd(0, sbg);
            }
        } else if (defeat) {
            
        }
    }
    
    public boolean judgeWinLoss(){
        if(party.allDead()){
            defeat = true;
            return true;
        } else if(gameTroop.allDead()){
            victory = true;
            BGM.fade(1500, 0.05f, true);
            BGM.addListener(new MusicListener(){ 

                @Override
                public void musicEnded(Music music) {
                    GameCache.me(GameSystem.battleME).play();
                    BGM.removeListener(this);
                }

                @Override
                public void musicSwapped(Music music, Music newMusic) {
                    
                }
            
            });
            for(GameActor a: party.getLivingMembers()){
                spriteset.setAction(true, party.getMembers().indexOf(a), "VICTORY");
            }
            return true;
        }
        return false;
    }
    
    public void actionEnd(){
        activeBattler.clearActionResults();
    }
    
    public void damageAction(Object action){
        activeBattler.play = null;
        if(activeBattler.action.isAttack()){
            GameBattler target = activeBattler.isActor() ? 
                    gameTroop.getMembers().get(activeBattler.action.targetIndex):
                    party.getMembers().get(activeBattler.action.targetIndex);
            target.attackEffect(activeBattler);
        }
    }
    
    public void startPartyCommandSelection(){
        party.clearActions();
        for(GameActor a: party.getMembers()){
            if(a.isInputable()){
                actorIndex = party.getMembers().indexOf(a);
                activeBattler = a;
                break;
            }
        }  
        partyCommand.index = 0;
        activeWindow = partyCommand;
    }
    
    public void updatePartyCommandSelection(StateBasedGame sbg){
        if (xOffset < 128) {
                xOffset += 16;
            }
            switch (partyCommand.index) {
                case 0://Fight
                    if (inputp.isCommandControlPressed(action)) {
                        Sounds.decision.play();
                        startActorCommandSelection();
                    }
                    break;
                case 1://Escape
                    if (inputp.isCommandControlPressed(action)) {
                        Sounds.decision.play();
                        BGM.fade(1500, 0.05f, true);
                        sbg.enterState(1, new FadeOutTransition(), new CameraFadeInTransition());
                    }
                    break;
            }
    }
    
    public void startActorCommandSelection(){
        actorCommand.index = 0;
        activeWindow = actorCommand;
        partyCommand.index = -1;
        spriteset.setTarget(true, party.getMembers().indexOf(activeBattler), null);
        spriteset.setAction(true, party.getMembers().indexOf(activeBattler), "COMMAND_INPUT");
    }
    
    public void updateActorCommandSelection(){
        if (xOffset > 0) {
                xOffset -= 16;
            }

            if (inputp.isCommandControlPressed(cancel)) {
                Sounds.cancel.play();
                if(!priorActor()){
                    startActorCommandSelection();
                } else {
                    actorCommand.index = -1;
                    spriteset.setTarget(true, party.getMembers().indexOf(activeBattler), null);
                    spriteset.setAction(true, party.getMembers().indexOf(activeBattler), "COMMAND_SELECT");
                    startPartyCommandSelection();
                }
            }
            switch (actorCommand.index) {
                case 0://Attack
                    if (inputp.isCommandControlPressed(action)) {
                        Sounds.decision.play();
                        activeBattler.action.setAttack();
                        startTargetEnemySelection();
                        actorCommand.index = -1;
                    }
                    break;
                case 3:
                    if (inputp.isCommandControlPressed(action)) {
                        Sounds.decision.play();
                        startItemSelection();
                        actorCommand.index = -1;
                    }
                    break;
            }
            clearPressedRecord(action);
    }
    
    public void startTargetEnemySelection(){
        activeWindow = targetEnemy;
    }
    
    public void updateTargetEnemySelection(){
        help.setBattleText(gameTroop.getMembers().get(targetEnemy.index));
        spriteset.setCursorTarget(false, targetEnemy.index);
        if(inputp.isCommandControlPressed(action)){
            Sounds.decision.play();
            activeBattler.action.targetIndex = targetEnemy.index;
            if(!nextActor()){
                startActorCommandSelection();
            } else {
                startMain();
            }
        } else if(inputp.isCommandControlPressed(cancel)){
            Sounds.cancel.play();
            activeWindow = actorCommand;
            actorCommand.index = 0;
        }
    }
    
    public void startTargetActorSelection(){
        activeWindow = stat;
        stat.index = 0;
        spriteset.setCursorTarget(true, 0);
    }
    
    public void updateTargetActorSelection(){
        spriteset.setCursorTarget(true, stat.index);
        help.setBattleText(party.getMembers().get(stat.index));
        if(inputp.isCommandControlPressed(action)){
            Sounds.decision.play();
            activeBattler.action.targetIndex = stat.index;
            stat.index = -1;
            if(!nextActor()){
                startActorCommandSelection();
            } else {
                startMain();
            }
        } else if(inputp.isCommandControlPressed(cancel)){
            Sounds.cancel.play();
            stat.index = -1;
            activeWindow = actorCommand;
            actorCommand.index = 0;
        }
    }
    
    public void startItemSelection(){        
        activeWindow = itemWindow;
    }
    
    public void updateItemSelection(){
        help.setText(itemWindow.getItem().getDesc());
        if(inputp.isCommandControlPressed(action)){
            Sounds.decision.play();
            activeBattler.action.setItem(GameData.items.indexOf(itemWindow.getItem()));
            if(((Consumable)itemWindow.getItem()).forAlly() || ((Consumable)itemWindow.getItem()).forDeadAlly()){
                startTargetActorSelection();
            } else if(((Consumable)itemWindow.getItem()).forEnemy()){
                startTargetEnemySelection();
            }
        } else if(inputp.isCommandControlPressed(cancel)){
            Sounds.cancel.play();
            activeWindow = actorCommand;
            actorCommand.index = 0;
        }
    }
    
    public boolean nextActor(){
        actorIndex++;
        if(actorIndex > gameParty.getMembers().size() - 1){
            actorIndex = 0;
            //Turn end
            return true;
        }
        if(!gameParty.getMembers().get(actorIndex).isInputable()){
            nextActor();
        }
        spriteset.setTarget(true, party.getMembers().indexOf(activeBattler), null);
        spriteset.setAction(true, party.getMembers().indexOf(activeBattler), "COMMAND_SELECT");
        activeBattler = gameParty.getMembers().get(actorIndex);
        return false;
    }
    
    public boolean priorActor(){
        actorIndex--;
        if(actorIndex < 0){
            //Return to party select.
            return true;
        }
        if(!gameParty.getMembers().get(actorIndex).isInputable()){
           priorActor();
        }
        if(actorIndex < 0){
            //Return to party select.
            return true;
        }
        spriteset.setTarget(true, party.getMembers().indexOf(activeBattler), null);
        spriteset.setAction(true, party.getMembers().indexOf(activeBattler), "COMMAND_SELECT");
        activeBattler = gameParty.getMembers().get(actorIndex);
        return false;
    }
    
    public void startMain(){
        gameTroop.makeActions();
        makeActionOrders();
        activeWindow = null;
    }
    
    public void makeActionOrders(){
        actionBattlers.clear();
        actionBattlers.addAll(party.getMembers());
        actionBattlers.addAll(gameTroop.getMembers());
        Collections.sort(actionBattlers, new SpeedComparator());
    }
    
    public void processAction(){
        if(judgeWinLoss()){
            return;
        }
        if(setNextActiveBattler()){
            if(activeBattler.action.isValid()){
                executeAction();
            }
        }
    }
    
    public boolean setNextActiveBattler(){
        activeBattler = actionBattlers.poll();
        if(activeBattler == null){
            turnEnd();
            return false;
        }
        return true;
    }
    
    public void turnEnd(){
        startPartyCommandSelection();
    }
    
    public void executeAction() {
        if (activeBattler.action.isAttack()) {
            executeAttack();
        } else if (activeBattler.action.isGuard()) {
            
        } else if (activeBattler.action.isSkill()) {
            
        } else if (activeBattler.action.isItem()) {
            
        }
    }
    
    public void executeAttack(){
        playingAction = true;
        if(activeBattler.isActor()){
            spriteset.setActive(activeBattler.isActor(), party.getMembers().indexOf(activeBattler), true);
            spriteset.setTarget(activeBattler.isActor(), party.getMembers().indexOf(activeBattler), activeBattler.action.targetIndex);
            spriteset.setAction(activeBattler.isActor(), party.getMembers().indexOf(activeBattler), "NORMAL_ATTACK");
        } else {
            spriteset.setActive(activeBattler.isActor(), gameTroop.getMembers().indexOf(activeBattler), true);
            spriteset.setTarget(activeBattler.isActor(), gameTroop.getMembers().indexOf(activeBattler), activeBattler.action.targetIndex);
            spriteset.setAction(activeBattler.isActor(), gameTroop.getMembers().indexOf(activeBattler), "NORMAL_ATTACK");
        }
    }
    
    public void battleEnd(int result, StateBasedGame sbg){
        sbg.enterState(1, new FadeOutTransition(), new CameraFadeInTransition());
    }

    public static Music getBGM() {
        return BGM;
    }

    public static void setBGM(String music) {
        BGM = GameCache.bgm(music);
    }

    public void setBattleBack(Image b) {
        battleBack = b;
    }

    public Image getBattleBack() {
        return battleBack;
    }
    
    private class SpeedComparator implements Comparator<GameBattler> {
        @Override
        public int compare(GameBattler b1, GameBattler b2) {
            return b2.getMaxSPD() - b1.getMaxSPD();
        }
    }
}
