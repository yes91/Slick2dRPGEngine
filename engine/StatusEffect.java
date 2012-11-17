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
public class StatusEffect extends Effect {
    
    private String name;

	public StatusEffect(String name, Item.Targets target, int targetArraySize){
        this.target = target;
        this.name = name; 
    }

	@Override
	public void activate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void activate(List<GameBattler> targets) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivate(List<GameBattler> targets) {
		// TODO Auto-generated method stub
		
	}
    
}
