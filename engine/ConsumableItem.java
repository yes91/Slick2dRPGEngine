package engine;

import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.Set;

import org.reflections.Reflections;

public class ConsumableItem extends Item implements Consumable {
    public HashMap<String, StatEffect> effects;
	public static enum Targets {SINGLE_PLAYER, WHOLE_PARTY, SINGLE_ENEMY, WHOLE_ENEMY_PARTY}

	
	public ConsumableItem(String name) {
		this(name, true);
	}

	public ConsumableItem(String name, boolean useable) {
		super(name, useable);
		effects = new HashMap<String, StatEffect>();
		Reflections r = new Reflections("engine");
	      Set<Class<? extends StatEffect>> subTypes = r.getSubTypesOf(StatEffect.class);
	      for(Class<? extends StatEffect> implementor : subTypes) {
	    	  if(implementor != null) {
	    		  String a = implementor.getName();
		    	  effects.put(a, null);
	    	  }
	    	  
	      }
	}

	
	public void putIntoEffects(String effectName, StatEffect effect) {
		effects.put(effectName, effect);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		
	}

	@Override
	public void consume(GameObject target, Inventory inv) {
		
	}

}
