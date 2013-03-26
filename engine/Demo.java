/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import com.esotericsoftware.kryo.Kryo;
import java.util.ArrayList;

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
        
        GameActor battleTester = new GameActor("mutsu_1");
        battleTester.name = "Nozomi";
        battleTester.faceName = "Mutsu_2";
        battleTester.faceIndex = 0;
        battleTester.characterName = "mutsu";
        battleTester.characterIndex = -1;
        battleTester.stats.level = 22;
        battleTester.stats.EXP = battleTester.stats.getEXP(22);
        battleTester.currentHP = battleTester.getMaxHP();
        battleTester.currentMP = battleTester.getMaxMP();
        battleTester.setTwoSwords(false);
        
        GameActor tester2 = new GameActor("gunslinger_1");
        tester2.name = "Alkan";
        tester2.faceName = "Actor1b";
        tester2.faceIndex = 0;
        tester2.characterName = "";
        tester2.characterIndex = -1;
        tester2.stats.level = 8;
        tester2.stats.EXP = tester2.stats.getEXP(8);
        tester2.currentHP = tester2.getMaxHP();
        tester2.currentMP = tester2.getMaxMP();
        
        GameActor tester3 = new GameActor("kasier_1");
        tester3.name = "Aran";
        tester3.faceName = "Spiritual";
        tester3.faceIndex = 5;
        tester3.characterName = "";
        tester3.characterIndex = -1;
        tester3.stats.level = 45;
        tester3.stats.EXP = tester3.stats.getEXP(45);
        tester3.currentHP = tester3.getMaxHP();
        tester3.currentMP = tester3.getMaxMP();
        
        GameActor tester4 = new GameActor("spearman_1");
        tester4.name = "Minato";
        tester4.faceName = "Evil";
        tester4.faceIndex = 4;
        tester4.characterName = "";
        tester4.characterIndex = -1;
        tester4.stats.level = 85;
        tester4.stats.EXP = tester4.stats.getEXP(85);
        tester4.currentHP = tester4.getMaxHP();
        tester4.currentMP = tester4.getMaxMP();
        
        GameActor tester5 = new GameActor("yuan_1");
        tester5.name = "Dipshit";
        tester5.faceName = "People1";
        tester5.faceIndex = 4;
        tester5.characterName = "";
        tester5.characterIndex = -1;
        tester5.stats.level = 25;
        tester5.stats.EXP = tester5.stats.getEXP(25);
        tester5.currentHP = tester5.getMaxHP();
        tester5.currentMP = tester5.getMaxMP();
        
        GameActor tester6 = new GameActor("yuan_1");
        tester6.name = "Dipshit";
        tester6.faceName = "People1";
        tester6.faceIndex = 6;
        tester6.characterName = "";
        tester6.characterIndex = -1;
        tester6.stats.level = 15;
        tester6.stats.EXP = tester6.stats.getEXP(15);
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
        testEnemy.stats.level = 50;
        testEnemy.stats.EXP = testEnemy.stats.getEXP(50);
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
        testEnemies.add(enemy3);
        testEnemies.add(enemy4);
    }
    
}
