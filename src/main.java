import mypack.InputFile;
import mypack.Replace;
import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        InputFile data = new InputFile();
        Replace[] replaceBase =  data.getReplace(1,1,1);
        ArrayList<String> text = data.getText(1,1,1);

        //System.out.println(data.coeffa+"  "+"  "+data.coeffb);
        for (int i = 0;i<replaceBase.length;i++)
        System.out.println(replaceBase[i].replacement+" "+replaceBase[i].substitute+" "+replaceBase[i].priority+" "+replaceBase[i].minDis+" "+replaceBase[i].type);
        System.out.println(text);

    }
}
