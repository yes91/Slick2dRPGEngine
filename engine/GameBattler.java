/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 *
 * @author redblast71
 */
public abstract class GameBattler {
    
    private final int HP_LIMIT = 999999;
    public SpriteBattler battleSprite;
    public BattleStats stats;
    public int currentHP;
    public int currentMP;
    public int HPplus;
    public int MPplus;
    public int ATKplus;
    public int MATKplus;
    public int DEFplus;
    public int MDEFplus;
    public int SPDplus;
    /**
     * Animation state testing variables
     */
    int stateTest = 0;
    /* End */
    
    public GameBattler(Image bSprite){
        stats = new BattleStats();
        battleSprite = new SpriteBattler(this, bSprite);
    }
    
    public void render(Graphics g, float x, float y, float scale){
        int width = battleSprite.getCurrentAni().getWidth();
        int height = battleSprite.getCurrentAni().getHeight();
        
        float cx = x +  width / 2;
        float cy = y +  height / 2;

        // get scaled draw coordinates (sx, sy)
        float sx = x - (width * scale / 2);
        float sy = y - (height * scale / 2);
        
        battleSprite.getCurrentAni().draw(sx, sy, width * scale, height * scale);
        
        if(SceneBattle.DEBUG_UTIL){
            g.setColor(Color.cyan);
            g.drawLine(x  + 10, y, x - 10, y);
            g.drawLine(x , y + 10, x, y - 10);
            g.setColor(Color.red);
            g.drawLine(cx  + 10, cy, cx - 10, cy);
            g.drawLine(cx , cy + 10, cx, cy - 10);
        }
        
    }
    
    public void updateLevel(){
        stats.updateLevel();
    }
    
    public void clearExtraValues(){
        HPplus = 0;
        MPplus = 0;
        ATKplus = 0;
        MATKplus = 0;
        DEFplus = 0;
        MDEFplus = 0;
        SPDplus = 0;
    }
    
    public void debugUpdate(Input in){
        if(in.isKeyPressed(Input.KEY_Q)){
            stateTest = 0;
        }
        if(in.isKeyPressed(Input.KEY_W)){
            stateTest = 1;
        }
        if(in.isKeyPressed(Input.KEY_E)){
            stateTest = 2;
        }
        if(in.isKeyPressed(Input.KEY_R)){
            stateTest = 3;
        }
        if(in.isKeyPressed(Input.KEY_T)){
            stateTest = 4;
        }
        if(in.isKeyPressed(Input.KEY_Y)){
            stateTest = 5;
        }
        if(in.isKeyPressed(Input.KEY_U)){
            stateTest = 6;
        }
        if(in.isKeyPressed(Input.KEY_I)){
            stateTest = 7;
        }
        if(in.isKeyPressed(Input.KEY_O)){
            stateTest = 8;
        }
        if(in.isKeyPressed(Input.KEY_P)){
            stateTest = 9;
        }
        if(in.isKeyPressed(Input.KEY_LBRACKET)){
            stateTest = 10;
        }
    }
    
    public boolean isDead(){
        return currentHP <= 0;
    }
    
    public int getMaxHP(){
        return Math.min(Math.max(stats.getBaseHP() + HPplus, 1), HP_LIMIT);
    }
    
    public int getMaxMP(){
        return Math.min(Math.max(stats.getBaseMP() + MPplus, 1), 9999);
    }
    
    public int getMaxATK(){
        return Math.min(Math.max(stats.getBaseATK() + ATKplus, 1), 999);
    }
    
    public int getMaxMATK(){
        return Math.min(Math.max(stats.getBaseMATK() + MATKplus, 1), 999);
    }
    
    public int getMaxDEF(){
        return Math.min(Math.max(stats.getBaseDEF() + DEFplus, 1), 999);
    }
    
    public int getMaxMDEF(){
        return Math.min(Math.max(stats.getBaseMDEF() + MDEFplus, 1), 999);
    }
    
    public int getMaxSPD(){
        return Math.min(Math.max(stats.getBaseSPD() + SPDplus, 1), 999);
    }
    
    public int getATKplus() {
        return ATKplus;
    }
    
    public void setATKplus(int aTK) {
        ATKplus = aTK;
    }

    public int getHPplus() {
        return HPplus;
    }

    public void setHPplus(int HPplus) {
        this.HPplus = HPplus;
    }

    public int getMPplus() {
        return MPplus;
    }

    public void setMPplus(int MPplus) {
        this.MPplus = MPplus;
    }

    public int getMATKplus() {
        return MATKplus;
    }

    public void setMATKplus(int MATKplus) {
        this.MATKplus = MATKplus;
    }

    public int getDEFplus() {
        return DEFplus;
    }

    public void setDEFplus(int DEFplus) {
        this.DEFplus = DEFplus;
    }

    public int getMDEFplus() {
        return MDEFplus;
    }

    public void setMDEFplus(int MDEFplus) {
        this.MDEFplus = MDEFplus;
    }

    public int getSPDplus() {
        return SPDplus;
    }

    public void setSPDplus(int SPDplus) {
        this.SPDplus = SPDplus;
    }

}