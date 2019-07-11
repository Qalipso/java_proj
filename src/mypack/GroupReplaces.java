package mypack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupReplaces {
    public Replace[] replacesInGr;
    public int numberOfGr;
    public int countRep;
    public String fileG;

    public GroupReplaces(Replace[] re, Map<Integer, Integer> countGroup, int num) {
        numberOfGr = num;
        fileG = "";
        countRep = countGroup.get(numberOfGr);
        int j = 0;
        for (Replace replace : re) {
            if (replace.group == numberOfGr) {
                if (fileG.equals("")) fileG = replace.file;
                j++;
            }
        }
        replacesInGr = new Replace[j];
        int i = 0;
        for (Replace replace : re) {
            if (replace.group == numberOfGr) {replacesInGr[i] = replace; i++;}
        }
    }

    public String print() {
        StringBuilder tmpStr = new StringBuilder();
        tmpStr.append("Группа №"+numberOfGr+ " файл: "+fileG+"\n");
        for (Replace replace : replacesInGr) {
            tmpStr.append(replace.replacement.replaceAll("[$&]", "") + " " + replace.substitute + " успешных замен:" + replace.countGood + " несостоявшихся замен:" + replace.countBad + " фиктивные замены: " + replace.countFake + "\n");
        }
        return tmpStr.toString();
    }

    public String printC() {
        StringBuilder tmpStr = new StringBuilder();
        tmpStr.append("Группа №"+numberOfGr+ " файл: "+fileG+"\n");
        for (Replace replace : replacesInGr) {
            tmpStr.append(replace.replacement.replaceAll("[$&]", "") + " " + replace.substitute + " коэфф. исп." + replace.coeffOfUsed + "\n");
        }
        return tmpStr.toString();
    }

    public String printG() {
        StringBuilder tmpStr = new StringBuilder();
        tmpStr.append("Группа №"+numberOfGr+ " файл: "+fileG+" кол-во замен: "+countRep+" " +"\n");
        for (Replace replace : replacesInGr) {
            tmpStr.append(replace.replacement.replaceAll("[$&]", "") + " " + replace.substitute +  " успешных замен:" + replace.countGood + " фиктивные замены: " + replace.countFake + "\n");
        }
        return tmpStr.toString();
    }
}
