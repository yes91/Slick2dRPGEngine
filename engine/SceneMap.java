  /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import effectutil.LightShader;
import effectutil.ShaderProgram;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Kieran
 */
public class SceneMap extends SceneBase {

    int stateID = -1;
    private ShaderProgram blurH;
    private ShaderProgram blurV;
    private LightShader effect;
    private int lastOption;
    private int shaderOption;
    private ArrayList<Light> lightArray;
    private Light mouseLight;
    private long elapsed;
    public static final int B_WIDTH = 1280;
    public static final int B_HEIGHT = 720;
    public static GameInterpreter interpreter;
    public static boolean allowClose;
    public static DepthCompare depthComp = new DepthCompare();
    public static int deltaG;
    boolean fullscreen;
    boolean isPlaying;
    public static boolean blocked;
    public static boolean stop;
    public static boolean uiFocus;
    private Music music;
    public static Window lastAdded;
    public static WindowMessage message;
    private Image testbattler;
    //WorldPlayer worldPlayer;
    Camera camera;
    private Image buffer;
    private Image fire1;
    private Image fire2;
    private float count;
    static Image items;
    //public Input input;
    public static Map map;
    public Image light;
    private static ArrayList<Window> uielements;

    public SceneMap(int stateID) {

        this.stateID = stateID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame sbg) throws SlickException {

        fire1 = Cache.getRes("fire1.png");
        fire2 = Cache.getRes("fire2.png");
        interpreter = new GameInterpreter(0, true);
        ImageBuffer scratch = new ImageBuffer(B_WIDTH, B_HEIGHT);
        buffer = scratch.getImage();
        allowClose = false;
        //container.setMaximumLogicUpdateInterval(60);
        testbattler = new Image("res/yuan_3.png");
        worldPlayer = new WorldPlayer(new Image("engine/craft.png"));
        blurH = ShaderProgram.loadProgram("engine/blurH.vert", "engine/blurH.frag");
        blurV = ShaderProgram.loadProgram("engine/blurV.vert", "engine/blurV.frag");
        effect = new LightShader("engine/oilpaint.vert", "engine/oilpaint.frag");
        mouseLight = new Light(500f, 2400f, 1f, 0.5f, new Color(105,150,50));
        lightArray = new ArrayList(Arrays.asList(new Light[]{
            mouseLight,
            new Light(100f, 100f, 2f, 0.5f, Color.cyan),
            new Light(1280f, 720f, 1f, 0.8f, Color.red),
            new Light(2048f, 2048f, 0.9f, 0.45f, Color.green),
            new Light(2800f, 2800f, 0.9f, 0.8f, new Color(240, 100, 10)),
            new Light(24*64f+32, 27*64f+32, 1f, 0.7f, new Color(255, 80, 10)),
            new Light(234, 567, 1f, 0.6f, new Color(59, 178, 30))
        }));
        message = new WindowMessage();
        uielements = new ArrayList<>();
        //uielements.add(wind);
        EnemyReader.populateEnemies();
        map = new Map(new TiledMapExtra("res/data/map/testmap2.tmx", "res/data/map"), worldPlayer);
        uiFocus = false;
        music = new Music("res/fatefulencounter.wav");
        //isPlaying = true;
        //music.loop();
        items = new Image("res/system/IconSet.png");
        light = new Image("res/LightRays.png");
        ItemReader.populateItems();
        //System.out.println(new GameBattler().stat.getBaseHP());

    }

