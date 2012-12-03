  /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import effectutil.GaussianFilter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import shaderutil.Shader;

/**
 *
 * @author Kieran
 */
public class SceneMap extends SceneBase {

    int stateID = -1;
    Shader s;
    Shader s2;
    public static final int B_WIDTH = 1280;
    public static final int B_HEIGHT = 720;
    private GaussianFilter gfilter;
    private final int BLUR_RADIUS = 10;
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
    private Image testbattler;
    //WorldPlayer worldPlayer;
    Camera camera;
    private Image buffer;
    private BufferedImage awtBuffer;
    private Graphics bg;
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

        gfilter = new GaussianFilter(BLUR_RADIUS);
        ImageBuffer scratch = new ImageBuffer(B_WIDTH, B_HEIGHT);
        buffer = scratch.getImage();
        bg = buffer.getGraphics();
        awtBuffer = new BufferedImage(B_WIDTH, B_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        allowClose = false;
        container.setShowFPS(false);
        //container.setMaximumLogicUpdateInterval(60);
        testbattler = new Image("/src/res/yuan_3.png");
        worldPlayer = new WorldPlayer(new Image("/src/engine/craft.png"));
        s = Shader.makeShader("/src/engine/blur.vrt", "/src/engine/blur.frg");
        s2 = Shader.makeShader("/src/engine/blur2.vrt", "/src/engine/blur2.frg");
        uielements = new ArrayList<>();
        //uielements.add(wind);
        EnemyReader.populateEnemies();
        map = new Map(new TiledMap("/src/res/testmap2.tmx", "/src/res"), worldPlayer);
        uiFocus = false;
        //music = new Music("/src/res/fatefulencounter.wav");
        //isPlaying = true;
        //music.loop();
        items = new Image("/src/engine/IconSet.png");
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
            //activeMenu = new Menu(worldPlayer);
            //uiFocus = true;
            //inMenu = true;
        }
        //Old menu
                /*if(inMenu==true){
         >>>>>>> upstream/master
         activeMenu.update(input);
         if((input.isKeyPressed(Input.KEY_K)) && activeMenu.getNotSub()){ 
         activeMenu.destroy();
         Sounds.cancel.play();
         inMenu = false;
         activeMenu = null;
         uiFocus = false;
         }
         <<<<<<< HEAD
         }
         =======
         }*/
        if (uiFocus == true) {
            if (input.isKeyDown(Input.KEY_K) && allowClose) {
                uielements.remove(lastAdded);
                uiFocus = false;
            }
        }
        map.update(container, worldPlayer);
        for (Window w : uielements) {
            if (w instanceof WindowSelectable) {
                ((WindowSelectable) w).update(inputp);
            }
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
        map.camera.translate(g);
        map.render(worldPlayer, g);
        //Sprite.animateSprite(testbattler, g, 200, 200, 423, 270, 4, 0, 4, 0, true);
        //Sprite.drawSpriteFrame(testbattler, g, 200, 200, 4, 0, 423, 270);
        Collections.sort(map.objs, depthComp);
        for (GameObject go : map.objs) {
            go.render(g);
        }

        //light.draw(0, 0, 1280, 720);



        for (Window w : uielements) {
            w.render(g, sbg);
            map.renderUI(worldPlayer);
        }

        //Old menu
        /*if(inMenu == true){
            
         activeMenu.render(g);
         }*/
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
        Graphics.setCurrent(bg);
        map.camera.translate(bg);
        map.render(worldPlayer, bg);
        Collections.sort(map.objs, depthComp);
        for (GameObject go : map.objs) {
            go.render(bg);
        }
        bg.flush();
        s.startShader();
        bg.drawImage(buffer, 0, 0);
        bg.flush();
        Shader.forceFixedShader();
        s2.startShader();
        bg.drawImage(buffer, 0, 0);
        Shader.forceFixedShader();
        Graphics.setCurrent(gc.getGraphics());
        SceneMenu.back = buffer.copy();
        

        /*try {
         gc.pause();
         buffer.getGraphics().clearAlphaMap();
         map.camera.translate(buffer.getGraphics(), worldPlayer);
         map.render(worldPlayer, buffer.getGraphics());
         Collections.sort(map.objs, depthComp);
         for(GameObject go: map.objs) {
         go.render(buffer.getGraphics());
         }
         buffer.getGraphics().flush();
         //s.setUniformFloatVariable("width", (float)B_WIDTH);
         s.startShader();
         buffer.setAlpha(100f);
         buffer.getGraphics().drawImage(buffer, 0, 0);
         buffer.getGraphics().clearAlphaMap();
         buffer.getGraphics().flush();
         s.stopShader();
         //s2.setUniformFloatVariable("height", (float)B_HEIGHT);
         s2.startShader();
         buffer.setAlpha(100f);
         buffer.getGraphics().drawImage(buffer, 0, 0);
         buffer.getGraphics().clearAlphaMap();
         buffer.getGraphics().flush();
         s2.stopShader();
         buffer.setAlpha(100f);
         SceneMenu.back = buffer;
         gc.resume();
         } catch (SlickException ex) {
         Logger.getLogger(SceneMap.class.getName()).log(Level.SEVERE, null, ex);
         }*/
        /*map.camera.translate(bg);
         map.render(worldPlayer, bg);
         Collections.sort(map.objs, depthComp);
         for(GameObject go: map.objs) {
         go.render(bg);
         }
         bg.flush();
         for(int x = 0; x < buffer.getWidth();x++ ){
         for(int y = 0; y < buffer.getHeight();y++){
         org.newdawn.slick.Color c = buffer.getColor(x, y);
         awtBuffer.setRGB(x, y, (c.getAlpha()<<24)+(c.getRed()<<16)+(c.getGreen()<<8)+c.getBlue());
         }
         }
            
         gfilter.filter(awtBuffer, awtBuffer);
         try {
         buffer.setTexture(BufferedImageUtil.getTexture(null, awtBuffer));
         } catch (IOException ex) {
         Logger.getLogger(SceneMap.class.getName()).log(Level.SEVERE, null, ex);
         }
         SceneMenu.back = buffer;
         * */
    }

    @Override
    public int getID() {
        return stateID;
    }
}
