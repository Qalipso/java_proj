package mypack;

import java.util.*;


public class HundlerWord {
    private String word;
    public Map<String, List<Integer>> indexes;
    public Map<Integer, List<Integer>> indexesToAdd;

    private boolean[] blocksL;
    private boolean[] blocksIm;
    private String d;
    private short modU;
    private short modJ;

    Replace[] replaces;
    private Map<Integer, Integer> groupCount;

    public HundlerWord(String w, Replace[] re, Map<Integer, Integer> groupC, int mark, short modU_, short modJ_ /* твердый знак 0 или 1 */) {
        word = w;
        groupCount = groupC;
        indexes = new HashMap<>();
        indexesToAdd = new HashMap<>();
        modU = modU_;
        modJ = modJ_;

        replaces = new Replace[re.length];
        for (int i = 0; i < re.length; i++) {
            replaces[i] = re[i];
        }

        blocksL = new boolean[word.length()];
        Arrays.fill(blocksL, false);
        blocksIm = new boolean[word.length()];
        Arrays.fill(blocksL, false);

        for (Replace replace : replaces) {
            if (!groupCount.containsKey(replace.group)) groupCount.put(replace.group, 0);
        }

        if (mark == 0) d = "i";
        else {
            int number = (int) (Math.random() * 2);
            if (number == 0) d = "/";
            else d = "\\";
        }
    }


    public String launch() {
        Arrays.sort(replaces, (o1, o2) -> compare(o1, o2));

        int i = 0;


        while (indexes.size() != 0) {
            if (i == replaces.length) break;

            if (indexes.containsKey(replaces[i].replacement) && indexes.get(replaces[i].replacement).size() > 0) {

                if (replaces[i].countBlock == 0) {
                    int index = indexes.get(replaces[i].replacement).get(0);

                    if (modJ == 1) {
                        if (index > 0 && (word.charAt(index - 1) == 'ъ' || word.charAt(index - 1) == 'Ъ')) { // буква стоящая после ъ не может начинать замену если стоит флаг modJ
                            indexes.get(replaces[i].replacement).remove(0);
                            continue;
                        }
                    }

                    if (modU == 2) {
                        if (index == 1 && (word.charAt(0) == 'Й' || word.charAt(0) == 'й')) {
                            if (word.charAt(1) == 'а' || word.charAt(1) == 'у' || word.charAt(1) == 'о' || word.charAt(1) == 'А' || word.charAt(1) == 'У' || word.charAt(1) == 'О') {
                                indexes.get(replaces[i].replacement).remove(0);
                                continue;
                            }
                        } else if (index > 1) {
                            if (isBadChar(word.toLowerCase().charAt(index - 2)) && (word.charAt(index - 1) == 'й' || word.charAt(index - 1) == 'Й') &&
                                    (word.charAt(index) == 'а' || word.charAt(index) == 'у' || word.charAt(index) == 'о' || word.charAt(index) == 'А' || word.charAt(index) == 'У' || word.charAt(index) == 'О')) {
                                indexes.get(replaces[i].replacement).remove(0);
                                continue;
                            }
                        }

                    }

                    //проверка: не заблокрованы ли буквы которые заменяем?
                    boolean lettersIsFree = true;
                    for (int j = index; j < index + replaces[i].replacement.length(); j++) {
                        if (blocksL[j]) {
                            lettersIsFree = false;
                            break;
                        }

                    }

                    if (lettersIsFree) {
                        //вероятность отказа
                        boolean isNotBlocked;
                        if (replaces[i].importance - 1.0 > 0.0001) {
                            double protectCh = replaces[i].importance - 1.0;
                            isNotBlocked = (!experiment(replaces[i].chanceBlock) || (experiment(replaces[i].chanceBlock) && experiment(protectCh)));

                        } else {
                            boolean blockLCh = false;
                            double protectCh = replaces[i].importance;

                            for (int j = index; j < index + replaces[i].replacement.length(); j++) {
                                if (blocksIm[j]) {
                                    blockLCh = true;
                                    break;
                                }
                            }

                            isNotBlocked = (!experiment(replaces[i].chanceBlock) && (!blockLCh || blockLCh && experiment(protectCh)));
                            if (!isNotBlocked) {
                                for (int j = index; j < index + replaces[i].replacement.length(); j++) {
                                    blocksIm[j] = true;
                                }
                            }
                        }

                        // если замена заблокировалась то мы удаляем из indexes индекс вхождения и продолжаем дальше цикл
                        if (!isNotBlocked) {
                            if (indexes.containsKey(replaces[i].replacement)) {
                                indexes.get(replaces[i].replacement).remove(0);
                                if (indexes.get(replaces[i].replacement).size() == 0)
                                    indexes.remove(replaces[i].replacement);
                            }
                            continue;
                        }

                        //иначе добавляем в Мар идексов для замен
                        if (indexesToAdd.containsKey(replaces[i].ID)) {
                            indexesToAdd.get(replaces[i].ID).add(index);
                        } else {
                            List<Integer> r = new ArrayList<>();
                            r.add(index);
                            indexesToAdd.put(replaces[i].ID, r);
                        }

                        //блокируем буквы с index по index + replace.len'
                        for (int j = index; j < index + replaces[i].replacement.length(); j++) {
                            blocksL[j] = true;
                        }


                        //увеличиваем счетчики
                        replaces[i].incCountGood(1);
                        replaces[i].incCoeffOfUsed();
                        replaces[i].repBlock(); //блокировка на мин. дис.
                        //счетчик групп
                        groupCount.put(replaces[i].group, groupCount.get(replaces[i].group) + 1);

                        //проверяем список подчинённых записей
                        if (replaces[i].childsRep.size() > 0) {
                            for (int j = 0; j < replaces[i].childsRep.size(); j++) {
                                replaces[i].childsRep.get(j).incCoeffOfUsed();

                                replaces[i].childsRep.get(j).incCountGood(1);
                                replaces[i].childsRep.get(j).incCountFake(1);

                                replaces[i].childsRep.get(j).blockChild();
                            }
                        }


                    } else {
                        replaces[i].incCountBad(1);
                    }

                } else {
                    List<Replace> check = new ArrayList<>();
                    for (Replace replace : replaces) {
                        if (replace.replacement.equals(replaces[i].replacement)) {
                            check.add(replace);
                        }
                    }
                    if (check.size() > 1) {
                        int j = i + 1;
                        while (j < replaces.length && !replaces[j].replacement.equals(replaces[i].replacement)) j++;
                        i = j;
                        continue;
                    }
                }

                //убираем из indexes вхождение
                indexes.get(replaces[i].replacement).remove(0);
                if (indexes.get(replaces[i].replacement).size() == 0) indexes.remove(replaces[i].replacement);

                //сортируем по приоритету и приравневаем указатель на замену к 1 замене
                Arrays.sort(replaces, (o1, o2) -> compare(o1, o2));
                i = 0;
            } else i++; // идем дальше по приоритету

        }

        //уменьшаем счетчик блокировки дле замен и переходим к следующему слову
        for (Replace replace : replaces) {
            if (replace.countBlock != 0) {
                replace.decCountBlock();
            }
        }


        ////////////////////////////////////////////////////////////////////////////////////
        //Далее само построение конечного слова с заменами на заменители по indexesToAdd
        //лист allInds - массив всех индексов вхождений замен отсортированый по возрастанию

        List<Integer> allInds = new ArrayList<>();
        for (int key : indexesToAdd.keySet()) {
            allInds.addAll(indexesToAdd.get(key));
        }
        Collections.sort(allInds, (o1, o2) -> compareDec(o1, o2));

        StringBuffer ww = new StringBuffer();
        int j = 0;
        while (j < word.length()) {

            if (!allInds.contains(j)) {
                if (j > 0 && isShip(word.toLowerCase().charAt(j - 1)) && word.charAt(j) == 'й' && allInds.contains(j + 1)) {
                    j++;
                    continue;
                }
                if (ww.length() != 0 && !myIsLetter(ww.charAt(ww.length() - 1)) && word.charAt(j) == '́')
                    ww.append('\'');
                else ww.append(word.charAt(j));
                j++;
            } else {
                for (int key : indexesToAdd.keySet()) {

                    if (indexesToAdd.get(key).contains(j)) {

                        Replace r = replaces[0];
                        for (Replace replace : replaces) { // находим по id replace
                            if (replace.ID == key) {
                                r = replace;
                            }
                        }

                        StringBuffer strForCompare = new StringBuffer();
                        while (!strForCompare.toString().toLowerCase().equals(r.replacement.toLowerCase()) && j < word.length()) {
                            if (word.charAt(j) != '$' && word.charAt(j) != '&') strForCompare.append(word.charAt(j));
                            j++;
                        }

                        Random rand = new Random(System.nanoTime());
                        int ranNum = (int) (rand.nextDouble() * r.substitute.size());

                        if (!d.equals("i")) {
                            if (r.substitute.get(ranNum).charAt(0) == '/' || r.substitute.get(ranNum).charAt(0) == '\\')
                                ww = ww.append(r.substitute.get(ranNum));
                            else ww = ww.append(d + r.substitute.get(ranNum));

                            int number = (int) (Math.random() * 2);
                            if (number == 0) d = "/";
                            else d = "\\";
                        } else {
                            if (ww.length() != 0) {
                                char x = ww.toString().charAt(ww.length() - 1);
                                if (x == '$' || x == '&') {
                                    if (ww.length() > 1) x = ww.toString().charAt(ww.length() - 2);
                                    else x = 'а';
                                }

                                if (myIsLetter(x) && x != 'i')
                                    ww = ww.append(r.substitute.get(ranNum));
                                else ww = ww.append(d + r.substitute.get(ranNum));

                            } else {
                                ww = ww.append(r.substitute.get(ranNum));
                            }
                        }
                    }
                }
            }
        }


        return ww.toString();
    }


