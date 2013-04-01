/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import engine.EffectAnimation.Frame;
import engine.EffectAnimation.Frame.Cell;
import engine.EffectAnimation.Timing;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Kieran
 */
public class Demo {
    /**
     * This class is a lovely place full of hacks, trickery, and dark magic
     * I will use for prototyping.
     */
    public static ArrayList<GameActor> testActors;
    public static ArrayList<GameEnemy> testEnemies;
    
    public static void init(){
        testActors = new ArrayList<>();
        testEnemies = new ArrayList<>();
        
        EffectAnimation testAni = new EffectAnimation();
        testAni.animationName = "Sword1";
        testAni.frameMax = 6;
        Frame frame1 = new Frame();
        Cell cell1 = new Cell();
        cell1.pattern = 1;
        cell1.blendType = Graphics.MODE_ADD;
        frame1.cellData.add(cell1);
        Frame frame2 = new Frame();
        Cell cell2 = new Cell();
        cell2.pattern = 2;
        cell2.blendType = Graphics.MODE_ADD;
        frame2.cellData.add(cell1);
        Frame frame3 = new Frame();
        Cell cell3 = new Cell();
        cell3.pattern = 2;
        cell3.blendType = Graphics.MODE_ADD;
        frame3.cellData.add(cell3);
        Frame frame4 = new Frame();
        Cell cell4 = new Cell();
        cell4.pattern = 3;
        cell4.blendType = Graphics.MODE_ADD;
        frame4.cellData.add(cell4);
        Frame frame5 = new Frame();
        Cell cell5 = new Cell();
        cell5.pattern = 4;
        cell5.blendType = Graphics.MODE_ADD;
        frame5.cellData.add(cell5);
        Frame frame6 = new Frame();
        Cell cell6 = new Cell();
        cell6.pattern = 5;
        cell6.blendType = Graphics.MODE_ADD;
        frame6.cellData.add(cell6);
        
        Timing time1 = new Timing();
        time1.frame = 1;
        time1.se = "Slash1.ogg";
        
        testAni.frames.add(frame1);
        testAni.frames.add(frame2);
        testAni.frames.add(frame3);
        testAni.frames.add(frame4);
        testAni.frames.add(frame5);
        testAni.frames.add(frame6);
        
        testAni.timings.add(time1);
        
        GameData.animations.add(null);
        GameData.animations.add(testAni);
        
        GameActor battleTester = new GameActor("mutsu_1");
        battleTester.name = "Nozomi";
        battleTester.faceName = "Mutsu_2";
        battleTester.faceIndex = 0;
        battleTester.characterName = "mutsu";
        battleTester.characterIndex = -1;
        battleTester.stats = new BattleStats(22);
        battleTester.currentHP = battleTester.getMaxHP();
        battleTester.currentMP = battleTester.getMaxMP();
        battleTester.setTwoSwords(false);
        
        GameActor tester2 = new GameActor("gunslinger_1");
        tester2.name = "Alkan";
        tester2.faceName = "Actor1b";
        tester2.faceIndex = 0;
        tester2.characterName = "";
        tester2.characterIndex = -1;
        tester2.stats = new BattleStats(8);
        tester2.currentHP = tester2.getMaxHP();
        tester2.currentMP = tester2.getMaxMP();
        
        GameActor tester3 = new GameActor("kasier_1");
        tester3.name = "Aran";
        tester3.faceName = "Spiritual";
        tester3.faceIndex = 5;
        tester3.characterName = "";
        tester3.characterIndex = -1;
        tester3.stats = new BattleStats(45);
        tester3.currentHP = tester3.getMaxHP();
        tester3.currentMP = tester3.getMaxMP();
        
        GameActor tester4 = new GameActor("spearman_1");
        tester4.name = "Minato";
        tester4.faceName = "Evil";
        tester4.faceIndex = 4;
        tester4.characterName = "";
        tester4.characterIndex = -1;
        tester4.stats = new BattleStats(38);
        tester4.currentHP = tester4.getMaxHP();
        tester4.currentMP = tester4.getMaxMP();
        
        GameActor tester5 = new GameActor("yuan_1");
        tester5.name = "Dipshit";
        tester5.faceName = "People1";
        tester5.faceIndex = 4;
        tester5.characterName = "";
        tester5.characterIndex = -1;
        tester5.stats = new BattleStats(25);
        tester5.currentHP = tester5.getMaxHP();
        tester5.currentMP = tester5.getMaxMP();
        
        GameActor tester6 = new GameActor("yuan_1");
        tester6.name = "Dipshit";
        tester6.faceName = "People1";
        tester6.faceIndex = 6;
        tester6.characterName = "";
        tester6.characterIndex = -1;
        tester6.stats = new BattleStats(15);
        tester6.currentHP = tester6.getMaxHP();
        tester6.currentMP = tester6.getMaxMP();
        
        testActors.add(battleTester);
        testActors.add(tester2);
        testActors.add(tester3);
        testActors.add(tester4);
        testActors.add(tester5);
        testActors.add(tester6);
        
        GameEnemy testEnemy = new GameEnemy("slime");
        testEnemy.name = "Slime";
        testEnemy.stats = new BattleStats(30);
        testEnemy.currentHP = testEnemy.getMaxHP();
        ArrayList<EnemyAction> actionList = new ArrayList<>();
        EnemyAction testAction = new EnemyAction();
        testAction.setAttack();
        testAction.setRating(5);
        testAction.setCondition(EnemyAction.Condition.ALWAYS);
        actionList.add(testAction);
        testEnemy.actions = actionList;
        
        GameEnemy enemy2 = GameData.kryo.copy(testEnemy);
        enemy2.spriteName = "wanderer";
        enemy2.name = "Wanderer";
        
        GameEnemy enemy3 = GameData.kryo.copy(testEnemy);
        enemy3.spriteName = "snake_1";
        enemy3.name = "Frog";
        
        GameEnemy enemy4 = GameData.kryo.copy(testEnemy);
        enemy4.spriteName = "jackall";
        enemy4.name = "Jackal";
        
        testEnemies.add(testEnemy);
        testEnemies.add(enemy2);
        testEnemies.add(GameData.kryo.copy(testEnemy));
        testEnemies.add(GameData.kryo.copy(testEnemy));
    }
    
}
