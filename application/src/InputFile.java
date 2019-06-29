package mypack;
import java.io.*;
import java.util.ArrayList;
import mypack.Replace;

public class InputFile {

    public double coeffa;
    public double coeffb;
    ArrayList<String> forChange;
    ArrayList<String> text;

    public InputFile(){
        String input;
        forChange = new ArrayList<>();
        text = new ArrayList<>();
        try{

            InputStream inpStream = checkForUtf8BOMAndDiscardIfAny(new FileInputStream("C:\\Users\\User\\IdeaProjects\\application\\test_replace.txt"));
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

        try{
            FileInputStream fstream = new FileInputStream("C:\\Users\\User\\IdeaProjects\\application\\test_text.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null){
                text.add(strLine);
            }
        }catch (IOException e){
            System.out.println("Ошибка");
        }

    }
    public mypack.Replace[] getReplace(){
        Replace[] replaceBase = new Replace[forChange.size()];
        for (int i = 0;i<forChange.size();i++)
            replaceBase[i] = new Replace(coeffa,coeffb,forChange.get(i));
        return replaceBase;
    }
    public ArrayList<String> getText(){
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
