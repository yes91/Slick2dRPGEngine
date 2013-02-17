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
public class RecoveryEffect extends Effect{
    
    private float hpRate;
    private float mpRate;
    private int hp;
    private int mp;
    
    public RecoveryEffect(int hp, float hpRate, int mp, int mpRate){
        this.hp = hp;
        this.mp = mp;
        this.hpRate = hpRate;
        this.mpRate = mpRate;
    }

    @Override
    public void activate(Object targets) {
        if(targets instanceof List){
        for(GameBattler b: (List<GameBattler>)targets){
            b.currentHP += hp;
            b.currentHP += (int)((float)b.currentHP * hpRate);
            if(b.currentHP > b.getMaxHP()){
                b.currentHP = b.getMaxHP();
            }
            b.currentMP += mp;
            b.currentMP += (int)((float)b.currentMP * mpRate);
            if(b.currentMP > b.getMaxMP()){
                b.currentMP = b.getMaxMP();
            }
        }
        }
        else if(targets instanceof GameBattler){
            GameBattler b = (GameBattler)targets;
            b.currentHP += hp;
            b.currentHP += (int)((float)b.currentHP * hpRate);
            if(b.currentHP > b.getMaxHP()){
                b.currentHP = b.getMaxHP();
            }
            b.currentMP += mp;
            b.currentMP += (int)((float)b.currentMP * mpRate);
            if(b.currentMP > b.getMaxMP()){
                b.currentMP = b.getMaxMP();
            }
        }
        
    }

    @Override
    public void deactivate() {
        
    }
    
    
    
}
