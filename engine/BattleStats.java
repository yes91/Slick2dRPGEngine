/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.io.Serializable;

/**
 *
 * @author Kieran
 */
public class BattleStats implements Serializable{
    
    private int levels = 99;
    private int[] expList;
    private int basis = 30;
    private double inflation = 35;
    public int level;
    public int EXP;
    
    public BattleStats(){
        level = 1;
        expList = new int[levels+1];
        int m = basis;
        double n = 0.75 + (inflation/200.0);
        expList[0] = 0;
        for(int i = 1; i< expList.length; i++){
            expList[i] = m;
            m *= 1 + n;
            n *= 0.9;
        }
        EXP = expList[1];
    }
    
    public BattleStats(int initLVL){
        level = initLVL;
        expList = new int[levels+1];
        int m = basis;
        double n = 0.75 + (inflation/200.0);
        expList[0] = 0;
        for(int i = 1; i< expList.length; i++){
            expList[i] = m;
            m *= 1 + n;
            n *= 0.9;
        }
        EXP = expList[level];
    }
    
    public BattleStats(int initLVL, int basis, int inflation){
        level = initLVL;
        expList = new int[levels+1];
        int m = basis;
        double n = 0.75 + ((double)inflation/200.0);
        expList[0] = 0;
        for(int i = 1; i< expList.length; i++){
            expList[i] = m;
            m *= 1 + n;
            n *= 0.9;
        }
        EXP = expList[level];
    }
    
    public int getEXP(int level){
        return expList[level];
    }
    
    public void updateLevel(){
        for(int i = 1; i<levels; i++){
            if(EXP >= expList[i]){
                level = i;
            }
        }     
    }
    
    public int getBaseHP(){
        
        int firstLevelValue = 400;
        int lastLevelValue = 9999;
    
        double b = Math.log((double)lastLevelValue / firstLevelValue) / (levels - 1);
        double a = (double)firstLevelValue / (Math.pow(Math.E, b) - 1.0);
        int old_value = (int)Math.round(a * Math.pow(Math.E, b * (level - 1)));
        int new_value = (int)Math.round(a * Math.pow(Math.E, b * level));
        
        return (new_value - old_value);
    }
    
    public int getBaseMP(){
        
        int firstLevelValue = 100;
        int lastLevelValue = 9999;
    
        double b = Math.log((double)lastLevelValue / firstLevelValue) / (levels - 1);
        double a = (double)firstLevelValue / (Math.pow(Math.E, b) - 1.0);
        int old_value = (int)Math.round(a * Math.pow(Math.E, b * (level - 1)));
        int new_value = (int)Math.round(a * Math.pow(Math.E, b * level));
        
        return (new_value - old_value);
    }
    
    public int getBaseATK(){
        
        int firstLevelValue = 18;
        int lastLevelValue = 427;
    
        double b = Math.log((double)lastLevelValue / firstLevelValue) / (levels - 1);
        double a = (double)firstLevelValue / (Math.pow(Math.E, b) - 1.0);
        int old_value = (int)Math.round(a * Math.pow(Math.E, b * (level - 1)));
        int new_value = (int)Math.round(a * Math.pow(Math.E, b * level));
        
        return (new_value - old_value);
    }
    
    public int getBaseMATK(){
        int firstLevelValue = 10;
        int lastLevelValue = 300;
    
        double b = Math.log((double)lastLevelValue / firstLevelValue) / (levels - 1);
        double a = (double)firstLevelValue / (Math.pow(Math.E, b) - 1.0);
        int old_value = (int)Math.round(a * Math.pow(Math.E, b * (level - 1)));
        int new_value = (int)Math.round(a * Math.pow(Math.E, b * level));
        
        return (new_value - old_value);
    }
    
    public int getBaseDEF(){
        int firstLevelValue = 25;
        int lastLevelValue = 500;
    
        double b = Math.log((double)lastLevelValue / firstLevelValue) / (levels - 1);
        double a = (double)firstLevelValue / (Math.pow(Math.E, b) - 1.0);
        int old_value = (int)Math.round(a * Math.pow(Math.E, b * (level - 1)));
        int new_value = (int)Math.round(a * Math.pow(Math.E, b * level));
        
        return (new_value - old_value);
    }
    
    public int getBaseMDEF(){
        int firstLevelValue = 15;
        int lastLevelValue = 400;
    
        double b = Math.log((double)lastLevelValue / firstLevelValue) / (levels - 1);
        double a = (double)firstLevelValue / (Math.pow(Math.E, b) - 1.0);
        int old_value = (int)Math.round(a * Math.pow(Math.E, b * (level - 1)));
        int new_value = (int)Math.round(a * Math.pow(Math.E, b * level));
        
        return (new_value - old_value);
    }
    
    public int getBaseSPD(){
        int firstLevelValue = 5;
        int lastLevelValue = 50;
    
        double b = Math.log((double)lastLevelValue / firstLevelValue) / (levels - 1);
        double a = (double)firstLevelValue / (Math.pow(Math.E, b) - 1.0);
        int old_value = (int)Math.round(a * Math.pow(Math.E, b * (level - 1)));
        int new_value = (int)Math.round(a * Math.pow(Math.E, b * level));
        
        return (new_value - old_value);
    }
    
}
