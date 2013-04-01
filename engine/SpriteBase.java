/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import engine.EffectAnimation.Frame;
import engine.EffectAnimation.Frame.Cell;
import engine.EffectAnimation.Timing;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Kieran
 */
public class SpriteBase extends Sprite{
    
    protected Animation[] states;
    
    private static List<EffectAnimation> animations = new ArrayList<>();
    
    protected List<Sprite> animationSprites;
    private EffectAnimation animation;
    private Image animationImage;
    private boolean animationMirror;
    protected int animationOX, animationOY;
    private boolean useSprite;
    private int animationDuration;
    
    public SpriteBase(){
        super();
        useSprite = true;
        animationDuration = 0;
        animationSprites = new ArrayList<>();
    }
    
    public void update(){
        if(animation != null){
            animationDuration -= 1;
            if(animationDuration % 4 == 0){
                updateAnimation();
            }
        }
        animations.clear();
    }
    
    public void startAnimation(EffectAnimation animation, boolean mirror){
        this.animation = animation;
        animationMirror = mirror;
        if(this.animation == null) return;
        loadAnimationImage();
        animationDuration = this.animation.frameMax * 4 + 1;
        animationSprites = new ArrayList<>();
        if(animation.position != 3 || !animations.contains(this.animation)){
            if(useSprite){
                for(int i = 0; i <= 15; i++){
                    Sprite sprite = new Sprite();
                    sprite.visible = false;
                    animationSprites.add(sprite);
                }
                if(!animations.contains(this.animation)){
                    animations.add(this.animation);
                }
            }
        }
    }
    
    public void loadAnimationImage(){
        this.animationImage = GameCache.animation(this.animation.animationName+".png");
    }
    
    public void updateAnimation(){
        if(animationDuration > 0){
            int frameIndex = animation.frameMax - (animationDuration + 3) / 4;
            animationSetSprites(animation.frames.get(frameIndex));
            for(Timing t : animation.timings){
                if(t.frame == frameIndex){
                    processAnimationTimings(t);
                }
            }
        } else {
            animationSprites.clear();
            animation = null;
        }
    }
    
    public void processAnimationTimings(Timing t){
        GameCache.se(t.se).play();
    }
    
    public void animationSetSprites(Frame frame){
        for(int i = 0; i <= frame.cellData.size() - 1; i++){
            Cell cell = frame.cellData.get(i);
            Sprite sprite = animationSprites.get(i);
            if(sprite == null) continue;
            int pattern = cell.pattern;
            if(pattern == -1){
                sprite.visible = false;
                continue;
            }
            if(pattern < 100){
                sprite.image = new SpriteSheet(animationImage, 1, 1);
            } else {
                
            }
            sprite.visible = true;
            sprite.srcRect.setBounds(pattern % 5 * 192, pattern % 100 / 5 * 192, 192, 192);
            if(animationMirror){
                sprite.pos.x = animationOX - cell.x;
                sprite.pos.y = animationOY + cell.y;
            } else {
                sprite.pos.x = animationOX + cell.x;
                sprite.pos.y = animationOY + cell.y;
            }
            sprite.opacity = this.opacity * cell.opacity;
            sprite.blendType = cell.blendType;
        }
    }
    
}
