package engine;

public abstract class Effect {
	
	//Item.Targets target; // The target of the Effect
	private boolean activated; // Whether or not the Effect is activated
	
	public abstract void activate(Object target);
	public abstract void deactivate();

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
	
	

}