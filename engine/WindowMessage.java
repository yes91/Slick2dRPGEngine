/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author redblast71
 */
public class WindowMessage extends WindowSelectable {

    private GameMessage gameMessage;
    public final int MAX_LINE = 4;
    private Color currentColor = Color.white;
    private Image face;
    public static float TYPE_DELAY = 10f;
    public boolean isTalking;
    private boolean isScrolling;
    private boolean skip;
    private int currentPage;
    private int currentLine;
    private int currentChar;
    private float time = TYPE_DELAY;
    private String drawChar = "";
    private int contX = 0;
    private int contY = 0;

    public WindowMessage() throws SlickException {
        super(0, (int) (SceneMap.B_HEIGHT * 0.82), SceneMap.B_WIDTH, (int) (SceneMap.B_HEIGHT * 0.18));
        this.index = -1;
        this.itemMax = 0;
        this.gameMessage = SceneBase.gameMessage;
    }

    public void startMessage() {
        face = Cache.getRes(gameMessage.faceName+".png");
        this.currentPage = 0;
        this.currentLine = 0;
        this.currentChar = 0;
        currentColor = Color.white;
        contX = 0;
        contY = 0;
        this.isTalking = true;
        isScrolling = true;
        skip = false;
        SceneMap.allowClose = false;
        convertSpecialChars();
        time = TYPE_DELAY;
        drawChar = "";
        cg.clear();
        updateMessage();
    }

    @Override
    public void render(Graphics g, StateBasedGame sbg) {
        super.render(g, sbg);
        Sprite.drawSpriteFrame(face, g, x + 16, y + 16, 4, gameMessage.faceIndex, 96, 96);
        cg.setColor(currentColor);
        if(skip){
            drawAll();
            skip = false;
            isScrolling = false;
        } else { 
            cg.drawString(drawChar, contX + 97, contY);
        }
        cg.flush();
    }

    @Override
    public void update(InputProvider input, int delta) {
        time -= (5f/16f)*delta;
        super.update(input, delta);
        if (isTalking) {
            if (input.isCommandControlPressed(SceneBase.down)) {
                if (isScrolling) {
                    skip = true;
                } else {
                    if ((currentPage < gameMessage.pages.length - 1)) {
                        drawChar = "";
                        currentPage++;
                        cg.clear();
                        isScrolling = true;
                        //updateMessage();
                    } else {
                        currentPage = 0;
                        //texts = new String[]{"", "", "", ""};
                        isTalking = false;
                        SceneMap.allowClose = true;
                    }
                }
            }
            updateMessage();
        }
    }

    public void convertSpecialChars() {
    }
    
    private void drawAll(){
        for(; currentLine < MAX_LINE; currentLine++){
            for(; currentChar < gameMessage.pages[currentPage][currentLine].length() - 1; currentChar++){

                switch (gameMessage.pages[currentPage][currentLine].charAt(currentChar)) {
                    case '\u0001':
                        cg.setColor(Color.red);
                        break;
                    case '\u0002':
                        cg.setColor(Color.white);
                        break;
                }
                drawChar = "" + gameMessage.pages[currentPage][currentLine].charAt(currentChar);
                switch (drawChar) {
                    case " ":
                        contX += Cache.getFont().getWidth("_");
                        break;
                    default:
                        contX += Cache.getFont().getWidth(drawChar);
                        break;
                }
                for(int z = 0; z < 4; z++){
                    cg.drawString(drawChar, contX + 97, contY);
                }
            }
            contY += 16;        
            contX = 0;
            currentChar = 0;
            drawChar = "";
        }
    }

    private void updateMessage() {
        int startPage = currentPage;
        if (currentLine > MAX_LINE - 1) {
            contY = 0;
            contX = 0;
            currentLine = 0;
            drawChar = "";
            isScrolling = false;
        }
        if (isScrolling && currentPage == startPage && time <= 0) {
            time = TYPE_DELAY;
            if (currentChar > gameMessage.pages[currentPage][currentLine].length() - 1) {
                contY += 16;
                currentLine++;
                contX = 0;
                currentChar = 0;
                drawChar = "";
            }
            if (isScrolling && currentPage == startPage && currentLine < MAX_LINE && !(currentChar > gameMessage.pages[currentPage][currentLine].length() - 1)) {
                switch (gameMessage.pages[currentPage][currentLine].charAt(currentChar)) {
                    case '\u0001':
                        currentColor = Color.red;
                        break;
                    case '\u0002':
                        currentColor = Color.white;
                        break;
                }
                drawChar = "" + gameMessage.pages[currentPage][currentLine].charAt(currentChar);
                switch (drawChar) {
                    case " ":
                        contX += Cache.getFont().getWidth("_");
                        break;
                    default:
                        contX += Cache.getFont().getWidth(drawChar);
                }
                currentChar++;
            } else {
                return;
            }
        }
    }
}
