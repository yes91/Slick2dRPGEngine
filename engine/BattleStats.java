/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

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
    public int level;
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
        table.put("HP", generateCurve(450, 8000, 0));
        //System.out.println(table.get("HP").toString());
        table.put("MP", generateCurve(100, 4500, 5));
        table.put("ATK", generateCurve(18, 430, 1));
        table.put("MATK", generateCurve(10, 300, 1));
        table.put("DEF", generateCurve(25, 200, 1));
        table.put("MDEF", generateCurve(15, 400, 1));
        table.put("SPD", generateCurve(5, 50, 1));
    }
    
    private HashMap<Integer, Integer> generateCurve(int start, int end, int speed){
        
        HashMap<Integer, Integer> map = new HashMap<>();
        
        float adjustedSpeed = MathHelper.clamp(speed, -10f, 10f);
        
        adjustedSpeed = MathHelper.scaleRange(adjustedSpeed, -10f, 10f, -(float)levels/5.85f, (float)levels/5.85f);        
        
        Vector2f control = new Vector2f(0, 0);
        control.set(new Vector2f((float)levels/2, (float)end/2).add(new Vector2f( 1,  - ((float)end/(float)levels)).scale(adjustedSpeed)));
            
        for (int i = 1; i <= levels; i++) {
            
            float t = (float)(Math.sqrt(levels*i-levels+Math.pow(control.x, 2)-2*control.x*i+i)-control.x+1)/(levels-2*control.x+1);
            
            float y = (float) ((1 - t) * (1 - t) * start + 2 * (1 - t) * t * control.y + t * t * end);
            
            y = MathHelper.clamp(y, start, end);
            
            map.put(i, (int)Math.ceil(y));
        }
        
        return map;
    }
    
    public int getEXP(int level){
        return expList[level];
    }
    
    public void updateLevel(){
        for(int i = 1; i <= levels; i++){
            if(EXP >= expList[i]){
                level = i;
            }
        }
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
