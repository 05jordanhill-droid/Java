package RPG;
public class Item extends Thing {
    private Entity _owner;

    Item(String name, Entity owner) {
        super(name);
        SetOwner(owner);
    }
    public void SetOwner(Entity owner){
        this._owner = owner;
    }
    public Entity GetOwner(){
        return this._owner;
    }

    public void Transfer(Entity newOwner){
        SetOwner(newOwner);
    }
}
