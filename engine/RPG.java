package engine;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class RPG extends StateBasedGame {

    public RPG() {

        super("J-RPG Engine Demo");

    }

    public static void main(String[] arguments) {
        System.setProperty("org.lwjgl.librarypath", new File(new File(System.getProperty("user.dir"), "native"), LWJGLUtil.getPlatformName()).getAbsolutePath());
        System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"));
        try {
            
            AppGameContainer app = new AppGameContainer(new RPG());
            app.setSmoothDeltas(true);
            app.setTargetFrameRate(60);
            app.setVerbose(true);
            app.setDisplayMode(SceneMap.B_WIDTH, SceneMap.B_HEIGHT, false);
            app.setVSync(true);
            app.setShowFPS(true);
            //Renderer.setRenderer(Renderer.VERTEX_ARRAY_RENDERER);
            app.start();
        } catch (SlickException ex) {
            Logger.getLogger(RPG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException{
        try {
            /*GL30.glClampColor(GL30.GL_CLAMP_VERTEX_COLOR, GL11.GL_FALSE); //Disables clamping of glColor
            GL30.glClampColor(GL30.GL_CLAMP_READ_COLOR, GL11.GL_FALSE); //Kind of unnecessary but whatever
            GL30.glClampColor(GL30.GL_CLAMP_FRAGMENT_COLOR, GL11.GL_FALSE);*/
            gc.getInput().initControllers();
            Demo.init();
            GameData.editorMode = false;
            GameData.populate();
            Sounds.load();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(RPG.class.getName()).log(Level.SEVERE, null, ex);
        }
        gc.setDefaultFont(GameCache.getFont());
        addState(new SceneTitle(0));
        addState(new SceneMap(1));
        addState(new SceneMenu(2));
        addState(new SceneBattle(3));
    }
}