package mypack;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class InputFiles {

    public ArrayList<Double> coeffsa;
    public ArrayList<Double> coeffsb;
    String currentfile;
    String curfilename;
    ArrayList<MyPair<String,Integer>> forChange; // массив строк базы замен
    ArrayList<String> preparedText;

    public InputFiles(String patternWay) throws Throwable {
        File file = new File(patternWay);
        forChange = new ArrayList<>();
        coeffsa = new ArrayList<>();
        coeffsb = new ArrayList<>();
        int fileNum = 0;
        if (file.isDirectory()) {
            File[] filesInDir = file.listFiles();
            for (File f : filesInDir) {
                if (!f.getName().equals("options.txt")) {
                    curfilename = f.getName();
                    forChange.add(new MyPair("*" + curfilename,0));
                    currentfile = f.toPath().toString();
                    InputFileInit();
                    fileNum++;
                }
            }
        } else {
            curfilename = file.getName();
            forChange.add(new MyPair("*" + curfilename,0));
            currentfile = file.toPath().toString();
            InputFileInit();
        }
    }

    public void InputFileInit() throws Throwable {
        try {

            InputStream inpStream = checkForUtf8BOMAndDiscardIfAny(new FileInputStream(currentfile));
            BufferedReader br = new BufferedReader(new InputStreamReader(inpStream, StandardCharsets.UTF_8));

            String strLine = br.readLine();
            int sn = 1;//№ строки
            String[] tmp = strLine.split(" ");
            try {
                coeffsa.add(Double.parseDouble(tmp[0]));
                coeffsb.add(Double.parseDouble(tmp[1]));
            } catch (NumberFormatException e) {
                Throwable ex = new Exception("Ошибка в вводе коэффициентов файла " + curfilename);
                throw ex;
            }

            while ((forChange.size() == 1 && (strLine = br.readLine()) != null)) {
                sn++;
                if (znrepStr(strLine)) forChange.add(new MyPair(strLine,sn));
            }
            if (forChange.size() == 1) sn++;
            while ((strLine = br.readLine()) != null) {
                sn++;
                if (znrepStr(strLine)) forChange.add(new MyPair(strLine,sn));
                else {
                    if ((!forChange.get(forChange.size() - 1).str.equals("")))
                            forChange.add(new MyPair("",sn));
                }
            }
            sn++;
            if (!forChange.get(forChange.size() - 1).str.equals(""))
                forChange.add(new MyPair("",sn));

        } catch (IOException e) {
            Throwable ex = new Exception("Ошибка считывания файла " + curfilename);
            throw ex;
        }
    }

    private boolean znrepStr(String s){
        int sl_minus_1=s.length()-1;
        for (int i=0; i<sl_minus_1; i++){
            if (s.charAt(i) == '*' || s.charAt(i) == '\r' || s.charAt(i) == '\n') return false;
            if (s.charAt(i) != '\t') return true;
        }
        return false;
    }

    public Replace[] getReplace(int baseMinDis, int propMinDis, short modifyU, double probability, double randmindis, double randUsed) throws Throwable {
        Replace[] replaceBase1 = new Replace[1];
        double coeffa =1;
        double coeffb =0;
        ArrayList<Replace> replaceBase = new ArrayList<>();
        Integer[] masgroup = new Integer[forChange.size()];
        int tmpgroup = 1;
        int j = 0;
        int filenum = 0;
        masgroup[0] = 0;
        for (int i = 0; i < forChange.size(); i++) {
            if ((!forChange.get(i).str.equals("")) && forChange.get(i).str.charAt(0) == '*') {
                curfilename = forChange.get(i).str.substring(1);
                coeffa = coeffsa.get(filenum);
                coeffb = coeffsb.get(filenum);
                filenum++;
                j++;
            } else {
                if (forChange.get(i).str.equals("")) {
                    masgroup[tmpgroup] = i - j;
                    tmpgroup++;
                    j++;
                } else {
                    replaceBase.add(new Replace(coeffa, coeffb, forChange.get(i).str, baseMinDis, propMinDis, modifyU, forChange.get(i).strnum, probability, randmindis, randUsed, tmpgroup, curfilename));
                    if (replaceBase.get(replaceBase.size() - 1).errors != -1) {
                        Throwable ex = new Exception("Ошибка ввода в строке " + replaceBase.get(replaceBase.size() - 1).errors + " файла " + curfilename);
                        throw ex;
                    }
                }
            }
        }
        for (int i = 0; i < replaceBase.size(); i++) {
            if (replaceBase.get(i).childsInt != null) {
                // добавление всeх в ckild из группы

                if ((replaceBase.get(i).childsInt.contains(0))) {
                    int g = masgroup[replaceBase.get(i).group] - masgroup[replaceBase.get(i).group - 1];
                    for (int z = 0; z < g; z++) {
                        if (z + masgroup[replaceBase.get(i).group - 1] != i)
                            replaceBase.get(i).childsRep.add(replaceBase.get(z + masgroup[replaceBase.get(i).group - 1]));
                    }
                } else
                    for (int k = 0; k < replaceBase.get(i).childsInt.size(); k++) {
                        try {
                            if ((replaceBase.get(i).childsInt.get(k) != null) && (replaceBase.get(i).childsInt.get(k) - 1 < replaceBase.size())) {
                                replaceBase.get(i).childsRep.add(replaceBase.get(replaceBase.get(i).childsInt.get(k) - 1 + masgroup[replaceBase.get(i).group - 1]));
                            }
                        } catch (Exception exe) {
                            Throwable ee = new Exception("Ошибка ввода в записи " + replaceBase.get(i).replacement + replaceBase.get(i).substitute.get(0) );
                            throw ee;
                        }

                    }
            }
        }
        replaceBase1 = replaceBase.toArray(replaceBase1);
        return replaceBase1;
    }

    public ArrayList<String> getPreparedText(String textWay, short modifyE, short modifyU, short modifyZi) throws Throwable {
        preparedText = new ArrayList<>();

        try {
            InputStream inpStream = checkForUtf8BOMAndDiscardIfAny(new FileInputStream(textWay));
            BufferedReader br = new BufferedReader(new InputStreamReader(inpStream, StandardCharsets.UTF_8));
            String strLine;
            StringBuilder buildStr = new StringBuilder();
            while ((strLine = br.readLine()) != null) {
                buildStr.replace(0, buildStr.length(), "");
                buildStr.append(ChangeStr.modSpecial(strLine + " "));
                buildStr.replace(0, buildStr.toString().length(), ChangeStr.modE(buildStr.toString(), modifyE));
                if (modifyU != 0)
                    buildStr.replace(0, buildStr.toString().length(), ChangeStr.modU(buildStr.toString(), modifyU));
                if (modifyZi != 0)
                    buildStr.replace(0, buildStr.toString().length(), ChangeStr.modZi(buildStr.toString()));

                preparedText.add(buildStr.toString());
            }
        } catch (IOException e) {
            Throwable ex = new Exception("Ошибка в считывании файла текста.");
            throw ex;
        }
        return preparedText;
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

    public static HashMap<String, Float> loadOptions(String optionsWay) throws Throwable {
        int si = 0;
        HashMap<String, Float> hm = new HashMap<>();
        try {
            InputStream istream = checkForUtf8BOMAndDiscardIfAny(new FileInputStream(optionsWay));
            BufferedReader br = new BufferedReader(new InputStreamReader(istream, StandardCharsets.UTF_8));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                si++;
                String[] strElements = strLine.split("\t");
                hm.put(strElements[0], Float.parseFloat(strElements[1]));
            }
        } catch (Throwable e) {
            e = new Exception("Ошибка в считывании опций, строка " + si);
            throw e;
        }
        return hm;
    }

}
