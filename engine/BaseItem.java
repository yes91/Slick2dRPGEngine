/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

/**
 *
 * @author Kieran
 */
public abstract class BaseItem {
    
    private String name;
    private String desc;
    private int gid;
    
    public BaseItem(String name){
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public int getIndex() {
        return gid;
    }

    public void setIndex(int n) {
        gid = n;
    }
}
