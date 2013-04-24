/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import effectutil.CameraFadeInTransition;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 *
 * @author Kieran
 */
public class SceneBattle extends SceneBase {

    public static final boolean DEBUG_UTIL = false;
    private float zoom = 1.0f;
    private float rot;
    private Vector2f screenPos = new Vector2f(0, 0);
    private boolean playingAction = false;
    private int stateID = -1;
    private static Image battleBack;
    private static Image battleOverlay;
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
    
    private static enum BattleState{
        PARTY_COMMAND, ACTOR_COMMAND, TARGET_ENEMY, TARGET_ACTOR, ITEM_SELECT, 
        MAIN, ACTION_WAIT, END
    }
    
    private static final BattleState PARTY_COMMAND = BattleState.PARTY_COMMAND;
    private static final BattleState ACTOR_COMMAND = BattleState.ACTOR_COMMAND;
    private static final BattleState TARGET_ENEMY = BattleState.TARGET_ENEMY;
    private static final BattleState TARGET_ACTOR = BattleState.TARGET_ACTOR;
    private static final BattleState ITEM_SELECT = BattleState.ITEM_SELECT;
    private static final BattleState MAIN = BattleState.MAIN;
    private static final BattleState ACTION_WAIT = BattleState.ACTION_WAIT;
    private static final BattleState END = BattleState.END;
    
