package engine;

public abstract class StatEffect extends Effect {
	

	
	public abstract void increaseStat(int amount, GameBattler target);
	
	public abstract void decreaseStat(int amount, GameBattler target);


}
