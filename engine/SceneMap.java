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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
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
    private SGL GL = Renderer.get();
    private ShaderProgram composite;
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
    //WorldPlayer worldPlayer;
    private static Camera camera;
    private Image buffer;
    private Image lightBuffer;
    private Graphics lg;
    private Image consoleBuffer;
    private Graphics cg;
    private Image fire1;
    private Image fire2;
    private float count;
    private boolean isLit;
    static Image items;
    //public Input input;
    public static String mapName = "testmap2";
    public static Map map;
    public Image light;
    private TextField console;
    private static ArrayList<Window> uielements;

    public SceneMap(int stateID) {

        this.stateID = stateID;
    }
    
    @Override
    public void enter(GameContainer game, StateBasedGame sbg) throws SlickException{
        super.enter(game, sbg);
        camera = new Camera(map.map, map.map.getWidth()*map.map.getTileWidth(),map.map.getHeight()*map.map.getTileHeight());
    }

    @Override
    public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
        fire1 = GameCache.res("fire1.png");
        fire2 = GameCache.res("fire2.png");
        interpreter = new GameInterpreter(0, true);
        lightBuffer = new Image(B_WIDTH, B_HEIGHT);
        lg = lightBuffer.getGraphics();
        consoleBuffer = new Image(B_WIDTH, B_HEIGHT);
        cg = consoleBuffer.getGraphics();
        buffer = new Image(B_WIDTH, B_HEIGHT);
        allowClose = false;
        //container.setMaximumLogicUpdateInterval(60);
        worldPlayer = new WorldPlayer("steampunk_m4");
        blurH = ShaderProgram.loadProgram("engine/blurH.vert", "engine/blurH.frag");
        blurV = ShaderProgram.loadProgram("engine/blurV.vert", "engine/blurV.frag");
        effect = new LightShader("engine/oilpaint.vert", "engine/oilpaint.frag");
        composite = ShaderProgram.loadProgram("engine/compositeV.glsl", "engine/compositeF.glsl");
        mouseLight = new Light(500f, 2400f, 1f, 0.5f, "test_light", new Color(105, 150, 50));
        lightArray = new ArrayList(Arrays.asList(new Light[]{
                    mouseLight,
                    new Light(100f, 100f, 2f, 0.5f, Color.cyan),
                    new Light(1280f, 720f, 8f, 1f, Color.red),
                    new Light(2048f, 2048f, 0.9f, 0.45f, Color.green),
                    new Light(2800f, 2800f, 0.9f, 0.8f, new Color(240, 100, 10)),
                    new Light(24 * 64f + 32, 27 * 64f + 32, 1f, 0.7f, new Color(255, 80, 10)),
                    new Light(234, 567, 1f, 0.6f, new Color(59, 178, 30)),
                    new BeamLight(234, 567, 8f, 60f, 0.6f, "test_light", new Color(59, 178, 30))
                }));
        uielements = new ArrayList<>();
        //uielements.add(wind);
        EnemyReader.populateEnemies();
        map = new Map(new TiledMapExtra("res/data/map/"+mapName+".tmx", "res/data/map"), worldPlayer);
        uiFocus = false;
        music = new Music("res/fatefulencounter.wav");
        //isPlaying = true;
        //music.loop();
        light = new Image("res/LightRays.png");
        camera = new Camera(map.map, map.map.getWidth()*map.map.getTileWidth(),map.map.getHeight()*map.map.getTileHeight());
        message = new WindowMessage(camera);
        //System.out.println(new GameBattler().stat.getBaseHP());
        final ScriptEngine js = new ScriptEngineManager().getEngineByName("javascript");
        Bindings bindings = js.getBindings(ScriptContext.ENGINE_SCOPE);
        bindings.put("stdout", System.out);
        bindings.put("actors", gameParty.getMembers());
        bindings.put("data", new GameData());
        bindings.put("cache", new GameCache());
        bindings.put("npc", map.events.get(0));
        bindings.put("player", worldPlayer);
        ComponentListener listener = new ComponentListener() {
            @Override
            public void componentActivated(AbstractComponent source) {
                try {
                    js.eval(console.getText());
                } catch (ScriptException ex) {
                    Logger.getLogger(SceneMap.class.getName()).log(Level.SEVERE, null, ex);
                }
                console.setFocus(true);
                input.clearKeyPressedRecord();
            }
        };
        console = new TextField(container, (Font)GameCache.getFont(), B_WIDTH/2 - 500/2, B_HEIGHT - 35,500,35, listener);
    }

    @Override
    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
        //System.out.println(delta);
        deltaG = delta;
        elapsed += delta;
        mouseLight.x = (float) input.getMouseX() + Camera.viewPort.getX();
        mouseLight.y = (float) input.getMouseY() + Camera.viewPort.getY();
        mouseLight.scale = 1f + Math.abs((float) Math.sin(elapsed / 1000f));
        lightArray.get(5).scale = 1f + Math.abs((float) Math.sin(elapsed / 1000f));
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            container.exit();
        }
        debugUpdate(sbg);
        if (input.isKeyDown(Input.KEY_RALT) && input.isKeyPressed(Input.KEY_ENTER)) {
            fullscreen = container.isFullscreen();
            container.pause();
            container.setFullscreen(!fullscreen);
            container.resume();
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
        for (Light l : lightArray) {
            if (!(l instanceof BeamLight)) {
                l.update(elapsed);
            }
        }
        interpreter.update();
    }
    
    public void debugUpdate(StateBasedGame sbg){
        if (input.isKeyPressed(Input.KEY_F10)) {
            if (isPlaying) {
                music.pause();
            } else {
                music.loop();
            }
            isPlaying = !isPlaying;
        }
        if (input.isKeyPressed(Input.KEY_0) && !console.hasFocus()) {
            ((SceneBattle) sbg.getState(3)).setBattleBack(GameCache.res("DecorativeTile.png"));
            SceneBattle.setBattleOverlay(GameCache.res("Temple.png"));
            sbg.enterState(3);
        }
        if (input.isKeyPressed(Input.KEY_1)) {
            shaderOption = 0;
            isLit = !isLit;
        }
        if (input.isKeyPressed(Input.KEY_2)) {
            shaderOption = 1;
        }
        if (input.isKeyPressed(Input.KEY_3)) {
            shaderOption = 2;
        }
        if (input.isKeyPressed(Input.KEY_4)) {
            shaderOption = 3;
        }
        if (input.isKeyPressed(Input.KEY_5)) {
            shaderOption = 4;
        }
        if (input.isKeyPressed(Input.KEY_6)) {
            shaderOption = 5;
        }
        if (input.isKeyPressed(Input.KEY_F4)) {
            camera.setTarget(map.objs.get(0));
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
        //effect.bind();
        if (isLit) {
            lg.clear();
            lg.setColor(Color.black);
            lg.fillRect(0, 0, B_WIDTH, B_HEIGHT);
            for (Light l : lightArray) {
                if (l.isVisible(camera)) {
                    l.render(lg);
                }
            }
            lg.flush();
        }
        
        if (shaderOption != lastOption) {
            effect.setUniform1i("choice", shaderOption);
            lastOption = shaderOption;
        }
        camera.translate(g);
        map.render(worldPlayer, g);

        Collections.sort(map.objs, depthComp);
        for (GameObject go : map.objs) {
            go.render(g);
        }
        
        

        if (count > 2) {
            count = 0;
        }
        if (count < 1) {
            fire1.draw(1536, 1710);
        } else if (count > 1) {
            fire2.draw(1536, 1710);
        }
        count += 0.04f;
        
        if(isLit){
            /*g.setDrawMode(Graphics.MODE_COLOR_MULTIPLY);
            lightBuffer.draw(Camera.viewPort.getX(), Camera.viewPort.getY());
            g.setDrawMode(Graphics.MODE_NORMAL);*/
            g.copyArea(buffer, 0, 0);
            g.clear();
            composite.bind();
            GL13.glActiveTexture(GL13.GL_TEXTURE10);
            lightBuffer.bind();
            composite.setUniform1i("lightBuffer", 10);
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            buffer.draw(Camera.viewPort.getX(), Camera.viewPort.getY());
            g.copyArea(buffer, 0, 0);
            ShaderProgram.unbind();
        }
        
        g.copyArea(buffer, 0, 0);
        /*g.setColor(Color.yellow);
         g.drawRect(camera.viewPort.getX() + 5, 
          camera.viewPort.getY() + 5,
          camera.viewPort.getWidth() - 10, 
          camera.viewPort.getHeight() - 10);*/

        for (Window w : uielements) {
            w.render(g, sbg);
        }
        console.render(container, cg);
        cg.flush();
        g.drawImage(consoleBuffer, Camera.viewPort.getX(), Camera.viewPort.getY());
    }
    
    public static Camera getCamera(){
        return camera;
    }
    
    public static void setCamera(Camera c){
        camera = c;
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
        gc.pause();
        blurH.bind();
        g.drawImage(buffer, 0, 0);
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
        if (lightArray.size() < 1000) {
            lightArray.add(new Light(x + Camera.viewPort.getX(), y + Camera.viewPort.getY(), 3f * (float) Math.random(), 0.6f,
                    new Color((float) Math.random(), (float) Math.random(), (float) Math.random())));
        }
    }

    @Override
    public int getID() {
        return stateID;
    }
}
