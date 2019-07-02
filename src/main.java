import mypack.InputFile;
import mypack.Replace;
import java.util.ArrayList;

public class main {
    public static void ahaCorasickText(String text, Replace[] replaces) {
        AhoCorasick ahoCorasick;
        String[] words = text.split(" ");

        for (int i = 0; i < words.length; i++) {
            // Ахо-Карасик
            ahoCorasick = new AhoCorasick();
            for (int k = 0; k < replaces.length; k++) {
//                if (replaces[k].minDis > 0)replaces[k].minDis--;
                /*else*/
                ahoCorasick.addToBohr(replaces[k].replacement);
            }

            HundlerWord hw = new HundlerWord(words[i]);
            ahoCorasick.findInd(words[i], hw);

            // Обработка слова
            System.out.println("-----> " + words[i] + " :");
            hw.printIndexes();

            hw.launch(replaces);


        }

    }

    public static void main(String[] args) {
        InputFile data = new InputFile();
        Replace[] replaceBase =  data.getReplace(0,0,1);
        ArrayList<String> text = data.getText(1,1,1);

        //System.out.println(data.coeffa+"  "+"  "+data.coeffb);
        for (int i = 0;i<replaceBase.length;i++)
        System.out.println(replaceBase[i].replacement+" "+replaceBase[i].substitute+" "+replaceBase[i].priority+" "+replaceBase[i].minDis+" "+replaceBase[i].type);
        System.out.println(text);

        ahaCorasickText(text.get(0), replaceBase);
    }
}