    private BattleState mode;
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
      for(GameEnemy e:gameTroop.getMembers()){
        e.currentHP = e.getMaxHP();
      }
      spriteset = new SpritesetBattle();
      actionBattlers.clear();
      setBGM("Earthquake.ogg");
      BGM.loop();
      startPartyCommandSelection();
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
            battleBack.draw(0, 0, SceneMap.B_WIDTH, SceneMap.B_HEIGHT, 0, 0, battleBack.getWidth(), battleBack.getHeight());
        }
        if (battleOverlay != null) {
            battleOverlay.draw(0, 0, SceneMap.B_WIDTH, SceneMap.B_HEIGHT, 0, 0, battleOverlay.getWidth(), battleOverlay.getHeight());
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
        
        SlickCallable callable = new SlickCallable() {
			protected void performGLOperations() throws SlickException {
				renderGL();
			}
		};
		callable.call();
    }
    
    public void renderGL() {		
		FloatBuffer pos = BufferUtils.createFloatBuffer(4);
		pos.put(new float[] { 5.0f, 5.0f, 10.0f, 0.0f}).flip();
		FloatBuffer red = BufferUtils.createFloatBuffer(4);
		red.put(new float[] { 0.8f, 0.1f, 0.0f, 1.0f}).flip();
	
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, pos);
		GL11.glEnable(GL11.GL_LIGHT0);

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		// define the properties for the perspective of the scene
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();		
		GLU.gluPerspective(45.0f, ((float) 1280) / ((float) 720), 0.1f, 100.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST); 
		GL11.glTranslatef(0.0f, 0.0f, -40.0f);	
		GL11.glRotatef(rot,0,1,1);		
		
		GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, red);
		gear(0.5f, 2.0f, 2.0f, 10, 0.7f);
	}
    
        private void gear(float inner_radius, float outer_radius, float width, int teeth, float tooth_depth) {
		int i;
		float r0, r1, r2;
		float angle, da;
		float u, v, len;

		r0 = inner_radius;
		r1 = outer_radius - tooth_depth / 2.0f;
		r2 = outer_radius + tooth_depth / 2.0f;

		da = 2.0f * (float) Math.PI / teeth / 4.0f;

		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glNormal3f(0.0f, 0.0f, 1.0f);

		/* draw front face */
		GL11.glBegin(GL11.GL_QUAD_STRIP);
		for (i = 0; i <= teeth; i++) {
			angle = i * 2.0f * (float) Math.PI / teeth;
			GL11.glVertex3f(r0 * (float) Math.cos(angle), r0 * (float) Math.sin(angle), width * 0.5f);
			GL11.glVertex3f(r1 * (float) Math.cos(angle), r1 * (float) Math.sin(angle), width * 0.5f);
			if (i < teeth) {
				GL11.glVertex3f(r0 * (float) Math.cos(angle), r0 * (float) Math.sin(angle), width * 0.5f);
				GL11.glVertex3f(r1 * (float) Math.cos(angle + 3.0f * da), r1 * (float) Math.sin(angle + 3.0f * da),
												width * 0.5f);
			}
		}
		GL11.glEnd();

		/* draw front sides of teeth */
		GL11.glBegin(GL11.GL_QUADS);
		for (i = 0; i < teeth; i++) {
			angle = i * 2.0f * (float) Math.PI / teeth;
			GL11.glVertex3f(r1 * (float) Math.cos(angle), r1 * (float) Math.sin(angle), width * 0.5f);
			GL11.glVertex3f(r2 * (float) Math.cos(angle + da), r2 * (float) Math.sin(angle + da), width * 0.5f);
			GL11.glVertex3f(r2 * (float) Math.cos(angle + 2.0f * da), r2 * (float) Math.sin(angle + 2.0f * da), width * 0.5f);
			GL11.glVertex3f(r1 * (float) Math.cos(angle + 3.0f * da), r1 * (float) Math.sin(angle + 3.0f * da), width * 0.5f);
		}
		GL11.glEnd();

		/* draw back face */
		GL11.glNormal3f(0.0f, 0.0f, -1.0f);
		GL11.glBegin(GL11.GL_QUAD_STRIP);
		for (i = 0; i <= teeth; i++) {
			angle = i * 2.0f * (float) Math.PI / teeth;
			GL11.glVertex3f(r1 * (float) Math.cos(angle), r1 * (float) Math.sin(angle), -width * 0.5f);
			GL11.glVertex3f(r0 * (float) Math.cos(angle), r0 * (float) Math.sin(angle), -width * 0.5f);
			GL11.glVertex3f(r1 * (float) Math.cos(angle + 3 * da), r1 * (float) Math.sin(angle + 3 * da), -width * 0.5f);
			GL11.glVertex3f(r0 * (float) Math.cos(angle), r0 * (float) Math.sin(angle), -width * 0.5f);
		}
		GL11.glEnd();

		/* draw back sides of teeth */
		GL11.glBegin(GL11.GL_QUADS);
		for (i = 0; i < teeth; i++) {
			angle = i * 2.0f * (float) Math.PI / teeth;
			GL11.glVertex3f(r1 * (float) Math.cos(angle + 3 * da), r1 * (float) Math.sin(angle + 3 * da), -width * 0.5f);
			GL11.glVertex3f(r2 * (float) Math.cos(angle + 2 * da), r2 * (float) Math.sin(angle + 2 * da), -width * 0.5f);
			GL11.glVertex3f(r2 * (float) Math.cos(angle + da), r2 * (float) Math.sin(angle + da), -width * 0.5f);
			GL11.glVertex3f(r1 * (float) Math.cos(angle), r1 * (float) Math.sin(angle), -width * 0.5f);
		}
		GL11.glEnd();
		GL11.glNormal3f(0.0f, 0.0f, 1.0f);

		/* draw outward faces of teeth */
		GL11.glBegin(GL11.GL_QUAD_STRIP);
		for (i = 0; i < teeth; i++) {
			angle = i * 2.0f * (float) Math.PI / teeth;
			GL11.glVertex3f(r1 * (float) Math.cos(angle), r1 * (float) Math.sin(angle), width * 0.5f);
			GL11.glVertex3f(r1 * (float) Math.cos(angle), r1 * (float) Math.sin(angle), -width * 0.5f);
			u = r2 * (float) Math.cos(angle + da) - r1 * (float) Math.cos(angle);
			v = r2 * (float) Math.sin(angle + da) - r1 * (float) Math.sin(angle);
			len = (float) Math.sqrt(u * u + v * v);
			u /= len;
			v /= len;
			GL11.glNormal3f(v, -u, 0.0f);
			GL11.glVertex3f(r2 * (float) Math.cos(angle + da), r2 * (float) Math.sin(angle + da), width * 0.5f);
			GL11.glVertex3f(r2 * (float) Math.cos(angle + da), r2 * (float) Math.sin(angle + da), -width * 0.5f);
			GL11.glNormal3f((float) Math.cos(angle), (float) Math.sin(angle), 0.0f);
			GL11.glVertex3f(r2 * (float) Math.cos(angle + 2 * da), r2 * (float) Math.sin(angle + 2 * da), width * 0.5f);
			GL11.glVertex3f(r2 * (float) Math.cos(angle + 2 * da), r2 * (float) Math.sin(angle + 2 * da), -width * 0.5f);
			u = r1 * (float) Math.cos(angle + 3 * da) - r2 * (float) Math.cos(angle + 2 * da);
			v = r1 * (float) Math.sin(angle + 3 * da) - r2 * (float) Math.sin(angle + 2 * da);
			GL11.glNormal3f(v, -u, 0.0f);
			GL11.glVertex3f(r1 * (float) Math.cos(angle + 3 * da), r1 * (float) Math.sin(angle + 3 * da), width * 0.5f);
			GL11.glVertex3f(r1 * (float) Math.cos(angle + 3 * da), r1 * (float) Math.sin(angle + 3 * da), -width * 0.5f);
			GL11.glNormal3f((float) Math.cos(angle), (float) Math.sin(angle), 0.0f);
		}
		GL11.glVertex3f(r1 * (float) Math.cos(0), r1 * (float) Math.sin(0), width * 0.5f);
		GL11.glVertex3f(r1 * (float) Math.cos(0), r1 * (float) Math.sin(0), -width * 0.5f);
		GL11.glEnd();

		GL11.glShadeModel(GL11.GL_SMOOTH);

		/* draw inside radius cylinder */
		GL11.glBegin(GL11.GL_QUAD_STRIP);
		for (i = 0; i <= teeth; i++) {
			angle = i * 2.0f * (float) Math.PI / teeth;
			GL11.glNormal3f(-(float) Math.cos(angle), -(float) Math.sin(angle), 0.0f);
			GL11.glVertex3f(r0 * (float) Math.cos(angle), r0 * (float) Math.sin(angle), -width * 0.5f);
			GL11.glVertex3f(r0 * (float) Math.cos(angle), r0 * (float) Math.sin(angle), width * 0.5f);
		}
		GL11.glEnd();
	}

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        
        rot += delta * 0.1f;
        
        if(DEBUG_UTIL){
            
            int zoomaxis = 4;
            int X = 3;
            int Y = 2;
            
            if (input.getAxisCount(0) >= 4) {
                
                
                
                if(Math.abs(input.getAxisValue(0, X)) > 0.2 && Math.abs(input.getAxisValue(0, Y)) > 0.2){
                    screenPos.x -= input.getAxisValue(0, X) * 5f;
                    screenPos.y -= input.getAxisValue(0, Y) * 5f;
                } else {

                    if (Math.abs(input.getAxisValue(0, X)) > 0.2) {
                        screenPos.x -= input.getAxisValue(0, X) * 5f;
                    }

                    if (Math.abs(input.getAxisValue(0, Y)) > 0.2) {
                        screenPos.y -= input.getAxisValue(0, Y) * 5f;
                    }
                }
                
                if(input.isButtonPressed(5, 0)){
                    zoom = 1f;
                    screenPos.x = 0;
                    screenPos.y = 0;
                }

                if (input.getAxisValue(0, zoomaxis) > 0.2) {
                    zoom -= input.getAxisValue(0, zoomaxis) / 60f;
                }
                if (input.getAxisValue(0, zoomaxis) < -0.2) {
                    zoom += Math.abs(input.getAxisValue(0, zoomaxis)) / 60f;
                }
            }
        }

        if (mode != END) {
            if (mode != ACTION_WAIT) {
                if (judgeWinLoss()) {
                    return;
                }
                if (activeWindow != null) {
                    stat.initX = xOffset;
                    partyCommand.initX = xOffset - 120;
                    actorCommand.initX = stat.width + xOffset;
                    activeWindow.update(inputp, delta);
                }
                if (mode == PARTY_COMMAND) {
                    updatePartyCommandSelection(sbg);
                } else if (mode == ACTOR_COMMAND) {
                    updateActorCommandSelection();
                } else if (mode == TARGET_ENEMY) {
                    updateTargetEnemySelection();
                } else if (mode == ITEM_SELECT) {
                    updateItemSelection();
                } else if (mode == TARGET_ACTOR) {
                    updateTargetActorSelection();
                } else if(mode == MAIN){
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
                        mode = MAIN;
                        actionEnd();
                    } else if (activeBattler.play[0].equals("OBJ_ANIM")) {
                        Object[] temp = Arrays.copyOfRange(activeBattler.play, 1, activeBattler.play.length);
                        damageAction(temp);
                    } else if (activeBattler.play[0].equals("Can Collapse")){
                        doDeaths();
                        activeBattler.play = null;
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
        spriteset.update(delta, input);
    }
    
    public boolean judgeWinLoss(){
        if(party.allDead()){
            mode = END;
            defeat = true;
            return true;
        } else if(gameTroop.allDead()){
            mode = END;
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
    
    public void doDeaths(){
        GameUnit unit = activeBattler.action.getEnemyUnit();
        GameBattler b = (GameBattler)unit.getMembers().get(activeBattler.action.targetIndex);
        if(b.isDead()){
            b.performCollapse();
        }
    }
    
    public void actionEnd(){
        GameUnit group = activeBattler.action.getAllyUnit();
        spriteset.setTarget(activeBattler.isActor(), group.getMembers().indexOf(activeBattler), null);
        activeBattler.clearActionResults();
    }
    
    public void damageAction(Object[] action){
        activeBattler.play = null;
        if(activeBattler.action.isAttack()){
            int index = activeBattler.action.targetIndex;
            GameBattler target = (GameBattler)activeBattler.action.getEnemyUnit().getMembers().get(index);
            target.attackEffect(activeBattler);
            spriteset.setDamageAction(target.isActor(), index, action);//TODO: Change to popDamage()
            
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
        mode = PARTY_COMMAND;
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
                        final StateBasedGame game = sbg;
                        mode = END;
                        BGM.addListener(new MusicListener() {
                            @Override
                            public void musicEnded(Music music) {
                                BGM.removeListener(this);
                                battleEnd(2, game);
                            }

                            @Override
                            public void musicSwapped(Music music, Music newMusic) {
                            }
                        });
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
        mode = ACTOR_COMMAND;
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
        targetEnemy.itemMax = gameTroop.getLivingMembers().size();
        activeWindow = targetEnemy;
        mode = TARGET_ENEMY;
    }
    
    public void updateTargetEnemySelection(){
        if(targetEnemy.index > targetEnemy.itemMax - 1){
            targetEnemy.index = targetEnemy.itemMax - 1;
        }
        int index = gameTroop.getMembers().indexOf(gameTroop.getLivingMembers().get(targetEnemy.index));
        spriteset.setCursorTarget(false, index);
        help.setBattleText(gameTroop.getMembers().get(index));
        if(inputp.isCommandControlPressed(action)){
            Sounds.decision.play();
            activeBattler.action.targetIndex = index;
            if(!nextActor()){
                startActorCommandSelection();
            } else {
                startMain();
            }
        } else if(inputp.isCommandControlPressed(cancel)){
            Sounds.cancel.play();
            activeBattler.action.clear();
            activeWindow = actorCommand;
            mode = ACTOR_COMMAND;
            actorCommand.index = 0;
        }
    }
    
    public void startTargetActorSelection(){
        activeWindow = stat;
        stat.index = 0;
        spriteset.setCursorTarget(true, 0);
        mode = TARGET_ACTOR;
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
            mode = ACTOR_COMMAND;
            actorCommand.index = 0;
        }
    }
    
    public void startItemSelection(){        
        activeWindow = itemWindow;
        mode = ITEM_SELECT;
    }
    
    public void updateItemSelection(){
        help.setText(itemWindow.getItem().getDesc());
        if(inputp.isCommandControlPressed(action) && inputp.isCommandControlDown(action)){
            Sounds.decision.play();
            activeBattler.action.setItem(GameData.items.indexOf(itemWindow.getItem()));
            if(((Consumable)itemWindow.getItem()).forAlly() || ((Consumable)itemWindow.getItem()).forDeadAlly()){
                startTargetActorSelection();
            } else if(((Consumable)itemWindow.getItem()).forEnemy()){
                startTargetEnemySelection();
            }
        } else if(inputp.isCommandControlPressed(cancel)){
            Sounds.cancel.play();
            activeBattler.action.clear();
            activeWindow = actorCommand;
            mode = ACTOR_COMMAND;
            actorCommand.index = 0;
        }
    }
    
    public boolean nextActor(){
        actorIndex++;
        if(actorIndex > gameParty.getMembers().indexOf(gameParty.getLivingMembers().get(
            gameParty.getLivingMembers().size() - 1))){
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
        mode = MAIN;
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
                activeBattler.whiteFlash = true;
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
        mode = ACTION_WAIT;
        if(((GameBattler)activeBattler.action.getEnemyUnit().getMembers().get(activeBattler.action.targetIndex)).isDead()){
            activeBattler.action.targetIndex = activeBattler.action.getEnemyUnit().getMembers().indexOf(activeBattler.action.getEnemyUnit().getRandomTarget());
        }
        spriteset.setActive(activeBattler.isActor(), activeBattler.action.getAllyUnit().getMembers().indexOf(activeBattler), true);
        spriteset.setTarget(activeBattler.isActor(), activeBattler.action.getAllyUnit().getMembers().indexOf(activeBattler), activeBattler.action.targetIndex);
        spriteset.setAction(activeBattler.isActor(), activeBattler.action.getAllyUnit().getMembers().indexOf(activeBattler), "NORMAL_ATTACK");
    }
    
    public void battleEnd(int result, StateBasedGame sbg){
        sbg.enterState(1, new FadeOutTransition(), new CameraFadeInTransition(SceneMap.getCamera()));
    }
    
    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        super.leave(container, game);
        BGM.stop();
        BGM = null;
        SceneMap.map.BGM.loop();
        battleBack = null;
        
    }

    public static Music getBGM() {
        return BGM;
    }

    public static void setBGM(String music) {
        BGM = GameCache.bgm(music);
    }

    public static Image getBattleOverlay() {
        return battleOverlay;
    }

    public static void setBattleOverlay(Image battleOverlay) {
        SceneBattle.battleOverlay = battleOverlay;
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
