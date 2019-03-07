public class Tree {

    private Tree left = null;
    private Tree right = null;

    private String key;

    Tree(String key) {
        this.key = key;
    }

    public void setLeft(Tree left) {
        this.left = left;
    }

    public void setRight(Tree right) {
        this.right = right;
    }

    public int pocet_porovnani(String srch) {
        return pocet_porovnani(srch, 1);
    }

    public int pocet_porovnani(String srch, int idx) {

        int comp = key.compareTo(srch);
        //System.out.println("Key: "+ key + " Srch:"+srch+" idx: " +idx+ " comp: "+comp);
        if(comp == 0)
        {
            return idx;
        } else if(comp <= -1){
            if(right == null) return idx;
            return right.pocet_porovnani(srch, idx+1);
        } else {
            if(left == null) return idx;
            return left.pocet_porovnani(srch, idx+1);
        }
    }

}