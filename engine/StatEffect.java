package engine;

import java.beans.*;

public abstract class StatEffect extends Effect {
	private PropertyChangeSupport propertySupport = new PropertyChangeSupport(this);
	protected boolean increase;
	protected int amount;

	
	public abstract void increaseStat(GameBattler target);
	
	public abstract void decreaseStat(GameBattler target);

	public boolean isIncrease() {
		return increase;
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
