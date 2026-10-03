

public class NumberGuess {
    private Boolean run_;
    private Integer randomNumber_;
    private Integer count_;
    private Integer bestGuess_;
    private Integer bestLowGuess_;
    private Integer bestHighGuess_;
    private Integer reductionHigh_;
    private Integer reductionLow_;
    private Integer reduction_;
    private String hint_;

    NumberGuess(Integer min, Integer max){
        SetRandomNumber(Support.GetRandomInt(min, max));
        SetLowReduction(Math.floorDiv((max-min), 2));
        SetHighReduction(Math.ceilDiv((max-min), 2));
        SetReduction(Math.round((max-min) / 2));
        SetBestGuess(min + GetReduction());
        SetBestLowGuess(min + GetLowReduction());
        SetBestHighGuess(min + GetHighReduction());
        SetHint("No Hints Right Now");
    }
    NumberGuess(Integer max){
        SetRandomNumber(Support.GetRandomInt(0, max));
        SetLowReduction(Math.floorDiv((max), 2));
        SetHighReduction(Math.ceilDiv((max), 2));
        SetReduction(Math.round((max) / 2));
        SetBestGuess(GetReduction());
        SetBestLowGuess(GetLowReduction());
        SetBestHighGuess(GetHighReduction());
        SetHint("No Hints Right Now");
    }
    NumberGuess(Integer min, Integer max, Integer randomNum){
        SetRandomNumber(randomNum);
        SetLowReduction(Math.floorDiv((max-min), 2));
        SetHighReduction(Math.ceilDiv((max-min), 2));
        SetReduction(Math.round((max-min) / 2));
        SetBestGuess(min + GetReduction());
        SetBestLowGuess(min + GetLowReduction());
        SetBestHighGuess(min + GetHighReduction());
        SetHint("No Hints Right Now");
    }
    public Integer GetRandomNumber() {
        return randomNumber_;
    }
    public Integer GetCount() {
        return count_;
    }
    protected Integer GetBestGuess() {
        return bestGuess_;
    }
    protected Integer GetReduction() {
        return reduction_;
    }
    protected Integer GetLowReduction() {
        return reductionLow_;
    }
    protected Integer GetHighReduction() {
        return reductionHigh_;
    }
    protected Integer GetBestHighGuess() {
        return bestHighGuess_;
    }
    protected Integer GetBestLowGuess() {
        return bestLowGuess_;
    }
    protected Boolean GetRun() {
        return run_;
    }
    protected String GetHint() {
        return hint_;
    }
    protected void SetHint(String hint) {
        hint_ = hint;
    }
    protected void SetRun(Boolean run) {
        run_ = run;
    }
    protected void SetBestLowGuess(Integer bestLowGuess) {
        bestLowGuess_ = bestLowGuess;
    }
    protected void SetBestHighGuess(Integer bestHighGuess) {
        bestHighGuess_ = bestHighGuess;
    }
    protected void SetHighReduction(Integer reduction) {
        if(reduction == 0){
            reduction = 1;
        }
        reductionHigh_ = reduction;
    }
    protected void SetLowReduction(Integer reduction) {
        if(reduction == 0){
            reduction = 1;
        }
        reductionLow_ = reduction;
    }
    protected void SetReduction(Integer reduction) {
        if(reduction == 0){
            reduction = 1;
        }
        reduction_ = reduction;
    }
    protected void SetBestGuess(Integer bestGuess) {
        bestGuess_ = bestGuess;
    }
    protected void SetRandomNumber(Integer randomNumber) {
        randomNumber_ = randomNumber;
    }
    protected void SetCount(Integer count) {
        count_ = count;
    }

    protected void IncreaseCount(){
        SetCount(GetCount() + 1);
    }

    protected void UpdateReduction(){
        SetLowReduction(Math.floorDiv(GetLowReduction(), 2));
        SetHighReduction(Math.ceilDiv(GetHighReduction(), 2));
        SetReduction(Math.round(GetReduction() / 2));
    }

    protected void UpdateBestGoal(Integer direction){
        SetBestGuess(GetBestGuess() + (direction * GetReduction()));
        SetBestLowGuess(GetBestLowGuess() + (direction * GetLowReduction()));
        SetBestHighGuess(GetBestHighGuess() + (direction * GetHighReduction()));
        
        UpdateReduction();
    }

    protected Integer CheckDirection(Integer guess){
        if (guess > GetRandomNumber()){
            SetHint("Too High");
            return -1;
        } else if (guess < GetRandomNumber()) {
            SetHint("Too Low");
            return 1;
        }
        return 0;
    }
    protected Object CheckGuess(Integer guess){
        Integer check = CheckDirection(guess);
        if (check == 0){
            return true;
        } else {
            return check;
        }
    }

    protected void Guess(Integer guess){
        Object check = CheckGuess(guess);
        if (check instanceof Integer direction){
            UpdateBestGoal(direction);
        } else {
            SetRun(false);
        }
        IncreaseCount();
    }
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

    public void Guess(){
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
            Support.Display("You took " + GetCount() + "tries.");
        } else {
            Support.Display("You took " + GetCount() + "try.");
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

    public void Guess(){
        super.Guess(GetBestGuess());
    }

    public void Run(){
        SetCount(0);
        while (GetRun()) {
            Guess();
        }
        if (GetCount() > 1){
            Support.Display("It took " + GetCount() + "tries.");
        } else {
            Support.Display("It took " + GetCount() + "try.");
        }
    }

    public void Run(Integer times){
        for (int i = 0; i < times; i++) {
            Run();
        }
    }

    
}

