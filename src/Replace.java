package mypack;

import java.util.ArrayList;
import java.lang.Math;
import java.util.List;
import java.util.Map;
import java.util.Random;

import mypack.ChangeStr;

public class Replace {
    public String replacement;
    public ArrayList<String> substitute;
    public int priority;
    public int minDis;

    public double importance;
    public double chanceBlock;

    public double coeffOfUsed;
    public double coeffOfUsedRan;
    public double coeffOfRan;

    public double coeffOfRandMinDis;
    public int minDisRan;

    public int countBlock;
    public int countGood;
    public int countBad;
    public int countFake;

    public int errors = -1;
    public boolean hundlered = false;

    public int group;
    public ArrayList<Integer> childs; /* используется только для заполнения childStr*/
    public ArrayList<Replace> childsStr;

    public Replace(double priorityCoeff, double minDisCoeff, String str, int baseMinDis, int propMinDis, int modifyU, int k, double probability, double randmindis, double randUsed, int group_) {
        chanceBlock = probability;
        coeffOfRan = randUsed;
        coeffOfRandMinDis = randmindis;
        group = group_;
        childsStr = new ArrayList<>();
        substitute = new ArrayList<>();
        ChangeStr tmp = new ChangeStr();
        StringBuilder buildStr = new StringBuilder();
        buildStr.append(str);
        errors = -1;
        ArrayList<String> substr = new ArrayList<>();
        try {
            if (buildStr.codePoints().filter(ch -> ch == '\t').count() < 2)
                errors = k;
            else {

                while ((buildStr.indexOf("\t") != -1)) {
                    substr.add(buildStr.substring(0, buildStr.indexOf("\t")));
                    buildStr.delete(0, buildStr.indexOf("\t") + 1);
                }
                substr.add(buildStr.substring(0, buildStr.length()));
                while (substr.size() <= 7) {
                    substr.add("");
                }
                if (substr.get(0).equals("") || substr.get(1).equals("") || substr.get(2).equals("") || substr.get(0).indexOf(' ') != -1 || substr.get(1).indexOf(' ') != -1)
                    errors = k;
                if (modifyU == 1)
                    replacement = tmp.modU(substr.get(0));
                else
                    replacement = substr.get(0);
                substitute.add(substr.get(1));
                if (!substr.get(2).equals("")) {
                    priority = (int) Math.round(Integer.parseInt(substr.get(2)) * priorityCoeff);
                    coeffOfUsed = 1 / (2 * (double) priority);

                    Random rand = new Random();
                    rand.setSeed(System.nanoTime());
                    double ranNumber = (rand.nextDouble() * coeffOfRan * 2) - coeffOfRan;
                    coeffOfUsedRan = coeffOfUsed + (1/(double)priority) * ranNumber;
                }
                if (!substr.get(3).equals("")) {
                    minDis = Math.max((int) Math.round(Integer.parseInt(substr.get(3)) * minDisCoeff), propMinDis);
                    minDis = Math.max(minDis, baseMinDis);
                    if (minDis < 0)
                        throw new ArrayIndexOutOfBoundsException();
                } else {
                    minDis = Math.max(baseMinDis, propMinDis);
                }

                Random rand = new Random();
                rand.setSeed(System.nanoTime());
                double ranNumber = (rand.nextDouble() * coeffOfRandMinDis * 2) - coeffOfRandMinDis;
                minDisRan = (int) Math.round( minDis * (1 + ranNumber) );
                countBlock = minDisRan;

                if (!substr.get(4).equals("")) {
                    if ((Double.valueOf(substr.get(4)) <= 2) && (Double.valueOf(substr.get(4)) >= 0))
                        importance = Double.valueOf(substr.get(4));
                    else {
                        throw new ArrayIndexOutOfBoundsException();

                    }
                } else {
                    importance = 0;
                }
                if (!substr.get(5).equals("")) {
                    childs = new ArrayList<>();
                    String[] tmpmas;
                    tmpmas = substr.get(5).split(" ");
                    for (int i = 0; i < tmpmas.length; i++) {
                        childs.add(Integer.parseInt(tmpmas[i]) - 1);
                    }
                }

                if (!substr.get(6).equals("")) {
                    for (int i = 6; i < substr.size(); i++) {
                        if (!substr.get(i).equals(""))
                            substitute.add(substr.get(i));
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            errors = k;
        } catch (NumberFormatException exep) {
            errors = k;
        }
    }
    public void incCoeffOfUsed(){
        coeffOfUsed += 1/(double)priority;

        Random rand = new Random();
        rand.setSeed(System.nanoTime());
        double ranNumber = (rand.nextDouble() * coeffOfRan * 2) - coeffOfRan;

        coeffOfUsedRan = coeffOfUsed + (1/(double)priority) * ranNumber;
    }

    public void incCountGood(int x) { countGood += x; }

    public void incCountBad(int x) { countBad += x; }

    public void incCountFake(int x) { countFake += x; }

    public void decCountBlock(Map<String, List<Integer>> indexes) {
        if (minDis != 0) {
            if (indexes.containsKey(replacement)) incCountBad(indexes.get(replacement).size());
            indexes.remove(replacement);

            countBlock--;

            if (countBlock == 0) {
                Random rand = new Random();
                rand.setSeed(System.nanoTime());
                double ranNumber = (rand.nextDouble() * coeffOfRandMinDis * 2) - coeffOfRandMinDis;
                minDisRan = (int) Math.round( minDis * (1 + ranNumber) );

                countBlock = minDisRan;
            }
        }
    }

    public boolean isBlocked() {
        return (countBlock > 0);
    }


}
