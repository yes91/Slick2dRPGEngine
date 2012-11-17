package engine;

import java.util.List;

/**
 * 
 * @author keith
 * This class is a class that represents an effect that changes the attack stat
 *
 */
public class AttackStatEffect extends StatEffect {
	
	public AttackStatEffect(boolean increase, Item.Targets target, int amount) {
		this.increase = increase;
		this.target = target;
		this.amount = amount;
	}

	@Override
	public void increaseStat(GameBattler target) {
		increaseStat(target, getAmount());
	}

	@Override
	public void decreaseStat(GameBattler target) {
		decreaseStat(target, getAmount());
	}
	
	public void increaseStat(GameBattler target, int amount) {
		target.setATKplus(target.getATKplus() + amount);
	}

	public void decreaseStat(GameBattler target, int amount) {
		target.setATKplus(target.getATKplus() - amount);
	}
	

	/**
	 * Activates the AttackStatEffect
	 * @param targets The targets of the activation
	 */
	public void activate(List<GameBattler> targets) { 
		if(!this.isActivated()) {
			if(this.increase) {
				for(GameBattler gameBattler : targets) {
					increaseStat(gameBattler);
				}
			} 
			else {
				for(GameBattler gameBattler : targets) {
					decreaseStat(gameBattler);
				}
			}
		}
		
	}

	@Override
	public void activate() {
		setActivated(true);
	}
	
	/***
	 * Deactivates the AttackStatEffect for the specified targets
	 * @param targets
	 */
	public void deactivate(List<GameBattler> targets) {
		deactivate(targets, getAmount());
	}
	
	public void deactivate(List<GameBattler> targets, int amount) {
		if(this.isActivated()) {
			if(this.increase) {
				for(GameBattler gameBattler : targets) {
					decreaseStat(gameBattler, amount);
				}
			} 
			else {
				for(GameBattler gameBattler : targets) {
					increaseStat(gameBattler, amount);
				}
			}
		}
		deactivate();
	}

	/**
	 * Sets activated to false
	 */
	@Override
	public void deactivate() {
		setActivated(false);
	}

}