    //статические методы
    public static int compareDec(Integer o1, Integer o2) {
        return o1 - o2;
    }

    public static int compare(Replace o1, Replace o2) {
        if (Math.abs(o1.coeffOfUsedRan - o2.coeffOfUsedRan) < 0.0001) {
            if (o1.priority == o2.priority) {
                if (o1.replacement.length() == o2.replacement.length()) return 0;
                else
                    return o2.replacement.replaceAll("[$&]", "").length() - o1.replacement.replaceAll("[$&]", "").length();
            } else return o2.priority - o1.priority;
        }
        if (o1.coeffOfUsedRan - o2.coeffOfUsedRan > 0.0001) return 1;
        else return -1;
    }

    public static boolean myIsLetter(char x) {
        return ((((int) x >= 'а') && ((int) x <= 'я')) || (((int) x >= 'А') && ((int) x <= 'Я')) || x == '\'');
    }

    public static boolean experiment(double chance) {
        Random rand = new Random();
        rand.setSeed(System.nanoTime());
        double comp = rand.nextDouble();

        return (chance - comp > 0.0001);

    }

    public static boolean isShip(char x) {
        return (x == 'ш' || x == 'щ' || x == 'ж' || x == 'ц' || x == 'ч');
    }

    public static boolean isBadChar(char x) {
        return (x == '-' || x == 'ъ' || x == 'ь' || isShip(x));
    }
}
