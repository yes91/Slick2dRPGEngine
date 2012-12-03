package engine;

import java.beans.PropertyChangeEvent;

public class Potion extends ConsumableItem {

	public Potion(String name, boolean useable) {
		super(name, useable);
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {

	}

	@Override
	public void consume(GameObject target, Inventory inv) {
	}

}
