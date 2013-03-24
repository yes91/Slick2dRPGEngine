/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.newdawn.slick.Graphics;
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
    public static float TYPE_DELAY = 10f;
    public boolean isTalking;
    private boolean isScrolling;
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
    
    public WindowMessage(Camera camera) throws SlickException {
        super(0, (int) (SceneMap.B_HEIGHT * 0.82), SceneMap.B_WIDTH, (int) (SceneMap.B_HEIGHT * 0.18));
        this.index = -1;
        this.itemMax = 0;
        this.gameMessage = SceneBase.gameMessage;
        this.camera = camera;
    }

    public void startMessage() {
        this.currentPage = 0;
        this.currentLine = 0;
        this.currentChar = 0;
        contX = 0;
        contY = 0;
        this.isTalking = true;
        isScrolling = true;
        SceneMap.allowClose = false;
        time = TYPE_DELAY;
        drawChar = "";
        cg.clear();
        drawFace(gameMessage.faceName, gameMessage.faceIndex, 0, 0, 96);
    }

    @Override
    public void render(Graphics g, StateBasedGame sbg) {
        super.render(g, sbg);
        g.drawImage(contents, x + 16, y + 16);
    }

    @Override
    public void update(InputProvider input, int delta) {
        time -= (5f/16f)*delta;
        super.update(input, delta);
        if (isTalking) {
            if (input.isCommandControlPressed(SceneBase.action)) {
                if (isScrolling) {
                    drawAll();
                } else {
                    if ((currentPage < gameMessage.pages.length - 1)) {
                        drawChar = "";
                        currentPage++;
                        cg.clear();
                        drawFace(gameMessage.faceName, gameMessage.faceIndex, 0, 0, 96);
                        isScrolling = true;
                    } else {
                        currentPage = 0;
                        isTalking = false;
                        SceneMap.allowClose = true;
                    }
                }
            }
            updateMessage();
            cg.flush();
        }
    }

    public void convertSpecialChars() {
        
    }
    
    private void drawAll(){
        Pattern p = Pattern.compile("\\[([0-9]+)\\]");
        Matcher m;
        for(; currentLine < MAX_LINE; currentLine++){
            for(; currentChar < gameMessage.pages[currentPage][currentLine].length(); currentChar++){
                drawChar = "" + gameMessage.pages[currentPage][currentLine].charAt(currentChar);
                switch (gameMessage.pages[currentPage][currentLine].charAt(currentChar)) {
                    case '\u0001':
                        m = p.matcher(gameMessage.pages[currentPage][currentLine].substring(currentChar, currentChar+5));
                        int color = 0;
                        if(m.find()){
                           color = Integer.parseInt(m.group(1)); 
                        } 
                        gameMessage.pages[currentPage][currentLine] =
                                gameMessage.pages[currentPage][currentLine]
                                    .replaceFirst("\\[([0-9]+)\\]", "");
                        cg.setColor(textColor(color));
                        break;
                    case ' ':
                        contX += GameCache.getFont().getWidth("_");
                        break;
                    default:
                        contX += GameCache.getFont().getWidth(drawChar);
                        break;
                }
                for(int z = 0; z < 4; z++){
                    cg.drawString(drawChar, contX + 97, contY);
                }
            }
            contY += WINDOW_LINE_HEIGHT;        
            contX = 0;
            currentChar = 0;
            drawChar = "";
        }
        isScrolling = false;
    }

    private void updateMessage() {
        int startPage = currentPage;
        Pattern p = Pattern.compile("\\[([0-9]+)\\]");
        Matcher m;
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
                contY += WINDOW_LINE_HEIGHT;
                currentLine++;
                contX = 0;
                currentChar = 0;
                drawChar = "";
            }
            if (isScrolling && currentPage == startPage && currentLine < MAX_LINE && !(currentChar > gameMessage.pages[currentPage][currentLine].length() - 1)) {
                drawChar = "" + gameMessage.pages[currentPage][currentLine].charAt(currentChar);
                switch (gameMessage.pages[currentPage][currentLine].charAt(currentChar)) {
                    case '\u0001':
                         m = p.matcher(gameMessage.pages[currentPage][currentLine]);
                        int color = 0;
                        if(m.find()){
                           color = Integer.parseInt(m.group(1)); 
                        }
                        gameMessage.pages[currentPage][currentLine] =
                                gameMessage.pages[currentPage][currentLine]
                                    .replaceFirst("\\[([0-9]+)\\]", "");
                        cg.setColor(textColor(color));
                        break;
                    case '\u0003':
                        time = 5f * 15;
                        break;
                    case '\u0004':
                        time = 5f * 60;
                        break;
                    case ' ':
                        contX += GameCache.getFont().getWidth("_");
                        break;
                    default:
                        contX += GameCache.getFont().getWidth(drawChar);
                        break;
                }
                for(int z = 0; z < 4; z++){
                    cg.drawString(drawChar, contX + 97, contY);
                }
                currentChar++;  
            } else {
                return;
            }
        }
    }
}
