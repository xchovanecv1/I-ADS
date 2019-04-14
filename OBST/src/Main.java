import java.io.*;
import java.util.*;

public class Main {

    // SRC https://github.com/CarterZhou/algorithms_practice/blob/master/dp/OptimalBST.java

    public static TreeMap<String, Integer> dic = new TreeMap<>();
    public static List<String> srch_keys = new ArrayList<>();
    public static Tree tree;

    public static Tree ConstructOptimalBST(int[][] root, int lowerKey,int higherKey,int numberOfKeys){
        int parent = root[lowerKey][higherKey];

        Tree buff = new Tree(srch_keys.get(parent));
        // Construct left sub-tree
        if(lowerKey<=parent-1){
            buff.setLeft(ConstructOptimalBST(root, lowerKey, parent-1, numberOfKeys));
        }else{
            buff.setLeft(null);
        }
        // Construct right sub-tree
        if(higherKey >=parent+1){
            buff.setRight(ConstructOptimalBST(root,     parent+1, higherKey, numberOfKeys));
        }else{
            buff.setRight(null);
        }
        return buff;
    }

    public static int[][] obst(double[] p,double q[],int n,double[][] e){
        double[][] w = new double[n+1+1][n+1];
        int[][] root = new int[n+1+1][n+1];

        for(int i=0;i<=n;i++){
            // Inicializacia hodnot
            // jedna sa o pripady kedy nemame ziadne kluce ki a existuju len dummy hodnoty di s pravdepodobnostou qi
            e[i+1][i] = q[i]; // cost
            w[i+1][i] = q[i]; // suma pravdepodobnosti, ale v prvom pripade bez klucov mame len pravdepodobnost neusmesneho hladania
        }
        for(int k=1;k<=n;k++){
            for(int i=1;i<=n-k+1;i++){
                int j = i+k-1;
                e[i][j] = Integer.MAX_VALUE; //e //ocakavana cena tohto podstromu
                w[i][j] = w[i][j-1] + p[j] + q[j]; // pravdepodobnost predosleho + aktualne
                // Prechadzame vsetky moznosti ako koren stromu a hladame tu s minimalnou cenou
                for(int r=i;r<=j;r++){
                    // skusime vsetky klue ako koren medzi i <= r <= j
                    // vypocitame cenu podstromu pri zbolenom roote r
                    // cena sa sklada z ceny laveho / praveho podstromu a hodnoty aktualneho kluca
                    double t = e[i][r-1] + e[r+1][j] + w[i][j];
                    if(t < e[i][j]){
                        // Minimalnu cenu ulozime a zapamatame si kto bol koren pre tento optimalnz podstrom
                        e[i][j] = t;
                        root[i][j] = r;
                    }
                }
            }
        }
        return root;

    }

    public static void main(String[] args) throws IOException {

        FileInputStream fstream = new FileInputStream("dictionary.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

        while ((strLine = br.readLine()) != null)   {
            // Print the content on the console
            String[] prts = strLine.split(" ");

            dic.put(prts[1], Integer.parseInt(prts[0]));
        }

        Integer freq_count = 0;
        List<Integer> p_c = new ArrayList<>();
        List<Integer> q_c = new ArrayList<>();

        p_c.add(-1);
        srch_keys.add("EMPTY");

        SortedSet<String> keys = new TreeSet<>(dic.keySet());

        Integer q_c_buff = 0;

        for (String key : keys) {
            Integer value = dic.get(key);
            freq_count += value;
            if(value <= 50000) {
                q_c_buff += value;
                //
            } else {
                q_c.add(q_c_buff);
                p_c.add(value);
                srch_keys.add(key);
                q_c_buff = 0;
            }
            //System.out.println(key + ": " + value);
            // do something
        }

        if(q_c_buff > 0) {
            q_c.add(q_c_buff);
        }

        double[] p = new double[q_c.size()];
        double[] q = new double[q_c.size()];

        for(int i = 0; i < q_c.size(); i++)
        {
            if(i == 0) {
                p[0] = -1;
                q[0] = ((double)q_c.get(0) / freq_count);
            } else {
                p[i] = ((double)p_c.get(i) / freq_count);
                q[i] = ((double)q_c.get(i) / freq_count);
            }
        }

        int numberOfKeys = p_c.size() - 1;

        double[][] cost_op = new double[numberOfKeys+1+1][numberOfKeys+1];

        int[][] root_op = obst(p, q, numberOfKeys,cost_op);

        System.out.println("Cena: " + (double)cost_op[1][numberOfKeys] + "\n");


        Tree strom = ConstructOptimalBST(root_op, 1, numberOfKeys, numberOfKeys);


        BufferedWriter writer = new BufferedWriter(new FileWriter("outSearch.txt"));

        for (String key : keys) {
            writer.write(key+": "+strom.pocet_porovnani(key)+"\n");
        }
        writer.close();
    }
}
