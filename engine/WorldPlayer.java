package engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class WorldPlayer extends GameCharacter {

    private boolean action;

    public WorldPlayer(String image) {
        super(image);
        characterName = image;
        pos.set(50, 60);
        bounds = new Rectangle(pos.x - width/2 + (width/8), pos.y, width - (width/4), height/2);
    }
    
    public void setActor(GameActor a){
        Image aSprite = GameCache.res(a.characterName+".png");
        width = aSprite.getWidth()/4;
        height = aSprite.getHeight()/4;
        sprite = new SpriteCharacter(this, aSprite);
        characterName = a.characterName;
        bounds = new Rectangle(pos.x - width/2 + (width/8), pos.y, width - (width/4), height/2);
    }

    public void update(InputProvider in, int delta) {
        getInput(in, delta);
        if (collide()) {
            pos.set(lastPos);
        }
        lastPos.set(pos);
        pos.add(deltaPos);
        super.update();
    }
    
    @Override
    public boolean collide(){
        boolean blocked = false;
        if(pos.x + width/2  > SceneMap.map.boundsX || pos.y + height/2 > SceneMap.map.boundsY || pos.x - width/2 < 0 || pos.y - height/2 < 0){
            blocked = true;
        }
        for(Rectangle o: SceneMap.map.listRect){
            if (this.bounds.intersects(o)) {
                blocked = true;
            }
        }
        for(GameObject o: SceneMap.map.objs){
            if(o instanceof GameCharacter && o != this){
                if (this.bounds.intersects(((GameCharacter)o).bounds)) {
                    blocked = true;
                }
            }
        }
        return blocked;
    }

    @Override
    public void render(Graphics g2d) {
        if (!collide()) {
            super.render(g2d);
        } else {
            sprite.getIdle().draw((int) lastPos.x - width/2, (int) lastPos.y - height/2);
        }
        //g2d.draw(bounds);
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
        return (((deltaPos.x != 0) || (deltaPos.y != 0)) && !collide());
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
                    deltaPos.x = -(0.48f * delta);
                } else {
                    deltaPos.x = -(0.24f * delta);
                }
            }

            if (input.isCommandControlDown(SceneBase.right)) {
                if (input.isCommandControlDown(SceneBase.sprint)) {
                    deltaPos.x = 0.48f * delta;
                } else {
                    deltaPos.x = 0.24f * delta;
                }
            }

            if ((!input.isCommandControlDown(SceneBase.right)) && (!input.isCommandControlDown(SceneBase.left))) {
                deltaPos.x = 0;
            }

            if (input.isCommandControlDown(SceneBase.up)) {
                if (input.isCommandControlDown(SceneBase.sprint)) {
                    deltaPos.y = -(0.48f * delta);
                } else {
                    deltaPos.y = -(0.24f * delta);
                }
            }

            if (input.isCommandControlDown(SceneBase.down)) {
                if (input.isCommandControlDown(SceneBase.sprint)) {
                    deltaPos.y = 0.48f * delta;
                } else {
                    deltaPos.y = 0.24f * delta;
                }
            }

            if ((!input.isCommandControlDown(SceneBase.up)) && (!input.isCommandControlDown(SceneBase.down))) {
                deltaPos.y = 0;
            }

        } else {
            action = false;
            deltaPos.set(0, 0);
        }
    }
    
    public void tp(float x, float y){
        pos.set(x, y);
        lastPos.set(pos);
    }

    public boolean getAction() {
        return action;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }
}
