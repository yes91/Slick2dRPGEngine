/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.List;
import org.lwjgl.Sys;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

/**
 *
 * @author redblast71
 */
public class AnimationSequence {
    
    private float x, y;
    
    private List<Animation> sequence;
    private int currentAni = -1;
    private long nextChange = 0;
    private int[] durations;
    private float[][] deltaPos;
    private long lastUpdate;
    private boolean firstUpdate;
    
    public AnimationSequence(List<Animation> seq, int[] frames, float[][] deltaPos){
        sequence = seq;
        durations = frames;
        this.deltaPos = deltaPos;
    }
    
    public void play(float x0, float y0){
        long now = getTime();
	long delta = now - lastUpdate;
	if (firstUpdate) {
            delta = 0;
            firstUpdate = false;
	}
        lastUpdate = now;
	nextAni(delta);
        
        x += deltaPos[currentAni][0];
        y += deltaPos[currentAni][1];
        
        if(sequence.get(currentAni).isStopped()){
            sequence.get(currentAni).restart();
        }
        
        sequence.get(currentAni).draw(x0 + x, y0 + y);
    }

    private long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    private void nextAni(long delta) {
		
		nextChange -= delta;
		
		while (nextChange < 0) {
			currentAni = (currentAni + 1) % sequence.size();
                        if(currentAni == 0){
                            x = 0;
                            y = 0;
                        }
			int realDuration = (int) (durations[currentAni]);
			nextChange = nextChange + realDuration;
		}
    }
    
    
}
