package engine;

public class HPPotion extends Potion{
	
	HPStatEffect heal;
	
	public HPPotion(String name) {
		this(name, true);
	}

	public HPPotion(boolean useable) {
		this("HP Potion", useable);
	}
	
	public HPPotion() {
		this(true);
	}
	
	public HPPotion(String name, boolean useable) {
		super(name, useable);
	}

}
