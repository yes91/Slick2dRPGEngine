/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author redblast71
 */
public class GameData {

    public static ArrayList<Item> items;
    public static ArrayList<Skill> skills;
    public static ArrayList<GameClass> classes;
    public static ArrayList<GameActor> actors;
    public static ArrayList<GameEnemy> enemies;

    public static void populate() throws IOException, ClassNotFoundException {
        items = new ArrayList<>();
        skills = new ArrayList<>();
        classes = new ArrayList<>();
        actors = new ArrayList<>();
        enemies = new ArrayList<>();
        
        readItems();
        readSkills();
        readClasses();
        readActors();
        readEnemies();
        
    }

    private static void readItems() {
        SAXBuilder builder = new SAXBuilder();

        try {
            InputStream is = ItemReader.class.getClassLoader().getResourceAsStream("res/ItemFile.xml");

            Document document = (Document) builder.build(is);
            org.jdom2.Element rootNode = document.getRootElement();
            List<org.jdom2.Element> list = rootNode.getChildren("item");

            for (int i = 0; i < list.size(); i++) {

                org.jdom2.Element node = (org.jdom2.Element) list.get(i);

                if (node.getChildText("type").equals("weapon")) {

                    Item item = new Weapon(node.getChildText("name"));
                    ((Weapon) item).setDmg(Integer.parseInt(node.getChildText("dmg")));
                    item.setDesc(node.getChildText("desc"));
                    item.setIndex(Integer.parseInt(node.getChildText("gid")));
                    items.add(item);

                }
                if (node.getChildText("type").equals("consumable")) {

                    Item item = new Consumable(node.getChildText("name"), node.getChildText("subtype"));
                    ((Consumable) item).setEffect(node.getChildText("effect"));
                    item.setDesc(node.getChildText("desc"));
                    item.setIndex(Integer.parseInt(node.getChildText("gid")));
                    items.add(item);

                }

            }

        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
        }
    }
    
    private static void readSkills(){
        
    }
    
    private static void readClasses() throws IOException, ClassNotFoundException{
        /*
         * int number of classes
         * for each class
         *  int position 
         *  String class name
         *  HashMap<Integer, Boolean> weapons
         *  HashMap<Integer, Boolean> armors
         *  HashMap<Element, Float> element efficiency
         *  HashMap<Integer, ArrayList<Integer>> skills to learn at level
         * end
         */
        ObjectInputStream classData = new ObjectInputStream(new FileInputStream(new File("src/classes.jrdata")));
        for(int i = 0; i < classData.readInt(); i++){
            GameClass gClass = new GameClass();
            gClass.className = classData.readUTF();
            for(GameClass.Position p: GameClass.Position.values()) {
                if(p.index == classData.readInt()){
                    gClass.pos = p; 
                }
            }
            gClass.weapons = (HashMap<Integer, Boolean>)classData.readObject();
            gClass.armors = (HashMap<Integer, Boolean>)classData.readObject();
            gClass.eEfficiency = (HashMap<Element, Float>)classData.readObject();
            gClass.learnings = (HashMap<Integer, ArrayList<Integer>>)classData.readObject();
            classes.add(gClass);
        }
        
    }

    private static void readActors() throws IOException {
        /*
         * int number of actors
         * for each actor
         *  int initial level
         *  int basis
         *  int inflation
         *  String actor name
         *  int class id
         * end
         */
        ObjectInputStream actorData = new ObjectInputStream(new FileInputStream(new File("src/actors.jrdata")));
        for (int i = 0; i < actorData.readInt(); i++) {
            GameActor actor = new GameActor();
            actor.stats = new BattleStats(actorData.readInt(), actorData.readInt(), actorData.readInt());
            actor.name = actorData.readUTF();
            actor.gClass = classes.get(actorData.readInt());
            actor.currentHP = actor.getMaxHP();
            actor.currentMP = actor.getMaxMP();
            actor.stats.EXP = actor.stats.getEXP(actor.stats.level);
            actors.add(actor);
        }
    }
    
    private static void readEnemies(){
        
    }
}
