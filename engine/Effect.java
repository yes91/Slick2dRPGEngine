package engine;

public abstract class Effect {
    
    public static enum Scope {
        NONE,
        SINGLE_ALLY, ALL_ALLY, SINGLE_ENEMY, ALL_ENEMY, DEAD_ALLY, ALL_DEAD, 
        USER, RANDOM_ENEMY, RANDOM_ENEMY_2, RANDOM_ENEMY_3
    }
    public static enum Place {
        MENU_ONLY, BATTLE_ONLY, ALWAYS, NEVER
    }
    
    private Scope scope;
    private Place place;
    
    private String name;
    private String desc;
    private int speed;
    private int gid;
    
    public int baseDamage;
    public int variance;
    public float ATKFactor;
    public float MATKFactor;
    
    public boolean physical;
    public boolean MPdamage;
    public boolean absorb;
    public boolean ignoreDEF;
    
    public Effect(String name){
        this.name = name;
    }
    
    public boolean forAlly(){
        return scope == Scope.SINGLE_ALLY || scope == Scope.ALL_ALLY || 
                scope == Scope.DEAD_ALLY || scope == Scope.ALL_DEAD || 
                scope == Scope.USER;
    }
    
    public boolean forEnemy(){
        if(scope.name().contains("ENEMY")){
            return true;
        }
        return false;
    }
    
    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public int getIndex() {
        return gid;
    }

    public void setIndex(int n) {
        gid = n;
    }
    
}