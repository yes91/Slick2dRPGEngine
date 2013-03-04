/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.ArrayList;

/**
 *
 * @author Kieran
 */
public class Demo {
    /* This class is a lovely place full of hacks, trickery, and dark magic
     * I will use for prototyping.
     */
    public static ArrayList<GameActor> testActors;
    public static void init(){
        testActors = new ArrayList<>();
        GameActor battleTester = new GameActor("yuan_1");
        battleTester.name = "TestDude";
        battleTester.faceName = "People1";
        battleTester.faceIndex = 2;
        battleTester.stats.level = 22;
        battleTester.stats.EXP = battleTester.stats.getEXP(22);
        battleTester.currentHP = battleTester.getMaxHP();
        battleTester.currentMP = battleTester.getMaxMP();
        
        GameActor tester2 = new GameActor("aster_1");
        tester2.name = "Dipshit";
        tester2.faceName = "People1";
        tester2.faceIndex = 3;
        tester2.stats.level = 8;
        tester2.stats.EXP = tester2.stats.getEXP(8);
        tester2.currentHP = tester2.getMaxHP();
        tester2.currentMP = tester2.getMaxMP();
        
        GameActor tester3 = new GameActor("kasier_1");
        tester3.name = "Dipshit";
        tester3.faceName = "People1";
        tester3.faceIndex = 0;
        tester3.stats.level = 45;
        tester3.stats.EXP = tester3.stats.getEXP(45);
        tester3.currentHP = tester3.getMaxHP();
        tester3.currentMP = tester3.getMaxMP();
        
        GameActor tester4 = new GameActor("spearman_1");
        tester4.name = "Dipshit";
        tester4.faceName = "People1";
        tester4.faceIndex = 7;
        tester4.stats.level = 85;
        tester4.stats.EXP = tester4.stats.getEXP(85);
        tester4.currentHP = tester4.getMaxHP();
        tester4.currentMP = tester4.getMaxMP();
        
        GameActor tester5 = new GameActor("yuan_1");
        tester5.name = "Dipshit";
        tester5.faceName = "People1";
        tester5.faceIndex = 4;
        tester5.stats.level = 25;
        tester5.stats.EXP = tester5.stats.getEXP(25);
        tester5.currentHP = tester5.getMaxHP();
        tester5.currentMP = tester5.getMaxMP();
        
        GameActor tester6 = new GameActor("yuan_1");
        tester6.name = "Dipshit";
        tester6.faceName = "People1";
        tester6.faceIndex = 6;
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
    }
    
}
