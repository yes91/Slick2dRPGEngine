/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import engine.GameBattleAction.ActionType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author redblast71
 */
public class GameEnemy extends GameBattler{
    
    public List<EnemyAction> actions;
    
    public GameEnemy(String battleSprite){
        super(battleSprite);
        currentHP = getMaxHP();
    }
    
    @Override
    public boolean isActor(){
        return false;
    }
    
    @Override
    public void performCollapse(){
        
    }
    
    public boolean isActionConditionMet(EnemyAction action){
        return true;
    }
    
    public void makeAction(){
        action.clear();
        if(!isMovable()){
            return;
        }
        ArrayList<EnemyAction> availableActions = new ArrayList<>();
        int maxRating = 0;
        for(EnemyAction a: actions){
            if(!isActionConditionMet(a)){
                continue;
            }
            if(a.getKind() == ActionType.SKILL){
                //continue if not skillCanUse(GameData.skills.get(a.skillID))
            }
            availableActions.add(a);
            maxRating = Math.max(maxRating, a.getRating());
        }
        int ratingsTotal = 0;
        int zeroRating = maxRating - 3;
        for(EnemyAction a: availableActions){
            if(a.getRating() <= zeroRating){
                continue;
            }
            ratingsTotal += a.getRating() - zeroRating;
        }
        if(ratingsTotal == 0){
            return;
        }
        int value = chance.nextInt(ratingsTotal);
        for(EnemyAction a: availableActions){
            if(a.getRating() <= zeroRating){
                continue;
            }
            if(value < a.getRating() - zeroRating){
                if(a.isAttack()){
                    action.setAttack();
                } else if(a.isGuard()){
                    action.setGuard();
                } else if(a.isItem()) {
                    action.setItem(GameData.items.indexOf(a.getItem()));
                } else if(a.isSkill()){
                    action.setSkill(GameData.skills.indexOf(a.getSkill()));
                }
                action.randomTarget();
                return;
            } else {
                value -= a.getRating() - zeroRating;
            }
        } 
    }
}
