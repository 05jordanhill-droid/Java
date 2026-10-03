package RPG;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;

public class Script {
    private Behavior behavior_;

    Script(Behavior behavior){
        behavior_ = behavior;
    }

    public Behavior GetBehavior() {
        return behavior_;
    }

    public void Run(Entity entity){

    }

    // Possible Actions
    public static Consumer<Creature> CheckAction(String choice, Creature creature, List<String> options){
        Behavior behavior = creature.GetScript().GetBehavior();
        try {
            Integer index = Support.ToInt(choice) - 1;
            if       (options.get(index) == "use item"){
                return Creature -> behavior.UseItem(creature);
            } else if(options.get(index) == "drop item"){
                return Creature -> behavior.DropItem(creature);
            } else if(options.get(index) == "take item"){
                return Creature -> behavior.TakeItem(creature);
            } else if(options.get(index) == "go north"){
                return Creature -> behavior.GoNorth(creature);
            } else if(options.get(index) == "go west"){
                return Creature -> behavior.GoWest(creature);
            } else if(options.get(index) == "go east"){
                return Creature -> behavior.GoEast(creature);
            } else if(options.get(index) == "go south"){
                return Creature -> behavior.GoSouth(creature);
            }
        } catch (Exception e) {
            if       (choice == "use item"){
                return Creature -> behavior.UseItem(creature);
            } else if(choice == "drop item"){
                return Creature -> behavior.DropItem(creature);
            } else if(choice == "take item"){
                return Creature -> behavior.TakeItem(creature);
            } else if(choice == "go north"){
                return Creature -> behavior.GoNorth(creature);
            } else if(choice == "go west"){
                return Creature -> behavior.GoWest(creature);
            } else if(choice == "go east"){
                return Creature -> behavior.GoEast(creature);
            } else if(choice == "go south"){
                return Creature -> behavior.GoSouth(creature);
            }
        }
        return Creature -> Script.DoNothing(creature);
    }

    // Possible Items
    public static Boolean CheckItem(String choice, List<String> options){
        try {
            Integer index = Support.ToInt(choice) - 1;
            if (options.contains(options.get(index))){
                return true;
            } 
        } catch (Exception e) {
            if (options.contains(choice)){
                return true;
            } 
        }
        return false;
    }

    // Possible Directions
    public static int[] CheckDirection(String choice){
        List<String> options = new ArrayList<>(List.of("north", "east", "south", "west"));
        try {
            Integer index = Support.ToInt(choice) - 1;
            choice = options.get(index);
        } catch (Exception e) {}

        if        (choice == "north"){
            return new int[] {-1, 0};
        } else if (choice == "east"){
            return new int[] {0, 1};
        } else if (choice == "south"){
            return new int[] {1, 0};
        } else if (choice == "west"){
            return new int[] {0, -1};
        }

        return new int[] {0, 0};
    }
    
    public static void DoNothing(Creature creature){}

    public static void AttackFailed(){
        Support.Display("Attack Failed.");
    }
}

interface Behavior {
    public void Act(Creature creature);

    public void UseItem(Creature creature);
    public void DropItem(Creature creature);
    public void TakeItem(Creature creature);
    public void GoNorth(Creature creature);
    public void GoWest(Creature creature);
    public void GoEast(Creature creature);
    public void GoSouth(Creature creature);
}

class Player implements Behavior {
    private List<String> options_ = new ArrayList<>();
    private Map<String, Map<String, Integer>> visInventory_;
    private Map<String, Map<String, Integer>> invisInventory_;

    Player(){
        Data data_ = new Data();
        data_.ReadFile("entities.json", "entities");
        Map<String, Object> player_ = data_.Get(List.of("entities", "player"));

        options_ = Support.Get(player_, "options");
        visInventory_ = Support.Get(player_, List.of("inventory", "visible"));
        invisInventory_ = Support.Get(player_, List.of("inventory", "invisible"));
    }

    public void Act(Creature creature){
        Support.Display("Choices: ");
        int i = 1;
        for (String option : options_) {
            Support.Display(i + ". " + option);
            i += 1;
        }

        String choice = Support.Input("What do you choose? >", false).toLowerCase().strip();

        Consumer<Creature> action = Script.CheckAction(choice, creature, options_);
        
        Support.Clear();
        action.accept(creature);
    }

    public static int[] GetDirection(){
        int i = 1;
        Support.Display("Options: ");
        for (String option : List.of("north", "east", "south", "west")) {
            Support.Display(i + ". " + option);
            i += 1;
        }

        String choice = Support.Input("Which direction? >", false).toLowerCase().strip();

        int[] direction = Script.CheckDirection(choice);

        return direction;
    }

    public void UseItem(Creature creature){
        Support.Display("Choices: ");
        int i = 1;
        List<String> options = new ArrayList<>();

        for (String option : visInventory_.keySet()) {
            Support.Display(i + ". " + option);
            options.add(option);
            i += 1;
        }
        Integer visRange = i-1;
        for (String option : invisInventory_.keySet()) {
            Support.Display(i + ". " + option);
            options.add(option);
            i += 1;
        }

        String choice = Support.Input("What do you choose? >", false).toLowerCase().strip();

        Boolean valid = Script.CheckItem(choice, options);

        if (valid){
            try {
                Integer index = Support.ToInt(choice) - 1;
                choice = options.get(index);
            } catch (Exception e) {}
            
            Map<String, Map<String, Integer>> inventory;

            if (options.indexOf(choice) <= visRange){
                inventory = visInventory_;
            } else {
                inventory = invisInventory_;
            }

            Map<String, Integer> item = inventory.get(choice);
            int[] direction = GetDirection();
            
            creature.Attack(direction, item);
        }
        
        Support.Clear();
    }

    public void DropItem(Creature creature){
        
    }

    public void TakeItem(Creature creature){
        
    }

    public void GoNorth(Creature creature){
        
    }

    public void GoWest(Creature creature){
        
    }

    public void GoEast(Creature creature){
        
    }

    public void GoSouth(Creature creature){
        
    }
}

class NPC implements Behavior{
    public void Act(Creature creature){
        
    }

    public void UseItem(Creature creature){
        
    }

    public void DropItem(Creature creature){
        
    }

    public void TakeItem(Creature creature){
        
    }

    public void GoNorth(Creature creature){
        
    }

    public void GoWest(Creature creature){
        
    }

    public void GoEast(Creature creature){
        
    }

    public void GoSouth(Creature creature){
        
    }
}

class Aggressive extends NPC {
    Aggressive(){

    }
}
