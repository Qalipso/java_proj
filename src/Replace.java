package mypack;
import java.text.ParseException;
import java.util.ArrayList;
import  java.lang.Math;
import mypack.ChangeStr;

public class Replace {
    public String replacement;
    public ArrayList<String> substitute;
    public int priority;
    public int minDis;
    public double importance;
    public double coeffOfUsed;
    public int errors = -1;
    public ArrayList<Integer> childs;
//    int count;
    public Replace(double priorityCoeff, double minDisCoeff, String str,int baseMinDis, int propMinDis,int modifyU, int k){
        substitute = new ArrayList<>();
        ChangeStr tmp = new ChangeStr();
        StringBuilder buildStr = new StringBuilder();
        buildStr.append(str);
        errors = -1;
        //count = 0;
        ArrayList<String> substr = new ArrayList<>();
        try {
            while ((buildStr.indexOf("\t") != -1)){
                substr.add(buildStr.substring(0,buildStr.indexOf("\t")));
                buildStr.delete(0,buildStr.indexOf("\t")+1);
            }
            substr.add(buildStr.substring(0,buildStr.length()));
            while (substr.size() <= 7){
                substr.add("");
            }
           // System.out.println(substr);
          //  substr = str.split("\t");
            if (modifyU == 1)
                replacement = tmp.modU(substr.get(0));
            else
                replacement = substr.get(0);
            substitute.add(substr.get(1));
            if (!substr.get(2).equals("")) {
                priority = (int) Math.round(Integer.parseInt(substr.get(2)) * priorityCoeff);
                coeffOfUsed = Math.round(1 / (2 * (double) priority)) ;
            }
            if (!substr.get(3).equals("")) {
                minDis = Math.max((int) Math.round(Integer.parseInt(substr.get(3)) * minDisCoeff), propMinDis);
                minDis = Math.max(minDis, baseMinDis);
                if (minDis < 0)
                    throw new ArrayIndexOutOfBoundsException();
            }
            else{
                minDis = Math.max(baseMinDis,propMinDis);
            }
                if (!substr.get(4).equals("")) {
                    if ((Double.valueOf(substr.get(4)) <=2) &&(Double.valueOf(substr.get(4))>=0))
                        importance = Double.valueOf(substr.get(4));
                    else
                        throw new ArrayIndexOutOfBoundsException();
                }
                else {
                    importance = 0;
                }
//            System.out.println("!"+substr.get(5)+"!");
                if (!substr.get(5).equals("")) {
                    String[] tmpmas;
                    if (substr.get(5).length() != 1) {
                        tmpmas = substr.get(5).split(" ");
                        for (int i = 0; i < tmpmas.length; i++) {
                            //childs.add(Integer.parseInt(tmpmas[i]));
                        }
                    }
                    else {
                        childs = null;
                        //childs.add(Integer.parseInt(substr.get(5)));
                    }
                }

                if (!substr.get(6).equals("")) {
                    for (int i = 6; i < substr.size(); i++) {
                        if (!substr.get(i).equals(""))
                        substitute.add(substr.get(i));
                    }
                }
        }
        catch (ArrayIndexOutOfBoundsException ex){
            errors = k;
            //System.out.println("Ошибка в строке " +k);
        }
        catch (NumberFormatException exep){
            errors = k;
        }
    }


    public void incCoeffOfUsed(){
        coeffOfUsed += 1/(double)priority;
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
