package engine;

import java.util.List;

public class DefenseStatEffect extends StatEffect {
	
	public DefenseStatEffect(boolean increase, Item.Targets target, int amount) {
		this.increase = increase;
		this.target = target;
		this.amount = amount;
	}

	@Override
	public void increaseStat(GameBattler target) {
		target.setDEFplus(target.getDEFplus() + amount);
	}

	@Override
	public void decreaseStat(GameBattler target) {

	}
	
	public void activate(List<GameBattler> targets) {
		
	}

	@Override
	public void activate() {

	}

	@Override
	public void deactivate() {

	}

	@Override
	public void deactivate(List<GameBattler> targets) {		
	}

}
