<<<<<<< HEAD
package engine;

public abstract class Effect {
	
	Item.Targets target; // The target of the Effect
	private boolean activated; // Whether or not the Effect is activated
	
	public abstract void activate();

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
=======
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

/**
 *
 * @author redblast71
 */
public abstract class Effect {
    String name;
    GameBattler[] target; // The target of a particular effect
    
    @Override
    public String toString(){
        return name;
    }
    
}
>>>>>>> upstream/master
