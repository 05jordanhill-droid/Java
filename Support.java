import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Function;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Random;

public class Support {
    private static final Scanner scanner = new Scanner(System.in);

    // Clear Terminal
    public static void Clear(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Print to Terminal
    public static void Display(Object string, boolean newLine){
        if(newLine){
            System.out.println(string);
        } else {
            System.out.print(string);
        }
    }
    public static void Display(Object string){
        System.out.println(string);
    }
    public static void Display(){
        System.out.println();
    }

    // Get Input from User in Terminal
    public static String Input(Object string, boolean newLine){        
        Display(string, newLine);
        String rvalue = scanner.nextLine();

        return rvalue;
    }
    public static String Input(Object string){        
        Display(string);
        String rvalue = scanner.nextLine();

        return rvalue;
    }
    public static String Input(){        
        Display();
        String rvalue = scanner.nextLine();

        return rvalue;
    }

    // Get Random Integer
    public static Integer GetRandomInt(Integer min, Integer max){
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    // Conversions
    public static Integer ToInt(String value){
        return Integer.parseInt(value);
    }
    public static Double ToDouble(String value){
        return Double.parseDouble(value);
    }
    public static String ToString(Integer value){
        return Integer.toString(value);
    }
    public static String ToString(Double value){
        return Double.toString(value);
    }
    public static Boolean ToBoolean(String value){
        return Boolean.parseBoolean(value);
    }

    // Get Valid Integer by Input
    public static Integer GetIntInput(Object prompt){
        int rvalue = 0;
        Boolean flag = true;
        while (flag)
        {
            try
            {
                String inputStr = Input(prompt, false);
                rvalue = ToInt(inputStr);
                flag = false;
            } catch (Exception e)
            {
                Display("Value is not acceptable, please try again.", false);
            }
        }

        return rvalue;
    }
    // Default Integer Value Input
    public static Integer GetIntInput(Object prompt, Integer defaultValue){
        Integer rvalue = defaultValue;
        try
        {
            String inputStr = Input(prompt, false);
            rvalue = ToInt(inputStr);
        } catch (Exception e){}

        return rvalue;
    }

    // Retrieves a list of coordinates arranged in a square
    public static List<int[]> GetCoordsSquare(int radius, int[] center){
        int x = radius;
        int y = radius;

        int _x = center[0];
        int _y = center[1];

        List<int[]> squareCoordinates = new ArrayList<>();

        for(int i = -x; i < x+1; i++)
        {
            //Top
            squareCoordinates.add(new int[]{_x+i, _y+y});
            //Bottom
            squareCoordinates.add(new int[]{_x-i, _y-y});
        }
        for(int i = -y; i < y+1; i++)
        {
            //Right
            squareCoordinates.add(new int[]{_x+x, _y+i});
            //Left
            squareCoordinates.add(new int[]{_x-x, _y+i});
        }
        squareCoordinates = RemoveListRedundancies(squareCoordinates);
        return squareCoordinates;
    }

    // Removes duplicates from provided list
    public static <T> List<T> RemoveListRedundancies(List<T> oldList)
    {
        List<T> newList = new ArrayList<>();
        for(T item : oldList)
        {
            if (!ListContainsItem(item, newList))
            {
                newList.add(item);
            }
        }
        return newList;
    }

    // Checks items in list for equivalency to item provided
    public static <T> boolean ListContainsItem(T[] item, List<T[]> list)
    {
        for(T[] compare : list)
        {
            if (Arrays.equals(item, compare))
            {
                return true;
            }
        }
        return false;
    }
    public static <T> boolean ListContainsItem(T item, List<T> list)
    {
        for(T compare : list)
        {
            if (item.equals(compare))
            {
                return true;
            }
        }
        return false;
    }
    
    // Combined content of one list to another
    public static <T> List<T> IntegrateLists(List<T> baseList, List<T> additionalList)
    {
        for(T item : additionalList)
        {
            baseList.add(item);
        }
        return baseList;
    }
    
    // Gathers a list of coordinates that form a straight line from pointA to pointB
    public static List<int[]> GetLineCoordinates(int[] pointA, int[] pointB, int thickness)
    {
        // a^2 + b^2 = c^2     (a^2 + b^2)^(1/2) = c

        // y = mx + b
        
        List<int[]> xLineCoordinates = GetXBaseLineCoordinates(pointA, pointB, thickness);
        List<int[]> yLineCoordinates = GetYBaseLineCoordinates(pointA, pointB, thickness);

        List<int[]> lineCoordinates = IntegrateLists(xLineCoordinates, yLineCoordinates);
        
        lineCoordinates = RemoveListRedundancies(lineCoordinates);

        return lineCoordinates;
    }
    public static List<int[]> GetLineCoordinates(int[] pointA, int[] pointB)
    {
        int thickness = 0;
        return GetLineCoordinates(pointA, pointB, thickness);
    }
    public static List<int[]> GetYBaseLineCoordinates(int[] pointA, int[] pointB, int thickness)
    {
        // a^2 + b^2 = c^2     (a^2 + b^2)^(1/2) = c

        // x = my + b
        double run = pointB[1] - pointA[1];
        double rise = pointB[0] - pointA[0];

        double rate = rise/run;

        int index = 1;
        int otherIndex = 0;

        List<double[]> lineCoordinates = new ArrayList<>();

        int lower = pointA[index];
        int higher = pointB[index];

        if(Math.abs(pointA[index]) > Math.abs(pointB[index]))
        {
            higher = pointA[index];
            lower = pointB[index];
        }

        for(int i = -thickness; i < thickness+1; i++)
        {
            double yIntercept = (pointA[otherIndex] - (rate * pointA[index])) + i;

            if(higher > 0)
            {
                for(int x = lower; x < higher+1; x++)
                {
                    double y = rate * x + yIntercept;
                    double[] coordinate = null;
                    coordinate = new double[]{y, x};
                    lineCoordinates.add(coordinate);
                }
            } else
            {
                for(int x = lower; x > higher-1; x--)
                {
                    double y = rate * x + yIntercept;
                    double[] coordinate = null;
                    coordinate = new double[]{y, x};
                    lineCoordinates.add(coordinate);
                }
            }
        }
        List<int[]> approximatedLineCoordinates = ApproximateCoordinates(lineCoordinates, true);
        approximatedLineCoordinates = RemoveListRedundancies(approximatedLineCoordinates);
        return approximatedLineCoordinates;
    }
    public static List<int[]> GetYBaseLineCoordinates(int[] pointA, int[] pointB)
    {
        int thickness = 0;
        return GetYBaseLineCoordinates(pointA, pointB, thickness);
    }
    public static List<int[]> GetXBaseLineCoordinates(int[] pointA, int[] pointB, int thickness)
    {
        // a^2 + b^2 = c^2     (a^2 + b^2)^(1/2) = c

        // y = mx + b
        double rise = pointB[1] - pointA[1];
        double run = pointB[0] - pointA[0];

        double rate = rise/run;

        int index = 0;
        int otherIndex = 1;

        List<double[]> lineCoordinates = new ArrayList<>();

        int lower = pointA[index];
        int higher = pointB[index];

        if(Math.abs(pointA[index]) > Math.abs(pointB[index]))
        {
            higher = pointA[index];
            lower = pointB[index];
        }

        for(int i = -thickness; i < thickness+1; i++)
        {
            double yIntercept = (pointA[otherIndex] - (rate * pointA[index])) + i;

            if(higher > 0)
            {
                for(int x = lower; x < higher+1; x++)
                {
                    double y = rate * x + yIntercept;
                    double[] coordinate = null;
                    coordinate = new double[]{x, y};
                    lineCoordinates.add(coordinate);
                }
            } else
            {
                for(int x = lower; x > higher-1; x--)
                {
                    double y = rate * x + yIntercept;
                    double[] coordinate = null;
                    coordinate = new double[]{x, y};
                    lineCoordinates.add(coordinate);
                }
            }
        }
        List<int[]> approximatedLineCoordinates = ApproximateCoordinates(lineCoordinates, true);
        approximatedLineCoordinates = RemoveListRedundancies(approximatedLineCoordinates);
        return approximatedLineCoordinates;
    }
    public static List<int[]> GetXBaseLineCoordinates(int[] pointA, int[] pointB)
    {
        int thickness = 0;
        return GetXBaseLineCoordinates(pointA, pointB, thickness);
    }

    // Rounds nonWhole coordinates to Whole coordinates
    public static List<int[]> ApproximateCoordinates(List<double[]> coordinateList, boolean wideGirth)
    {
        List<int[]> approximatedCoordinates = new ArrayList<>();
        int[] approximatedCoordinate;
        for(double[] coordinate : coordinateList)
        {
            approximatedCoordinate = new int[]
            {
                (int)Math.ceil(coordinate[0]), 
                (int)Math.ceil(coordinate[1])
            };
            approximatedCoordinates.add(approximatedCoordinate);

            approximatedCoordinate = new int[]
            {
                (int)Math.ceil(coordinate[0]), 
                (int)Math.floor(coordinate[1])
            };
            approximatedCoordinates.add(approximatedCoordinate);

            approximatedCoordinate = new int[]
            {
                (int)Math.floor(coordinate[0]), 
                (int)Math.ceil(coordinate[1])
            };
            approximatedCoordinates.add(approximatedCoordinate);

            approximatedCoordinate = new int[]
            {
                (int)Math.floor(coordinate[0]), 
                (int)Math.floor(coordinate[1])
            };
            approximatedCoordinates.add(approximatedCoordinate);
        }
        approximatedCoordinates = RemoveListRedundancies(approximatedCoordinates);

        return approximatedCoordinates;
    }

    // Applies quality to items of list and checks attribute of said quality
    public static <T, K> boolean ListHasQuality(List<T> list, Function<T, K> GetQuality, K quality){
        for(T item : list){
            if (GetQuality.apply(item).equals(quality)){
                return true;
            }
        }
        return false;
    }
    
    // returns the duplicate items between two lists
    public static <T> List<T> GetOverlap(List<T> listOne, List<T> listTwo)
    {
        List<T> rList = new ArrayList<>();
        for(T item : listOne)
        {
            if(ListContainsItem(item, listTwo))
            {
                rList.add(item);
            }
        }
        return rList;
    }

    // Saves content to a file
    public static void SaveFile(String fileName, Object content){
        try{
            String newContent;

            if (fileName.endsWith(".json")){
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                newContent = gson.toJson(content);
            } else {
                // .txt files as default
                newContent = (String) content;
            }
            Files.writeString(Path.of(fileName), newContent);
        } 
        catch (IOException e){
            Display("ERROR: Could not save file '" + fileName + "'");
            System.exit(1);
        }
    }
    
    // Retrieves something from a complex Dictionary or List that requires more than one .get()
    @SuppressWarnings("unchecked")
    public static <T> T Get(Map<String, Object> data, List<Object> address){
        Map<String, Object> dict = data;
        List<Object> list = new ArrayList<>();

        T rvalue = null;
        
        for (Object oldKey : address){
            if (oldKey instanceof String key){
                if (oldKey == address.getLast()){
                    rvalue = (T) dict.get(key);
                } else {
                    try{
                        dict = AsMap(dict.get(key));
                    } catch (Exception e) {
                        list = AsList(dict.get(key));
                    }
                }
            } else if (oldKey instanceof Integer key){
                Integer listSize = list.size();
                key = key % listSize;
                if (key < 0){
                    key += listSize;
                }
                
                if (oldKey == address.getLast()){
                    rvalue = (T) list.get(key);
                } else {
                    try{
                        list = AsList(list.get(key));
                    } catch (Exception e) {
                        dict = AsMap(list.get(key));
                    }
                }
            } else {
                Support.Display("ERROR: " + oldKey + "invalid in address" + address);
                System.exit(1);
            }
        }
        return rvalue;
    }
    @SuppressWarnings("unchecked")
    public static <T> T Get(List<Object> data, List<Object> address){
        Map<String, Object> dict = new HashMap<>();
        List<Object> list = data;

        T rvalue = null;
        
        for (Object oldKey : address){
            if (oldKey instanceof String key){
                if (oldKey == address.getLast()){
                    rvalue = (T) dict.get(key);
                } else {
                    try{
                        dict = AsMap(dict.get(key));
                    } catch (Exception e) {
                        list = AsList(dict.get(key));
                    }
                }
            } else if (oldKey instanceof Integer key){
                Integer listSize = list.size();
                key = key % listSize;
                if (key < 0){
                    key += listSize;
                }

                if (oldKey == address.getLast()){
                    rvalue = (T) list.get(key);
                } else {
                    try{
                        list = AsList(list.get(key));
                    } catch (Exception e) {
                        dict = AsMap(list.get(key));
                    }
                }
            } else {
                Support.Display("ERROR: " + oldKey + "invalid in address" + address);
                System.exit(1);
            }
        }
        return rvalue;
    }
    public static <T> T Get(Map<String, Object> data, Object address){
        return Get(data, List.of(address));
    }
    public static <T> T Get(List<Object> data, Object address){
        return Get(data, List.of(address));
    }
    @SuppressWarnings("unchecked")
    public static Map<String, Object> AsMap(Object encrypted){
        return (Map<String, Object>) encrypted;
    }
    @SuppressWarnings("unchecked")
    public static List<Object> AsList(Object encrypted){
        return (List<Object>) encrypted;
    }

    // Retrieves letter from a word
    public static <T> T Get(String data, Integer key){
        List<Object> list = new ArrayList<>();
        for (Character letter : data.toCharArray()){
            list.add(letter);
        }
        return Get(list, List.of(key));
    }
    
    // Assigns item into a complicated list or dictionary that requires more than one .put()
    public static <T> Map<String, Object> Put(Map<String, Object> data, List<Object> address, Object key, T value, Boolean insert){
        if (key instanceof String key_){
            if (key_.equals("append")){
                Support.<List<Object>>Get(data, address).add(value);
            } else {
                Support.<Map<String, Object>>Get(data, address).put(key_, value);
            }
        }
        else if (key instanceof Integer key_){
            Integer listSize = Support.<List<Object>>Get(data, address).size();

            key_ = key_ % listSize;
            if (key_ < 0){
                key_ += listSize;
            }

            if (insert){
                Support.<List<Object>>Get(data, address).add(key_, value);
            } else {
                Support.<List<Object>>Get(data, address).set(key_, value);
            }
        }
        return data;
    }
    // default insert
    public static <T> Map<String, Object> Put(Map<String, Object> data, List<Object> address, Object key, T value){
        return Put(data, address, key, value, false);
    }
    public static <T> List<Object> Put(List<Object> data, List<Object> address, Object key, T value, Boolean insert){
        if (key instanceof String key_){
            if (key_.equals("append")){
                Support.<List<Object>>Get(data, address).add(value);
            } else {
                Support.<Map<String, Object>>Get(data, address).put(key_, value);
            }
        }
        else if (key instanceof Integer key_){
            Integer listSize;

            if (! address.isEmpty()){
                listSize = Support.<List<Object>>Get(data, address).size();
            } else {
                listSize = data.size();
            }

            key_ = key_ % listSize;
            if (key_ < 0){
                key_ += listSize;
            }

            if (insert){
                if (! address.isEmpty()){
                    Support.<List<Object>>Get(data, address).add(key_, value);
                } else {
                    data.add(key_, value);
                }
            } else {
                if (! address.isEmpty()){
                    Support.<List<Object>>Get(data, address).set(key_, value);
                } else {
                    data.set(key_, value);
                }
            }
        }
        return data;
    }
    // default insert
    public static <T> List<Object> Put(List<Object> data, List<Object> address, Object key, T value){
        return Put(data, address, key, value, false);
    }
    // Word
    public static String Put(String data, Integer key, String value, Boolean insert){
        List<Object> list = new ArrayList<>();
        for (Character letter : data.toCharArray()){
            list.add(letter);
        }
        
        list = Put(list, List.of(), key, value, insert);

        String word = "";
        for (Object letter : list){
            word += letter;
        }
        return word;
    }
    // default insert && Word
    public static String Put(String data, Integer key, String value){
        return Put(data, key, value, false);
    }
    
    // Truncates content of string from and after the first indicator in string
    public static String Truncate(String word, String indicator){
        String rvalue = "";
        for (int i = 0; i < word.length(); i++) {
            String letter = Get(word, i);

            if (! letter.equals(indicator)){
                rvalue += letter;
            } else {
                return rvalue;
            }
        }
        return rvalue;
    }
}

// Extract data from a file
class Data {
    public Map<String, Object> variables = new HashMap<>();

    Data(){}

    public void SaveData(String dataName, String fileName){
        Support.SaveFile(fileName, variables.get(dataName));
    }

    public void ReadFile(String fileName, String variableName){
        Object rvalue = null;

        try {
            if (fileName.endsWith(".json")){
                String json = Files.readString(Path.of(fileName));
                Gson gson = new Gson();
                rvalue = gson.fromJson(json, Map.class);
            } else {
                // .txt files as default
                rvalue = Files.readString(Path.of(fileName));
            }
        } catch (IOException e){
            Support.Display("ERROR: Could not read file '" + fileName + "'");
            System.exit(1);
        }
        
        variables.put(variableName, rvalue);
    }

    public <T> T Get(List<Object> address){
        return Support.Get(variables, address);
    }
    public <T> T Get(Object address){
        return Get(List.of(address));
    }

    public <T> void Put(List<Object> address, Object key, T value){
        Put(address, key, value, false);
    } 
    public <T> void Put(List<Object> address, Object key, T value, Boolean insert){
        variables = Support.Put(variables, address, key, value, insert);
    }
}

// Implement to classes meant to be capable of displaying to a Terminal as a single *thing*
interface Visual {
    public void Display();
    public int[] GetXY();
}

//////////////////////////////////////////////////////////////////////////////////////////////

// WIP
// class ConsoleDisplay {
//     private Field _field;

//     ConsoleDisplay(Field field){
//         _field = field;
//     }

//     public void Display(){
//         for (int y = 0; y < _field.GetField().length; y++){
//             for (int x = 0; x < _field.GetField()[0].length; x++){
//                 _field.GetSlot(new int[] {x, y}).Display();
//             }
//             Support.Display();
//         }
//     }
// }