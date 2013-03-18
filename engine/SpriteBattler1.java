/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Kieran
 */
public class SpriteBattler1 extends SpriteBase {

    public GameBattler battler;
    private Animation currentAni;
    private float moveSpeedX = 20f, moveSpeedY = 20f, moveSpeedZ = 20f;
    private boolean animationDone;
    private boolean moveDone;
    public float moveX;
    public float moveY;
    public float moveZ;
    public float basePosX, basePosY, basePosZ;
    public float deltaX, deltaY, deltaZ;
    public float distX;
    public float distY;
    public float distZ;
    public SpriteBattler1 target;
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

    public SpriteBattler1() {
    }

    public SpriteBattler1(GameBattler b, String sprite) {
        battler = b;
        Image i = GameCache.res(sprite + ".png");
        image = new SpriteSheet(i, i.getWidth() / 4, i.getHeight() / 11);
        states = new Animation[]{
            new Animation(image, 0, 0, 3, 0, true, 160, false),//IDLE(0)
            new Animation(image, 0, 1, 3, 1, true, 120, false),//DAMAGE(1)
            new Animation(image, 0, 2, 3, 2, true, 120, false),//CRITICAL(2)
            new Animation(image, 0, 3, 3, 3, true, 120, false),//GUARD(3)
            new Animation(image, 0, 4, 3, 4, true, 120, false),//APPROACH(4)
            new Animation(image, 0, 5, 3, 5, true, 120, false),//RETREAT(5)
            new Animation(image, 0, 6, 3, 6, true, 120, false),//ATTACK_HEAVY(6)
            new Animation(image, 0, 7, 3, 7, true, 120, false),//ATTACK_LIGHT(7)
            new Animation(image, 0, 8, 3, 8, true, 120, false),//CAST(8)
            new Animation(image, 0, 9, 3, 9, true, 120, false),//VICTORY(9)
            new Animation(image, 0, 10, 3, 10, true, 120, false),//COLLAPSE(10)
        };
        Animation[] attack = new Animation[]{ states[APPROACH], states[ATTACK_HEAVY], states[RETREAT]};
        Animation test = new Animation(image, attack);
        states[ATTACK_HEAVY].setLooping(false);
        currentAni = states[IDLE];
    }
    
    

    public void render(Graphics g, float x, float y, float scale) {
        int width = getCurrentAni().getWidth();
        int height = getCurrentAni().getHeight();

        // get scaled draw coordinates (sx, sy)
        float sx = x - (width * scale / 2);
        float sy = y - (height * scale / 2);

        getCurrentAni().draw(sx, sy, width * scale, height * scale);

        if (SceneBattle.DEBUG_UTIL) {
            g.setColor(Color.cyan);
            g.drawLine(x + 10, y, x - 10, y);
            g.drawLine(x, y + 10, x, y - 10);
        }
    }

    public Animation getCurrentAni() {
        return currentAni;
    }
    
    public boolean isStopped(){
        return currentAni.isStopped();
    }
    
    public void updateMove(float delta) {
        distX += ((deltaX < 0 ? deltaX : -deltaX)*16f)/delta;
        distY += ((deltaY < 0 ? deltaY : -deltaY)*16f)/delta;
        distZ += ((deltaZ < 0 ? deltaZ : -deltaZ)*16f)/delta;
        if (distX <= 0 && distY <= 0 && distZ <= 0) {
            moveDone = true;
            distX = 0;
            distY = 0;
            distZ = 0;
            deltaX = 0;
            deltaY = 0;
            deltaZ = 0;
        }
        moveX += (deltaX*16f)/delta;
        moveY += (deltaY*16f)/delta;
        moveZ += (deltaZ*16f)/delta;
    }

    public void toPoint(float x, float y, float z) {
        moveDone = false;
        currentAni = states[APPROACH];
        distX = Math.abs(basePosX - x);
        distY = Math.abs(basePosY - y);
        distZ = Math.abs(basePosZ - z);
        deltaX = distX / moveSpeedX * (x - basePosX < 0 ? -1 : 1);
        deltaY = distY / moveSpeedY * (y - basePosY < 0 ? -1 : 1);
        deltaZ = distZ / moveSpeedZ * (z - basePosZ < 0 ? -1 : 1);
    }

    public void moveAmount(float x, float y, float z) {
        moveDone = false;
        currentAni = states[APPROACH];
        distX = x;
        distY = y;
        distZ = z;
        deltaX = distX / moveSpeedX * (x > 0 ? -1 : 1);
        deltaY = distY / moveSpeedY * (y > 0 ? -1 : 1);
        deltaZ = distZ / moveSpeedZ * (z > 0 ? -1 : 1);
    }

    public void retreat() {
        currentAni = states[RETREAT];
        distX = Math.abs(basePosX - posX());
        distY = Math.abs(basePosY - posY());
        distZ = Math.abs(basePosZ - posZ());
        deltaX = distX / moveSpeedX * (posX() - basePosX < 0 ? 1 : -1);
        deltaY = distY / moveSpeedY * (posY() - basePosY < 0 ? 1 : -1);
        deltaZ = distZ / moveSpeedZ * (posZ() - basePosZ < 0 ? 1 : -1);
    }

    public void attack() {
        animationDone = false;
        currentAni = states[ATTACK_HEAVY];
    }
    
    public void idle(){
        currentAni = states[IDLE];
    }

    public float posX() {
        return basePosX + moveX;
    }

    public float posY() {
        return basePosY + moveY;
    }

    public float posZ() {
        return basePosZ + moveZ;
    }

    public void update(int delta) {
        currentAni.update(delta);
        updateMove(delta);   
    }
}
