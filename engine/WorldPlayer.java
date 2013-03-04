package engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class WorldPlayer extends GameCharacter {

    private boolean action;

    public WorldPlayer(Image i) {
        super(i);
        pos.x = 50;
        pos.y = 60;
    }

    public void update(InputProvider in, int delta) {
        getInput(in, delta);
        if (SceneMap.isBlocked() == true || SceneMap.stopPlayer() == true) {
            pos.x = lastX;
            pos.y = lastY;
        }
        lastX = pos.x;
        lastY = pos.y;
        pos.x += dx;
        pos.y += dy;
        sprite.updateAnimationState();
    }

    @Override
    public void render(Graphics g2d) {
        if (!SceneMap.blocked) {
            sprite.getCurrentAni().draw((int) pos.x - 32, (int) pos.y - 48);
        } else {
            sprite.getIdle().draw((int) lastX - 32, (int) lastY - 48);
        }
    }

    public void setX(float x1) {
        pos.x = x1;
    }

    public void setY(float y1) {
        pos.y = y1;
    }

    public int getTileX(TiledMap map) {
        return (int) (getBounds().getX() / map.getTileWidth());
    }

    public int getTileY(TiledMap map) {
        return (int) (getBounds().getY() / map.getTileHeight());
    }

    public Image getImage() {
        return sprite.image;
    }

    public boolean isMoving() {

        if (((dx != 0) | (dy != 0)) & !SceneMap.blocked) {
            return true;
        } else {
            return false;
        }
    }

    public void getInput(InputProvider input, int delta) {


        if (SceneMap.uiFocus == false) {

            if (input.isCommandControlPressed(SceneBase.action)) {
                action = true;
            } else {
                action = false;
            }

            if (input.isCommandControlDown(SceneBase.left)) {
                if (input.isCommandControlDown(SceneBase.sprint)) {
                    dx = -(0.48f * delta);
                } else {
                    dx = -(0.24f * delta);
                }
            }

            if (input.isCommandControlDown(SceneBase.right)) {
                if (input.isCommandControlDown(SceneBase.sprint)) {
                    dx = 0.48f * delta;
                } else {
                    dx = 0.24f * delta;
                }
            }


            if ((!input.isCommandControlDown(SceneBase.right)) && (!input.isCommandControlDown(SceneBase.left))) {
                dx = 0;
            }

            if (input.isCommandControlDown(SceneBase.up)) {
                if (input.isCommandControlDown(SceneBase.sprint)) {
                    dy = -(0.48f * delta);
                } else {
                    dy = -(0.24f * delta);
                }
            }

            if (input.isCommandControlDown(SceneBase.down)) {
                if (input.isCommandControlDown(SceneBase.sprint)) {
                    dy = 0.48f * delta;
                } else {
                    dy = 0.24f * delta;
                }
            }

            if ((!input.isCommandControlDown(SceneBase.up)) && (!input.isCommandControlDown(SceneBase.down))) {
                dy = 0;
            }

        } else {
            action = false;
            dx = 0;
            dy = 0;
        }
    }

    public boolean getAction() {

        return action;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((pos.x - 32) + 10, (pos.y - 48) + 40, 52, 52);
    }
}
