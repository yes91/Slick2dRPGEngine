package engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class WorldPlayer extends GameCharacter {

    private boolean action;
    private Rectangle bounds;

    public WorldPlayer(String image) {
        super(image);
        characterName = image;
        pos.x = 50;
        pos.y = 60;
        bounds = new Rectangle(pos.x - width/2 + 8, pos.y, width - 16, height/2);
    }
    
    public void setActor(GameActor a){
        sprite = new SpriteCharacter(this, GameCache.res(a.characterName+".png"));
        characterName = a.characterName;
    }

    public void update(InputProvider in, int delta) {
        getInput(in, delta);
        if (collide()) {
            pos.x = lastX;
            pos.y = lastY;
        }
        lastX = pos.x;
        lastY = pos.y;
        pos.x += dx;
        pos.y += dy;
        bounds.setLocation(pos.x - width/2 + 8, pos.y);
        sprite.updateAnimationState();
    }
    
    @Override
    public boolean collide(){
        boolean blocked = false;
        if(pos.x + width/2  > SceneMap.map.boundsX || pos.y + height/2 > SceneMap.map.boundsY || pos.x - width/2 < 0 || pos.y - height/2 < 0){
            blocked = true;
        }
        for(Rectangle o: SceneMap.map.listRect){
            if (Physics.checkCollisions(this, o)) {
                blocked = true;
            }
        }
        return blocked;
    }

    @Override
    public void render(Graphics g2d) {
        //g2d.draw(bounds);
        if (!collide()) {
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
        return (int) (pos.x / map.getTileWidth());
    }

    public int getTileY(TiledMap map) {
        return (int) (pos.y / map.getTileHeight());
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
        return bounds;
    }
}
