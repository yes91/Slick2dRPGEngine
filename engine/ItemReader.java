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
              InputStream is = ItemReader.class.getClassLoader().getResourceAsStream("res/ItemFile.xml");
 
		Document document = (Document) builder.build(is);
		Element rootNode = document.getRootElement();
		List<Element> list = rootNode.getChildren("item");
 
		for (int i = 0; i < list.size(); i++) {
 
		   Element node = (Element) list.get(i);
                   
                   if(node.getChildText("type").equals("weapon")){
                       
                       Item item = new Weapon(node.getChildText("name"),node.getChildText("subtype"));
                       ((Weapon)item).setDmg(Integer.parseInt(node.getChildText("dmg")));
                       item.setIndex(Integer.parseInt(node.getChildText("gid")));
                       items.add(item);
                       
                   }
                   if(node.getChildText("type").equals("consumable")){
                       
                       Item item = new Consumable(node.getChildText("name"),node.getChildText("subtype"));
                       ((Consumable)item).setEffect(node.getChildText("effect"));
                       item.setIndex(Integer.parseInt(node.getChildText("gid")));
                       items.add(item);
                       
                   }
 
		}
 
	  } catch (IOException io) {
		System.out.println(io.getMessage());
	  } catch (JDOMException jdomex) {
		System.out.println(jdomex.getMessage());
	  }
}
    public static ArrayList<Item> getItems(){
        
        return items;
    }
    
}
