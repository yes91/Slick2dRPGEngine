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
    private WindowCommand activeWindow;
    private WindowBattleStatus stat;
    private List<GameActor> drawList = new ArrayList<>();
    private DepthBuffCompare comparator = new DepthBuffCompare();
    private final int DEPTH_BUFFER_SIZE = 360;
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
        BGM = null;
        createInfoView();
    }
    
    @Override
    public void enter(GameContainer container, StateBasedGame game)
         throws SlickException {
      super.enter(container, game);
      drawList.clear();
      drawList.addAll(party.getMembers());
      int index = 0;
      for(GameActor ga: drawList){
          ga.x = position[index][0];
          ga.y = position[index][1];
          ga.z = position[index][2];
          index++;
      }
    }

    public void createInfoView() throws SlickException {
        partyCommand = new WindowCommand(120,
                new String[]{"Fight",
                    "Escape"
                }, 1, 4);
        partyCommand.initX = -120;
        partyCommand.initY = 720 - partyCommand.height;
        stat = new WindowBattleStatus(120, 720 - partyCommand.height, 1280 - 120 - 8, partyCommand.height);
        actorCommand = new WindowCommand(120,
                new String[]{"Attack",
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
        if (battleBack != null) {
            battleBack.draw(0, 0, 1280, 720, 0, 0, 580, 444);
        }
        Collections.sort(drawList, comparator);
        //gameParty.getMembers().get(0).battleSprite.aSeq.play(768.0f, 273.0f);
        for (GameActor ga : drawList) {
            ga.battleSprite.updateAnimationState();
            ga.render(g, ga.x, ga.y, 1.0f + 1.0f * (ga.z / DEPTH_BUFFER_SIZE));
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
        if (BGM != null) {
            if (!BGM.playing()) {
                BGM.loop();
            }
        }

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
            if (input.isKeyDown(Input.KEY_UP)) {
                party.getMembers().get(activePos).y -= 1f;
            } else if (input.isKeyDown(Input.KEY_DOWN)) {
                party.getMembers().get(activePos).y += 1f;
            } else if (input.isKeyDown(Input.KEY_RIGHT)) {
                party.getMembers().get(activePos).x += 1f;
            } else if (input.isKeyDown(Input.KEY_LEFT)) {
                party.getMembers().get(activePos).x -= 1f;
            } else if (input.isKeyDown(Input.KEY_Z)) {
                party.getMembers().get(activePos).z -= 1f;
            } else if (input.isKeyDown(Input.KEY_X)) {
                party.getMembers().get(activePos).z += 1f;
            }
        }
        stat.initX = xOffset;
        partyCommand.initX = xOffset - 120;
        actorCommand.initX = stat.width + xOffset;
        activeWindow.update(inputp, delta);
        if (activeWindow.equals(partyCommand)) {
            if (xOffset < 128) {
                xOffset += 16;
            }
            switch (partyCommand.index) {
                case 0:
                    if (inputp.isCommandControlPressed(action)) {
                        actorCommand.index = 0;
                        activeWindow = actorCommand;
                        partyCommand.index = -1;
                    }
                    break;
                case 1:
                    if (inputp.isCommandControlPressed(action)) {
                        sbg.enterState(1, new FadeOutTransition(), new CameraFadeInTransition());
                    }
                    break;
            }
        } else if (activeWindow.equals(actorCommand)) {
            if (xOffset > 0) {
                xOffset -= 16;
            }

            if (inputp.isCommandControlPressed(cancel)) {
                actorCommand.index = -1;
                partyCommand.index = 0;
                gameParty.getMembers().get(0).currentHP = gameParty.getMembers().get(0).getMaxHP();
                activeWindow = partyCommand;
            }
            switch (actorCommand.index) {
                case 0:
                    if (inputp.isCommandControlPressed(action)) {
                        gameParty.getMembers().get(0).currentHP -= 50;
                        if (gameParty.getMembers().get(0).currentHP <= 0) {
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

    public void setBattleBack(Image b) {
        battleBack = b;
    }

    public Image getBattleBack() {
        return battleBack;
    }
}
