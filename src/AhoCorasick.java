import java.util.ArrayList;
import java.util.List;

public class AhoCorasick {

    public List<MyPair> answer;
    public List<String> patterns ;
    private List<Node> bohr;

    public AhoCorasick() {
        answer = new ArrayList<>();
        patterns = new ArrayList<>();
        bohr = new ArrayList<>();
        bohr.add(new Node(0,' '));
    }

    public void addToBohr(String tmp_pattern) {
        int n = 0;
        for(char symb : tmp_pattern.toCharArray()) {
            if(!bohr.get(n).next_vexes.containsKey(symb)) {
                bohr.add(new Node(n, symb));
                bohr.get(n).next_vexes.put(symb, bohr.size() - 1);
            }
            n = bohr.get(n).next_vexes.get(symb);
        }
        bohr.get(n).flag = true;
        patterns.add(tmp_pattern);
        bohr.get(n).pattern_index = patterns.size() - 1;
    }

    private int getSuffixLink(int vex) {
        if(bohr.get(vex).sfx_link == -1) {
            if(vex == 0 || bohr.get(vex).parent == 0) {
                bohr.get(vex).sfx_link = 0;
            } else {
                bohr.get(vex).sfx_link = getMove(getSuffixLink(bohr.get(vex).parent), bohr.get(vex).symb);
            }
        }
        return bohr.get(vex).sfx_link;
    }

    private int getMove(int vex, char symb) {
        if(!bohr.get(vex).move.containsKey(symb)) {
            if(bohr.get(vex).next_vexes.containsKey(symb)) {
                bohr.get(vex).move.put(symb, bohr.get(vex).next_vexes.get(symb));
            } else if(vex == 0) {
                bohr.get(vex).move.put(symb, 0);
            } else {
                int gm = getMove(getSuffixLink(vex), symb);
                bohr.get(vex).move.put(symb, gm);
            }
        }
        return bohr.get(vex).move.get(symb);
    }

    public void findInd(String text) {
        int t = 0;
        for(int i = 0; i < text.length(); i++) {
            t = this.getMove(t, text.toCharArray()[i]);
            this.check(t, i);
        }
    }

    private void check(int vex, int i) {
        for(int t = vex; t != 0; t = getSuffixLink(t)) {
            if(bohr.get(t).flag == true) {
                answer.add( new MyPair(i - (patterns.get(bohr.get(t).pattern_index).length() - 1), bohr.get(t).pattern_index ) );
            }
        }
    }
}
