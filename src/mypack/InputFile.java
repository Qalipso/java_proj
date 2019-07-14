package mypack;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class InputFile {

    public double coeffa;
    public double coeffb;
    String currentfile;
    ArrayList<String> forChange; // массив строк базы замен
    ArrayList<String> preparedText;

    public InputFile(String patternWay) throws Throwable {
        File file = new File(patternWay);
        forChange = new ArrayList<>();
        if (file.isDirectory()) {
            //ArrayList<Replace> tmparr = new ArrayList<>();
            File[] filesInDir = file.listFiles();
            for (File f : filesInDir) {
                if (!f.getName().equals("options.txt")) {
                    forChange.add("*"+ f.getName());
                    InputFileInit(f);
                }
            }
        }
        else {
            forChange.add("*"+ file.getName());
            InputFileInit(file);
        }
    }

    public void InputFileInit(File file) throws Throwable{
        try {

            InputStream inpStream = checkForUtf8BOMAndDiscardIfAny(new FileInputStream(file));
            BufferedReader br = new BufferedReader(new InputStreamReader(inpStream, StandardCharsets.UTF_8));

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
                    if ((forChange.size() != 1))
                        if ((!forChange.get(forChange.size() - 1).equals("")))
                            forChange.add("");
                }
            }
            if (!forChange.get(forChange.size() - 1).equals(""))
                forChange.add("");

        } catch (IOException e) {
            Throwable ex = new Exception("Ошибка считывания файла " + file.getName());
            throw ex;
        }
    }
    public Replace[] getReplace(int baseMinDis, int propMinDis, short modifyU, double probability, double randmindis, double randUsed) throws Throwable {
        Replace[] replaceBase1 = new Replace[1];
        ArrayList<Replace> replaceBase = new ArrayList<>();
        Integer[] masgroup = new Integer[forChange.size()];
        int tmpgroup = 1;
        int j = 0;
        masgroup[0] = 0;
        //currentfile =
        for (int i = 0; i < forChange.size(); i++) {
            if ((!forChange.get(i).equals(""))&&forChange.get(i).charAt(0) == '*') {
                currentfile = forChange.get(i).substring(1);
                j++;
            }
            else{
             if (forChange.get(i).equals("")) {
                masgroup[tmpgroup] = i - j;
                tmpgroup++;
                j++;
            } else {
                replaceBase.add(new Replace(coeffa, coeffb, forChange.get(i), baseMinDis, propMinDis, modifyU, i - j, probability, randmindis, randUsed, tmpgroup, currentfile));
                if (replaceBase.get(replaceBase.size() - 1).errors != -1) {
                    Throwable ex = new Exception("Ошибка ввода в строке " + (i - j + 1));
                    throw ex;
                }
            }
        }
        }
        for (int i = 0; i < replaceBase.size(); i++) {
            if (replaceBase.get(i).childsInt != null) {
                // добавление всeх в ckild из группы
//                if (replaceBase.get(i).childsInt.contains(0)) {
//
//                }
                //
                if ((replaceBase.get(i).childsInt.contains(0))) {
                    int g = masgroup[replaceBase.get(i).group - masgroup[replaceBase.get(i).group - 1]];
                    for (int z = 0; z < g; z++) {
                        if (z + masgroup[replaceBase.get(i).group - 1] != i)
                            replaceBase.get(i).childsRep.add(replaceBase.get(z + masgroup[replaceBase.get(i).group - 1]));
                    }
                }
                else
                for (int k = 0; k < replaceBase.get(i).childsInt.size(); k++) {
                    try {
                        if ((replaceBase.get(i).childsInt.get(k) != null) && (replaceBase.get(i).childsInt.get(k)-1 < replaceBase.size())) {
                            replaceBase.get(i).childsRep.add(replaceBase.get(replaceBase.get(i).childsInt.get(k) -1 + masgroup[replaceBase.get(i).group-1]));
                        }
                    } catch (Exception exe) {
                        Throwable ee = new Exception("Ошибка ввода в строке " + (i + 1));
                        throw ee;
                    }

                }
                System.out.println(replaceBase.get(i).replacement + " childsRep:" + replaceBase.get(i).childsRep);
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
            ChangeStr tmp = new ChangeStr();
            while ((strLine = br.readLine()) != null) {
                buildStr.replace(0, buildStr.length(), "");
                buildStr.append(tmp.modSpecial(strLine+" "));
                buildStr.replace(0, buildStr.toString().length(), tmp.modE(buildStr.toString(),modifyE));
                if (modifyU != 0)
                    buildStr.replace(0, buildStr.toString().length(), tmp.modU(buildStr.toString(),modifyU));
                if (modifyZi != 0)
                    buildStr.replace(0, buildStr.toString().length(), tmp.modZi(buildStr.toString()));

                preparedText.add(buildStr.toString());
            }
        } catch (IOException e) {
            Throwable ex = new Exception("Ошибка в считывании файла");
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
        int si=0;
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
            e = new Exception("Ошибка в считывании опций, строка "+si);
            throw e;
        }
        return hm;
    }

}
