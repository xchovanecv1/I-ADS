import java.io.*;
import java.util.*;

public class Main {

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
            buff.setRight(ConstructOptimalBST(root, parent+1, higherKey, numberOfKeys));
        }else{
            buff.setRight(null);
        }
        return buff;
    }

    public static int[][] obst(double[] p,double q[],int n,double[][] cost){
        double[][] w = new double[n+1+1][n+1];
        int[][] root = new int[n+1+1][n+1];

        for(int i=0;i<=n;i++){
            cost[i+1][i] = q[i];
            w[i+1][i] = q[i];
        }
        for(int k=1;k<=n;k++){
            for(int i=1;i<=n-k+1;i++){
                int j = i+k-1;
                cost[i][j] = Integer.MAX_VALUE;
                w[i][j] = w[i][j-1] + p[j] + q[j];
                for(int r=i;r<=j;r++){
                    double t = cost[i][r-1] + cost[r+1][j] + w[i][j];
                    if(t < cost[i][j]){
                        cost[i][j] = t;
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
            //System.out.println(p[i] + " " + q[i]);
        }

       // System.out.println(p_c.size());
        //System.out.println(q_c.size());

        int numberOfKeys = p_c.size() - 1;

        double[][] cost_op = new double[numberOfKeys+1+1][numberOfKeys+1];

        int[][] root_op = obst(p, q, numberOfKeys,cost_op);

        System.out.println("A search cost of this optimal BST is " + (double)cost_op[1][numberOfKeys] + "\n");


        Tree strom = ConstructOptimalBST(root_op, 1, numberOfKeys, numberOfKeys);


        BufferedWriter writer = new BufferedWriter(new FileWriter("outSearch.txt"));

        for (String key : keys) {
            writer.write(key+": "+strom.pocet_porovnani(key)+"\n");
        }
        writer.close();
    }
}
