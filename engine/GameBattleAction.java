/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.List;

/**
 *
 * @author redblast71
 */
public class GameBattleAction {

    public enum ActionType {

        BASIC, SKILL, ITEM
    }

    public enum BasicType {

        ATTACK, GUARD, ESCAPE, WAIT
    }
    private GameBattler battler;
    private int speed;
    private ActionType kind;
    private BasicType basic;
    private int skillID;
    private int itemID;
    public int targetIndex;

    public GameBattleAction(GameBattler battler) {
        this.battler = battler;
        clear();
    }

    public GameUnit getAllyUnit() {
        if (battler.isActor()) {
            return SceneBase.gameParty;
        }
        return SceneBase.gameTroop;
    }

    public GameUnit getEnemyUnit() {
        if (!battler.isActor()) {
            return SceneBase.gameParty;
        }
        return SceneBase.gameTroop;
    }

    public final void clear() {
        speed = 0;
        kind = ActionType.BASIC;
        basic = null;
        skillID = 0;
        itemID = 0;
        targetIndex = -1;
        //forcing = false
        //value = 0
    }

    public void setAttack() {
        kind = ActionType.BASIC;
        basic = BasicType.ATTACK;
    }

    public void setGuard() {
        kind = ActionType.BASIC;
        basic = BasicType.GUARD;
    }

    public void setSkill(int skillID) {
        kind = ActionType.SKILL;
        this.skillID = skillID;
    }

    public void setItem(int itemID) {
        kind = ActionType.ITEM;
        this.itemID = itemID;
    }

    public boolean isAttack() {
        return kind == ActionType.BASIC
                && basic == BasicType.ATTACK;
    }
    
    public boolean isGuard(){
        return kind == ActionType.BASIC
                && basic == BasicType.GUARD;
    }
    
    public boolean isNothing(){
        return kind == ActionType.BASIC
                && basic == null;
    }
    
    public boolean isSkill(){
        return kind == ActionType.SKILL;
    }
    
    public Skill getSkill(){
        return isSkill() ? GameData.skills.get(skillID): null;
    }
    
    public boolean isItem(){
        return kind == ActionType.ITEM;
    }
    
    public Consumable getItem(){
        return isItem() ? (Consumable)ItemReader.items.get(itemID): null;
    }
    
    public boolean isValid(){
        if(isNothing()){
            return false;
        }
        if(!battler.isMovable()){
            return false;
        }
        return true;
    }
    
    public boolean forAlly(){
        if(isSkill() && getSkill().forAlly()){
            return true;
        } else if(isItem() && getItem().forAlly()){
            return true;
        }
        return false;
    }
    
    public boolean forDeadAlly(){
        if(isSkill() && getSkill().forDeadAlly()){
            return true;
        } else if(isItem() && getItem().forDeadAlly()){
            return true;
        }
        return false;
    }
    
    public void randomTarget(){
        GameBattler target;
        GameUnit unit;
        if(forAlly()){
            unit = getAllyUnit();
            target = unit.getRandomTarget();
        } else if(forDeadAlly()){
            unit = getAllyUnit();
            target = unit.getRandomDeadTarget();
        } else {
            unit = getEnemyUnit();
            target = unit.getRandomTarget();
        }
        if(target == null){
            clear();
        } else {
            targetIndex = unit.getMembers().indexOf(target);
        }
    }
    
}
