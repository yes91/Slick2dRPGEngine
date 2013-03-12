/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;


/**
 *
 * @author Kieran
 */
public class SpriteBattler extends SpriteBase{
    
    private GameBattler battler;
    private Animation currentAni;
    
    //public AnimationSequence aSeq;
    
    public float moveX;
    public float moveY;
    public float moveZ;
    public float deltaX, deltaY, deltaZ;
    public float distX;
    public float distY;
    public float distZ;
    public int pose;
    
    private static final int IDLE = 0;
    private static final int DAMAGE = 1;
    private static final int CRITICAL = 2;
    private static final int GUARD = 3;
    private static final int APPROACH = 4;
    private static final int RETREAT = 5;
    private static final int ATTACK_HEAVY = 6;
    private static final int ATTACK_LIGHT = 7;
    private static final int CAST = 8;
    private static final int VICTORY = 9;
    private static final int COLLAPSE = 10;
    
    public SpriteBattler(){
        
    }
    
    public SpriteBattler(GameBattler b, String sprite){
        battler = b;
        Image i = GameCache.res(sprite);
        image = new SpriteSheet(i, i.getWidth()/4, i.getHeight()/11);
        states = new Animation[]{
            new Animation(image, 0, 0, 3, 0, true, 160, true),//IDLE(0)
            new Animation(image, 0, 1, 3, 1, true, 120, true),//DAMAGE(1)
            new Animation(image, 0, 2, 3, 2, true, 120, true),//CRITICAL(2)
            new Animation(image, 0, 3, 3, 3, true, 120, true),//GUARD(3)
            new Animation(image, 0, 4, 3, 4, true, 120, true),//APPROACH(4)
            new Animation(image, 0, 5, 3, 5, true, 120, true),//RETREAT(5)
            new Animation(image, 0, 6, 3, 6, true, 120, true),//ATTACK_HEAVY(6)
            new Animation(image, 0, 7, 3, 7, true, 120, true),//ATTACK_LIGHT(7)
            new Animation(image, 0, 8, 3, 8, true, 120, true),//CAST(8)
            new Animation(image, 0, 9, 3, 9, true, 120, true),//VICTORY(9)
            new Animation(image, 0, 10, 3, 10, true, 120, true),//COLLAPSE(10)
        };
        /*List<Animation> seq;
        seq = new ArrayList<>();
        seq.add(states[IDLE]);
        seq.add(states[APPROACH]);
        seq.add(states[ATTACK_HEAVY]);
        seq.add(states[RETREAT]);
        aSeq = new AnimationSequence(seq, new int[]{ 500, 500, 500, 500}, new float[][]{ 
            new float[]{0.0f, 0.0f},
            new float[]{-20.0f, 0.0f},
            new float[]{0.0f, 0.0f},
            new float[]{20.0f, 0.0f}
        });*/
        //states[ATTACK_HEAVY].setLooping(false);
        //states[ATTACK_LIGHT].setLooping(false);
        currentAni = states[IDLE];
    }
    
    public Animation getCurrentAni() {
        return currentAni;
    }
    
    public void updateMove(){
        distX += (deltaX < 0 ? deltaX:-deltaX);
        distY += (deltaY < 0 ? deltaY:-deltaY);
        distZ += (deltaZ < 0 ? deltaZ:-deltaZ);
        if(distX <= 0 && distY <= 0 && distZ <= 0){
            distX = 0;
            distY = 0;
            distZ = 0;
            deltaX = 0;
            deltaY = 0;
            deltaZ = 0;
        }
        moveX += deltaX;
        moveY += deltaY;
        moveZ += deltaZ;
        battler.moveX = moveX;
        battler.moveY = moveY;
        battler.moveZ = moveZ;
    }
    
    public void toPoint(float x, float y, float z){
        distX = Math.abs(battler.basePosX - x);
        distY = Math.abs(battler.basePosY - y);
        distZ = Math.abs(battler.basePosZ - z);
        deltaX = distX/20f * (x - battler.basePosX < 0 ? -1:1);
        deltaY = distY/20f * (y - battler.basePosY < 0 ? -1:1);
        deltaZ = distZ/20f * (z - battler.basePosZ < 0 ? -1:1);
    }
    
    public void moveAmount(float x, float y, float z){
        distX = x;
        distY = y;
        distZ = z;
        deltaX = distX/20f * (x > 0 ? -1:1);
        deltaY = distY/20f * (y > 0 ? -1:1);
        deltaZ = distZ/20f * (z > 0 ? -1:1);
    }
    
    public void retreat(){
        distX = Math.abs(battler.basePosX - battler.posX());
        distY = Math.abs(battler.basePosY - battler.posY());
        distZ = Math.abs(battler.basePosZ - battler.posZ());
        deltaX = distX/20f * (battler.posX() - battler.basePosX < 0 ? 1:-1);
        deltaY = distY/20f * (battler.posY() - battler.basePosY < 0 ? 1:-1);
        deltaZ = distZ/20f * (battler.posZ() - battler.basePosZ < 0 ? 1:-1);
    }
    
    public void updateAnimationState(){
        updateMove();
        if(SceneBattle.DEBUG_UTIL){
            //currentAni = states[gBattler.stateTest];
            if(battler.isDead()){
                currentAni = states[COLLAPSE];
            } else if(deltaX > 0){
                currentAni = states[RETREAT];
            } else if(deltaX < 0){
                currentAni = states[APPROACH];
            } else{
                currentAni = states[IDLE];
            }
        } else {
            if(battler.isDead()){
                currentAni = states[COLLAPSE];
            } else if(deltaX > 0 || deltaY > 0 || deltaZ > 0){
                currentAni = states[RETREAT];
            } else if(deltaX < 0 || deltaY < 0 || deltaZ < 0){
                currentAni = states[APPROACH];
            } else{
                currentAni = states[IDLE];
            }
        }
    }
}
