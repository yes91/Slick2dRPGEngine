/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.Arrays;
import java.util.LinkedList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author Kieran
 */
public class SpriteBattler extends SpriteBase {

    public GameBattler battler;
    public boolean active;
    private LinkedList<String> action;
    private Object[] activeAction;
    private String repeatAction = "WAIT";
    
    private int wait;
    private boolean animeEnd;
    private boolean patternBack;
    private boolean nonRepeat;
    private boolean reverse;
    private Rectangle srcRect;
    private float moveSpeedX, moveSpeedY, moveSpeedZ;
    private int pattern;
    private int animeKind;
    private int animeSpeed;
    private int animeLoop;
    private int frame;
    private int width;
    private int height;
    
    public float moveX;
    public float moveY;
    public float moveZ;
    public float basePosX, basePosY, basePosZ;
    public float deltaX, deltaY, deltaZ;
    public float distX;
    public float distY;
    public float distZ;
    public SpriteBattler target;
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

    public SpriteBattler() {
    }

    public SpriteBattler(GameBattler b, String sprite) {
        battler = b;
        Image i = GameCache.res(sprite + ".png");
        image = new SpriteSheet(i, i.getWidth() / 4, i.getHeight() / 11);
        width = i.getWidth() / 4;
        height = i.getHeight() / 11;
        srcRect = new Rectangle(0, 0, width, height);
        pattern = 0;
        action = new LinkedList();
        startAction("BATTLE_START");
    }
    
    

    public void render(Graphics g, float x, float y, float scale) {
        /*
        int width = getCurrentAni().getWidth();
        int height = getCurrentAni().getHeight();

        // get scaled draw coordinates (sx, sy)
        float sx = x - (width * scale / 2);
        float sy = y - (height * scale / 2);

        getCurrentAni().draw(sx, sy, width * scale, height * scale);*/

        
        
        float sx = x - (width * scale / 2);
        float sy = y - (height * scale / 2);
        if(battler instanceof GameEnemy){
            image.draw(sx, sy, sx + width * scale, sy + height * scale, srcRect.getWidth(), srcRect.getY(), srcRect.getX(), srcRect.getHeight());
        } else {
            image.draw(sx, sy, sx + width * scale, sy + height * scale, srcRect.getX(), srcRect.getY(), srcRect.getWidth(), srcRect.getHeight());
        }
        
        if (SceneBattle.DEBUG_UTIL) {
            g.setColor(Color.cyan);
            g.drawLine(x + 10, y, x - 10, y);
            g.drawLine(x, y + 10, x, y - 10);
        }
    }
    
    public void startAction(String kind){
        
        reset();
        
        standBy();
        
        action.clear();
        try{
        action.addAll(Arrays.asList(Actions.ACTION.get(kind)));
        } finally{
            if(action.isEmpty()){
                System.err.println("An error has occurred."
                +"Action Sequence Key "+kind+" does not exist."
                +"Define it first or make sure it is spelled correctly.");
            }
        }
        String active = action.poll();
        action.addLast("End");
        activeAction = Animes.ANIME.get(active);
        if(activeAction == null){
            wait = Integer.parseInt(active);
        }
        
        action();
    }
    
    public void nextAction(){
        
        if(wait > 0){
            wait -= 1;
            return;
        }
        
        if(!animeEnd){
            return;
        }
        
        String active = action.poll();
        activeAction = Animes.ANIME.get(active);
        if(activeAction == null && active != null){
            wait = Integer.parseInt(active);
        }
        
        action();
    }
    
    public void standBy(){
        repeatAction = "WAIT";
        
        if(battler.currentHP < battler.getMaxHP()/4){
            repeatAction = "WAIT-CRITICAL";
        }
        if(battler.isDead()){
            repeatAction = "DEAD";
        }
    }
    
    public void nonRepeat(){
        repeatAction = null;
        nonRepeat = true;
        animeFinish();
    }
    
