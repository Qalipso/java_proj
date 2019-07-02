import mypack.Replace;

import java.util.ArrayList;
import java.util.List;

public class HundlerWord {
    String word;
    public List<MyPair<Integer, String>> indexes;

    public HundlerWord(String w) {
        word = w;
        indexes = new ArrayList<>();
    }

    public void printIndexes() {
        for (int j = 0; j < indexes.size(); j ++) {
            System.out.println(indexes.get(j).getFirst() + " " + indexes.get(j).getSecond());
        }
    }

    public String launch(Replace[] replaces) {


        //
        return "";
    }


}
