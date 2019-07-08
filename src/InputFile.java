package mypack;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import mypack.Replace;
import mypack.ChangeStr;

public class InputFile {

    public double coeffa;
    public double coeffb;
    ArrayList<String> forChange;
    ArrayList<String> text;

    public InputFile(String patternWay) throws Throwable {
        String input;
        forChange = new ArrayList<>();
        try {

            InputStream inpStream = checkForUtf8BOMAndDiscardIfAny(new FileInputStream(patternWay));
            BufferedReader br = new BufferedReader(new InputStreamReader(inpStream));

            String strLine;
            strLine = br.readLine();
            String[] tmp = strLine.split(" ");
            try {
                coeffa = Double.parseDouble(tmp[0]);
                coeffb = Double.parseDouble(tmp[1]);
            } catch (NumberFormatException e) {
                Throwable ex = new Exception("Ошибка в вводе коэффициентов");
                throw ex;
            }
            while ((strLine = br.readLine()) != null) {
                if ((strLine.indexOf('*') == -1) && (strLine.indexOf('\n') != 0) && (!strLine.equals("")))
                    forChange.add(strLine);
                else {
                    if (!forChange.get(forChange.size() - 1).equals(""))
                        forChange.add("");
                }
            }

        } catch (IOException e) {
            Throwable ex = new Exception("Ошибка считывания файла");
            throw ex;
        }
    }

    public mypack.Replace[] getReplace(int baseMinDis, int propMinDis, int modifyU, double probability, double randmindis, double randUsed) throws Throwable {
        Replace[] replaceBase1 = new Replace[1];
        ArrayList<Replace> replaceBase = new ArrayList<>();
        Integer[] masgroup = new Integer[forChange.size()];
        int tmpgroup = 0;
        int j = 0;
        masgroup[0] = 0;
        for (int i = 0; i < forChange.size(); i++) {
            if (forChange.get(i).equals("")) {
                tmpgroup++;
                masgroup[tmpgroup] = i - j;
                j++;
            } else {
                replaceBase.add(new Replace(coeffa, coeffb, forChange.get(i), baseMinDis, propMinDis, modifyU, i - j, probability, randmindis, randUsed, tmpgroup));
                if (replaceBase.get(replaceBase.size() - 1).errors != -1) {
                    Throwable ex = new Exception("Ошибка ввода в строке " + (i - j + 1));
                    throw ex;
                }
            }
        }
        for (int i = 0; i < replaceBase.size(); i++) {
            if (replaceBase.get(i).childs != null)
                for (int k = 0; k < replaceBase.get(i).childs.size(); k++) {
                    try {
                        if ((replaceBase.get(i).childs.get(k) < replaceBase.size()) && (replaceBase.get(i).childs.get(k) != null)) {
                            replaceBase.get(i).childsStr.add(replaceBase.get(replaceBase.get(i).childs.get(k) + masgroup[replaceBase.get(i).group]));
                        }
                    } catch (Exception exe) {
                        Throwable ee = new Exception("Ошибка ввода в строке " + (i + 1));
                        throw ee;
                    }

                }
            System.out.println(replaceBase.get(i).replacement + " childs:" + replaceBase.get(i).childsStr);
        }
        replaceBase1 = replaceBase.toArray(replaceBase1);
        return replaceBase1;
    }

    public ArrayList<String> getText(String textWay, int modifyE, int modifyU, int modifyZi) throws Throwable {
        text = new ArrayList<>();
        try {
            FileInputStream fstream = new FileInputStream(textWay);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            StringBuilder buildStr = new StringBuilder();
            ChangeStr tmp = new ChangeStr();
            while ((strLine = br.readLine()) != null) {
                buildStr.replace(0, buildStr.length(), "");
                buildStr.append(tmp.modE(strLine, modifyE));
                if (modifyU != 0)
                    buildStr.replace(0, buildStr.toString().length(), tmp.modU(buildStr.toString()));
                if (modifyZi != 0)
                    buildStr.replace(0, buildStr.toString().length(), tmp.modZi(buildStr.toString()));
                text.add(buildStr.toString());
            }
        } catch (IOException e) {
            Throwable ex = new Exception("Ошибка в считывании файла");
            throw ex;
        }
        return text;
    }

    private static InputStream checkForUtf8BOMAndDiscardIfAny(InputStream inputStream) throws IOException {
        PushbackInputStream pushbackInputStream = new PushbackInputStream(new BufferedInputStream(inputStream), 3);
        byte[] bom = new byte[3];
        if (pushbackInputStream.read(bom) != -1) {
            if (!(bom[0] == (byte) 0xEF && bom[1] == (byte) 0xBB && bom[2] == (byte) 0xBF)) {
                pushbackInputStream.unread(bom);
            }
        }
        return pushbackInputStream;
    }
}
