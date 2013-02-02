/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
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
    public static int scrollSpeed = 30;
    private int waitCount;
    private String[] texts;
    public boolean isTalking;
    private boolean isScrolling;
    private int currentPage;
    private String drawChar = "";
    private int contX = 0;
    private int contY = 0;

    public WindowMessage() {
        super(0, (int) (SceneMap.B_HEIGHT * 0.85), SceneMap.B_WIDTH, (int) (SceneMap.B_HEIGHT * 0.15));
        this.index = -1;
        this.itemMax = 0;
        this.gameMessage = SceneBase.gameMessage;
        this.texts = new String[]{"", "", "", ""};
    }

    public void startMessage() {
        texts = new String[]{"", "", "", ""};
        this.currentPage = 0;
        contX = 0;
        contY = 0;
        this.isTalking = true;
        SceneMap.allowClose = false;
        convertSpecialChars();
        cg.clear();
        updateMessage();
    }

    @Override
    public void render(Graphics g, StateBasedGame sbg) {
        super.render(g, sbg);
        cg.setColor(currentColor);
        /*for (int i = 0; i < MAX_LINE; i++) {
            cg.drawString(texts[i],x + 0, y + (16 * (i)));
        }*/
        cg.drawString(drawChar, contX, contY);
        cg.flush();
    }

    @Override
    public void update(InputProvider input) {
        super.update(input);
        if (isTalking) {
            if (input.isCommandControlPressed(SceneBase.down)) {
                if (isScrolling) {
                    //texts = gameMessage.pages[currentPage];
                    //isScrolling = false;
                } else {
                    if ((currentPage < gameMessage.pages.length - 1)) {
                        texts = new String[]{"", "", "", ""};
                        currentPage++;
                        cg.clear();
                        updateMessage();
                    } else {
                        currentPage = 0;
                        //texts = new String[]{"", "", "", ""};
                        isTalking = false;
                        scroller = null;
                        SceneMap.allowClose = true;
                    }
                }
            }
        }
    }
    
    public void convertSpecialChars(){
        
    }
    
    private Thread scroller = new Thread() {
            @Override
            public void run() {
                isScrolling = true;
                int startPage = currentPage;
                for (int j = 0; j < 4; j++) {
                    for (int i = 0; i < gameMessage.pages[currentPage][j].length(); i++) {
                        if (isScrolling && currentPage == startPage) {
                            switch(gameMessage.pages[currentPage][j].charAt(i)){
                                case '\u0001': currentColor = Color.red; break;
                                case '\u0002': currentColor = Color.white; break;
                            }
                            drawChar = ""+gameMessage.pages[currentPage][j].charAt(i);
                            switch(drawChar){
                                case " ": contX += Cache.getFont().getWidth("_"); break;
                                default: contX += Cache.getFont().getWidth(drawChar);
                            } 
                            contY = 16*j;
                            //texts[j] = displayText;
                            try {
                                sleep(1000 / scrollSpeed);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(WindowMessage.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            return;
                        }
                    }
                    if(isScrolling && currentPage == startPage){
                        contX = 0;
                    }
                }

                isScrolling = false;
            }
        };

    private void updateMessage() {
        scroller = new Thread() {
            @Override
            public void run() {
                isScrolling = true;
                int startPage = currentPage;
                for (int j = 0; j < 4; j++) {
                    for (int i = 0; i < gameMessage.pages[currentPage][j].length(); i++) {
                        if (isScrolling && currentPage == startPage) {
                            switch(gameMessage.pages[currentPage][j].charAt(i)){
                                case '\u0001': currentColor = Color.red; break;
                                case '\u0002': currentColor = Color.white; break;
                            }
                            drawChar = ""+gameMessage.pages[currentPage][j].charAt(i);
                            switch(drawChar){
                                case " ": contX += Cache.getFont().getWidth("_"); break;
                                default: contX += Cache.getFont().getWidth(drawChar);
                            } 
                            contY = 16*j;
                            //texts[j] = displayText;
                            try {
                                sleep(1000 / scrollSpeed);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(WindowMessage.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            scroller = null;
                            return;
                        }
                    }
                    if(isScrolling && currentPage == startPage){
                        contX = 0;
                    }
                }

                isScrolling = false;
                scroller = null;
            }
        };
        scroller.start();
    }
}
