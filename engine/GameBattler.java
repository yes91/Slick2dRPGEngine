/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import engine.Effect.Scope;
import java.util.Random;
import org.newdawn.slick.Input;
import util.MathHelper;

/**
 *
 * @author redblast71
 */
public abstract class GameBattler {
    
    private final int HP_LIMIT = 999999;
    public Random chance = new Random();
    public String name;
    public String spriteName;
    public transient Object[] play;
    public BattleStats stats;
    public GameBattleAction action;
    public int currentHP;
    public int currentMP;
    public int HPchange;
    public int MPchange;
    public transient int animationID = -1;
    
    public transient boolean whiteFlash;
    public transient boolean blink;
    public transient boolean appear;
    public transient boolean disappear;
    public transient boolean collapse;
    
    public transient boolean skipped;
    public transient boolean missed;
    public transient boolean evaded;
    public transient boolean critical;
    public transient boolean absorbed;
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
    
    public GameBattler(){
        
    }
    
    public GameBattler(String sprite){
        spriteName = sprite;
        stats = new BattleStats();
        action = new GameBattleAction(this);
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
    
    public void clearActionResults(){
        HPchange = 0;
        MPchange = 0;
        skipped = false;
        missed = false;
        evaded = false;
        critical = false;
        absorbed = false;
    }
    
    public abstract void performCollapse();
    
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
    
    public boolean isInputable(){
        return !isDead(); //Replace with death state restriction check.
    }
    
    public boolean isMovable(){
        return !isDead();
    }
    
    public boolean isActor(){
        return false;
    }
    
    public void doDamage(){
        this.currentHP -= HPchange;
        this.currentMP -= MPchange;
        currentHP = MathHelper.clamp(currentHP, 0, getMaxHP());
        currentMP = MathHelper.clamp(currentMP, 0, getMaxMP());
    }
    
    public void makeEffectDamageValue(GameBattler user, Effect effect){
        int damage = effect.baseDamage;
        if(damage > 0){
            damage += user.getMaxATK() * 4 * effect.ATKFactor / 100;
            damage += user.getMaxMATK() * 2 * effect.MATKFactor / 100;
            if(!effect.ignoreDEF){
                damage -= this.getMaxDEF() * 2 * effect.ATKFactor / 100;
                damage -= this.getMaxMDEF() * 1 * effect.MATKFactor / 100;
            }
            if(damage < 0){
                damage = 0;
            }
        } else if(damage < 0){
            damage -= user.getMaxATK() * 4 * effect.ATKFactor / 100;
            damage -= user.getMaxMATK() * 2 * effect.MATKFactor / 100;
        }
        //Element rate
        applyVariance(damage, effect.variance);
        applyGuard(damage);
        if(effect.MPdamage){
            MPchange = damage;
        } else {
            HPchange = damage;
        }        
    }
    
    public void makeAttackDamageValue(GameBattler attacker){
        int damage = attacker.getMaxATK() * 4 - this.getMaxDEF() * 2;
        if(damage < 0){
            damage = 0;
        }
        //element rate
        if(damage == 0){
            damage = chance.nextInt(2);
        } else if(damage > 0){
            //crit
        }
        damage = applyVariance(damage, 20);
        damage = applyGuard(damage);
        HPchange = damage;
    }
    
    public boolean isEffective(Effect item){
        if(item.getScope() == Scope.DEAD_ALLY && isDead()){
            return true;
        } else if(item.getScope() == Scope.SINGLE_ALLY && !isDead()){
            return itemTest(item);
        }
        return false;
    }
    
    private boolean itemTest(Effect item){
        if(item instanceof Consumable){
            if(calcHPRecovery((Consumable)item) > 0 && currentHP < getMaxHP()){
                return true;
            }
            if(calcMPRecovery((Consumable)item) > 0 && currentMP < getMaxMP()){
                return true;
            }
        }
        return false;
    }
    
    public void itemEffect(Item item){
        clearActionResults();
        if(item instanceof Consumable){
            HPchange -= calcHPRecovery((Consumable)item);
            MPchange -= calcMPRecovery((Consumable)item);
        }
        doDamage();
    }
    
    public void attackEffect(GameBattler attacker){
        makeAttackDamageValue(attacker);
        doDamage();
    }
    
    public int applyVariance(int damage, float variance){
        if(damage != 0){
            int amp = (int)Math.max(Math.abs(damage) * variance / 100, 0);
            damage += chance.nextInt(amp+1) + chance.nextInt(amp+1) - amp;
        }
        return damage;
    }
    
    public int applyGuard(int damage){
        if(damage > 0 && isGuarding()){
            damage /= 2;
        }
        return damage;
    }
    
    public int calcHPRecovery(Consumable item){
        int result = (int) ((getMaxHP() * (item.getHPrate() / 100)) + item.getHPamount());
        return result;
    }
    
    public int calcMPRecovery(Consumable item){
        int result = (int) ((getMaxMP() * (item.getMPrate() / 100)) + item.getMPamount());
        return result;
    }
    
    private boolean isGuarding() {
        return action.isGuard();
    }
    
    public int getMaxHP(){
        return MathHelper.clamp(stats.getBaseHP() + HPplus, 1, HP_LIMIT);
    }
    
    public int getMaxMP(){
        return MathHelper.clamp(stats.getBaseMP() + MPplus, 1, 9999);
    }
    
    public int getMaxATK(){
        return MathHelper.clamp(stats.getBaseATK() + ATKplus, 1, 999);
    }
    
    public int getMaxMATK(){
        return MathHelper.clamp(stats.getBaseMATK() + MATKplus, 1, 999);
    }
    
    public int getMaxDEF(){
        return MathHelper.clamp(stats.getBaseDEF() + DEFplus, 1, 999);
    }
    
    public int getMaxMDEF(){
        return MathHelper.clamp(stats.getBaseMDEF() + MDEFplus, 1, 999);
    }
    
    public int getMaxSPD(){
        return MathHelper.clamp(stats.getBaseSPD() + SPDplus, 1, 999);
    }
    
    public int getATKplus() {
        return ATKplus;
    }
    
    public void setATKplus(int atk) {
        ATKplus = atk;
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