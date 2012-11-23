package engine;

public class HPStatEffect extends StatEffect {
	
	public HPStatEffect(boolean increase, Item.Targets target, int amount) {
		super(increase, target, amount);
	}

	@Override
	public void increaseStat(GameBattler target) {
		increaseStat(target, getAmount());
	}

	public void increaseStat(GameBattler target, int amount) {
		target.setCurrentHP(target.getCurrentHP() + amount);
	}

	public void decreaseStat(GameBattler target, int amount) {
		target.setCurrentHP(target.getCurrentHP() - amount);
	}

	@Override
	public void decreaseStat(GameBattler target) {
		decreaseStat(target, getAmount());
	}
}
