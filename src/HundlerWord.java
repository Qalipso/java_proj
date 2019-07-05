package mypack;
import mypack.Replace;

import java.util.*;


public class HundlerWord {
    String word;
    final String originalWord;
    public Map<String, List<Integer>> indexes;
    private String d;

    public HundlerWord(String w, int mark) {
        word = w;
        originalWord = w;
        indexes = new HashMap<>();
        if (mark == 0) d = "i";
        else {
            int number = (int) ( Math.random() * 2 );
            if (number == 0) d = "/";
            else d = "\\";
        }
    }

    public void fixIndexes(int ind, int delta) {
        for (String name: indexes.keySet()){
            List<Integer> value = indexes.get(name);

            if (value.size() == 0) {continue; }
            List<Integer> tmp = new ArrayList<>();
            for (int i = 0; i < value.size(); i++) {
                //System.out.println(value.get(i) - (originalWord.substring(0, value.get(i)).length() - word.substring(0, value.get(i)).length()));
                if (ind < value.get(i)) tmp.add(value.get(i) - delta);
                else tmp.add(value.get(i));
            }

            indexes.put(name, tmp);
        }
    }
    public String myReplaceFirst(String s, Replace replace) {
        String target = replace.replacement;
        //int number = (int) ( Math.random() * replace.substitute.size() );
        String r = replace.substitute.get(0).toString();

        if (!indexes.containsKey(target)) return s;
        int i = indexes.get(target).get(0);

        char cS;
        if (i > 0) cS = s.charAt(i - 1);
        else cS = '!';
        if (cS == '$') target = "$" + target;
        if ((cS == '&' || cS =='$')) {
            if (i > 1) {
                cS = s.charAt(i-2);
                i--;
            }
        }

        char cE;
        if (i + target.length() < s.length()) cE = s.charAt(i + target.length());
        else cE = '!';
        if ((cE == '&' || cE =='$')) {
            if (i + target.length() < s.length() - 1) cE = s.charAt(i+target.length()+1);
        }

        if (target.contains("́")) {r = r + "'"; cE = '!';}

        if ( cS != '!' && cE != '!' && (!(((int) cS >= 'а') && ((int) cS <= 'я')) && !((int) cS >= 'А' && (int) cS <= 'Я')) &&
                !(((int) cE >= 'а') && ((int) cE <= 'я')) && !((int) cE >= 'А' && (int) cE <= 'Я') && cS != '\'') {
            r = d + r + d;
        }
        else if ( cS != '!' && (!(((int) cS >= 'а') && ((int) cS <= 'я')) && !((int) cS >= 'А' && (int) cS <= 'Я')) && cS != '\'') {
            r = d + r;
        }
        else if (cE != '!' && (i <s.length() - 1) && !(((int) cE >= 'а') && ((int) cE <= 'я')) && !((int) cE >= 'А' && (int) cE <= 'Я')) {
            r = r + d;
        }


        fixIndexes(i, target.length() - r.length());
        replace.incCountGood(1);


        s = s.substring(0, i) + r + s.substring(i + target.length());
        //s = s.replaceFirst(target, r);

        return s;
    }
    public String launch(Replace[] replaces) {
        Arrays.sort(replaces,(o1, o2)-> compare(o1,o2));
        int i = 0;

        while(indexes.size() != 0) {

            if (i == replaces.length) break;
            if(indexes.containsKey(replaces[i].replacement)) {

                if (replaces[i].countBlock == replaces[i].minDis) {

                    /*replaces[i].group = new ArrayList<>();
                    List<String> bl = new ArrayList<>();

                    for (String str : indexes.keySet()) {

                        for (int j = 0; j < str.length(); j++) {
                            for (int k = j; k < str.length()+1; k++) {

                                if ((indexes.containsKey(replaces[i].replacement) && indexes.get(replaces[i].replacement).size() > 0) && replaces[i].replacement.contains(str.substring(j, k))) {
                                    if (j == 0 && k == str.length() && !replaces[i].replacement.equals(str)) replaces[i].group.add(str);
                                    else if (!bl.contains(str) && !str.equals(replaces[i].replacement)) {
                                        bl.add(str);
                                    }
                                }
//                                if(!word.contains(replaces[i].replacement) && !bl.contains(replaces[i].replacement)) {
//
//                                    replaces[i].incCountBad(1);
//                                    bl.add(replaces[i].replacement);
//                                }

                            }
                        }

                    }
                    //System.out.println("-" +replaces[i].group);


                    for(int q = 0; q < bl.size(); q++) {
                        if (indexes.containsKey(bl.get(q)) && indexes.get(bl.get(q)).size() > 1) {

                            for (int e = 0; e < indexes.get(bl.get(q)).size(); e++) {
                                if (indexes.containsKey(bl.get(q)) && indexes.containsKey(replaces[i].replacement) &&
                                        indexes.get(replaces[i].replacement).size() > 0) {

                                    int i1 = indexes.get(bl.get(q)).get(e);
                                    int l1 = i1 + bl.get(q).length() - 1;
                                    int i2 = indexes.get(replaces[i].replacement).get(0);
                                    int l2 = i2 + replaces[i].replacement.length() - 1;

                                    if (((i1 >= i2) && (i1 <= l2)) || ((l1 >= i2) && (l1 <= l2))) {

                                        if (indexes.get(bl.get(q)).size() > 1) indexes.get(bl.get(q)).remove(indexes.get(bl.get(q)).get(e));
                                        else indexes.remove(bl.get(q));
                                    }
                                }
                            }
                        } else indexes.remove(bl.get(q));
                    }

                    for(int q = 0; q < replaces[i].group.size(); q++) {
                        if (indexes.containsKey(replaces[i].group.get(q)) && indexes.get(replaces[i].group.get(q)).size() > 1) {

                            for (int e = 0; e < indexes.get(replaces[i].group.get(q)).size(); e++) {
                                if ( indexes.containsKey(replaces[i].group.get(q)) && indexes.containsKey(replaces[i].replacement) &&
                                        indexes.get(replaces[i].replacement).size() > 0) {

                                    int i1 = indexes.get(replaces[i].group.get(q)).get(e);
                                    int l1 = i1 + replaces[i].group.get(q).length() - 1;
                                    int i2 = indexes.get(replaces[i].replacement).get(0);
                                    int l2 = i2 + replaces[i].replacement.length() - 1;

                                    if ( ((i1 >= i2) && (i1 <= l2)) || ((l1 >= i2) && (l1 <= l2)) ) {

                                        indexes.get(replaces[i].group.get(q)).remove(indexes.get(replaces[i].group.get(q)).get(e));

                                    }
                                }
                            }
                        }
                        else indexes.remove(replaces[i].group.get(q));

                        for (Replace replace : replaces) {
                            if (replace.replacement.equals(replaces[i].group.get(q))){
                                replace.incCoeffOfUsed();
                                replace.incCountGood(1);
                                replace.incCountFake(1);

                                if (replace.minDis != 0) {
                                    if (indexes.containsKey(replace.replacement)) {
                                        replace.incCountBad(indexes.get(replace.replacement).size());
                                        indexes.remove(replace.replacement);
                                    }

                                    replace.countBlock--;
                                    if (replace.countBlock == 0) replace.countBlock = replace.minDis;
                                }
                            }
                        }
                    }*/

                    word = myReplaceFirst(word, replaces[i]);
                    replaces[i].incCoeffOfUsed();

                    if (indexes.containsKey(replaces[i].replacement)) {
                        indexes.get(replaces[i].replacement).remove(0);
                        if (indexes.get(replaces[i].replacement).size() == 0) indexes.remove(replaces[i].replacement);
                    }

                }

                if (replaces[i].minDis != 0) {
                    if (indexes.containsKey(replaces[i].replacement)) {
                        replaces[i].incCountBad(indexes.get(replaces[i].replacement).size());
                        indexes.remove(replaces[i].replacement);
                    }

                    replaces[i].countBlock--;
                    if (replaces[i].countBlock == 0) replaces[i].countBlock = replaces[i].minDis;
                }

                Arrays.sort(replaces,(o1, o2)-> compare(o1,o2));
                i = 0;
            } else i++;

        }

        for (int j = 0; j < word.length()-2; j++) {
            if (word.charAt(j) == '$' || word.charAt(j) =='&') continue;
            if (word.charAt(j) == 'ш' || word.charAt(j) == 'щ' || word.charAt(j) == 'ж' || word.charAt(j) == 'ц') {
                if (word.charAt(j+1) == 'й' && word.charAt(j+2) != '$' &&!(((int) word.charAt(j+2) >= 'а') && ((int) word.charAt(j+2) <= 'я')) && !((int) word.charAt(j+2) >= 'А' && (int) word.charAt(j+2) <= 'Я')) {
                    word = word.substring(0, j+1) + word.substring(j+2);
                }
            }
        }


        return word;
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
