/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import engine.Effect.Scope;
import java.io.Serializable;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 *
 * @author redblast71
 */
public abstract class GameBattler implements Serializable{
    
    private final int HP_LIMIT = 999999;
    private Random chance = new Random();
    public transient SpriteBattler battleSprite = null;
    public String spriteName;
    public BattleStats stats;
    public float moveX, moveY, moveZ;
    public float basePosX, basePosY, basePosZ;
    public GameBattleAction action;
    public int currentHP;
    public int currentMP;
    public int HPchange;
    public int MPchange;
    public boolean skipped;
    public boolean missed;
    public boolean evaded;
    public boolean critical;
    public boolean absorbed;
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
        stats = new BattleStats();
        battleSprite = new SpriteBattler(this, sprite);
        action = new GameBattleAction(this);
    }
    
    public void render(Graphics g, float x, float y, float scale){
        int width = battleSprite.getCurrentAni().getWidth();
        int height = battleSprite.getCurrentAni().getHeight();
        
        // get scaled draw coordinates (sx, sy)
        float sx = x - (width * scale / 2);
        float sy = y - (height * scale / 2);
        
        battleSprite.getCurrentAni().draw(sx, sy, width * scale, height * scale);
        
        if(SceneBattle.DEBUG_UTIL){
            g.setColor(Color.cyan);
            g.drawLine(x + 10, y, x - 10, y);
            g.drawLine(x , y + 10, x, y - 10);
        }
        
    }
    
    public void toPoint(float x, float y, float z){
        battleSprite.distX = Math.abs(basePosX - x);
        battleSprite.distY = Math.abs(basePosY - y);
        battleSprite.distZ = Math.abs(basePosZ - z);
        battleSprite.deltaX = battleSprite.distX/20f * (x - basePosX < 0 ? -1:1);
        battleSprite.deltaY = battleSprite.distY/20f * (y - basePosY < 0 ? -1:1);
        battleSprite.deltaZ = battleSprite.distZ/20f * (z - basePosZ < 0 ? -1:1);
    }
    
    public void moveAmount(float x, float y, float z){
        battleSprite.distX = x;
        battleSprite.distY = y;
        battleSprite.distZ = z;
        battleSprite.deltaX = battleSprite.distX/20f * (x > 0 ? -1:1);
        battleSprite.deltaY = battleSprite.distY/20f * (y > 0 ? -1:1);
        battleSprite.deltaZ = battleSprite.distZ/20f * (z > 0 ? -1:1);
    }
    
    public void retreat(){
        battleSprite.distX = Math.abs(basePosX - posX());
        battleSprite.distY = Math.abs(basePosY - posY());
        battleSprite.distZ = Math.abs(basePosZ - posZ());
        battleSprite.deltaX = battleSprite.distX/20f * (posX() - basePosX < 0 ? 1:-1);
        battleSprite.deltaY = battleSprite.distY/20f * (posY() - basePosY < 0 ? 1:-1);
        battleSprite.deltaZ = battleSprite.distZ/20f * (posZ() - basePosZ < 0 ? 1:-1);
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
    
    public void clearActionResults(){
        HPchange = 0;
        MPchange = 0;
        skipped = false;
        missed = false;
        evaded = false;
        critical = false;
        absorbed = false;
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
    
    public boolean isActor(){
        return false;
    }
    
    public void doDamage(){
        this.currentHP -= HPchange;
        this.currentMP -= MPchange;
        currentHP = Math.max(Math.min(getMaxHP(), currentHP), 0);
        currentMP = Math.max(Math.min(getMaxMP(), currentMP), 0);
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
        applyVariance(damage, 20);
        applyGuard(damage);
        HPchange = damage;
    }
    
    public boolean isEffective(Item item){
        if(item.getScope() == Scope.DEAD_ALLY && isDead()){
            return true;
        } else if(item.getScope() == Scope.SINGLE_ALLY && !isDead()){
            return itemTest(item);
        }
        return false;
    }
    
    private boolean itemTest(Item item){
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
    
    public float posX(){
        return basePosX + moveX; 
    }
    
    public float posY(){
        return basePosY + moveY;
    }
    
    public float posZ(){
        return basePosZ + moveZ;
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