package engine;

import java.util.List;

public abstract class Effect {
	
	Item.Targets target; // The target of the Effect
	private boolean activated; // Whether or not the Effect is activated
	
	public abstract void activate();
	public abstract void deactivate();
	public abstract void activate(List<GameBattler> targets);
	public abstract void deactivate(List<GameBattler> targets);

	/**
	 * @return whether or not the effect is activated
	 */
	public boolean isActivated() {
		return activated;
	}

	/**
	 * @param activated whether or not the effect is activated
	 */
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
	public Item.Targets getTarget() {
		return target;
	}
	public void setTarget(Item.Targets target) {
		this.target = target;
	}
	
	public String getTargetString() {
		return getTarget().toString();
	}

}