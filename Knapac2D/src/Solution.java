import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Solution {

    // list of items to put in the bag to have the maximal value
    public List<Item> items;
    // maximal value possible
    public int value;

    public Solution(List<Item> items, int value) {
        this.items = items;
        this.value = value;
    }

    public void display() {
        if (items != null  &&  !items.isEmpty()){
            System.out.println("\nKnapsack solution");
            System.out.println("Value = " + value);
            System.out.println("Items to pick :");

            for (Item item : items) {
                System.out.println("- " + item.str());
            }
        }
    }

    public void save(String name) throws IOException {
        BufferedWriter bf = new BufferedWriter(new FileWriter(name));
        bf.write(value+"\n");
        bf.write(items.size()+"\n");
        Collections.sort(items, new CustomComparator());

        for (Item item : items) {
            bf.write(item.name+"\n");
        }
        bf.close();
    }

}