    public void action() {
        if (activeAction == null) {
            return;
        }
        Object act = activeAction[0];
        if (act instanceof String) {
            switch ((String) act) {
                case "End":
                    animeFinish();
                    return;
                case "Don't Wait":
                    nonRepeat();
                    return;
                case "Can Collapse":
                    sendAction(act);
                    return;
                case "anime":
                    battleAnime();
                    return;
                case "Two Wpn Only":
                    twoSwords();
                    return;
                case "One Wpn Only":
                    nonTwoSwords();
                    return;
            }
        }
        switch (activeAction.length) {
            case 9:
                battlerAnime();
                return;
            case 5:
                reseting();
                return;
            case 7:
                moving();
                return;
        }
    }
    
    public void twoSwords(){
        
        String active = action.poll();
        
        if(!battler.isActor()){
            return;
        } else if(((GameActor)battler).getWeapons().size() != 2){
            return;
        }
        
        activeAction = Animes.ANIME.get(active);
        
        if(activeAction == null && active != null){
            wait = Integer.parseInt(active);
        }
        
        action();
    }
    
    public void nonTwoSwords(){
        
        String active = action.poll();
        
        if(!battler.isActor()){
            return;
        } else if(((GameActor)battler).getWeapons().size() > 1){
            return;
        }
        
        activeAction = Animes.ANIME.get(active);
        
        if(activeAction == null && active != null){
            wait = Integer.parseInt(active);
        }
        
        action();
    }
    
    public void reset(){
        animeEnd = true;
    }
    
    public void reseting(){
        moveSpeedX = (int)activeAction[1];
        moveSpeedY = (int)activeAction[1];
        moveSpeedZ = (int)activeAction[1];
        
        retreat();
        Object[] moveAnime = Animes.ANIME.get((String)activeAction[4]);
        wait = 1;
        if(moveAnime != null){
            activeAction = moveAnime;
            battlerAnime();
        }
        animeEnd = true;
    }
    
    public void moving(){
        
        moveSpeedX = (int)activeAction[3];
        moveSpeedY = (int)activeAction[3];
        moveSpeedZ = (int)activeAction[3];
        
        if(target == null){
            moveAmount((int)activeAction[1], (int)activeAction[2], 0);
        } else {
            int inFront = (target.posX() - posX() > 0) ? -100:100;
            toPoint(target.posX() + inFront, target.posY(), target.posZ());
        }
        
        Object[] moveAnime = Animes.ANIME.get((String)activeAction[6]);
        wait = 1;
        if(moveAnime != null){
            activeAction = moveAnime;
            battlerAnime();
        }
        animeEnd = true;
    }
    
    public void animeFinish(){
        
        if(active){
            sendAction(activeAction[0]);
        }
        if(!nonRepeat){
            startAction(repeatAction);
        }
    }
    
    public void sendAction(Object action){
        if(action instanceof Object[]){
           battler.play = (Object[])action;
        } else {
            battler.play = new Object[]{action};
        }
    }
    
    public void battlerAnime(){
        animeKind = (int)activeAction[1];
        animeSpeed = (int)activeAction[2];
        animeLoop = (int)activeAction[3];
        
        reverse = false;
        
        animeEnd = true;
        
        animeEnd = false;
        
        patternBack = false;
        
        frame = animeSpeed * 16;
        
        float sx = pattern * width;
        float sy = animeKind * height;
        srcRect.setBounds(sx, sy, sx + width, sy + height);
    }
    
    public void battleAnime(){
        
        if(!battler.isActor() && (boolean)activeAction[5]){
            return;
        }
        if((boolean)activeAction[5] && (((GameActor)battler).getWeapons().size() < 2)){
            return;
        }
        int animeID = (int)activeAction[1];
        
        Object[] damageAction = new Object[]{ animeID, false, true };
        battler.play = new Object[]{ "OBJ_ANIM", damageAction };
        return; 
        //wait = 24;
    }

