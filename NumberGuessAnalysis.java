import java.util.HashMap;
import java.util.Map;

public class NumberGuessAnalysis {
    private Map<String, Map<String, Integer>> hardestNumData = new HashMap<>();

    NumberGuessAnalysis(Integer range){
        EstablishData(range);
    }

    private void AddDataPoint(String name, Map<String, Integer> dataPoint){
        hardestNumData.put(name, dataPoint);
    }

    // Single Data point
    public static Map<String, Integer> FindHardestToGuess(Integer range){
        Map<String, Integer> hardestNumCount = new HashMap<>();
        
        // x
        hardestNumCount.put("Number", 0);
        // y
        hardestNumCount.put("Count", 0);

        for (int number = 1; number <= range; number++) {
            AutoNumberGuess guessGame = new AutoNumberGuess(1, range, number);

            if (guessGame.GetCount() > hardestNumCount.get("Count")){
                hardestNumCount.put("Number", guessGame.GetRandomNumber());
                hardestNumCount.put("Count", guessGame.GetCount());
            }
        }

        return hardestNumCount;
    }

    // Graph Data Points
    private void EstablishData(Integer range){
        Map<String, Integer> dataPoint = new HashMap<>();

        for (int i = 1; i <= range; i++) {
            dataPoint = FindHardestToGuess(i);
            AddDataPoint(i + "", dataPoint);
        }
    }

    public void GraphDataPoint(Map<String, Integer> dataPoint){
        Integer y = dataPoint.get("Count");

        for (int i = 0; i < y-1; i++) {
            Support.Display("-", false);
        }
        Support.Display("O");
    }
    public void GraphData(){
        for (String x : hardestNumData.keySet()) {
            GraphDataPoint(hardestNumData.get(x));
        }
    }
}