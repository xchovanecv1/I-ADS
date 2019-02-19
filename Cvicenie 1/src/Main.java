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
        while ((strLine = br.readLine()) != null)   {
            // Print the content on the console
            vals.add(Integer.parseInt(strLine));
            //System.out.println (vals.get(vals.size()-1));
        }
        Double allpokuta = 0.0;
        Integer elOff = 0;
        Integer okolie = 500;
        Integer base = 0;
        Integer at = 0;
        Integer srchend = at+okolie;
        List<Integer> stops = new ArrayList<>();
        while(true) {
            List<Integer> day = segregateDays(at, srchend, base);
            List<Double> pkt = pokutaList(day);

            //Double min = cutRod(pkt, day.size());

            int minn = findMinIndex(pkt);
/*
            for (int g = 0; g < pkt.size(); g++) {
                System.out.println("i: " + g + ": " + pkt.get(g));
            }
*/
            if(vals.size() < elOff+minn) {
                break;
            }
                at = vals.get(elOff+minn);
                srchend = at + okolie;
                base = vals.get(elOff+minn);
                System.out.println("Allpkt: "+ allpokuta);
                allpokuta += pkt.get(minn);
                stops.add(elOff+minn);
                System.out.println("Vys " + minn + " " + pkt.get(minn) + " Sleep at: " + vals.get(elOff+minn)+" At: "+at);
                elOff += pkt.size();

  /*          } catch (IndexOutOfBoundsException e) {
                i = 800001;

            }*/
        }


        for (int g = 0; g < stops.size(); g++) {
            if(g > 0) {
                System.out.println("Travel dist: "+ (vals.get(stops.get(g)) - vals.get(stops.get(g-1))));
            }
            System.out.println("Stop at: " + g + ": " + vals.get(stops.get(g)));
        }
        System.out.println("Allpkt: "+ allpokuta);

/*
        int arr[] = new int[] {1, 5, 8, 9, 10, 17, 17, 20};
        int size = arr.length;
        System.out.println("Maximum Obtainable Value is "+
                cutRod(arr, size));
*/
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
