package engine;

import java.beans.*;
import java.util.List;

public abstract class StatEffect extends Effect {
	private PropertyChangeSupport propertySupport = new PropertyChangeSupport(this);
	protected boolean increase;
	protected int amount;
	
	public StatEffect(boolean increase, Item.Targets target, int amount) {
		this.increase = increase;
		this.target = target;
		this.amount = amount;
	}

	
	public abstract void increaseStat(GameBattler target);
	
	public abstract void decreaseStat(GameBattler target);
	
	public abstract void increaseStat(GameBattler target, int amount);
	
	public abstract void decreaseStat(GameBattler target, int amount);

	public boolean isIncrease() {
		return increase;
	}
	
	

	/**
	 * Activates the AttackStatEffect
	 * @param targets The targets of the activation
	 */
    @Override
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
		activate();
	}

	@Override
	public void activate() {
		setActivated(true);
	}
	
	/***
	 * Deactivates the AttackStatEffect for the specified targets
	 * @param targets
	 */
    @Override
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

	public void setIncrease(boolean increase) {
		this.increase = increase;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		int oldAmount = this.amount;
		this.amount = amount;
		propertySupport.firePropertyChange("amount", oldAmount, amount);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
}
