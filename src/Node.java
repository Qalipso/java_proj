import java.util.*;

public class Node {
    int parent;
    char symb;
    boolean flag;
    int pattern_index;
    int sfx_link;
    int sfx_good_link;
    Map<Character, Integer> next_vexes;
    Map<Character, Integer> move;

    public Node(int pr, char s) {
        parent = pr;
        symb = s;
        flag = false;
        sfx_good_link = -1;
        sfx_link = -1;
        next_vexes = new HashMap<>();
        move = new HashMap<>();
    }
}