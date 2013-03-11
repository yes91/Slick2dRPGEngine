package engine;

public abstract class Item extends Effect{

    private boolean useable;
    private int price;

    public Item(String name, boolean use) {
        super(name);
        this.useable = use;
        this.price = 0;
        this.setDesc("Default item description.");

    }
    
    public int getPrice(){
        return price;
    }
    
    public void setPrice(int price){
        this.price = price;
    }

    public boolean isUseable() {

        return useable;
    }

    @Override
    public String toString() {
        String list = 
                "Name: "+getName()+"\n"+
                "Scope: "+getScope().toString()+"\n"+
                "Description: "+getDesc();
        return list;
    }
}
