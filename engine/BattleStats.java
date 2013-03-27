/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.HashMap;

/**
 *
 * @author Kieran
 */
public class BattleStats{
    
    private int levels = 99;
    private int[] expList;
    private HashMap<String, HashMap<Integer, Integer>> table;
    private int basis = 30;
    private double inflation = 35;
    private int level;
    public int EXP;
    
    public BattleStats(){
        table = new HashMap<>();
        makeTable();
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
        table = new HashMap<>();
        makeTable();
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
        table = new HashMap<>();
        makeTable();
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
    
    private void makeTable(){
        table.put("HP", generateCurve(405, 9999, 0));
        //System.out.println(table.get("HP").toString());
        table.put("MP", generateCurve(150, 9999, -3.5f));
        table.put("ATK", generateCurve(18, 427, 0.5f));
        //System.out.println(table.get("ATK").toString());
        table.put("MATK", generateCurve(10, 300, 1f));
        table.put("DEF", generateCurve(25, 200, 1f));
        table.put("MDEF", generateCurve(15, 400, 1f));
        table.put("SPD", generateCurve(5, 50, 1f));
    }
    
    private HashMap<Integer, Integer> generateCurve(int start, int end, float speed){
        HashMap<Integer, Integer> map = new HashMap<>();
        
        for(int i = 1; i <= levels; i++){
            
            /*double b = Math.log((double)end/ start) / (levels - 1);
            
            double a = (double)start / (Math.exp(b) - 1.0);
            
            int old_value = (int)Math.round(a * Math.exp(b * (i - 1)));
            int new_value = (int)Math.round(a * Math.exp(b * i));
            
            map.put(i, (new_value - old_value));*/  
            
        }
        
        speed = Math.max(-10f, Math.min( speed, 10f));
            
            
            for(int i = 1; i <= levels; i++){  
                double t = (double)i/(double)levels;
                int y = (int) (  Math.pow(1-t, 2)*start + 2*(1-t)*t*(speed * 100)+Math.pow(t, 2)*end );  
                map.put(i, y);
            }
        
        return map;
    }
    
    public int getEXP(int level){
        return expList[level];
    }
    
    public int getLevel(){
        for(int i = 1; i <= levels; i++){
            if(EXP >= expList[i]){
                level = i;
            }
        }
        return level;
    }
    
    
    
    public int getBaseHP(){
        return table.get("HP").get(getLevel());
    }
    
    public int getBaseMP(){
       return table.get("MP").get(getLevel());
    }
    
    public int getBaseATK(){
        return table.get("ATK").get(getLevel());
    }
    
    public int getBaseMATK(){
        return table.get("MATK").get(getLevel());
    }
    
    public int getBaseDEF(){
        return table.get("DEF").get(getLevel());
    }
    
    public int getBaseMDEF(){
        return table.get("MDEF").get(getLevel());
    }
    
    public int getBaseSPD(){
        return table.get("SPD").get(getLevel());
    }
    
}
