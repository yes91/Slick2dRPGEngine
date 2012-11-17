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
public abstract class GameBattler{
    
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

	public Image getBattleSprite() {
		return battleSprite;
	}

	public void setBattleSprite(Image battleSprite) {
		this.battleSprite = battleSprite;
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}

	public int getCurrentMP() {
		return currentMP;
	}

	public void setCurrentMP(int currentMP) {
		this.currentMP = currentMP;
	}

	public int getBaseHP() {
		return baseHP;
	}

	public void setBaseHP(int baseHP) {
		this.baseHP = baseHP;
	}

	public int getHPplus() {
		return HPplus;
	}

	public void setHPplus(int hPplus) {
		HPplus = hPplus;
	}

	public int getBaseMP() {
		return baseMP;
	}

	public void setBaseMP(int baseMP) {
		this.baseMP = baseMP;
	}

	public int getMPplus() {
		return MPplus;
	}

	public void setMPplus(int mPplus) {
		MPplus = mPplus;
	}

	public int getBaseATK() {
		return baseATK;
	}

	public void setBaseATK(int baseATK) {
		this.baseATK = baseATK;
	}

	public int getATKplus() {
		return ATKplus;
	}

	public void setATKplus(int aTKplus) {
		ATKplus = aTKplus;
	}

	public int getBaseMATK() {
		return baseMATK;
	}

	public void setBaseMATK(int baseMATK) {
		this.baseMATK = baseMATK;
	}

	public int getMATKplus() {
		return MATKplus;
	}

	public void setMATKplus(int mATKplus) {
		MATKplus = mATKplus;
	}

	public int getBaseDEF() {
		return baseDEF;
	}

	public void setBaseDEF(int baseDEF) {
		this.baseDEF = baseDEF;
	}

	public int getDEFplus() {
		return DEFplus;
	}

	public void setDEFplus(int dEFplus) {
		DEFplus = dEFplus;
	}

	public int getBaseMDEF() {
		return baseMDEF;
	}

	public void setBaseMDEF(int baseMDEF) {
		this.baseMDEF = baseMDEF;
	}

	public int getMDEFplus() {
		return MDEFplus;
	}

	public void setMDEFplus(int mDEFplus) {
		MDEFplus = mDEFplus;
	}

	public int getBaseSPD() {
		return baseSPD;
	}

	public void setBaseSPD(int baseSPD) {
		this.baseSPD = baseSPD;
	}

	public int getSPDplus() {
		return SPDplus;
	}

	public void setSPDplus(int sPDplus) {
		SPDplus = sPDplus;
	}
}