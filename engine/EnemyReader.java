/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author redblast71
 */
public class EnemyReader {
    
    public static ArrayList<Enemy> enemies = new ArrayList<>();
    
    public static void populateEnemies() {
          SAXBuilder builder = new SAXBuilder();
 
	  try {
              InputStream is = EnemyReader.class.getResourceAsStream("res/EnemyFile.xml");
 
		Document document = (Document) builder.build(is);
		Element rootNode = document.getRootElement();
		List list = rootNode.getChildren("enemy");
 
		for (int i = 0; i < list.size(); i++) {
 
		   Element node = (Element) list.get(i);
                   Enemy e = null;
                  try {
                      e = new Enemy(new Image(node.getChildText("image")),node.getChildText("name"),Integer.parseInt(node.getChildText("hp")), Integer.parseInt(node.getChildText("mp")), Integer.parseInt(node.getChildText("atk")),Integer.parseInt(node.getChildText("def")), Integer.parseInt(node.getChildText("matk")), Integer.parseInt(node.getChildText("mdef")));
                  } catch (SlickException ex) {
                      Logger.getLogger(EnemyReader.class.getName()).log(Level.SEVERE, null, ex);
                  }
                       System.out.println(e.toString());
                       enemies.add(e);
		}
 
	  } catch (IOException io) {
		System.out.println(io.getMessage());
	  } catch (JDOMException jdomex) {
		System.out.println(jdomex.getMessage());
	  }
}
    public static ArrayList<Enemy> getEnemies(){
        
        return enemies;
    }
    
}
