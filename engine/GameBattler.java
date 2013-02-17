/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Image;

/**
 *
 * @author redblast71
 */
public abstract class GameBattler {
    
    private final int HP_LIMIT = 999999;
    public Image battleSprite;
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
    
    public GameBattler(){
        stats = new BattleStats();
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