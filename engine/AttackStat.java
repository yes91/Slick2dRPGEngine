package engine;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AttackStat extends Stat {
	private PropertyChangeSupport mPcs = new PropertyChangeSupport(this);

	private int attack;

	/**
	 * @return the attack
	 */
	public int getAttack() {
		return attack;
	}

	/**
	 * @param attack the attack to set
	 */
	public void setAttack(int attack) {
		mPcs.firePropertyChange("attack", this.attack, attack);
		this.attack = attack;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		mPcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		mPcs.removePropertyChangeListener(listener);
	}

}
