/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import effectutil.CameraFadeInTransition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Kieran
 */
public class SceneBattle extends SceneBase {

    public static final boolean DEBUG_UTIL = true;
    private int stateID = -1;
    private static Image battleBack;
    private static Music BGM;
    private int xOffset = 0;
    private WindowCommand partyCommand;
    private WindowCommand actorCommand;
    private WindowItem itemWindow;
    private WindowHelp help;
    private WindowSelectable activeWindow;
    private WindowSelectable targetEnemy; 
    private WindowBattleStatus stat;
    private Cursor cursor;
    private List<GameBattler> drawList = new ArrayList<>();
    private List<GameActor> actionBattlers = new ArrayList<>();
    private int actorIndex;
    private GameBattler activeBattler;
    private GameEnemy testEnemy;
    private DepthBuffCompare comparator = new DepthBuffCompare();
    private final int DEPTH_BUFFER_SIZE = SceneMap.B_HEIGHT/2;
    private int activePos = 0;
    private static float[][] position = new float[][]{
        new float[]{768.0f, 273.0f, 74.0f},
        new float[]{865.0f, 327.0f, 177.0f},
        new float[]{983.0f, 401.0f, 272.0f},
        new float[]{1108.0f, 486.0f, 388.0f}
    };
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
        testEnemy = new GameEnemy();
        BGM = null;
        createInfoView();
    }
    
    @Override
    public void enter(GameContainer container, StateBasedGame game)
         throws SlickException {
      super.enter(container, game);
      cursor = new Cursor();
      actorIndex = 0;
      activeBattler = party.getMembers().get(actorIndex);
      drawList.clear();
      drawList.addAll(party.getMembers());
      testEnemy.basePosX = 200;
      testEnemy.basePosY = 327;
      testEnemy.basePosZ = 180;
      drawList.add(testEnemy);
      BGM = GameCache.bgm("Trial_By_Fire.ogg");
      int index = 0;
      for(GameActor ga: party.getMembers()){
          ga.basePosX = position[index][0];
          ga.basePosY = position[index][1];
          ga.basePosZ = position[index][2];
          index++;
      }
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
        if (battleBack != null) {
            battleBack.draw(0, 0, SceneMap.B_WIDTH, SceneMap.B_HEIGHT, 0, 0, 580, 444);
        }
        Collections.sort(drawList, comparator);
        //gameParty.getMembers().get(0).battleSprite.aSeq.play(768.0f, 273.0f);
        for (GameBattler ga : drawList) {
            ga.battleSprite.updateAnimationState();
            ga.render(g, ga.posX(), ga.posY(), 1.0f + (ga.posZ() / DEPTH_BUFFER_SIZE));
        }
        /*for(int i = 0; i < gameParty.getMembers().size(); i++){
         gameParty.getMembers().get(i).battleSprite.getCurrentAni().draw(
         (!(i < 4) ? 800:700) + 100 * (!(i < 4) ? i - 4:i), (!(i < 4) ? 200:300) + 100 * (!(i < 4) ? i - 4:i));
         }*/
        partyCommand.render(g, sbg);
        actorCommand.render(g, sbg);
        stat.render(g, sbg);
        if(activeWindow == itemWindow){
            itemWindow.render(g, sbg);
            help.render(g, sbg);
        } else if(activeWindow == null){
            cursor.render();
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

        if (DEBUG_UTIL) {
            gameParty.getMembers().get(activePos).debugUpdate(input);
            if (input.isKeyPressed(Input.KEY_1)) {
                activePos = 0;
            }
            if (input.isKeyPressed(Input.KEY_2)) {
                activePos = 1;
            }
            if (input.isKeyPressed(Input.KEY_3)) {
                activePos = 2;
            }
            if (input.isKeyPressed(Input.KEY_4)) {
                activePos = 3;
            }
            if (input.isKeyPressed(Input.KEY_ENTER)) {
                System.out.println(Arrays.deepToString(position));
            }
            if(input.isKeyPressed(Input.KEY_BACKSLASH)){
                gameParty.getMembers().get(activePos).battleSprite.toPoint(testEnemy.posX() + 50, testEnemy.posY(), testEnemy.posZ() + 5);
            } else if(input.isKeyPressed(Input.KEY_TAB)){
                gameParty.getMembers().get(activePos).battleSprite.retreat();
            }
            if (input.isKeyDown(Input.KEY_UP)) {
                party.getMembers().get(activePos).moveY -= 1f;
            } else if (input.isKeyDown(Input.KEY_DOWN)) {
                party.getMembers().get(activePos).moveY += 1f;
            } else if (input.isKeyDown(Input.KEY_RIGHT)) {
                party.getMembers().get(activePos).moveX += 1f;
            } else if (input.isKeyDown(Input.KEY_LEFT)) {
                party.getMembers().get(activePos).moveX -= 1f;
            } else if (input.isKeyDown(Input.KEY_Z)) {
                party.getMembers().get(activePos).moveZ -= 1f;
            } else if (input.isKeyDown(Input.KEY_X)) {
                party.getMembers().get(activePos).moveZ += 1f;
            }
        }
        stat.initX = xOffset;
        partyCommand.initX = xOffset - 120;
        actorCommand.initX = stat.width + xOffset;
        if(activeWindow != null){
            activeWindow.update(inputp, delta);
        }
        if (activeWindow == partyCommand) {
            updatePartyCommandSelection(sbg);
        } else if (activeWindow == actorCommand) {
            updateActorCommandSelection();
        } else if(activeWindow == null){
            updateTargetEnemySelection();
        } else if(activeWindow == itemWindow){
            updateItemSelection();
        }
    }
    
    public void updatePartyCommandSelection(StateBasedGame sbg){
        if (xOffset < 128) {
                xOffset += 16;
            }
            switch (partyCommand.index) {
                case 0://Fight
                    if (inputp.isCommandControlPressed(action)) {
                        startActorCommandSelection();
                    }
                    break;
                case 1://Escape
                    if (inputp.isCommandControlPressed(action)) {
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
        activeBattler.battleSprite.moveAmount(50, 0, 0);
    }
    
    public void updateActorCommandSelection(){
        if (xOffset > 0) {
                xOffset -= 16;
            }

            if (inputp.isCommandControlPressed(cancel)) {
                actorCommand.index = -1;
                partyCommand.index = 0;
                activeWindow = partyCommand;
                activeBattler.battleSprite.retreat();
            }
            switch (actorCommand.index) {
                case 0://Attack
                    if (inputp.isCommandControlPressed(action)) {
                        activeBattler.action.setAttack();
                        startTargetEnemySelection();
                    }
                    break;
                case 3:
                    if (inputp.isCommandControlPressed(action)) {
                        startItemSelection();
                        activeBattler.action.setItem(GameData.items.indexOf(itemWindow.getItem()));
                        if(itemWindow.getItem().forAlly()){
                            startTargetActorSelection();
                        } else if(itemWindow.getItem().forEnemy()){
                            startTargetEnemySelection();
                        }
                    }
                    break;
            }
            clearPressedRecord(action);
    }
    
    public void startTargetEnemySelection(){
        activeWindow = null;
        cursor.setTarget(testEnemy);
    }
    
    public void updateTargetEnemySelection(){
        if(inputp.isCommandControlPressed(action)){
            activeBattler.action.targetIndex = 0;
            if(!nextActor()){
                activeWindow = actorCommand;
            } else {
                
            }
        } else if(inputp.isCommandControlPressed(cancel)){
            activeWindow = actorCommand;
        }
    }
    
    public void startTargetActorSelection(){
        
    }
    
    public void updateTargetActorSelection(){
        
    }
    
    public void startItemSelection(){        
        activeWindow = itemWindow;
    }
    
    public void updateItemSelection(){
        help.setText(itemWindow.getItem().getDesc());
        
    }
    
    public boolean nextActor(){
        activeBattler.battleSprite.retreat();
        actorIndex++;
        if(actorIndex > gameParty.getMembers().size() - 1){
            actorIndex = 0;
            //Turn end
            return true;
        }
        activeBattler = gameParty.getMembers().get(actorIndex);
        activeBattler.battleSprite.moveAmount(50, 0, 0);
        return false;
    }
    
    public void executeAction(){
        if(activeBattler.action.isAttack()){
            executeAttack();
        } else if(activeBattler.action.isGuard()){
            
        } else if(activeBattler.action.isSkill()){
            
        } else if(activeBattler.action.isItem()){
            
        }
    }
    
    public void executeAttack(){
        testEnemy.attackEffect(activeBattler);
    }
    
    public void playAction(){
        while(true){
            
        }
    }

    public static Music getBGM() {
        return BGM;
    }

    public static void setBGM(String music) {
        BGM = Sounds.getMusic(music);
    }

    public void setBattleBack(Image b) {
        battleBack = b;
    }

    public Image getBattleBack() {
        return battleBack;
    }
    
    private class Cursor{
        public float x, y;
        private int yOffset;
        private Animation anim;
        
        public Cursor(){
            x = 0;
            y = 0;
            yOffset = 0;
            Image sheet = GameCache.system("cursor.png");
            SpriteSheet s = new SpriteSheet(sheet, sheet.getWidth(), sheet.getHeight()/2);
            anim = new Animation(s, 0, 0, 0, 1, true, 350, true);
        }
        
        public void render(){
            anim.draw(x - 32, y - 32 - yOffset);
        }
        
        public void setTarget(GameBattler b){
            yOffset = b.battleSprite.image.getHeight()/b.battleSprite.image.getVerticalCount();
            x = b.posX();
            y = b.posY();
        }
        
    }
}