    public void updateMove(float delta) {
        distX += ((deltaX < 0 ? deltaX : -deltaX)*16f)/delta;
        distY += ((deltaY < 0 ? deltaY : -deltaY)*16f)/delta;
        distZ += ((deltaZ < 0 ? deltaZ : -deltaZ)*16f)/delta;
        if (distX <= 0 && distY <= 0 && distZ <= 0) {
            distX = 0;
            distY = 0;
            distZ = 0;
            deltaX = 0;
            deltaY = 0;
            deltaZ = 0;
        } else {
            animeEnd = false;
        }
        moveX += (deltaX*16f)/delta;
        moveY += (deltaY*16f)/delta;
        moveZ += (deltaZ*16f)/delta;
    }

    public void toPoint(float x, float y, float z) {
        distX = Math.abs(basePosX - x);
        distY = Math.abs(basePosY - y);
        distZ = Math.abs(basePosZ - z);
        deltaX = distX / moveSpeedX * (x - basePosX < 0 ? -1 : 1);
        deltaY = distY / moveSpeedY * (y - basePosY < 0 ? -1 : 1);
        deltaZ = distZ / moveSpeedZ * (z - basePosZ < 0 ? -1 : 1);
    }

    public void moveAmount(float x, float y, float z) {
        distX = Math.abs(x);
        distY = Math.abs(y);
        distZ = Math.abs(z);
        deltaX = distX / moveSpeedX * (x < 0 ? -1 : 1);
        deltaY = distY / moveSpeedY * (y < 0 ? -1 : 1);
        deltaZ = distZ / moveSpeedZ * (z < 0 ? -1 : 1);
    }

    public void retreat() {
        distX = Math.abs(basePosX - posX());
        distY = Math.abs(basePosY - posY());
        distZ = Math.abs(basePosZ - posZ());
        deltaX = distX / moveSpeedX * (posX() - basePosX < 0 ? 1 : -1);
        deltaY = distY / moveSpeedY * (posY() - basePosY < 0 ? 1 : -1);
        deltaZ = distZ / moveSpeedZ * (posZ() - basePosZ < 0 ? 1 : -1);
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
        
        frame -= delta;
        
        //battlerJoin();
        
        nextAction();
        
        updateAnimePattern();
        
        //updateTarget();
        
        //updateForceAction
        
        updateMove(delta);
        
        //if (animeMoving) updateMoveAnime();
        
        //if (damage != null) damage.update();
        
        
    }
    
    public void updateAnimePattern() {
        if (frame <= 0) {
            int end = image.getHorizontalCount() - 1;
            frame = animeSpeed * 16;
            if (patternBack) {
                if(animeLoop == 0){
                    if(reverse){
                        pattern += 1;
                        if (pattern >= end){
                            if(pattern > end){
                                pattern = end;
                            }
                            patternBack = false; 
                            animeEnd = true;
                        }
                    } else {
                        pattern -= 1;
                        if (pattern <= 0){
                            if(pattern < 0){
                                pattern = 0;
                            }
                            patternBack = false; 
                            animeEnd = true;
                        }
                    }
                } else {
                    animeEnd = true;
                    if(animeLoop == 1){
                        if(!reverse) { 
                            pattern = 0;
                        } else {
                            pattern = end;
                        }
                        patternBack = false;
                    }
                }

            } else {
                if(reverse){
                    pattern -= 1;
                    if(pattern <= 0){
                        if(pattern < 0){
                            pattern = 0;
                        }
                        patternBack = true;
                    }
                } else {
                    pattern += 1;
                    if(pattern >= end){
                        if(pattern > end){
                            pattern = end;
                        }
                        patternBack = true;
                    }
                }
            }
            float sx = pattern * width;
            float sy = animeKind * height;
            srcRect.setBounds(sx, sy, sx + width, sy + height);
        }
    }
}
