package mypack;
import java.io.*;
import java.util.ArrayList;
import mypack.Replace;
import mypack.ChangeStr;
public class InputFile {

    public double coeffa;
    public double coeffb;
    ArrayList<String> forChange;
    ArrayList<String> text;

    public InputFile(){
        String input;
        forChange = new ArrayList<>();

        try{

            InputStream inpStream = checkForUtf8BOMAndDiscardIfAny(new FileInputStream("C:\\Users\\Mikhail\\Documents\\GitHub\\java_proj\\test_replace.txt"));
            BufferedReader br = new BufferedReader(new InputStreamReader(inpStream));

            String strLine;
            strLine = br.readLine();
            String[] tmp = strLine.split(" ");
            try {
                coeffa = Double.valueOf(tmp[0]);
                coeffb = Double.valueOf(tmp[1]);
            }
            catch (java.lang.ArrayIndexOutOfBoundsException e1){
                System.out.println("Ошибка 1 коэфф");
            }

            while ((strLine = br.readLine()) != null){
                forChange.add(strLine);
            }
        }catch (IOException e){
            System.out.println("Ошибка");
        }
    }

    public mypack.Replace[] getReplace(int baseMinDis,int propMinDis, int modifyU){
        Replace[] replaceBase = new Replace[forChange.size()];
        for (int i = 0;i<forChange.size();i++)
            replaceBase[i] = new Replace(coeffa,coeffb,forChange.get(i),baseMinDis,propMinDis,modifyU);
        return replaceBase;
    }

    public ArrayList<String> getText(int modifyE,int modifyU,int modifyZi){
        text = new ArrayList<>();
        try{
            FileInputStream fstream = new FileInputStream("C:\\Users\\Mikhail\\Documents\\GitHub\\java_proj\\test_text.txt");
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
            System.out.println("Ошибка");
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
