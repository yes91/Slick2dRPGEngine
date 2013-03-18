/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author Kieran
 */
public class SpritesetBattle1 {
    
    private static float[][] position = new float[][]{
        new float[]{768.0f, 273.0f, 74.0f},
        new float[]{865.0f, 327.0f, 177.0f},
        new float[]{983.0f, 401.0f, 272.0f},
        new float[]{1108.0f, 486.0f, 388.0f}
    };
    
    public Cursor cursor;
    private DepthBuffCompare1 comparator = new DepthBuffCompare1();
    private final int DEPTH_BUFFER_SIZE = SceneMap.B_HEIGHT/2;
    private List<SpriteBattler1> enemySprites;
    private List<SpriteBattler1> actorSprites;
    
    public List<SpriteBattler1> drawList;
    
    public SpritesetBattle1(){
        cursor = new Cursor();
        drawList = new ArrayList<>();
        enemySprites = new ArrayList<>();
        actorSprites = new ArrayList<>();
        createActors();
        createEnemies();
        enemySprites.get(0).basePosX = 200;
        enemySprites.get(0).basePosY = 350;
        enemySprites.get(0).basePosZ = 150;
        int index = 0;
        for(SpriteBattler1 a: actorSprites){
          a.basePosX = position[index][0];
          a.basePosY = position[index][1];
          a.basePosZ = position[index][2];
          index++;
      }
    }
    
    public final void createEnemies(){
        for(GameEnemy e: SceneBase.gameTroop.getMembers()){
            enemySprites.add(new SpriteBattler1(e, e.spriteName));   
        }
    }
    
    public final void createActors(){
        for(GameActor a: SceneBase.gameParty.getMembers()){
            actorSprites.add(new SpriteBattler1(a, a.spriteName));   
        }
    }
    
    public void setAction(boolean isActor, int index, String action){
        SpriteBattler1 t = isActor ? actorSprites.get(index):enemySprites.get(index);
        
    }
    
    public void setTarget(boolean isActor, int index, int targetIndex){
        SpriteBattler1 t = isActor ? actorSprites.get(index): enemySprites.get(index);
        t.target = isActor ? enemySprites.get(targetIndex): actorSprites.get(targetIndex);
    }
    
    public void setCursorTarget(boolean isActor, int index){
        SpriteBattler1 t = isActor ? actorSprites.get(index): enemySprites.get(index);
        cursor.setTarget(t);
    }
    
    public void doTest(){
        actorSprites.get(0).toPoint(300, 300, 100);
        actorSprites.get(0).attack();
    }
    
    public void render(Graphics g){
        drawList.clear();
        drawList.addAll(enemySprites);
        drawList.addAll(actorSprites);
        Collections.sort(drawList, comparator);
        for(SpriteBattler1 b : drawList){
            b.render(g, b.posX(), b.posY(), 1.0f + (b.posZ() / DEPTH_BUFFER_SIZE));
        }
    }
    
    public void update(int delta){
        List<SpriteBattler1> updateList = new ArrayList<>(actorSprites);
        updateList.addAll(enemySprites);
        for(SpriteBattler1 b : updateList){
            b.update(delta);
        }
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
            org.newdawn.slick.SpriteSheet s = new org.newdawn.slick.SpriteSheet(sheet, sheet.getWidth(), sheet.getHeight()/2);
            anim = new Animation(s, 0, 0, 0, 1, true, 350, true);
        }
        
        public void render(){
            anim.draw(x - 32, y - 32 - yOffset);
        }
        
        public void setTarget(SpriteBattler1 b){
            yOffset = b.image.getHeight()/b.image.getVerticalCount();
            x = b.posX();
            y = b.posY();
        }
        
    }
    
}
