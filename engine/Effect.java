package engine;

public abstract class Effect extends BaseItem{
    
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
    
    private int speed;
    
    public int baseDamage;
    public int variance;
    public float ATKFactor;
    public float MATKFactor;
    
    public boolean physical;
    public boolean MPdamage;
    public boolean absorb;
    public boolean ignoreDEF;
    
    public Effect(String name){
        super(name);
    }
    
    public boolean forAlly(){
        return scope == Scope.SINGLE_ALLY || scope == Scope.ALL_ALLY ||
                scope == Scope.USER;
    }
    
    public boolean forDeadAlly(){
        return scope == Scope.DEAD_ALLY || scope == Scope.ALL_DEAD;
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
    
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
}