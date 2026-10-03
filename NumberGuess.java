public abstract class NumberGuess {
    private Boolean run_;
    private Integer randomNumber_;
    private Integer count_;
    private Integer bestGuess_;
    private Integer reduction_;
    private String hint_;

    NumberGuess(Integer min, Integer max){
        SetRandomNumber(Support.GetRandomInt(min, max));
        SetReduction(Math.round((max-min) / 2));
        SetBestGuess(min + GetReduction());

        if (GetBestGuess() > max){
            SetBestGuess(max);
        } else if (GetBestGuess() < min){
            SetBestGuess(min);
        }

        UpdateReduction();
        SetHint("No Hints Right Now");
        SetRun(true);
    }
    NumberGuess(Integer max){
        SetRandomNumber(Support.GetRandomInt(0, max));
        SetReduction(Math.round((max) / 2));
        SetBestGuess(GetReduction());

        if (GetBestGuess() > max){
            SetBestGuess(max);
        } else if (GetBestGuess() < 1){
            SetBestGuess(1);
        }

        UpdateReduction();
        SetHint("No Hints Right Now");
        SetRun(true);
    }
    NumberGuess(Integer min, Integer max, Integer randomNum){
        SetRandomNumber(randomNum);
        SetReduction(Math.round((max-min) / 2));
        SetBestGuess(min + GetReduction());

        if (GetBestGuess() > max){
            SetBestGuess(max);
        } else if (GetBestGuess() < min){
            SetBestGuess(min);
        }

        UpdateReduction();
        SetHint("No Hints Right Now");
        SetRun(true);
    }

    // Getters / Setters
    public Integer GetRandomNumber() {
        return randomNumber_;
    }
    public Integer GetCount() {
        return count_;
    }
    protected Integer GetBestGuess() {
        return bestGuess_;
    }
    private Integer GetReduction() {
        return reduction_;
    }
    protected Boolean GetRun() {
        return run_;
    }
    protected String GetHint() {
        return hint_;
    }
    private void SetHint(String hint) {
        hint_ = hint;
    }
    private void SetRun(Boolean run) {
        run_ = run;
    }
    private void SetReduction(Integer reduction) {
        if(reduction == 0){
            reduction = 1;
        }
        reduction_ = reduction;
    }
    private void SetBestGuess(Integer bestGuess) {
        bestGuess_ = bestGuess;
    }
    private void SetRandomNumber(Integer randomNumber) {
        randomNumber_ = randomNumber;
    }
    protected void SetCount(Integer count) {
        count_ = count;
    }

    // Update Methods
    protected void IncreaseCount(){
        SetCount(GetCount() + 1);
    }

    private void UpdateReduction(){
        SetReduction(Math.round(GetReduction() / 2));
    }

    private void UpdateBestGoal(Integer direction){
        SetBestGuess(GetBestGuess() + (direction * GetReduction()));
        
        UpdateReduction();
    }

    // Check Methods
    private Integer CheckDirection(Integer guess){
        if (guess > GetRandomNumber()){
            SetHint("Too High");
            return -1;
        } else if (guess < GetRandomNumber()) {
            SetHint("Too Low");
            return 1;
        }
        return 0;
    }
    private Object CheckGuess(Integer guess){
        Integer check = CheckDirection(guess);
        if (check == 0){
            return true;
        } else {
            return check;
        }
    }

    // Guess Method
    protected void Guess(Integer guess){
        Object check = CheckGuess(guess);
        if (check instanceof Integer direction){
            UpdateBestGoal(direction);
        } else {
            SetRun(false);
        }
        IncreaseCount();
    }

    public abstract void Run();
}

class PlayableNumberGuess extends NumberGuess{
    private Boolean hints_;

    PlayableNumberGuess(Integer min, Integer max, Boolean hints){
        super(min, max);
        SetHints(hints);
    }

    private Boolean Hints(){
        return hints_;
    }
    public void SetHints(Boolean hints){
        hints_ = hints;
    }

    private void Guess(){
        Integer guess = Support.GetIntInput("What is your guess? ");
        super.Guess(guess);
    }

    private void Hint(){
        Support.Display(GetHint());
    }

    public void Run(){
        SetCount(0);
        while (GetRun()) {
            Guess();
            if (Hints() && GetRun()){
                Hint();
            }
        }
        if (GetCount() > 1){
            Support.Display("You took " + GetCount() + " tries.");
        } else {
            Support.Display("You took " + GetCount() + " try.");
        }
    }
}

class AutoNumberGuess extends NumberGuess{
    AutoNumberGuess(Integer max){
        super(max);
    }
    AutoNumberGuess(Integer min, Integer max){
        super(min, max);
    }
    AutoNumberGuess(Integer min, Integer max, Integer randomNum){
        super(min, max, randomNum);
    }

    private void Guess(){
        super.Guess(GetBestGuess());
    }

    public void Run(){
        SetCount(0);
        while (GetRun()) {
            Guess();
        }
    }

    public void Run(Integer times){
        for (int i = 0; i < times; i++) {
            Run();
        }
    }    
}

