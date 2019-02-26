import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import java.io.*;

public class Main {

    private static int rows = 1000;
    private static int cols = 50;

    public static void main(String[] args) throws IOException {

        FileInputStream fstream = new FileInputStream("I-ADS.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

        int[][] M = new int[rows][cols];
        int[][] hod = new int[rows][cols];
        int r = 0;
        while ((strLine = br.readLine()) != null)   {
            // Print the content on the console

            String[] prts = strLine.split(" ");
            for(int i = 0; i < prts.length; i++) {
                M[r][i] = Integer.parseInt(prts[i]);
                hod[r][i] = Integer.parseInt(prts[i]);
                System.out.print(hod[r][i] + " ");
            }
            System.out.println (" ");
            r++;
        }
        int[][] back = new int[rows][cols];

        for(int i=1; i < rows; i++) {
            for(int j=0; j < cols ; j++) {
                int minD = 0;
                if(j == 0) { // lavobok
                    int[] ch = {M[i-1][j], M[i-1][j+1]};
                    int idx = FindSmallestInRange(ch);
                    back[i][j] = idx + j;
                    minD = M[i - 1][idx + j];
                } else if(j == cols - 1) { // pravobok
                    int[] ch = {M[i-1][j-1], M[i-1][j]};
                    int idx = FindSmallestInRange(ch);
                    back[i][j] = idx + j;
                    minD = M[i - 1][idx + j];
                } else { // centrobok
                    int[] ch = {M[i-1][j-1], M[i-1][j], M[i-1][j+1]};
                    int idx = FindSmallestInRange(ch);
                    back[i][j] = idx + j - 1;
                    minD = M[i - 1][idx  + j - 1];

                }
                M[i][j] += minD;
            }
        }
        int j = FindSmallestInRange(M[rows-1]);
        System.out.println(j);
        int sum = 0;
        for(int i = rows-1; i >= 0; i--) {
            sum += hod[i][j];
            j = back[i][j];
        }

        System.out.println("Min:"+ sum);
    }

    public static int FindSmallestInRange (int [] arr1){//start method

        int index = 0;
        int min = arr1[0];
        for (int i = 1; i< arr1.length; i++){

            if (arr1[i] < min ){
                min = arr1[i];
                index = i;
            }

        }
        return index ;

    }
}
