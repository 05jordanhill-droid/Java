package RPG;
import java.util.List;
import java.util.ArrayList;

class Slot implements Visual {
    private Entity _entity;
    private int _x;
    private int _y;
    private Data data;

    Slot(Entity entity) {
        SetEntity(entity);
        _x = entity.GetX();
        _y = entity.GetY();

        data = new Data();
        data.ReadFile("map-key.json", "key");
    }

    public Entity GetEntity() {
        return this._entity;
    }
    public int GetX() {
        return this._x;
    }
    public int GetY() {
        return this._y;
    }
    public int[] GetXY() {
        return new int[]{this._x, this._y};
    }

    public void Display(){
        Support.Display(
            data.Get( 
                List.of(
                    "key", 
                    Support.Truncate(GetEntity().GetName(), "_")
                )
            ), false
        );
    }
    
    public void SetEntity(Entity entity) {
        this._entity = entity;
    }

    public boolean IsEmpty(){
        if(GetEntity().GetName() == null){
            return true;
        }
        return false;
    }
    public boolean CanSeePast(){
        return GetEntity().CanSeePast();
    }
}

public class Field {
    private Slot[][] _field;

    Field(int width, int height){
        SetEmptyField(width, height);
    }
    Field(Slot[][] field){
        SetField(field);
    }

    public void SetField(Slot[][] _field) {
        this._field = _field;
    }
    public void SetEmptyField(int width, int height) {
        this._field = new Slot[height][width];
        
        int x = 0, y = 0;
        for (Slot[] row : this._field){
            for (Slot slot : row){
                slot.SetEntity(new Entity(null, x, y, true, this));
                x++;
            }
            y++;
        }
    }

    public Slot[][] GetField() {
        return this._field;
    }
    public Slot GetSlot(int[] coords) {
        return this._field[coords[1]][coords[0]];
    }

    public void Move(Entity mover, int x, int y){
        int x_ = mover.GetX(), y_ = mover.GetY();

        GetField()[y][x].SetEntity(mover);
        GetField()[y_][x_].SetEntity(new Entity(null, x_, y_, true, this));
        
        mover.SetX(x);
        mover.SetY(y);
    }

    //Helpers
    public List<Slot> VisibleRange(Entity looker, int range){
        List<int[]> maxCoords = Support.GetCoordsSquare(range, looker.GetXY());
        List<Slot> maxSlots = CoordsToSlots(maxCoords);
        List<Slot> visibleSlots = new ArrayList<>();

        for(Slot slot : maxSlots){
            List<int[]> coordsLine = Support.GetLineCoordinates(looker.GetXY(), slot.GetXY());
            List<Slot> slotsLine = CoordsToSlots(coordsLine);
            if (IsVisible(slot, slotsLine)){
                visibleSlots.add(slot);
            }
        }

        return visibleSlots;
    }
    public List<Slot> CoordsToSlots(List<int[]> coords){
        List<Slot> rvalue = new ArrayList<>();

        for(int[] coord : coords){
            rvalue.add(GetSlot(coord));
        }

        return rvalue;
    }
    public boolean IsVisible(Slot slot, List<Slot> SlotsList){
        if (SlotsList.contains(slot)){
            SlotsList.remove(slot);
        }
        if (Support.ListHasQuality(SlotsList, var -> var.CanSeePast(), false)){
            return false;
        }
        return true;
    }
}