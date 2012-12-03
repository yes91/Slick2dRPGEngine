/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author redblast71
 */
public class ItemReader {

	public static ArrayList<Item> items = new ArrayList<>();

	public void populateItems() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();

		InputStream is = ItemReader.class.getClassLoader().getResourceAsStream("res/ItemFile.xml");

		Document document = (Document) builder.build(is);
		Element rootNode = document.getRootElement();
		List<Element> list = rootNode.getChildren();
		/*
		 *  Loop through every element. 
		 *  If it has a child element of item, initialize it based on parent node name
		 */
		for(Element e : list) {
			checkNode(e);
		}
	}
	
	public void checkNode(Element node) {
		if(node.getName().equals("item")) {
			Method[] allMethods = this.getClass().getDeclaredMethods();
			for(Method m : allMethods) {
				if(m.getName().equals(node.getParentElement().getName() + "Init")) {
					try {
						m.invoke(this, node.getParentElement().getChildren());
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			for(Element e : node.getChildren()) {
				checkNode(e);
			}
		}
	}

public void weaponsInit(List<Element> list) {
	for (Element node : list) {
		Item item = new Weapon(node.getChildText("name"), node.getChildText("subtype"));
		((Weapon)item).setDmg(Integer.parseInt(node.getChildText("dmg")));
		item.setIndex(Integer.parseInt(node.getChildText("gid")));
		items.add(item);
	}
}

public void consumablesInit(List<Element> list) {
	HashMap<String, ConsumableItem> consumables = new HashMap<String, ConsumableItem>();
	for(Element node : list) {
		ConsumableItem consumable = new ConsumableItem(node.getChildText("name")); 
		for(String s : consumable.effects.keySet()) {
			if(node.getChild("effect").getChildText("classname").equals(s)) {
				Method[] methods = this.getClass().getDeclaredMethods();
				for(Method m : methods) {
					 int mid = s.lastIndexOf ('.') + 1;
					 String finalClassName = s.substring(mid);
					if(m.getName().equals("get" + finalClassName + "Instance")) {
						boolean increase = Boolean.parseBoolean(node.getChild("effect").getChildText("increase"));
						ConsumableItem.Targets target;	
						switch(node.getChild("effect").getChildText("target")) {
						case "Whole Party":
							target = ConsumableItem.Targets.WHOLE_PARTY;
							break;
						case "Single Enemy":
							target = ConsumableItem.Targets.SINGLE_ENEMY;
							break;
						case "Whole Enemy Party":
							target = ConsumableItem.Targets.WHOLE_ENEMY_PARTY;
						default:
							target = ConsumableItem.Targets.SINGLE_PLAYER;
							break;
						}
						int amount = Integer.parseInt(node.getChild("effect").getChildText("amount"));
						consumable.setIndex(Integer.parseInt(node.getChildText("gid")));
						try {
							consumable.putIntoEffects(s, (StatEffect) m.invoke(this, new Object[] {increase, target, amount}));
							consumables.put(node.getChildText("classname"), consumable);
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e) {
							e.printStackTrace();
							System.exit(1);
						}
					}
				}
			}
		}
	}
	for(String s : consumables.keySet()) {
		try {
			s = s.replaceAll("\\s","");
			Class<?> consumable = Class.forName(s);
			ConsumableItem otherConsumable = (ConsumableItem) consumable.getConstructor(String.class, boolean.class).newInstance(consumables.get(s).getName(), consumables.get(s).isUseable());
			items.add(otherConsumable);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
}
	public static ArrayList<Item> getItems(){

		return items;
	}
	
	@SuppressWarnings("unused")
	private AttackStatEffect getAttackStatEffectInstance(boolean increase, ConsumableItem.Targets target, int amount) {
		return new AttackStatEffect(increase, target, amount);
	}

}