    @Override
    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
        //System.out.println(delta);
        deltaG = delta;
        elapsed += delta;
        if (input.isKeyPressed(Input.KEY_F10)) {
            if (isPlaying) {
                music.pause();
            } else {
                music.loop();
            }
            isPlaying = !isPlaying;
        }
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            container.exit();
        }
        if (input.isKeyPressed(Input.KEY_0)) {
            ((SceneBattle)sbg.getState(3)).setBattleBack(Cache.getRes("Cobblestones3.png"));
            sbg.enterState(3);
        }
        if(input.isKeyPressed(Input.KEY_1)){
            shaderOption = 0;
        }
        if(input.isKeyPressed(Input.KEY_2)){
            shaderOption = 1;
        }
        if(input.isKeyPressed(Input.KEY_3)){
            shaderOption = 2;
        }
        if(input.isKeyPressed(Input.KEY_4)){
            shaderOption = 3;
        }
        if(input.isKeyPressed(Input.KEY_5)){
            shaderOption = 4;
        }
        if(input.isKeyPressed(Input.KEY_6)){
            shaderOption = 5;
        }
        if (input.isKeyPressed(Input.KEY_F4)) {
            map.camera.setTarget(map.objs.get(0));
        }
        if (input.isKeyDown(Input.KEY_RALT) && input.isKeyPressed(Input.KEY_ENTER)) {
            fullscreen = container.isFullscreen();
            container.pause();
            container.setFullscreen(!fullscreen);
            container.resume();
        }
        if (worldPlayer.currentHP <= 0) {
            worldPlayer.currentHP = 0;

        }
        worldPlayer.update(inputp, delta);
        if ((inputp.isCommandControlPressed(menu))) {
            makeMenuBack(container);
            input.clearKeyPressedRecord();
            input.clearControlPressedRecord();
            sbg.getState(2).update(container, sbg, delta);
            sbg.enterState(2);
        }
        
        if (uiFocus == true) {
            if (inputp.isCommandControlDown(cancel) && allowClose) {
                uielements.remove(lastAdded);
                uiFocus = false;
            }
        }
        map.update(container, worldPlayer);
        for (Window w : uielements) {
            if (w instanceof WindowSelectable) {
                ((WindowSelectable) w).update(inputp, delta);
            }
        }
        interpreter.update();
    }

    @Override
    public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
        effect.bind();
        
        mouseLight.x = (float)input.getMouseX() + Camera.viewPort.getX();
        mouseLight.y = (float)input.getMouseY() + Camera.viewPort.getY();
        mouseLight.scale = 1f + 2f*Math.abs((float)Math.sin(elapsed /1000f));
        lightArray.get(5).scale = 2f + 2f*Math.abs((float)Math.sin(elapsed /1000f));
        effect.setUniformLightArray("lights", lightArray);
        if(shaderOption != lastOption){
            effect.setUniform1i("choice", shaderOption);
            lastOption = shaderOption;
        }
        map.camera.translate(g);
        map.render(worldPlayer, g);
        //Sprite.animateSprite(testbattler, g, 200, 200, 423, 270, 4, 0, 4, 0, true);
        //Sprite.drawSpriteFrame(testbattler, g, 200, 200, 4, 0, 423, 270);
        
        Collections.sort(map.objs, depthComp);
        for (GameObject go : map.objs) {
            go.render(g);
        }
        
        if(count > 2){
            count = 0;
        }
        if(count < 1){
            fire1.draw(1536, 1710);
        }
        else if(count > 1){
            fire2.draw(1536, 1710);
        }
        count += 0.04f;

        //light.draw(0, 0, 1280, 720);

        ShaderProgram.unbind();
        
        /*g.setColor(Color.yellow);
        g.drawRect(Camera.viewPort.getX() + 5, 
        * Camera.viewPort.getY() + 5, 
        * Camera.viewPort.getWidth() - 10, 
        * Camera.viewPort.getHeight() - 10);*/
        
        for (Window w : uielements) {
            w.render(g, sbg);
            map.renderUI(worldPlayer);
        }
        
    }

    private void tpPlayer(int tileX, int tileY, WorldPlayer p) {

        p.setX(tileX * map.map.getTileWidth());
        p.setY(tileY * map.map.getTileHeight());

    }

    public static Image getImage() {

        return items;
    }

    public static void addUIElement(Window w) {

        uielements.add(w);
    }

    public static void removeUIElement(Window w) {

        uielements.remove(w);
    }

    public static boolean isBlocked() {
        return blocked;
    }

    public static boolean stopPlayer() {

        return stop;
    }

    public void makeMenuBack(GameContainer gc) {
        Graphics g = gc.getGraphics();
        g.flush();
        g.copyArea(buffer, 0, 0);
        gc.pause();
        blurH.bind();
        g.drawImage(buffer, 0, 0);
        g.flush();
        ShaderProgram.unbind();
        g.copyArea(buffer, 0, 0);
        blurV.bind();
        g.drawImage(buffer, 0, 0);
        ShaderProgram.unbind();
        g.copyArea(buffer, 0, 0);
        SceneMenu.back = buffer.copy();
        gc.resume();
        
        /*try {
         gc.pause();
         buffer.getGraphics().clearAlphaMap();
         map.camera.translate(buffer.getGraphics(), worldPlayer);
         map.render(worldPlayer, buffer.getGraphics());
         Collections.sort(map.objs, depthComp);
         for(GameObject go: map.objs) {
         go.render(buffer.getGraphics());
         }*/
    }
    

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        if(lightArray.size() < 100){
            lightArray.add(new Light(x + Camera.viewPort.getX(), y + Camera.viewPort.getY(), 1f, 0.6f, 
            new Color((float)Math.random(), (float)Math.random(), (float)Math.random())));
        }
    }

    @Override
    public int getID() {
        return stateID;
    }
}
