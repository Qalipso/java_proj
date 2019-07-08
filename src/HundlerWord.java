package mypack;
import mypack.Replace;

import java.util.*;


public class HundlerWord {
    final private String word;
    public Map<String, List<Integer>> indexes;
    public Map<String, List<Integer>> indexesToAdd;

    private boolean[] blocksL;
    private boolean[] blocksIm;
    private String d;
    Replace[] replaces;

    public HundlerWord(String w, Replace[] re, int mark) {
        word = w;

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
                replaces[i].hundlered = true;
                if (replaces[i].countBlock == replaces[i].minDis) {
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

                        //проверяем список подчинённых записей
                        if (replaces[i].childsStr.size() > 0) {
                            for (int j = 0; j < replaces[i].childsStr.size(); j++) {
                                for (Replace replace: replaces) {
                                    if ( replaces[i].childsStr.get(j).equals(replace.replacement) ) {
                                        replace.incCoeffOfUsed();

                                        replace.incCountGood(1);
                                        replace.incCountFake(1);

                                        replace.decCountBlock(indexes);
                                    }
                                }
                            }
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


                        // убираем из indexes первое вхождение
                        if (indexes.containsKey(replaces[i].replacement)) {
                            indexes.get(replaces[i].replacement).remove(0);
                            if (indexes.get(replaces[i].replacement).size() == 0) indexes.remove(replaces[i].replacement);
                        }

                        //увеличиваем счетчики
                        replaces[i].incCountGood(1);
                        replaces[i].incCoeffOfUsed();

                        // блокировка замен по мин дис
                        replaces[i].decCountBlock(indexes);

                    } else {
                        //если буквы заблокированы

                        // убираем из indexes первое вхождение
                        if (indexes.containsKey(replaces[i].replacement)) {
                            indexes.get(replaces[i].replacement).remove(0);
                            if (indexes.get(replaces[i].replacement).size() == 0) indexes.remove(replaces[i].replacement);
                        }

                        //увеличиваем счетчики
                        replaces[i].incCountBad(1);
                    }

                } else {
                    replaces[i].decCountBlock(indexes);
                }

                //сортируем по приоритету и приравневаем указатель на замену к 1 замене
                Arrays.sort(replaces,(o1, o2)-> compare(o1,o2));

                // System.out.println(replaces);
                i = 0;
            } else i++; // идем дальше по приоритету

        }



        //уменьшаем счетчик блокировки дле тех замен которые не обработались, также сбрасываем все обработки и переходи к следующему слову
        for (Replace replace : replaces) {
            if (!replace.hundlered && replace.countBlock != replace.minDis) {
                replace.decCountBlock(indexes);
            }

            if (replace.hundlered) {
                replace.hundlered = false;
            }
        }


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
                if (ww.length() != 0 && !myIsLetter(ww.charAt(ww.length()-1)) && word.charAt(j) == '́') ww.append('\'');
                else ww.append(word.charAt(j));
                j++;
            } else {
                for (String key : indexesToAdd.keySet()) {

                    if (indexesToAdd.get(key).contains(j)) {
                        j += key.length();

                        for (Replace replace : replaces) {
                            if (key.equals(replace.replacement)) {
                                Random rand = new Random(System.nanoTime());
                                int ranNum = (int) (rand.nextDouble() * replace.substitute.size() );

                                if (ww.length() != 0) {

                                    char x = ww.toString().charAt(ww.length()-1);
                                    if (x == '$' || x == '&') {
                                        if (ww.length() > 1) x = ww.toString().charAt(ww.length()-2);
                                        else break;
                                    }

                                    if (replace.substitute.get(ranNum).charAt(0) == '/' || replace.substitute.get(ranNum).charAt(0) == '\\'
                                            || myIsLetter(x)) ww = ww.append(replace.substitute.get(ranNum));
                                    else ww = ww.append(d + replace.substitute.get(ranNum));

                                } else {
                                    ww = ww.append(replace.substitute.get(ranNum));
                                }
                                break;
                            }
                        }
                    }
                }
            }

        }
        String q = ww.toString();
        for (int k = 0; k < q.length()-2; k++) {
            if (q.charAt(k) == '$' || q.charAt(k) =='&') continue;
            if (q.charAt(k) == 'ш' || q.charAt(k) == 'щ' || q.charAt(k) == 'ж' || q.charAt(k) == 'ц') {
                if (q.charAt(k+1) == 'й' && q.charAt(k+2) != '$' &&!(((int) q.charAt(k+2) >= 'а') && ((int) q.charAt(k+2) <= 'я')) && !((int) q.charAt(k+2) >= 'А' && (int) q.charAt(k+2) <= 'Я')) {
                    q = q.substring(0, k+1) + q.substring(k+2);
                }
            }
        }

        return q/*.replaceAll("[&$]", "")*/;
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
}
