import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Main {

    public static List<Integer> vals = new ArrayList<>();

    static Double cutRod(List<Double> price, int n)
    {
        if (n <= 0)
            return 0.0;
        Double max_val = Double.MAX_VALUE;

        // Recursively cut the rod in different pieces and
        // compare different configurations
        for (int i = 0; i<n; i++)
            max_val = Math.min(max_val,
                    price.get(i) + cutRod(price, n-i-1));

        return max_val;
    }

    /* Driver program to test above functions */
    public static void main(String args[]) throws IOException {
        FileInputStream fstream = new FileInputStream("I-ADS.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

//Read File Line By Line
        vals.add(0);
        while ((strLine = br.readLine()) != null)   {
            // Print the content on the console
            vals.add(Integer.parseInt(strLine));
            //System.out.println (vals.get(vals.size()-1));
        }

        //int penalties[] = {0, (int)Math.pow(200 - hotelList[1], 2), -1, -1, -1};
        //int path[] = {0, 0, -1, -1, -1};
        List<Integer> penalties = new ArrayList<>();
        List<Integer> path = new ArrayList<>();

        for(int i =0; i < vals.size(); i++) {
            penalties.add(-1);
        }
        penalties.set(0, 0);
        penalties.set(1, (int)Math.pow(400 - vals.get(1), 2));

        for(int i =0; i < vals.size(); i++) {
            path.add(-1);
        }

        path.set(0,0);
        path.set(1,0);

        for (int i = 2; i <= vals.size() - 1; i++) {
            for(int j = 0; j < i; j++){
                int tempPen = (int)(penalties.get(j) + Math.pow(400 - (vals.get(i) - vals.get(j)), 2));
                if(penalties.get(i) == -1 || tempPen < penalties.get(i)){
                    penalties.set(i, tempPen);
                    path.set(i,j);
                }
            }
        }
        for (int i = 1; i < vals.size(); i++) {
            System.out.print("Hotel: " + vals.get(i) + ", penalty: " + penalties.get(i) + ", path: ");
            printPath(path, i);
            System.out.println();
        }

    }

    public static void printPath(List<Integer> path, int i) {
        if (i == 0) return;
        printPath(path, path.get(i));
        System.out.print(i + " ");
    }

    public static List<Double> pokutaList(List<Integer> days) {
        List<Double> ret = new ArrayList<>();
        for(int i = 0; i < days.size(); i++) {
            ret.add(pokuta(days.get(i)));
        }

        return ret;
    }

    public static Double pokuta(Integer len) {
        return Math.pow((400-len), 2);
    }

    public static List<Integer> segregateDays(Integer start, Integer end, Integer actpos) {
        Integer si = closest(vals,start);
        Integer ei = closest(vals,end);
        List<Integer> ret = new ArrayList<>();

        if(vals.contains(start)) {
            si = vals.indexOf(start);
        }

        for(int i = si; i <= ei; i++) {
            ret.add(vals.get(i)-actpos);
        }

        return ret;
    }

    public static Integer closest(List<Integer> numbers, Integer val) {
        int distance = Math.abs(numbers.get(0) - val);
        int idx = 0;
        for(int c = 1; c < numbers.size(); c++){
            int cdistance = Math.abs(numbers.get(c) - val);
            if(cdistance < distance){
                idx = c;
                distance = cdistance;
            }
        }
        return idx;
    }
    public static <T extends Comparable<T>> int findMinIndex(final List<T> xs) {
        int minIndex;
        if (xs.isEmpty()) {
            minIndex = -1;
        } else {
            final ListIterator<T> itr = xs.listIterator();
            T min = itr.next(); // first element as the current minimum
            minIndex = itr.previousIndex();
            while (itr.hasNext()) {
                final T curr = itr.next();
                if (curr.compareTo(min) < 0) {
                    min = curr;
                    minIndex = itr.previousIndex();
                }
            }
        }
        return minIndex;
    }
}
