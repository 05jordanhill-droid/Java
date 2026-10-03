package RPG;
// Inventory existence

import java.util.Map;

class Thing {
    private String _name;

    Thing(String name){
        SetName(name);
    }
    
    public String GetName() {
        return _name;
    }
    public void SetName(String _name) {
        this._name = _name;
    }
}

// Exists physically
class Entity extends Thing{
    private int _x;
    private int _y;

    private boolean _canSeePast;

    private Field _field;

    Entity(String name, int x, int y, boolean canSeePast, Field field){
        super(name);

        SetX(x);
        SetY(y);
        SetCanSeePast(canSeePast);
        SetField(field);
    }

    public int GetX() {
        return this._x;
    }
    public int GetY() {
        return this._y;
    }
    public int[] GetXY() {
        return new int[] {GetX(), GetY()};
    }
    public Field GetField() {
        return this._field;
    }
    public boolean CanSeePast() {
        return this._canSeePast;
    }

    public void SetX(int x) {
        this._x = x;
    }
    public void SetY(int y) {
        this._y = y;
    }
    public void SetField(Field field) {
        this._field = field;
    }
    public void SetCanSeePast(boolean canSeePast) {
        this._canSeePast = canSeePast;
    }
}

// Can move and have an inventory
public class Creature extends Entity {
    private int _health;
    private int _defense;
    private int _strength;
    private int _intelligence;
    private Script _script;

    Creature(String name, int x, int y, boolean canSeePast, Field field, int health, int defense, int intelligence, int strength, Script script) {
        super(name, x, y, canSeePast, field);

        SetHealth(health);
        SetDefense(defense);
        SetIntelligence(intelligence);
        SetStrength(strength);
        SetScript(script);
    }

    // Getters
    public int GetHealth() {
        return _health;
    }
    public int GetDefense() {
        return _defense;
    }
    public int GetIntelligence() {
        return _intelligence;
    }
    public int GetStrength() {
        return _strength;
    }
    public Script GetScript() {
        return _script;
    }

    //Setters
    public void SetHealth(int health) {
        this._health = health;
    }
    public void SetDefense(int defense) {
        this._defense = defense;
    }
    public void SetIntelligence(int intelligence) {
        this._intelligence = intelligence;
    }
    public void SetStrength(int strength) {
        this._strength = strength;
    }
    public void SetScript(Script script) {
        this._script = script;
    }
    
    // Behaviors
    public void Attacked(Creature attacker, Map<String, Integer> item) {
        SetHealth(
            CalculateHealth(attacker, this) - item.get("damage")
        );
    }
    public void Attacked(Creature attacker) {
        SetHealth(
            CalculateHealth(attacker, this)
        );
    }
    public void Attack(Creature defender, Map<String, Integer> item) {
        defender.Attacked(this, item);
    }
    public void Attack(Creature defender) {
        defender.Attacked(this);
    }
    
    public void Attack(int[] direction) {
        int[] target = new int[]{GetX()+direction[0], GetY()+direction[1]};

        Entity entity = GetField().GetSlot(target).GetEntity();

        if (entity instanceof Creature defender){
            defender.Attacked(this);
        } else {
            Script.AttackFailed();
        }
    }
    public void Attack(int[] direction, Map<String, Integer> item) {
        int[] target = new int[]{GetX()+direction[0], GetY()+direction[1]};

        Entity entity = GetField().GetSlot(target).GetEntity();

        if (entity instanceof Creature defender){
            defender.Attacked(this);
        } else {
            Script.AttackFailed();
        }
    }

    public void Move(int x_, int y_){
        int x = GetX()+x_, y = GetY()+y_;

        if(GetField().GetField()[y][x].IsEmpty()) {
            GetField().Move(this, x, y);
        }
    }

    // Helpers
    private static int CalculateHealth(Creature attacker, Creature defender){
        int damage = attacker.GetStrength() - defender.GetDefense();
        if (damage < 0){
            damage = 0;
        }
        int newHealth = defender.GetHealth() - damage;
        if (newHealth < 0){
            newHealth = 0;
        }

        return newHealth;
    }
}


class Goblin extends Creature {
    Goblin(int x, int y, Field field, int ID){
        super("Goblin_"+ID, x, y, false, field, 10, 10, 5, 10, new Script(new Aggressive()));
    }
}