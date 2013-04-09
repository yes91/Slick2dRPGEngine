/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.newdawn.slick.util.ResourceLoader;
import org.objenesis.instantiator.ObjectInstantiator;
import org.objenesis.strategy.StdInstantiatorStrategy;

/**
 *
 * @author redblast71
 */
public class GameData {
    
    public static final Kryo kryo = new Kryo();
    
    static{
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
        ObjectInstantiator objInst = new ObjectInstantiator(){
            @Override
            public Object newInstance(){ 
                return new GameActor();
            }
        };
        kryo.getRegistration(GameActor.class).setInstantiator(objInst);
    }

    public static ArrayList<Item> items = new ArrayList<>();
    public static ArrayList<Skill> skills = new ArrayList<>();
    public static ArrayList<GameClass> classes = new ArrayList<>();
    public static ArrayList<GameActor> actors = new ArrayList<>();
    public static ArrayList<GameEnemy> enemies = new ArrayList<>();
    public static ArrayList<EffectAnimation> animations = new ArrayList<>();

    public static void populate() throws IOException, ClassNotFoundException {
        
        readItems();
        //readSkills();
        //readClasses();
        readAnimations();
        readActors();
        readEnemies();
        
        save();
    }
    
    public static void save(){
        try {
            writeItems();
            writeActors();
            writeEnemies();
            writeAnimations();
        } catch (IOException ex) {
            Logger.getLogger(GameData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void readItemXML() {
        SAXBuilder builder = new SAXBuilder();

        try {
            InputStream is = ResourceLoader.getResourceAsStream("res/data/ItemFile.xml");

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
    
    private static void readItems() throws FileNotFoundException{
        Input input = new Input(ResourceLoader.getResourceAsStream("data/consumables.jrdata"));
        int lengthCons = input.readInt();
        for(int i = 0; i < lengthCons; i++){
            Item item = kryo.readObject(input, Consumable.class);
            items.add(item);
        }
        Input input2 = new Input(ResourceLoader.getResourceAsStream("data/weapons.jrdata"));
        int lengthWeps = input2.readInt();
        for(int i = 0; i < lengthWeps; i++){
            Item item = kryo.readObject(input2, Weapon.class);
            items.add(item);
        }
    }
    
    private static void writeItems() throws FileNotFoundException{
        try (Output output = new Output(new FileOutputStream("src/data/consumables.jrdata"))) {
            int consumableCount = 0;
            for(Item i: items){
                if(i instanceof Consumable){
                    consumableCount++;
                }
            }
            output.writeInt(consumableCount);
            for(Item i: items){
                if(i instanceof Consumable){
                    kryo.writeObject(output, i);
                }
            }
        }
        try (Output output2 = new Output(new FileOutputStream("src/data/weapons.jrdata"))) {
            int weaponCount = 0;
            for(Item i: items){
                if(i instanceof Weapon){
                    weaponCount++;
                }
            }
            output2.writeInt(weaponCount);
            for(Item i: items){
                if(i instanceof Weapon){
                    kryo.writeObject(output2, i);
                }
            }
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
         *  HashMap<Integer, List<Integer>> skills to learn at level
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
            gClass.learnings = (HashMap<Integer, List<Integer>>)classData.readObject();
            classes.add(gClass);
        }
        
    }

    private static void readActors() throws IOException, ClassNotFoundException {
        Input input = new Input(ResourceLoader.getResourceAsStream("data/actors.jrdata"));
        int length = input.readInt();
        for(int i = 0; i < length; i++){
            GameActor actor = kryo.readObject(input, GameActor.class);
            actors.add(actor);
        }
    }
    
    private static void writeActors() throws IOException{
        try (Output output = new Output(new FileOutputStream("src/data/actors.jrdata"))) {
            output.writeInt(actors.size());
            for(GameActor a: actors){
                kryo.writeObject(output, a);
            }
        }
    }
    
    private static void readEnemies(){
        Input input = new Input(ResourceLoader.getResourceAsStream("data/enemy.jrdata"));
        int length = input.readInt();
        for(int i = 0; i < length; i++){
            GameEnemy enemy = kryo.readObject(input, GameEnemy.class);
            enemies.add(enemy);
        }
    }
    
    private static void writeEnemies() throws FileNotFoundException{
        try (Output output = new Output(new FileOutputStream("src/data/enemy.jrdata"))) {
            output.writeInt(enemies.size());
            for(GameEnemy e: enemies){
                kryo.writeObject(output, e);
            }
        }
    }
    
    private static void readAnimations() throws FileNotFoundException {
        Input input = new Input(ResourceLoader.getResourceAsStream("data/animations.jrdata"));
        int length = input.readInt();
        for(int i = 0; i < length; i++){
            EffectAnimation ani = kryo.readObject(input, EffectAnimation.class);
            animations.add(ani);
        }
    }

    private static void writeAnimations() throws FileNotFoundException {
        try (Output output = new Output(new FileOutputStream("src/data/animations.jrdata"))) {
            output.writeInt(animations.size());
            for(EffectAnimation a: animations){
                kryo.writeObject(output, a);
            }
        }
    }
}
