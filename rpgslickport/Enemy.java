/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rpgslickport;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author redblast71
 */
public class Enemy extends Event{
    
    private Image enemySprite;
    private Image eBattleSprite;
    private String name;
    private int maxHP;
    private int currentHP;
    private int maxMP;
    private int currentMP;
    
    public Enemy(Image i,String n,int hp, int mp,int atk, int def,int matk,int mdef){
        
        super(0,0,0,0,"Enemy",null,null,null,null);
        enemySprite = i;
        try {
            eBattleSprite = new Image(i.getResourceReference().replaceAll(".png", "")+"!.png");
            System.out.println(eBattleSprite.getResourceReference());
        } catch (SlickException ex) {
            Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, ex);
        }
        name = n;
        maxHP = hp;
        maxMP = mp;
        
    }
    
    @Override
    public void render(Graphics g){
        Sprite.animateSprite(enemySprite, g, pos.x, pos.y, width, height, 4, 0, 4, .125f, true);   
    }
    
}
