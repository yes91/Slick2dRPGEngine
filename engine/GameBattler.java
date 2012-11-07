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
    public int currentHP;
    public int currentMP;
    public int baseHP;
    public int HPplus;
    public int baseMP;
    public int MPplus;
    public int baseATK;
    public int ATKplus;
    private int ATK;
    public int baseMATK;
    public int MATKplus;
    public int baseDEF;
    public int DEFplus;
    public int baseMDEF;
    public int MDEFplus;
    public int baseSPD;
    public int SPDplus;
    
    public void clearExtraValues(){
        HPplus = 0;
        MPplus = 0;
        ATKplus = 0;
        MATKplus = 0;
        DEFplus = 0;
        MDEFplus = 0;
        SPDplus = 0;
    }
    
    public int getMaxHP(){
        return Math.min(Math.max(baseHP + HPplus, 1), HP_LIMIT);
    }
    
    public int getMaxMP(){
        return Math.min(Math.max(baseMP + MPplus, 1), 9999);
    }
    
    public int getMaxATK(){
        return Math.min(Math.max(baseATK + ATKplus, 1), 999);
    }
    
    public int getMaxMATK(){
        return Math.min(Math.max(baseMATK + MATKplus, 1), 999);
    }
    
    public int getMaxDEF(){
        return Math.min(Math.max(baseDEF + DEFplus, 1), 999);
    }
    
    public int getMaxMDEF(){
        return Math.min(Math.max(baseMDEF + MDEFplus, 1), 999);
    }
    
    public int getMaxSPD(){
        return Math.min(Math.max(baseSPD + SPDplus, 1), 999);
    }

	/**
	 * @return the aTK
	 */
	public int getATK() {
		return ATK;
	}

	/**
	 * @param aTK the aTK to set
	 */
	public void setATK(int aTK) {
		ATK = aTK;
	}

}