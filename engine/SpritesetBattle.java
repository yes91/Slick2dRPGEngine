/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Kieran
 */
public class SpritesetBattle {
    
    private static float[][] position = new float[][]{
        new float[]{875.0f, 285.0f + 50, 141.0f},
        new float[]{967.0f, 317.0f + 50, 167.0f},
        new float[]{1087.0f, 371.0f + 50, 231.0f},
        new float[]{1153.0f, 451.0f + 50, 288.0f}
    };
    
    private static float[][] enemyPos = new float[][]{
        new float[]{1280-875.0f, 285.0f + 50, 141.0f},
        new float[]{1280-967.0f, 317.0f + 50, 167.0f},
        new float[]{1280-1087.0f, 371.0f + 50, 231.0f},
        new float[]{1280-1153.0f, 451.0f + 50, 288.0f}
    };
    
    public Cursor cursor;
    private DepthBuffCompare comparator = new DepthBuffCompare();
    public static final int DEPTH_BUFFER_SIZE = 360;
    private List<SpriteBattler> enemySprites;
    private List<SpriteBattler> actorSprites;
    
    public List<SpriteBattler> drawList;
    private int activePos;
    
    public SpritesetBattle(){
        cursor = new Cursor();
        drawList = new ArrayList<>();
        enemySprites = new ArrayList<>();
        actorSprites = new ArrayList<>();
        createActors();
        createEnemies();
        int index = 0;
        for(SpriteBattler a: actorSprites){
          a.basePosX = position[index][0];
          a.basePosY = position[index][1] - (((float)a.image.getHeight()/a.image.getVerticalCount())*0.7f)/2f;
          a.basePosZ = position[index][2];
          index++;
        }
        index = 0;
        for(SpriteBattler a: enemySprites){
          a.basePosX = enemyPos[index][0];
          a.basePosY = enemyPos[index][1] - (((float)a.image.getHeight()/a.image.getVerticalCount())*0.7f)/2f;
          a.basePosZ = enemyPos[index][2];
          index++;
      }
    }
    
    public void debugUpdate(Input input){
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
                position[activePos][1] -= 1f;
            } else if (input.isKeyDown(Input.KEY_DOWN)) {
                position[activePos][1] += 1f;
            } else if (input.isKeyDown(Input.KEY_RIGHT)) {
                position[activePos][0] += 1f;
            } else if (input.isKeyDown(Input.KEY_LEFT)) {
                position[activePos][0] -= 1f;
            } else if (input.isKeyDown(Input.KEY_Z)) {
                position[activePos][2] -= 1f;
            } else if (input.isKeyDown(Input.KEY_X)) {
                position[activePos][2] += 1f;
            }
            /*int index = 0;
            for(SpriteBattler a: actorSprites){
                a.basePosX = position[index][0];
                a.basePosY = position[index][1];
                a.basePosZ = position[index][2];
                index++;
            }*/
    }
    
    public final void createEnemies(){
        for(GameEnemy e: SceneBase.gameTroop.getMembers()){
            enemySprites.add(new SpriteBattler(e, e.spriteName));   
        }
    }
    
    public final void createActors(){
        for(GameActor a: SceneBase.gameParty.getMembers()){
            actorSprites.add(new SpriteBattler(a, a.spriteName));   
        }
    }
    
    public void setAction(boolean isActor, int index, String action){
        SpriteBattler t = isActor ? actorSprites.get(index):enemySprites.get(index);
        switch (action) {
            case "COMMAND_INPUT":
                t.startAction(action);
                //t.moveAmount(50, 0, 0);
                return;
        }
        t.startAction(action);
    }
    
    public void setDamageAction(boolean isActor, int index, Object[] action){
        SpriteBattler t = isActor ? actorSprites.get(index):enemySprites.get(index);
        t.damageAction(action);
    }
    
    public void setTarget(boolean isActor, int index, Integer targetIndex){
        SpriteBattler t = isActor ? actorSprites.get(index): enemySprites.get(index);
        if(targetIndex != null){
            t.target = isActor ? enemySprites.get(targetIndex): actorSprites.get(targetIndex);
        } else {
            t.target = null;
        }
    }
    
    public void setActive(boolean isActor, int index, boolean active){
        SpriteBattler t = isActor ? actorSprites.get(index):enemySprites.get(index);
        t.active = active;
    }
    
    public void setCursorTarget(boolean isActor, int index){
        SpriteBattler t = isActor ? actorSprites.get(index): enemySprites.get(index);
        cursor.setTarget(t);
    }
    
    public void render(Graphics g){
        drawList.clear();
        drawList.addAll(enemySprites);
        drawList.addAll(actorSprites);
        Collections.sort(drawList, comparator);
        for(SpriteBattler b : drawList){
            b.render(g, b.posX(), b.posY(), 2.0f * (b.posZ()/DEPTH_BUFFER_SIZE));
        }
    }
    
    public void update(int delta, Input input){
        List<SpriteBattler> updateList = new ArrayList<>(actorSprites);
        updateList.addAll(enemySprites);
        for(SpriteBattler b : updateList){
            b.update(delta);
        }
        debugUpdate(input);
    }
    
    public class Cursor{
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
        
        public void setTarget(SpriteBattler b){
            yOffset = (int)(((float)b.image.getHeight()/(float)b.image.getVerticalCount()) * 0.70f);
            yOffset *= 2.0f * (b.posZ()/DEPTH_BUFFER_SIZE);
            x = b.posX();
            y = b.posY();
        }
        
    }
    
}
