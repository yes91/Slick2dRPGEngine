/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
    
    public static void populateItems() {
          SAXBuilder builder = new SAXBuilder();
 
	  try {
              InputStream is = ItemReader.class.getClassLoader().getResourceAsStream("res/data/ItemFile.xml");
 
		Document document = (Document) builder.build(is);
		Element rootNode = document.getRootElement();
		List<Element> list = rootNode.getChildren("item");
 
		for (int i = 0; i < list.size(); i++) {
 
		   Element node = (Element) list.get(i);
                   
                   if(node.getChildText("type").equals("weapon")){
                       
                       Item item = new Weapon(node.getChildText("name"));
                       ((Weapon)item).setDmg(Integer.parseInt(node.getChildText("dmg")));
                       item.setDesc(node.getChildText("desc"));
                       item.setIndex(Integer.parseInt(node.getChildText("gid")));
                       items.add(item);
                       
                   }
                   if(node.getChildText("type").equals("consumable")){
                       
                       Consumable item = new Consumable(node.getChildText("name"));
                       item.setHPrate(Float.parseFloat(node.getChildText("HPrate")));
                       item.setHPamount(Integer.parseInt(node.getChildText("HPamount")));
                       item.setMPrate(Float.parseFloat(node.getChildText("MPrate")));
                       item.setMPamount(Integer.parseInt(node.getChildText("MPamount")));
                       item.setScope(Effect.Scope.valueOf(node.getChildText("scope")));
                       item.setPlace(Effect.Place.valueOf(node.getChildText("place")));
                       item.setDesc(node.getChildText("desc"));
                       item.setIndex(Integer.parseInt(node.getChildText("gid")));
                       items.add(item);
                       
                   }
 
		}
 
	  } catch (IOException | JDOMException io) {
		System.err.println(io.getMessage());
	  }
}
    public static ArrayList<Item> getItems(){
        
        return items;
    }
    
}
