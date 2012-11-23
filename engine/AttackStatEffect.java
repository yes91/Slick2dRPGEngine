package engine;

/**
 * 
 * @author keith
 * This class is a class that represents an effect that changes the attack stat
 * temporarily.
 *
 */
public class AttackStatEffect extends StatEffect {
	
	public AttackStatEffect(boolean increase, Item.Targets target, int amount) {
		super(increase, target, amount);
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

}
