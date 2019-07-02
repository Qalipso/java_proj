package mypack;
import mypack.Replace;

import java.util.*;

public class HundlerWord {
    String word;
    public Map<String, int[]> indexes;
    public List<String> entries;

    public HundlerWord(String w) {
        word = w;
        indexes = new HashMap<>();
        entries = new ArrayList<>();
    }

    public void printIndexes() {
        for (String name: indexes.keySet()){
            String key = name.toString();
            int[] value = indexes.get(name);
            System.out.print(key + ": ");
            for (int i = 0; i < value.length; i++) System.out.print(value[i]+" ");
            System.out.println("");
        }
    }

    public String launch(Replace[] replaces) {
        Arrays.sort(replaces,(o1, o2)-> compare(o1,o2));
        int i = 0;
        while(entries.size() != 0) {
            if(entries.contains(replaces[i].replacement)) {
                if (replaces[i].minDis > 0) {
                    replaces[i].minDis--;
                    cleanOInEntries(replaces[i].replacement);
                    i++;
                } else {
                    Random rnd = new Random(System.currentTimeMillis());
                    int number = rnd.nextInt(replaces[i].substitute.size());

                    replaces[i].incCoeffOfUsed();
                    Arrays.sort(replaces,(o1, o2)-> compare(o1,o2));

                    word = word.replaceFirst(replaces[i].replacement, replaces[i].substitute.get(0));

                    entries.remove(replaces[i].replacement);
                }
            } else i++;

        }

        return word;

    }

    public void cleanOInEntries(String O) {
        while (entries.contains(O)) entries.remove(O);
    }

    public static int compare(Replace o1, Replace o2) {
        if (Math.abs(o2.coeffOfUsed - o1.coeffOfUsed) < 0.00001){
            if (o1.priority == o2.priority) {
                if (o1.replacement.length() == o2.replacement.length()) return 0;
                else return o2.replacement.replaceAll("[$&]", "").length() - o1.replacement.replaceAll("[$&]", "").length();
            }
            else return o2.priority - o1.priority;
        }
        else {
            if (o1.coeffOfUsed < o2.coeffOfUsed) return -1;
            else return 1;
        }
    }
}
