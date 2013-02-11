package engine;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
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
        try {
            try {
                Controllers.create();
            } catch (LWJGLException ex) {
                Logger.getLogger(RPG.class.getName()).log(Level.SEVERE, null, ex);
            }
            AppGameContainer app = new AppGameContainer(new RPG());
            Sounds.load();
            app.setSmoothDeltas(true);
            app.setTargetFrameRate(60);
            app.setDisplayMode(SceneMap.B_WIDTH, SceneMap.B_HEIGHT, false);
            app.setVSync(true);
            app.setShowFPS(true);
            app.start();
            Controllers.destroy();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        addState(new SceneTitle(0));
        addState(new SceneMap(1));
        addState(new SceneMenu(2));
        addState(new SceneBattle(3));
    }
}