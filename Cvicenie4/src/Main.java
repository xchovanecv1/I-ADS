import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import java.io.*;

class Main
{
    public static int withoutRepetition(int[][] values, int[][] weights, int weight) {
        assert (values.length == weights.length); // 1

        int[][] results = new int[values.length + 1][weight + 1]; // 2
        for(int i = 1; i < results.length; ++i) { // 3
            for(int j = 1; j < results[i].length; ++j) { // 4
                results[i][j] = results[i - 1][j]; // 5
                if(weights[i - 1][0] > j) // 6
                    continue;
                if(weights[i - 1][1] > j)
                    continue;

                int candidate = results[i - 1][j - weights[i - 1][0]] + values[i - 1][0]; // 7
                int candidate2 = results[i - 1][j - weights[i - 1][1]] + values[i - 1][1]; // 7
                if(candidate > results[i][j])
                    results[i][j] = candidate;
                if(candidate2 > results[i][j])
                    results[i][j] = candidate2;
                System.out.print(results[i][j] + " ");
            }
            System.out.println(" ");
        }

        return results[values.length][weight];
    }

    public static void main(String[] args) throws IOException {

        FileInputStream fstream = new FileInputStream("I-ADS.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

        int[][] values = new int[1000][2];
        int[][] weights = new int[1000][2];
        int weight = 6;

        int r = 0;
        while ((strLine = br.readLine()) != null)   {
            // Print the content on the console
            String[] prts = strLine.split(",");
            values[r][0] = Integer.parseInt(prts[0]);
            weights[r][0] = Integer.parseInt(prts[1]);
            values[r][0] = Integer.parseInt(prts[2]);
            weights[r][0] = Integer.parseInt(prts[3]);
            r++;
        }

        System.out.println(withoutRepetition(values, weights, weight));
    }
}
