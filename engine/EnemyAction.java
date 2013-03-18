/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import engine.GameBattleAction.ActionType;
import engine.GameBattleAction.BasicType;

/**
 *
 * @author Kieran
 */
public class EnemyAction {
    
    public enum Condition {
        ALWAYS, TURN, HP, MP, STATE, LEVEL, SWITCH
    }
    
    private int speed;
    private GameBattleAction.ActionType kind;
    private GameBattleAction.BasicType basic;
    private int skillID;
    private int itemID;
    
    private Condition condition;
    private int rating;

    public ActionType getKind() {
        return kind;
    }

    public BasicType getBasic() {
        return basic;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    
    public void setAttack() {
        kind = GameBattleAction.ActionType.BASIC;
        basic = GameBattleAction.BasicType.ATTACK;
    }

    public void setGuard() {
        kind = GameBattleAction.ActionType.BASIC;
        basic = GameBattleAction.BasicType.GUARD;
    }

    public void setSkill(int skillID) {
        kind = GameBattleAction.ActionType.SKILL;
        this.skillID = skillID;
    }

    public void setItem(int itemID) {
        kind = GameBattleAction.ActionType.ITEM;
        this.itemID = itemID;
    }

    public boolean isAttack() {
        return kind == GameBattleAction.ActionType.BASIC
                && basic == GameBattleAction.BasicType.ATTACK;
    }
    
    public boolean isGuard(){
        return kind == GameBattleAction.ActionType.BASIC
                && basic == GameBattleAction.BasicType.GUARD;
    }
    
    public boolean isNothing(){
        return kind == GameBattleAction.ActionType.BASIC
                && basic == null;
    }
    
    public boolean isSkill(){
        return kind == GameBattleAction.ActionType.SKILL;
    }
    
    public Skill getSkill(){
        return isSkill() ? GameData.skills.get(skillID): null;
    }
    
    public boolean isItem(){
        return kind == GameBattleAction.ActionType.ITEM;
    }
    
    public Item getItem(){
        return isItem() ? ItemReader.items.get(itemID): null;
    }
    
}
