package mypack;

import java.util.*;


public class HundlerWord {
    private String word;
    public Map<String, List<Integer>> indexes;
    public Map<String, List<Integer>> indexesToAdd;

    private boolean[] blocksL;
    private boolean[] blocksIm;
    private String d;
    Replace[] replaces;
    private Map<Integer, Integer> groupCount;

    public HundlerWord(String w, Replace[] re, Map<Integer, Integer> groupC, int mark) {
        word = w;
        groupCount = groupC;
        indexes = new HashMap<>();
        indexesToAdd = new HashMap<>();

        replaces = new Replace[re.length];
        for (int i = 0; i < re.length; i++) {
            replaces[i] = re[i];
        }

        blocksL = new boolean[word.length()];
        Arrays.fill(blocksL, false);
        blocksIm = new boolean[word.length()];
        Arrays.fill(blocksL, false);

        if (mark == 0) d = "i";
        else {
            int number = (int) ( Math.random() * 2 );
            if (number == 0) d = "/";
            else d = "\\";
        }
    }

    public void printBlockL() {
        for(int g = 0; g < blocksL.length; g++) {
            if (blocksL[g])System.out.print(1);
            else System.out.print(0);
        }
        System.out.println();
    }

    public String launch() {
        Arrays.sort(replaces,(o1, o2)-> compare(o1,o2));

        int i = 0;

        while(indexes.size() != 0) {
            if (i == replaces.length) break;

            if(indexes.containsKey(replaces[i].replacement) && indexes.get(replaces[i].replacement).size() > 0) {

                if (replaces[i].countBlock == 0) {
                    int index = indexes.get(replaces[i].replacement).get(0);

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
                        if (replaces[i].importance - 1.0 > 0.0001 ) {
                            double protectCh = replaces[i].importance - 1.0;
                            isNotBlocked = (!experiment(replaces[i].chanceBlock) || (experiment(replaces[i].chanceBlock) && experiment(protectCh)));

                        } else {
                            boolean blockLCh = false;
                            double protectCh = replaces[i].importance;

                            for (int j = index; j < index + replaces[i].replacement.length(); j++ ) {
                                if (blocksIm[j]) {
                                    blockLCh = true;
                                    break;
                                }
                            }

                            isNotBlocked = (!experiment(replaces[i].chanceBlock) && (!blockLCh || blockLCh && experiment(protectCh)) );
                            if (!isNotBlocked) {
                                for (int j = index; j < index + replaces[i].replacement.length(); j++ ) {
                                    blocksIm[j] = true;
                                }
                            }
                        }

                        // если замена заблокировалась то мы удаляем из indexes индекс вхождения и продолжаем дальше цикл
                        if (!isNotBlocked) {
                            if (indexes.containsKey(replaces[i].replacement)) {
                                indexes.get(replaces[i].replacement).remove(0);
                                if (indexes.get(replaces[i].replacement).size() == 0) indexes.remove(replaces[i].replacement);
                            }
                            continue;
                        }

                        //добавляем в Мар идексов для замен
                        if (indexesToAdd.containsKey(replaces[i].replacement)) {
                            indexesToAdd.get(replaces[i].replacement).add(index);
                        } else {
                            List<Integer> r = new ArrayList<>();
                            r.add(index);
                            indexesToAdd.put(replaces[i].replacement, r);
                        }

                        //блокируем буквы с index по index + replace.len'
                        for (int j = index; j < index + replaces[i].replacement.length(); j++) {
                            blocksL[j] = true;
                        }


                        //увеличиваем счетчики
                        replaces[i].incCountGood(1);
                        replaces[i].incCoeffOfUsed();
                        replaces[i].repBlock(indexes); //блокировка на мин. дис.
                        //счетчик групп
                        if (groupCount.containsKey(replaces[i].group)) groupCount.put(replaces[i].group, groupCount.get(replaces[i].group)+1);
                        else groupCount.put(replaces[i].group, 1);

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

                }
                //убираем из indexes вхождение
                indexes.get(replaces[i].replacement).remove(0);
                if (indexes.get(replaces[i].replacement).size() == 0) indexes.remove(replaces[i].replacement);

                //сортируем по приоритету и приравневаем указатель на замену к 1 замене
                Arrays.sort(replaces,(o1, o2)-> compare(o1,o2));
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
        //лист allInds - массив всех индексов вхождений замен отсортированый по неубыванию

        List<Integer> allInds = new ArrayList<>();
        for (String key : indexesToAdd.keySet()) {
            allInds.addAll(indexesToAdd.get(key));
        }
        Collections.sort(allInds, (o1, o2) -> compareDec(o1, o2));

        StringBuffer ww = new StringBuffer();
        int j = 0;
        while(j < word.length()) {
            if (!allInds.contains(j)) {
                if (j > 0 && isShip(word.charAt(j-1)) && word.charAt(j) == 'й' && allInds.contains(j+1)) {
                    j++;
                    continue;
                }
                if (ww.length() != 0 && !myIsLetter(ww.charAt(ww.length()-1)) && word.charAt(j) == '́') ww.append('\'');
                else ww.append(word.charAt(j));
                j++;
            } else {
                for (String key : indexesToAdd.keySet()) {

                    if (indexesToAdd.get(key).contains(j)) {
                        int u = 0;
                        for  (int k = j; k < j + key.length(); k++) {
                            if (word.charAt(k) == '&') u++;
                        }
                        j += key.length() + u;

                        for (Replace replace : replaces) {
                            if (key.equals(replace.replacement)) {
                                Random rand = new Random(System.nanoTime());
                                int ranNum = (int) (rand.nextDouble() * replace.substitute.size() );

                                if (!d.equals("i")) {
                                    if (replace.substitute.get(ranNum).charAt(0) == '/' || replace.substitute.get(ranNum).charAt(0) == '\\')
                                        ww = ww.append(replace.substitute.get(ranNum));
                                    else ww = ww.append(d + replace.substitute.get(ranNum));

                                    int number = (int) ( Math.random() * 2 );
                                    if (number == 0) d = "/";
                                    else d = "\\";
                                } else {
                                    if (ww.length() != 0) {
                                        char x = ww.toString().charAt(ww.length()-1);
                                        if (x == '$' || x == '&') {
                                            if (ww.length() > 1) x = ww.toString().charAt(ww.length()-2);
                                            else break;
                                        }

                                        if (myIsLetter(x) && x != 'i')
                                            ww = ww.append(replace.substitute.get(ranNum));
                                        else ww = ww.append(d + replace.substitute.get(ranNum));

                                    } else {
                                        ww = ww.append(replace.substitute.get(ranNum));
                                    }
                                }
                                System.out.println(ww);
                                break;
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
        if (Math.abs(o1.coeffOfUsedRan - o2.coeffOfUsedRan) < 0.0001 ) {
            if (o1.priority == o2.priority) {
                if (o1.replacement.length() == o2.replacement.length()) return 0;
                else return o2.replacement.replaceAll("[$&]", "").length() - o1.replacement.replaceAll("[$&]", "").length();
            }
            else return o2.priority - o1.priority;
        }
        if (o1.coeffOfUsedRan - o2.coeffOfUsedRan > 0.0001) return 1;
        else return -1;
    }
    public static boolean myIsLetter(char x) {
        return ( (((int) x >= 'а') && ((int) x <= 'я')) || ( ((int) x >= 'А') && ((int) x <= 'Я')) || x =='\'' );
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
}
