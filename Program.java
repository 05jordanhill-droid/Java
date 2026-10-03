public class Program {
    public static void main(String[] args) {
        Support.Clear();

        Boolean flag = true;
        
        while (flag) {
            flag = false;
            
            // Main Menu Options
            Support.Display("1. Normal Play");
            Support.Display("2. Robot Play");
            Support.Display("3. Analyze Robot Patterns");
            String choice = Support.Input("Which mode would you like to try out?\n>", false).toLowerCase().strip();
            Support.Clear();

            // Normal Mode
            if (choice.equals("normal") || choice.equals("normal play") || choice.equals("1")){
                Integer min = Support.GetIntInput("Enter Minimal Value (Default: 1) \n>", 1);
                Integer max = Support.GetIntInput("Enter Maximal Value (Default: 100) \n>", 100);
                
                Support.Clear();

                PlayableNumberGuess game = new PlayableNumberGuess(min, max, true);
                game.Run();
            } 
            // Robot Mode 
            else if (choice.equals("robot") || choice.equals("robot play") || choice.equals("2")){
                Integer min = Support.GetIntInput("Enter Minimal Value (Default: 1) >", 1);
                Integer max = Support.GetIntInput("Enter Maximal Value (Default: 100) >", 100);

                Support.Clear();

                AutoNumberGuess game = new AutoNumberGuess(min, max);
                game.Run();

                Support.Display("Random Number: " + game.GetRandomNumber());
                Support.Display("Number of guesses: " + game.GetCount());
            } 
            // Analyze Mode
            else if (choice.equals("analyze") || choice.equals("analyze robot patterns") || choice.equals("3")){
                Integer max = Support.GetIntInput("Enter Maximal Value (Default: 10) >", 10);
                
                Support.Clear();

                Boolean xFlag = true;
                while (xFlag) {
                    xFlag = false;

                    Support.Display("1. Normal Increase");
                    Support.Display("2. Determine Base Number Increase (May be difficult to run if Max value is higher than 10)");

                    choice = Support.Input("Which mode would you like to try out?\n>", false).toLowerCase().strip();

                    Support.Clear();

                    if (choice.equals("normal") || choice.equals("normal increase") || choice.equals("1")){
                        NumberGuessAnalysis game = new NumberGuessAnalysis(max);
                        game.GraphData();
                    } else if (choice.equals("determine") || choice.equals("determine base number increase") || choice.equals("2")){
                        Integer base = Support.GetIntInput("Enter Base Value (Default: 2) >", 2);
                        
                        Support.Clear();

                        NumberGuessAnalysis game = new NumberGuessAnalysis(max, base);
                        game.GraphData();
                    } else {
                        xFlag = true;
                        Support.Display("Invalid. Try Again.");
                    }
                }
            } else {
                flag = true;
                Support.Display("Invalid. Try Again.");
            }

            Support.Display();
            Support.Display("1. Yes");
            Support.Display("2. No");

            choice = Support.Input("Go Again?\n>", false).toLowerCase().strip();

            if (choice.equals("yes") || choice.equals("1")){
                flag = true;
                Support.Clear();
            }
        }
    }
}
