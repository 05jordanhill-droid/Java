import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class NumberGuessAnalysis {
    public List<Map<String, Integer>> hardestNumData = new ArrayList<>();

    NumberGuessAnalysis(Integer range){
        EstablishData(range);
    }
    NumberGuessAnalysis(Integer range, Integer base){
        EstablishData(range, base);
    }

    private void AddDataPoint(Map<String, Integer> dataPoint){
        hardestNumData.add(dataPoint);
    }

    // Single Data point
    private static Map<String, Integer> FindHardestToGuess(Integer range){
        Map<String, Integer> hardestNumCount = new HashMap<>();
        
        // x
        hardestNumCount.put("Number", 0);
        // y
        hardestNumCount.put("Count", 0);

        for (int number = 1; number <= range; number++) {
            AutoNumberGuess guessGame = new AutoNumberGuess(1, range, number);
            guessGame.Run();

            if (guessGame.GetCount() >= hardestNumCount.get("Count")){
                hardestNumCount.put("Number", guessGame.GetRandomNumber());
                hardestNumCount.put("Count", guessGame.GetCount());
            }
        }

        return hardestNumCount;
    }

    // Graph Data Points
    private void EstablishData(Integer range, Integer base){
        Map<String, Integer> dataPoint = new HashMap<>();

        for (int i = 1; i <= range; i++) {
            dataPoint = FindHardestToGuess(Math.powExact(base, i));
            AddDataPoint(dataPoint);
        }
    }
    private void EstablishData(Integer range){
        Map<String, Integer> dataPoint = new HashMap<>();

        for (int i = 1; i <= range; i++) {
            dataPoint = FindHardestToGuess(i);
            AddDataPoint(dataPoint);
        }
    }

    // Display Graph Point
    private void GraphDataPoint(Map<String, Integer> dataPoint){
        Integer x = dataPoint.get("Number");
        Integer y = dataPoint.get("Count");

        Support.Display(
            String.format("%06d", x) + "|" + 
            String.format("%06d", y) + "|", 
            false
        );
        
            for (int i = 0; i < y-1; i++) {
            Support.Display("-", false);
        }
        Support.Display("O");
    }

    // Display Whole Graph
    public void GraphData(){
        Support.Display("Range is the maximal value in the range to guess for.");
        Support.Display("Number is the number that was the hardest to pinpoint.");
        Support.Display("Count is the number of guesses taken to get to Number.");
        Support.Display();
        Support.Display("Range |Number|Count |");

        for (int i = 0; i < hardestNumData.size(); i++) {
            Support.Display(String.format("%06d", i+1) + "|", false);
            GraphDataPoint(hardestNumData.get(i));
        }
    }
}