package rpgslickport;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Missile {

    private int x, y;
    private Image image;
    boolean visible;

    private final int BOARD_WIDTH = 570;
    private final int MISSILE_SPEED = 2;

    public Missile(int x, int y) {
        try {
            image = new Image("missile.png");
        } catch (SlickException ex) {
            Logger.getLogger(Missile.class.getName()).log(Level.SEVERE, null, ex);
        }
        visible = true;
        this.x = x;
        this.y = y;
    }


    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void move() {
        x += MISSILE_SPEED;
        if (x > BOARD_WIDTH) {
            visible = false;
        }
    }
}