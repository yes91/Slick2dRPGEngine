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
    
    private enum EffectType {
        WHITEN, BLINK, APPEAR, DISAPPEAR, COLLAPSE
    }
    private EffectType effectType;
    private int effectDuration;
    
    public boolean active;
    private LinkedList<String> action;
    private Object[] activeAction;
    private String repeatAction = "WAIT";
    private int wait;
    private boolean animeEnd;
    private boolean patternBack;
    private boolean nonRepeat;
    private boolean reverse;
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
    public float blah;
    public float distX;
    public float distY;
    public float distZ;
    public SpriteBattler target;
    public int pose;

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
    
    

    public void render(Graphics g) {
        
        float scale = getScale();
        
        float sx = posX() - (width * scale / 2);
        float sy = posY() - (height * scale / 2);
        
        g.setDrawMode(blendType);
        if(battler instanceof GameEnemy){
            Image spriteFrame = image.getSubImage((int)srcRect.getX(), (int)srcRect.getY(), width, height).getFlippedCopy(true, false);
            spriteFrame.setAlpha(opacity);
            spriteFrame.draw((int)sx, (int)sy, (int)(width * scale), (int)(height * scale));
            spriteFrame.drawFlash((int)sx, (int)sy, (int)(width * scale), (int)(height * scale), color);
        } else {
            Image spriteFrame = image.getSubImage((int)srcRect.getX(), (int)srcRect.getY(), width, height);
            spriteFrame.setAlpha(opacity);
            spriteFrame.draw((int)sx, (int)sy, (int)(width * scale), (int)(height * scale));
            spriteFrame.drawFlash((int)sx, (int)sy, (int)(width * scale), (int)(height * scale), color);
        }
        g.setDrawMode(Graphics.MODE_NORMAL);
        
        for(Sprite s : animationSprites){
            s.render(g, scale);
        }
        
        if (SceneBattle.DEBUG_UTIL) {
            g.setColor(Color.cyan);
            g.drawLine(posX() + 10, posY(), posX() - 10, posY());
            g.drawLine(posX(), posY() + 10, posX(), posY() - 10);
        }
    }
    
    public float getScale(){
        return (2.0f * (posZ()/SpritesetBattle.DEPTH_BUFFER_SIZE));
    }
    
    public final void startAction(String kind){
        
        reset();
        
        standBy();
        
        action.clear();
        try{
            action.addAll(Arrays.asList(Actions.ACTION.get(kind)));
            if(action.isEmpty()){
                throw new Exception();
            }
        } catch(Exception e){
            System.err.println("An error has occurred."
            +"Action Sequence Key "+kind+" does not exist."
            +"Define it first or make sure it is spelled correctly.");
        }
        String activeAct = action.poll();
        action.addLast("End");
        activeAction = Animes.ANIME.get(activeAct);
        if(activeAction == null){
            wait = Integer.parseInt(activeAct);
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
    
    public void damageAction(Object[] action){
        int damage = battler.HPchange;
        
        if(damage > 0){
            battler.blink = true;
            startAction("HURT");
        }
    }
    
    public void twoSwords(){
        
        String activeAct = action.poll();
        
        if(!battler.isActor()){
            return;
        } else if(((GameActor)battler).getWeapons().size() != 2){
            return;
        }
        
        activeAction = Animes.ANIME.get(activeAct);
        
        if(activeAction == null && activeAct != null){
            wait = Integer.parseInt(activeAct);
        }
        
        action();
    }
    
    public void nonTwoSwords(){
        
        String activeAct = action.poll();
        
        if(!battler.isActor()){
            return;
        } else if(((GameActor)battler).getWeapons().size() > 1){
            return;
        }
        
        activeAction = Animes.ANIME.get(activeAct);
        
        if(activeAction == null && activeAct != null){
            wait = Integer.parseInt(activeAct);
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
            int inFront = (int)(((target.posX() - posX() > 0) ? -100f:100f) * target.getScale());
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
        
        if(battler instanceof GameEnemy){
            reverse = true;
        }
        
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
        
        target.battler.animationID = 1;
        
        wait = GameData.animations.get(1).frameMax * 4;
    }

    public void updateMove(float delta) {
        distX += ((deltaX < 0 ? deltaX : -deltaX)/16f)*delta;
        distY += ((deltaY < 0 ? deltaY : -deltaY)/16f)*delta;
        distZ += ((deltaZ < 0 ? deltaZ : -deltaZ)/16f)*delta;
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
        moveX += (deltaX/16f)*delta;
        moveY += (deltaY/16f)*delta;
        moveZ += (deltaZ/16f)*delta;
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
        
        super.update();
        
        frame -= delta;
        
        //battlerJoin();
        
        nextAction();
        
        updateAnimePattern();
        
        //updateTarget();
        
        //updateForceAction
        
        updateMove(delta);
        
        //if (animeMoving) updateMoveAnime();
        
        //if (damage != null) damage.update();
        setupNewEffect();
        
        updateEffect();
        
    }
    
    @Override
    public void updateAnimation(){
        animationOX = (int)(posX() - (96 * getScale()));
        animationOY = (int)(posY() - (96 * getScale()));
        super.updateAnimation();
    }
    
    public void setupNewEffect(){
        if(battler.whiteFlash){
            effectType = EffectType.WHITEN;
            effectDuration = 16;
            battler.whiteFlash = false;
        } else if (battler.blink){
            effectType = EffectType.BLINK;
            effectDuration = 20;
            battler.blink = false;
        }
        if(battler.animationID != 0){
            EffectAnimation animation = GameData.animations.get(battler.animationID);
            battler.animationID = 0;
            startAnimation(animation, false);
        }
    }
    
    public void updateEffect(){
        if(effectDuration > 0){
            effectDuration--;
            switch(effectType){
                case WHITEN: 
                    updateWhiten();
                    break;
                case BLINK:
                    updateBlink();
                    break;
                case COLLAPSE:
                    updateCollapse();
                    break;
            }
        }
    }
    
    public void updateWhiten(){
        color = new Color(255, 255, 255, 128);
        opacity = 1f;
        color.a = (128 - (16 - effectDuration) * 10)/255f;
    }
    
    public void updateBlink(){
        color = new Color(1f, 0.3f, 0.3f, 1f);
        opacity = 1f;
        color.a = (255 - (20 - effectDuration) * 12.75f)/255f;
    } 
    
    public void updateCollapse(){
        
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
