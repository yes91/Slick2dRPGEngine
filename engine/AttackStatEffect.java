package engine;

/**
 * 
 * @author keith
 * This class is a class that represents an effect that changes the attack stat
 *
 */
public class AttackStatEffect extends StatEffect {
	
	private boolean increase;
	private int amount;
	
	public AttackStatEffect(boolean increase, Item.Targets target, int amount) {
		this.increase = increase;
		this.target = target;
		this.amount = amount;
	}

	@Override
	public void increaseStat(int amount, GameBattler target) {
		target.setAttack(target.getAttack() + amount);
	}

	@Override
	public void decreaseStat(int amount, GameBattler target) {
		target.setAttack(target.getAttack() - amount);
	}

	/**
	 * Activates the AttackStatEffect
	 * @param targets The targets of the activation
	 */
	public void activate(GameBattler[] targets) { 
		if(!this.isActivated()) {
			if(this.increase) {
				for(GameBattler gameBattler : targets) {
					increaseStat(this.amount, gameBattler);
				}
			} 
			else {
				for(GameBattler gameBattler : targets) {
					decreaseStat(this.amount, gameBattler);
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
	public void deactivate(GameBattler[] targets) {
		if(this.isActivated()) {
			if(this.increase) {
				for(GameBattler gameBattler : targets) {
					decreaseStat(this.amount, gameBattler);
				}
			} 
			else {
				for(GameBattler gameBattler : targets) {
					increaseStat(this.amount, gameBattler);
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
