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
    
    public static Kryo kryo = new Kryo();
    
    public static boolean editorMode = false;
    
    static{
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
        ObjectInstantiator objInst = new ObjectInstantiator(){
            @Override
            public Object newInstance(){ 
                return new GameActor();
            }
        };
        
        kryo.getRegistration(GameActor.class).setInstantiator(objInst);
        //kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy());
        //FieldSerializer serializer = new FieldSerializer(kryo, GameBattler.class);
        //serializer.removeField("battleSprite");
        //serializer.getField("battleSprite").setCanBeNull(true);
        //kryo.register(GameBattler.class, serializer);
    }

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
        
        //readItems();
        readItemBin();
        //writeItems();
        //readSkills();
        //readClasses();
        writeActors();
        if(editorMode){
            readEditorActors();
        } else {
            readGameActors();
        }
        
        //readEnemies();
        
    }

    private static void readItems() {
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
    
    private static void readItemBin() throws FileNotFoundException{
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
    
    private static void readEditorActors() throws FileNotFoundException {
        Input input = new Input(ResourceLoader.getResourceAsStream("data/actors.jrdata"));
        int length = input.readInt();
        for(int i = 0; i < length; i++){
            GameActor actor = kryo.readObject(input, GameActor.class);
            actors.add(actor);
        }
    }

    private static void readGameActors() throws IOException, ClassNotFoundException {
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
        Input input = new Input(ResourceLoader.getResourceAsStream("data/actors.jrdata"));
        int length = input.readInt();
        for(int i = 0; i < length; i++){
            GameActor actor = kryo.readObject(input, GameActor.class);
            actors.add(actor);
        }
        
        
        /*
        ObjectInputStream actorData = new ObjectInputStream(new FileInputStream(new File("src/actors.jrdata")));
        if(actorData.readUTF().equals("JRD")){
            for(int i = 1; i < actorData.readInt(); i++){
                actors.add(GameActor.readObject(actorData));
            }
        }*/
        
        /*for (int i = 0; i < actorData.readInt(); i++) {
            GameActor actor = new GameActor("");
            actor.stats = new BattleStats(actorData.readInt(), actorData.readInt(), actorData.readInt());
            actor.name = actorData.readUTF();
            actor.classID = actorData.readInt();
            actor.currentHP = actor.getMaxHP();
            actor.currentMP = actor.getMaxMP();
            actor.stats.EXP = actor.stats.getEXP(actor.stats.level);
            actors.add(actor);
        }*/
    }
    
    private static void writeActors() throws IOException{
        try (Output output = new Output(new FileOutputStream("src/data/actors.jrdata"))) {
            output.writeInt(Demo.testActors.size());
            for(GameActor a: Demo.testActors){
                kryo.writeObject(output, a);
            }
        }
    }
    
    private static void readEnemies(){
        
    }
}
