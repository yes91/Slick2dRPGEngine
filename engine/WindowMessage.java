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
    public static int TYPE_DELAY = 20;
    public boolean isTalking;
    private boolean isScrolling;
    private int currentPage;
    private int currentLine;
    private int currentChar;
    private int time = TYPE_DELAY;
    private String drawChar = "";
    private int contX = 0;
    private int contY = 0;

    public WindowMessage() throws SlickException {
        super(0, (int) (SceneMap.B_HEIGHT * 0.85), SceneMap.B_WIDTH, (int) (SceneMap.B_HEIGHT * 0.15));
        face = Cache.getRes("People1.png");
        this.index = -1;
        this.itemMax = 0;
        this.gameMessage = SceneBase.gameMessage;
    }

    public void startMessage() {
        this.currentPage = 0;
        this.currentLine = 0;
        this.currentChar = 0;
        currentColor = Color.white;
        contX = 0;
        contY = 0;
        this.isTalking = true;
        isScrolling = true;
        SceneMap.allowClose = false;
        convertSpecialChars();
        drawChar = "";
        cg.clear();
        updateMessage();
    }

    @Override
    public void render(Graphics g, StateBasedGame sbg) {
        super.render(g, sbg);
        Sprite.drawSpriteFrame(face, g, x + 8, y + 6, 4, 2, 96, 96);
        cg.setColor(currentColor);
        cg.drawString(drawChar, contX + 97, contY);
        cg.flush();
    }

    @Override
    public void update(InputProvider input) {
        time -= 16;
        super.update(input);
        if (isTalking) {
            if (input.isCommandControlPressed(SceneBase.down)) {
                if (isScrolling) {
                    //Do somethig to print all.
                    //isScrolling = false;
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

    private void updateMessage() {
        int startPage = currentPage;
        if (isScrolling && currentPage == startPage && time <= 0) {
            time = TYPE_DELAY;
            if (currentChar > gameMessage.pages[currentPage][currentLine].length() - 1) {
                contY += 16;
                currentLine++;
                contX = 0;
                currentChar = 0;
                drawChar = "";
            }
            if (isScrolling && currentPage == startPage && currentLine < 4 && !(currentChar > gameMessage.pages[currentPage][currentLine].length() - 1)) {
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
        if (currentLine > 3) {
            contY = 0;
            currentLine = 0;
            drawChar = "";
            isScrolling = false;
        }
    }
}
