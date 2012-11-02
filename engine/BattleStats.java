/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

/**
 *
 * @author Kieran
 */
public class BattleStats {
    
    private int levels = 99;
    private int firstLevelEXP = 30;
    private int lastLevelEXP = 99999;
    
    double B = Math.log((double)lastLevelEXP / firstLevelEXP) / (levels - 1);
    double A = (double)firstLevelEXP / (Math.pow(Math.E, B) - 1.0);
    
    public int baseHP;
    public int baseMP;
    public int baseATK;
    public int baseMATK;
    public int baseDEF;
    public int baseMDEF;
    public int baseSPD;
    public int level;
    public int EXP;
    
    public BattleStats(){
        level = 1;
        EXP = getEXPforLVL(1);
    }
    
    public BattleStats(int initLVL){
        level = initLVL;
        EXP = getEXPforLVL(level);
    }
    
    public int getEXPforLVL(int level){
        int old_xp = (int)Math.round(A * Math.pow(Math.E, B * (level - 1)));
        int new_xp = (int)Math.round(A * Math.pow(Math.E, B * level));
        
        return (new_xp - old_xp);
    }
    
    public void updateLevel(){
        for(int i = 1; i<levels; i++){
            if(EXP >= getEXPforLVL(i)){
            level = i;
            }
        }
    }
    
    public int getBaseHP(){
        
        int firstLevelValue = 200;
        int lastLevelValue = 8000;
    
        double b = Math.log((double)lastLevelValue / firstLevelValue) / (levels - 1);
        double a = (double)firstLevelValue / (Math.pow(Math.E, b) - 1.0);
        int old_value = (int)Math.round(a * Math.pow(Math.E, b * (level - 1)));
        int new_value = (int)Math.round(a * Math.pow(Math.E, b * level));
        
        return (new_value - old_value);
    }
    
    public int getBaseMP(){
        
        int firstLevelValue = 50;
        int lastLevelValue = 4000;
    
        double b = Math.log((double)lastLevelValue / firstLevelValue) / (levels - 1);
        double a = (double)firstLevelValue / (Math.pow(Math.E, b) - 1.0);
        int old_value = (int)Math.round(a * Math.pow(Math.E, b * (level - 1)));
        int new_value = (int)Math.round(a * Math.pow(Math.E, b * level));
        
        return (new_value - old_value);
    }
    
    public int getBaseATK(){
        
        int firstLevelValue = 18;
        int lastLevelValue = 225;
    
        double b = Math.log((double)lastLevelValue / firstLevelValue) / (levels - 1);
        double a = (double)firstLevelValue / (Math.pow(Math.E, b) - 1.0);
        int old_value = (int)Math.round(a * Math.pow(Math.E, b * (level - 1)));
        int new_value = (int)Math.round(a * Math.pow(Math.E, b * level));
        
        return (new_value - old_value);
    }
    
}
