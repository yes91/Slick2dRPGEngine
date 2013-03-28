/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.Collections;
import java.util.HashMap;
import org.newdawn.slick.geom.Vector2f;
import util.MathHelper;

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
        table.put("HP", generateCurve(400, 9999, 0));
        System.out.println(table.get("HP").toString());
        table.put("MP", generateCurve(100, 4545, 10f));
        System.out.println(table.get("MP").toString());
        table.put("ATK", generateCurve(18, 427, 0.5f));
        //System.out.println(table.get("ATK").toString());
        table.put("MATK", generateCurve(10, 300, 1f));
        table.put("DEF", generateCurve(25, 200, 1f));
        table.put("MDEF", generateCurve(15, 400, 1f));
        table.put("SPD", generateCurve(5, 50, 1f));
    }
    
    private HashMap<Integer, Integer> generateCurve(int start, int end, float speed){
        HashMap<Integer, Integer> map = new HashMap<>();
        
        /*for(int i = 1; i <= levels; i++){
            
            double b = Math.log((double)end/ start) / (levels - 1);
            
            double a = (double)start / (Math.exp(b) - 1.0);
            
            int old_value = (int)Math.round(a * Math.exp(b * (i - 1)));
            int new_value = (int)Math.round(a * Math.exp(b * i));
            
            map.put(i, (new_value - old_value));  
            
        }*/
        
        speed = MathHelper.clamp(speed, -10f, 10f);
        
        speed = MathHelper.scaleRange(speed, -10f, 10f, -((float)levels/2), ((float)levels/2));
        
        
        Vector2f control = (new Vector2f((float)levels/2, (float)end/2).add(new Vector2f( 1,  - ((float)end/(float)levels)).scale(speed)));
        
        int i = 1;    
        for (double t = 0.0; t <= 1; t += 0.01) {
            
            float x = (float) ((1 - t) * (1 - t) * 1 + 2 * (1 - t) * t * control.x + t * t * levels);
            float y = (float) ((1 - t) * (1 - t) * start + 2 * (1 - t) * t * control.y + t * t * end);
            
            map.put(i, (int)y);
            i++;
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
        return table.get("HP").get(level);
    }
    
    public int getBaseMP(){
       return table.get("MP").get(level);
    }
    
    public int getBaseATK(){
        return table.get("ATK").get(level);
    }
    
    public int getBaseMATK(){
        return table.get("MATK").get(level);
    }
    
    public int getBaseDEF(){
        return table.get("DEF").get(level);
    }
    
    public int getBaseMDEF(){
        return table.get("MDEF").get(level);
    }
    
    public int getBaseSPD(){
        return table.get("SPD").get(level);
    }
    
}
