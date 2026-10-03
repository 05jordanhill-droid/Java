package RPG;
// import java.util.HashMap;
// import java.util.Map;

public class Frame {
    private String _file;
    private Field _field;
    // private Map<String, Integer> _script = new HashMap<>();
    
    public Frame(String fileName){
        SetFile(fileName);
        
    }

    public void SetFile(String fileName){
        this._file = fileName;
    }
    public String GetFile(){
        return this._file;
    }

    public void ProcessMap(){
    }
}