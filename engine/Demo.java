/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

/**
 *
 * @author Kieran
 */
public class Demo {
    /* This class is a lovely place full of hacks, trickery, and dark magic
     * I will use for prototyping.
     */
    public static GameActor battleTester;
    public static void init(){
        battleTester = new GameActor();
        battleTester.name = "TestDude";
        battleTester.faceName = "People1";
        battleTester.faceIndex = 2;
        battleTester.stats.level = 5;
        battleTester.stats.EXP = battleTester.stats.getEXP(5);
        battleTester.currentHP = battleTester.getMaxHP();
        battleTester.currentMP = battleTester.getMaxMP();
    }
    
}
