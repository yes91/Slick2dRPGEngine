/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Kieran
 */
public class SpriteCharacter extends SpriteBase {

    private GameCharacter gChar;
    private String imageName;
    private Animation currentAni;
    private Animation idle;
    
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
    private static final int IDLE_UP = 4;
    private static final int IDLE_DOWN = 5;
    private static final int IDLE_LEFT = 6;
    private static final int IDLE_RIGHT = 7;

    public SpriteCharacter(GameCharacter c, Image i) {
        gChar = c;
        imageName = gChar.characterName;
        image = new SpriteSheet(i, gChar.width, gChar.height);
        states = new Animation[]{
            new Animation(image, 0, 3, 3, 3, true, 120, true),//UP(0)
            new Animation(image, 0, 0, 3, 0, true, 120, true),//DOWN(1)
            new Animation(image, 0, 1, 3, 1, true, 120, true),//LEFT(2)
            new Animation(image, 0, 2, 3, 2, true, 120, true),//RIGHT(3)
            new Animation(image, 0, 3, 0, 3, false, 1, false),//IDLE_UP(4)
            new Animation(image, 0, 0, 0, 0, false, 1, false),//IDLE_DOWN(5)
            new Animation(image, 0, 1, 0, 1, false, 1, false),//IDLE_LEFT(6)
            new Animation(image, 0, 2, 0, 2, false, 1, false),//IDLE_RIGHT(7)
        };
        currentAni = states[5];
        idle = states[5];
    }

    public Animation getCurrentAni() {
        return currentAni;
    }

    public Animation getIdle() {
        return idle;
    }

    public void updateAnimationState() {
        if (gChar.dx > 0) {
            idle = states[IDLE_RIGHT];
            gChar.facing = GameCharacter.Direction.RIGHT;
            currentAni = states[RIGHT];
        } else if (gChar.dx < 0) {
            idle = states[IDLE_LEFT];
            gChar.facing = GameCharacter.Direction.LEFT;
            currentAni = states[LEFT];
        } else if (gChar.dy < 0) {
            idle = states[IDLE_UP];
            gChar.facing = GameCharacter.Direction.UP;
            currentAni = states[UP];
        } else if (gChar.dy > 0) {
            idle = states[IDLE_DOWN];
            gChar.facing = GameCharacter.Direction.DOWN;
            currentAni = states[DOWN];
        } else {
            currentAni = idle;
        }
    }
}
