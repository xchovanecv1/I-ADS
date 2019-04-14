import java.util.Comparator;

public class Item {

    public String name;
    public int value;
    public int weight;
    public int fragile;

    public Item(String name, int value, int weight, int fragile) {
        this.name = name;
        this.value = value;
        this.weight = weight;
        this.fragile = fragile;
    }

    public String str() {
        return name + " [value = " + value + ", weight = " + weight + "+ \", fragile = " + fragile + "]";
    }

    public int compareTo(Item compareFruit) {

        return name.compareTo(compareFruit.name);
    }
}