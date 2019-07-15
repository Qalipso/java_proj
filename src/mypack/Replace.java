package mypack;

import java.util.ArrayList;
import java.lang.Math;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    public int countBlock;
    public int countGood;
    public int countBad;
    public int countFake;

    public int errors = -1;

    public static int saveID = 0;
    public int ID;

    public int group;
    public String file;
    public ArrayList<Integer> childsInt; /* используется только для заполнения childStr*/
    public ArrayList<Replace> childsRep;

    public Replace(double priorityCoeff, double minDisCoeff, String str, int baseMinDis, int propMinDis, short modifyU, int k, double probability, double randmindis, double randUsed, int group_, String nameFile) {
        chanceBlock = probability;
        coeffOfRan = randUsed;
        file = nameFile;
        coeffOfRandMinDis = randmindis;
        group = group_;
        saveID++;
        ID = saveID;
        childsRep = new ArrayList<>();
        substitute = new ArrayList<>();
        //ChangeStr tmp = new ChangeStr();
        StringBuilder buildStr = new StringBuilder();
        buildStr.append(str);
        errors = -1;
        ArrayList<String> substr = new ArrayList<>();
        try {
            if (buildStr.codePoints().filter(ch -> ch == '\t').count() < 1)
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
                while (substr.get(0).equals("")) {
                    substr.remove(0);
                }
                if (substr.get(0).equals("") || substr.get(1).equals("") || substr.get(0).indexOf(' ') != -1 || substr.get(1).indexOf(' ') != -1)
                    errors = k;
                if (modifyU != 0)
                    replacement = ChangeStr.modU(substr.get(0), modifyU);
                else
                    replacement = substr.get(0);
                substitute.add(substr.get(1));
                if (!substr.get(2).equals("")) priority = (int) Math.round(Integer.parseInt(substr.get(2)) * priorityCoeff);
                else priority = (int) Math.round(1000 * priorityCoeff);
                coeffOfUsed = 1 / (2 * (double) priority);

                Random rand = new Random();
                rand.setSeed(System.nanoTime());
                double ranNumber = (rand.nextDouble() * coeffOfRan * 2) - coeffOfRan;
                coeffOfUsedRan = coeffOfUsed + (1 / (double) priority) * ranNumber;

                if (!substr.get(3).equals("")) {
                    minDis = Math.max((int) Math.round(Integer.parseInt(substr.get(3)) * minDisCoeff), propMinDis);
                    minDis = Math.max(minDis, (int) Math.round(baseMinDis * minDisCoeff));
                    if (minDis < 0)
                        throw new ArrayIndexOutOfBoundsException();
                } else {
                    minDis = Math.max(baseMinDis, propMinDis);
                }

                countBlock = 0;

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
                    childsInt = new ArrayList<>();
                    String[] tmpmas;
                    tmpmas = substr.get(5).split(" ");
                    for (int i = 0; i < tmpmas.length; i++) {
                        childsInt.add(Integer.parseInt(tmpmas[i]));
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

    public void incCoeffOfUsed() {
        coeffOfUsed += 1 / (double) priority;

        Random rand = new Random();
        rand.setSeed(System.nanoTime());
        double ranNumber = (rand.nextDouble() * coeffOfRan * 2) - coeffOfRan;

        coeffOfUsedRan = coeffOfUsed + (1 / (double) priority) * ranNumber;
    }

    public void incCountGood(int x) {
        countGood += x;
    }

    public void incCountBad(int x) {
        countBad += x;
    }

    public void incCountFake(int x) {
        countFake += x;
    }

    public void decCountBlock() {
        countBlock--;
    }

    public void repBlock() {
        //удаление вне зависимости успех или неудача
        Random rand = new Random();
        rand.setSeed(System.nanoTime());
        double ranNumber = (rand.nextDouble() * coeffOfRandMinDis * 2) - coeffOfRandMinDis;

        countBlock = (int) Math.abs(Math.round(minDis * (1 + ranNumber)));
    }

    public void blockChild() {
        Random rand = new Random();
        rand.setSeed(System.nanoTime());
        double ranNumber = (rand.nextDouble() * coeffOfRandMinDis * 2) - coeffOfRandMinDis;

        countBlock = Math.max(countBlock, (int) Math.round(minDis * (1 + ranNumber)));
    }

}
