package mypack;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class InputConsole {

    public  ArrayList<String> forChange;
    public  ArrayList<String> onChange;
    public  ArrayList<String> text;

    public InputConsole(){
        String input;
        forChange = new ArrayList<>();
        onChange = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        do {
            input = in.next();
            if (input.equals("!"))
                break;
            forChange.add(input);
            input = in.next();
            onChange.add(input);
        } while(!input.equals("!"));
        in.close();
        //System.out.println(res);

}
}
