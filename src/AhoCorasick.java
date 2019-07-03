package mypack;
import java.util.ArrayList;
import mypack.Node;
import java.util.Arrays;
import java.util.List;

public class AhoCorasick {

    public List<String> patterns ;
    private List<Node> bohr;

    public AhoCorasick() {
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
            } else if (bohr.get(vex).next_vexes.containsKey('&') ) {
                bohr.get(vex).move.put(symb, bohr.get(vex).next_vexes.get('&'));
            } else if(vex == 0) {
                bohr.get(vex).move.put(symb, 0);
            } else {
                int gm = getMove(getSuffixLink(vex), symb);
                bohr.get(vex).move.put(symb, gm);
            }
        }
        return bohr.get(vex).move.get(symb);
    }

    public void findInd(String text, HundlerWord hw) {
        int t = 0;
        for(int i = 0; i < text.length(); i++) {

            t = this.getMove(t, text.toCharArray()[i]);
            if (text.toCharArray()[i] == '$') {
                t = 0;
                continue;
            }

            this.check(t, i, hw);
        }
    }

    private void check(int vex, int i, HundlerWord hw) {
        for(int t = vex; t != 0; t = getSuffixLink(t)) {
            if(bohr.get(t).flag) {
//                if (hw.indexes.containsKey(patterns.get(bohr.get(t).pattern_index))) {
//                    int[] array = hw.indexes.get(patterns.get(bohr.get(t).pattern_index));
//                    array = Arrays.copyOf(array, array.length + 1);
//                    array[array.length - 1] = i - (patterns.get(bohr.get(t).pattern_index).length() - 1);
//
//                    hw.indexes.put(patterns.get(bohr.get(t).pattern_index), array);
//                } else {
//                    int[] array = new int[1];
//                    array[0] = i - (patterns.get(bohr.get(t).pattern_index).length() - 1);
//                    hw.indexes.put(patterns.get(bohr.get(t).pattern_index), array);
//                }
                hw.entries.add(patterns.get(bohr.get(t).pattern_index));
            }
        }
    }
}
