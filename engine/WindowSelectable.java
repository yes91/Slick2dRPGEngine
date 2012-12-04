/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author redblast71
 */
public class WindowSelectable extends Window{
    
    public int itemMax;
    public int columnMax;
    public int index;
    private int spacing;
    
    public WindowSelectable(int x, int y, int width, int height){
    super(x, y, width, height);
    itemMax = 1;
    columnMax = 1;
    index = -1;
    spacing = 32;
    }
    
    @Override
    public void render(Graphics g, StateBasedGame sbg){
        super.render(g, sbg);
    }
    
    public void setCursorPos(int id){
        index = id;
    }
    
    public int getRowCount(){
       return (itemMax + columnMax - 1) / columnMax;
    }
    
    public int getTopRow(){
        return oy/WINDOW_LINE_HEIGHT;
    }
    
    public int getRowMax(){
    return (itemMax + columnMax - 1) / columnMax;
    }
    
    public void setTopRow(int row){
        if(row < 0){ row = 0; }
        if(row > getRowMax() - 1){ row = getRowMax() - 1; }
        oy = row * WINDOW_LINE_HEIGHT;
    }
    
    public int getPageRowMax(){
        return (height - 32) / WINDOW_LINE_HEIGHT;
    }
    
    public int getPageItemMax(){
        return getPageRowMax() * columnMax;
    }
    
    public int getBottomRow(){
        return getTopRow() + (getPageRowMax() - 1);
    }
    
    public void setBottomRow(int row){
        setTopRow(row - (getPageRowMax() - 1));
    }
    
    public Rectangle getItemRect(int id){
        Rectangle rect = new Rectangle(0,0,0,0);
        rect.setWidth(((width - 32) + spacing) / columnMax - spacing);
        rect.setHeight(WINDOW_LINE_HEIGHT);
        rect.setX(id % columnMax * (rect.getWidth() + spacing));
        rect.setY((id / columnMax )* WINDOW_LINE_HEIGHT);
        return rect;
    }
    
    public void cursorDown(boolean wrap){
    if((index < itemMax - columnMax) || (wrap && columnMax == 1)){
      index = (index + columnMax) % itemMax;
      }
    }
    
    public void cursorUp(boolean wrap){
    if((index >= columnMax) || (wrap && columnMax == 1)){
      index = (index - columnMax + itemMax) % itemMax;
      }
    }
    
    public void cursorRight(boolean wrap){
    if((columnMax >= 2) && (index < itemMax - 1 || (wrap && getPageRowMax() == 1))){
      index = (index + 1 + itemMax) % itemMax;
      }
    }
    
    public void cursorLeft(boolean wrap){
    if((columnMax >= 2) && (index > 0 || (wrap && getPageRowMax() == 1))){
      index = (index - 1 + itemMax) % itemMax;
      }
    }
    
    public void cursorPageDown(){
    if(getTopRow() + getPageRowMax() < getRowMax()){
      index = Math.min(index + getPageItemMax(), itemMax - 1);
      setTopRow(getTopRow() + getPageRowMax());
        }
    }
    
    public void cursorPageUp(){
    if(getTopRow() > 0){
      index = Math.max(index - getPageItemMax(), 0);
      setTopRow(getTopRow() - getPageRowMax());
        }
    }
    
    public void update(InputProvider input){
        if(itemMax > 0){
        int lastIndex = index;
        if(input.isCommandControlPressed(SceneBase.down)){
            cursorDown(input.isCommandControlDown(SceneBase.down));
        }
        if(input.isCommandControlPressed(SceneBase.up)){
            cursorUp(input.isCommandControlDown(SceneBase.up));
        }
        if(input.isCommandControlPressed(SceneBase.down)){
            cursorDown(input.isCommandControlDown(SceneBase.down));
        }
        if(input.isCommandControlPressed(SceneBase.right)){
            cursorRight(input.isCommandControlDown(SceneBase.right));
        }
        if(input.isCommandControlPressed(SceneBase.left)){
            cursorLeft(input.isCommandControlDown(SceneBase.left));
        }
        if(index != lastIndex) { Sounds.cursor.play(); }
        updateCursor();
        }
    }
    
    public void updateCursor(){
      int row = 0;
      if(index < 0){
          cursorRect = new Rectangle(0,0,0,0);
      }
      else{
          row = index / columnMax;
      }
      if(row < getTopRow()){
        setTopRow(row);
      }
      if(row > getBottomRow()){
        setBottomRow(row);
      }
      Rectangle rect = getItemRect(index);
      rect.setY(rect.getY() - oy);
      cursorRect = rect;
    }
}
