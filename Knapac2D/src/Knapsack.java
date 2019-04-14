import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
// https://medium.com/@ssaurel/solving-the-knapsack-problem-in-java-c985c71a7e64
public class Knapsack {

    // items of our problem
    private Item[] items;
    // capacity of the bag
    private int capacity;
    private int fragCount;

    public Knapsack(Item[] items, int capacity, int fragCount) {
        this.items = items;
        this.capacity = capacity;
        this.fragCount = fragCount;
    }

    public void display() {
        if (items != null  &&  items.length > 0) {
            System.out.println("Knapsack problem");
            System.out.println("Capacity : " + capacity);
            System.out.println("Fragile count : " + fragCount);
            System.out.println("Items :");

            for (Item item : items) {
                System.out.println("- " + item.str());
            }
        }
    }

    // we write the solve algorithm
    public Solution solve() {
        int NB_ITEMS = items.length;

        // we use a matrix to store the max value at each n-th item
        int[][][] matrix = new int[NB_ITEMS + 1][capacity + 1][fragCount +1];

        // first line is initialized to 0
        for (int i = 0; i <= capacity; i++)
            for(int j = 0; j < fragCount; j++)
            matrix[0][i][j] = 0;

        // we iterate on items
        for (int i = 1; i <= NB_ITEMS; i++) {
            // we iterate on each capacity
            for (int j = 0; j <= capacity; j++) {
                for(int g = 0; g <= fragCount; g++) {
                    // daný predmet presahuje max povolenú váhu alebo frag count ruxaku a tak použujeme už najdene hodnoty
                    if (items[i - 1].weight > j || items[i - 1].fragile > g)
                        matrix[i][j][g] = matrix[i-1][j][g];
                    else
                        // we maximize value at this rank in the matrix
                        matrix[i][j][g] = Math.max(matrix[i-1][j][g], matrix[i-1][j - items[i-1].weight][g - items[i-1].fragile]
                                + items[i-1].value);
                }
            }
        }

        int res = matrix[NB_ITEMS][capacity][fragCount];
        int w = capacity;
        int f = fragCount;

        for(int ff = 0; ff <= fragCount; ff++) {
            System.out.println("f: "+ff);
            for(int ii = 0; ii <= NB_ITEMS; ii++) {
                for(int ww = 0; ww <= capacity; ww++) {
                    System.out.print(matrix[ii][ww][ff] + " ");
                }
                System.out.println(" ");
            }
            System.out.println("");
        }

        List<Item> itemsSolution = new ArrayList<>();

        for (int i = NB_ITEMS; i > 0  &&  res > 0; i--) {
            // hladame prípa kedy sa daná hodnota nenachádza aj v predchádzajúcom riadku,
            // vtedy tam dany predmet patrí
            if (res != matrix[i-1][w][f]) {
                itemsSolution.add(items[i-1]);
                // we remove items value and weight
                res -= items[i-1].value;
                w -= items[i-1].weight;
                f -= items[i-1].fragile;
                // minimalizujeme zostavajuci obsah ruksak pre vahu a fragile
            }
        }

        return new Solution(itemsSolution, matrix[NB_ITEMS][capacity][fragCount]);
    }

    public static Knapsack parseInput(String name) {
        Knapsack ksc = null;
        int lineNum = 0;
        int predmetov = 0;
        int maxVaha = 0;
        int krehke = 0;
        List<Item> items = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(name))) {
            for (String line; (line = br.readLine()) != null; ) {
                if(lineNum == 0) {
                    predmetov = Integer.parseInt(line);
                } else if(lineNum == 1) {
                    maxVaha = Integer.parseInt(line);
                } else if(lineNum == 2){
                    krehke = Integer.parseInt(line);
                } else if(lineNum < predmetov + 3){
                    String[] parts = line.split(" ");
                    Item bf = new Item(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                    items.add(bf);
                }
                lineNum++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Item[] itemy = new Item[items.size()];
        itemy = items.toArray(itemy);
        ksc = new Knapsack(itemy, maxVaha, krehke);
        return ksc;
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static void generateSack(String in, int count, int waga, int frags) throws IOException {
        BufferedWriter wr = new BufferedWriter(new FileWriter(in));
        wr.write(count+"\n");
        wr.write(waga+"\n");
        wr.write(frags+"\n");
        for(int i=0; i < count; i++) {
            String item = (i+1)+" ";
            item += getRandomNumberInRange(1,9)+" ";
            item += getRandomNumberInRange(1,9)+" ";
            item += getRandomNumberInRange(0,1)+"\n";
            wr.write(item);
        }
        wr.close();
    }

    public static void main(String[] args) throws IOException {
        // we take the same instance of the problem displayed in the image
/*
        Item[] items = {
                new Item("Elt1", 8, 4, 1),
                new Item("Elt2", 5, 1, 1),
                new Item("Elt3", 6, 2, 1)};

        generateSack("predmety.txt", 100, 100,10);
*/
        Knapsack knapsack = parseInput("predmety.txt");
        knapsack.display();
        Solution solution = knapsack.solve();
        solution.display();
        solution.save("out.txt");
    }
}
