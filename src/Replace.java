package mypack;
import java.util.ArrayList;
import  java.lang.Math;
import mypack.ChangeStr;

public class Replace {
    public String replacement;
    public ArrayList<String> substitute;
    public int priority;
    public int minDis;
    public short type;
    public double coeffOfUsed;
//    int count;
    public Replace(double priorityCoeff, double minDisCoeff, String str,int baseMinDis, int propMinDis,int modifyU){
        substitute = new ArrayList<>();
        ChangeStr tmp = new ChangeStr();
        //count = 0;
        String[] substr;
        substr = str.split(" ");
        if (modifyU == 1)
            replacement = tmp.modU(substr[0]);
        else
            replacement = substr[0];
        substitute.add(substr[1]);
        if (substr.length > 2) {
            priority = (int)Math.round(Integer.parseInt(substr[2]) * priorityCoeff);
            coeffOfUsed = 1/(2*priority);
            if ((substr.length > 3)&&(substr[3] != "")){
                minDis = Math.max((int)Math.round(Integer.parseInt(substr[3]) * minDisCoeff),propMinDis);
                if (substr.length > 4){
                    type = Short.parseShort(substr[4]);
                    if (substr.length > 5){
                        for (int i = 5;i<substr.length;i++){
                            substitute.add(substr[i]);
                        }
                    }

                }
            }
            else{
                minDis = Math.max(baseMinDis,propMinDis);
            }
        }

    }

    public void incCoeffOfUsed(){
        coeffOfUsed += 1/priority;
    }
//    public void setBlock(){
//        count = minDis;
//    }
//    public boolean decCounter(){
//        count--;
//        if (count <= 0) {
//            count = 0;
//            return true;
//        }
//        else{
//            return false;
//        }
//    }
}
