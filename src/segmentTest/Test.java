package segmentTest;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        String text = "Володя  три раза из трёх 124 вот!34";
        ArrayList<Character> arrayList = new ArrayList<>();
        char[] chars = text.toCharArray();
        System.out.println(chars.length);
        int a = 9;
        long b = 7;
        double c = (double) a / ((double) b / 2);
        DecimalFormat decimalFormat = new DecimalFormat("###.###");
        System.out.println(decimalFormat.format(c));
        c = Double.parseDouble(decimalFormat.format(c));
        System.out.println(c);

        String alo = null;
        if (alo == null){
            System.out.println("JOPA");
        }

        Map<Integer, Integer> lol = new HashMap<>();
        lol.put(213,123);
        if (lol.isEmpty()){
            System.out.println("OBAMA");
        }

        System.out.println(System.currentTimeMillis() / 60000);

        long af = 1231;
        System.out.println(af);





    }
}
