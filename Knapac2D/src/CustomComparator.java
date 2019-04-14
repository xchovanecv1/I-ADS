import java.util.Comparator;

public class CustomComparator implements Comparator<Item> {
    @Override
    public int compare(Item o1, Item o2) {
        return Integer.parseInt(o1.name) - (Integer.parseInt(o2.name));
    }
}