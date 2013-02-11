  /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import effectutil.ShaderProgram;
import java.util.ArrayList;
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
    private ShaderProgram lighting;
    private ShaderProgram effect;
    private int lastOption;
    private int shaderOption;
    private float[][] vec3Array;
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
        testbattler = new Image("/src/res/yuan_3.png");
        worldPlayer = new WorldPlayer(new Image("/src/engine/craft.png"));
        blurH = ShaderProgram.loadProgram("/src/engine/blurH.vert", "/src/engine/blurH.frag");
        blurV = ShaderProgram.loadProgram("/src/engine/blurV.vert", "/src/engine/blurV.frag");
        effect = ShaderProgram.loadProgram("/src/engine/oilpaint.vert", "/src/engine/oilpaint.frag");
        lighting = ShaderProgram.loadProgram("/src/engine/lightV.glsl", "/src/engine/lightF.glsl");
        vec3Array = new float[][]{
        {0, 0, 0},
        {1280f-100f, 100f, 0},
        {100f, 720f-100f, 0},
        {100f, 100f, 0},
        {-1f, -1f, 0},
        {-1f, -1f, 0},
        {-1f, -1f, 0},
        {-1f, -1f, 0},
        {-1f, -1f, 0},
        {-1f, -1f, 0}
        };
        message = new WindowMessage();
        uielements = new ArrayList<>();
        //uielements.add(wind);
        EnemyReader.populateEnemies();
        map = new Map(new TiledMapExtra("/src/res/data/map/testmap2.tmx", "/src/res/data/map"), worldPlayer);
        uiFocus = false;
        music = new Music("/src/res/fatefulencounter.wav");
        //isPlaying = true;
        //music.loop();
        items = new Image("/src/res/system/IconSet.png");
        light = new Image("/src/res/LightRays.png");
        ItemReader.populateItems();
        //System.out.println(new GameBattler().stat.getBaseHP());

    }

    @Override
    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
        //System.out.println(delta);
        deltaG = delta;
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
        //effect.setUniform2f("mouse", (float)input.getMouseX(), (float)720 - input.getMouseY());
        //effect.setUniform3f("lights[3]", 0f, 0f, 0f);
        //effect.setUniform3f("lights[2]", 1280f-100f, 100f, 0);
        //effect.setUniform3fArray("lights", vec3Array);
        effect.setUniform2f("lights[0].pos", (float)input.getMouseX(), (float)720 - input.getMouseY());
        effect.setUniform4f("lights[0].color", Color.magenta);
        effect.setUniform3f("lights[0].attenuation", 0.4f, 3.0f, 20.0f);
        effect.setUniform1f("lights[0].intensity", 0.5f);
        effect.setUniform2f("lights[1].pos", 100f, 720f - 100f);
        effect.setUniform4f("lights[1].color", Color.yellow);
        effect.setUniform3f("lights[1].attenuation", 0.4f, 3.0f, 20.0f);
        effect.setUniform1f("lights[1].intensity", 0.5f);
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
    public int getID() {
        return stateID;
    }
}
