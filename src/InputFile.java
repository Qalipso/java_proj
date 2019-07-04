package mypack;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import mypack.Replace;
import mypack.ChangeStr;
public class InputFile{

    public double coeffa;
    public double coeffb;
    ArrayList<String> forChange;
    ArrayList<String> text;

    public InputFile(String patternWay) throws Throwable{
        String input;
        forChange = new ArrayList<>();
        try{

            InputStream inpStream = checkForUtf8BOMAndDiscardIfAny(new FileInputStream(patternWay));
            BufferedReader br = new BufferedReader(new InputStreamReader(inpStream));

            String strLine;
            strLine = br.readLine();
            String[] tmp = strLine.split(" ");
            try {
                coeffa = Double.parseDouble(tmp[0]);
                coeffb = Double.parseDouble(tmp[1]);
            }
            catch (NumberFormatException e){
                Throwable ex = new Exception("Ошибка в вводе коэффициентов");
                throw ex;
                //System.out.println();
            }

            while ((strLine = br.readLine()) != null){
                if ((strLine.indexOf('*') == -1) ||(strLine.indexOf('\n') != 0))
                    forChange.add(strLine);
            }
        }catch (IOException e){
            Throwable ex = new Exception("Ошибка считывания файла");
            throw ex;
        }
    }

    public mypack.Replace[] getReplace(int baseMinDis,int propMinDis, int modifyU) throws Throwable{
        Replace[] replaceBase = new Replace[forChange.size()];
        for (int i = 0;i<forChange.size();i++) {
                replaceBase[i] = new Replace(coeffa, coeffb, forChange.get(i), baseMinDis, propMinDis, modifyU, i);
                if (replaceBase[i].errors != -1){
                    Throwable ex = new Exception("Ошибка ввода в строке " + (i+1));
                    throw ex;
                    //System.out.print("Ошибка ввода в строке " + i+1);
                    //return null;
                }
        }
                    return replaceBase;
    }

    public ArrayList<String> getText(String textWay,int modifyE,int modifyU,int modifyZi) throws Throwable{
        text = new ArrayList<>();
        try{
            FileInputStream fstream = new FileInputStream(textWay);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            StringBuilder buildStr = new StringBuilder();
            ChangeStr tmp = new ChangeStr();
            while ((strLine = br.readLine()) != null){
                buildStr.replace(0,buildStr.length(),"");
                buildStr.append(tmp.modE(strLine,modifyE));
                if (modifyU != 0)
                    buildStr.replace(0,buildStr.toString().length(),tmp.modU(buildStr.toString()));
                if (modifyZi != 0)
                    buildStr.replace(0,buildStr.toString().length(),tmp.modZi(buildStr.toString()));
                text.add(buildStr.toString());
            }
        }catch (IOException e){
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
        return pushbackInputStream; }
}
