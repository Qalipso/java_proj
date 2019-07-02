import mypack.InputFile;
import mypack.Replace;
import java.util.ArrayList;
import mypack.MainWindow;
import java.util.Arrays;
import java.util.Comparator;

public class main {
    public static void ahaCorasickText(String text, Replace[] replaces) {
        AhoCorasick ahoCorasick;
        String[] words = text.split(" ");

        for (int i = 0; i < words.length; i++) {
            // Ахо-Карасик
            ahoCorasick = new AhoCorasick();
            for (int k = 0; k < replaces.length; k++) {
                ahoCorasick.addToBohr(replaces[k].replacement);
            }

            HundlerWord hw = new HundlerWord(words[i]);
            ahoCorasick.findInd(words[i], hw);

            // Обработка слова
            System.out.print("-----> " + words[i] + " :");
            hw.printIndexes();

            System.out.println(hw.launch(replaces));

        }
    }

    public static void main(String[] args) {
        MainWindow w = new MainWindow();
        w.setVisible(true);
//        InputFile data = new InputFile();
//        Replace[] replaceBase =  data.getReplace(0,0,1);
//        ArrayList<String> text = data.getText(1,1,1);
//
//        //System.out.println(data.coeffa+"  "+"  "+data.coeffb);
//        for (int i = 0;i<replaceBase.length;i++)
//            System.out.println(replaceBase[i].replacement+" "+replaceBase[i].substitute+" "+replaceBase[i].priority+" "+replaceBase[i].minDis+" "+replaceBase[i].type + " " + replaceBase[i].coeffOfUsed);
//        System.out.println(text);
//
//        ahaCorasickText(text.get(0), replaceBase);
//        for (int i = 0;i<replaceBase.length;i++)
//            System.out.println(replaceBase[i].replacement+" "+replaceBase[i].substitute+" "+replaceBase[i].priority+" "+replaceBase[i].minDis+" "+replaceBase[i].type + " " + replaceBase[i].coeffOfUsed);

    }

}
