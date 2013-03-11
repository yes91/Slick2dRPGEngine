package engine;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.input.Controllers;
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
            
            Controllers.create();
            AppGameContainer app = new AppGameContainer(new RPG());
            app.setSmoothDeltas(true);
            app.setTargetFrameRate(60);
            app.setDisplayMode(SceneMap.B_WIDTH, SceneMap.B_HEIGHT, false);
            app.setVSync(true);
            app.setShowFPS(true);
            app.start();
            Controllers.destroy();  
        } catch (LWJGLException | SlickException ex) {
            Logger.getLogger(RPG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException{
        try {
            //Demo.init();
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