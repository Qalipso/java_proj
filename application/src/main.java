import mypack.InputFile;
import mypack.Replace;
import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        InputFile data = new InputFile();
        //data.get_patterns();
       // System.out.println(data.forChange);
       // System.out.println(data.onChange);
        Replace[] replaceBase =  data.getReplace();
        System.out.println(data.coeffa+"  "+"  "+data.coeffb);
        for (int i = 0;i<replaceBase.length;i++)
        System.out.println(replaceBase[i].replacement+" "+replaceBase[i].substitute+" "+replaceBase[i].priority+" "+replaceBase[i].minDis+" "+replaceBase[i].type);
//
//        AhoCorasick ahoCorasick = new AhoCorasick();
//        for (int i = 0;i<data.forChange.size();i++)
//            ahoCorasick.addToBohr(data.forChange.get(i));
//
//        String s = "qwertyzxcvqwerty";
//
//        ahoCorasick.findInd(s);
//
//        for (int i = 0; i < ahoCorasick.answer.size(); i ++) {
//            System.out.println(ahoCorasick.answer.get(i).key() + " " + ahoCorasick.patterns.get(ahoCorasick.answer.get(i).value()));
//        }
    }
}
