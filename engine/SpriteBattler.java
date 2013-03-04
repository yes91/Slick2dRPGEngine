/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Kieran
 */
public class SpriteBattler extends SpriteBase{
    private GameBattler gBattler;
    private Animation currentAni;
    
    private static final int IDLE = 0;
    private static final int DAMAGE = 1;
    private static final int KNOCKBACK = 2;
    private static final int GUARD = 3;
    private static final int APPROACH = 4;
    private static final int RETREAT = 5;
    private static final int ATTACK_HEAVY = 6;
    private static final int ATTACK_LIGHT = 7;
    private static final int CAST = 8;
    private static final int VICTORY = 9;
    private static final int COLLAPSE = 10;
    
    public SpriteBattler(GameBattler b, Image i){
        gBattler = b;
        image = new SpriteSheet(i, i.getWidth()/4, i.getHeight()/11);
        states = new Animation[]{
            new Animation(image, 0, 0, 3, 0, true, 160, true),//IDLE(0)
            new Animation(image, 0, 1, 3, 1, true, 120, true),//DAMAGE(1)
            new Animation(image, 0, 2, 3, 2, true, 120, true),//KNOCKBACK(2)
            new Animation(image, 0, 3, 3, 3, true, 120, true),//GUARD(3)
            new Animation(image, 0, 4, 3, 4, true, 120, true),//APPROACH(4)
            new Animation(image, 0, 5, 3, 5, true, 120, true),//RETREAT(5)
            new Animation(image, 0, 6, 3, 6, true, 120, true),//ATTACK_HEAVY(6)
            new Animation(image, 0, 7, 3, 7, true, 120, true),//ATTACK_LIGHT(7)
            new Animation(image, 0, 8, 3, 8, true, 120, true),//CAST(8)
            new Animation(image, 0, 9, 3, 9, true, 120, true),//VICTORY(9)
            new Animation(image, 0, 10, 3, 10, true, 120, true),//COLLAPSE(10)
        };
        currentAni = states[IDLE];
    }
    
    public Animation getCurrentAni() {
        return currentAni;
    }
    
    public void updateAnimationState(){
        if(gBattler.isDead()){
            currentAni = states[COLLAPSE];
        }
        else{
            currentAni = states[IDLE];
        }
    }
}
