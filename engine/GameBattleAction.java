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

    private enum ActionType {

        BASIC, SKILL, ITEM
    }

    private enum BasicType {

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

    public List getAllyUnit() {
        if (battler.isActor()) {
            return SceneBase.gameParty.getMembers();
        }
        return SceneBase.gameTroop.getMembers();
    }

    public List getEnemyUnit() {
        if (!battler.isActor()) {
            return SceneBase.gameParty.getMembers();
        }
        return SceneBase.gameTroop.getMembers();
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
        return kind.equals(ActionType.BASIC)
                && basic.equals(BasicType.ATTACK);
    }
    
    public boolean isGuard(){
        return kind.equals(ActionType.BASIC)
                && basic.equals(BasicType.GUARD);
    }
    
    public boolean isNothing(){
        return kind.equals(ActionType.BASIC)
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
    
    public Item getItem(){
        return isItem() ? ItemReader.items.get(itemID): null;
    }
    
    
    
}
