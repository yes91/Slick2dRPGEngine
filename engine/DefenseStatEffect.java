package engine;

public class DefenseStatEffect extends StatEffect {

	public DefenseStatEffect(boolean increase, ConsumableItem.Targets target, int amount) {
		super(increase, target, amount);
	}

	public void increaseStat(GameBattler target, int amount) {
		target.setDEFplus(target.getDEFplus() + amount);
	}

	@Override
	public void increaseStat(GameBattler target) {
		increaseStat(target, getAmount());
	}

	public void decreaseStat(GameBattler target, int amount) {
		target.setDEFplus(target.getDEFplus() - amount);
	}

	@Override
	public void decreaseStat(GameBattler target) {
		decreaseStat(target, getAmount());
	}
}
