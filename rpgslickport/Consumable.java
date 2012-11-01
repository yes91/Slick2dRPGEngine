/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rpgslickport;

/**
 *
 * @author Kieran
 */
public class Consumable extends Item {
    
    private String effect;
    private String type;
    
    public Consumable(String name,String type) {
        super(name, true);
        this.type = type;
    }
    
    public void use(GameObject target, Inventory inv){
        
        if(type.equals("hppotion")){
            if(((WorldPlayer)target).currentHP < ((WorldPlayer)target).maxHP){
                ((WorldPlayer)target).currentHP += Integer.parseInt(effect);
                inv.remove(this, 1);
            }
        }
    }
    
    public void setEffect(String s){
        
        effect = s;
    }
    
    
}
