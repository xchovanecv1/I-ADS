import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class BinarySearchTree {

    Tree prev;

    /* Returns true if given search tree is binary
       search tree (efficient version) */

    class BNode{
        Tree n;
        Integer left;
        Integer right;
        public BNode(Tree n, Integer left, Integer right){
            this.n = n;
            this.left = left;
            this.right = right;
        }
    }
    public boolean isValidBST(Tree root) {
        if(root == null)
            return true;

        LinkedList<BNode> queue = new LinkedList<BNode>();
        queue.add(new BNode(root, Integer.MIN_VALUE, Integer.MAX_VALUE));
        while(!queue.isEmpty()){
            BNode b = queue.poll();
            System.out.println("["+b.left + " <= "+b.n.val + " <= " + b.right+"]");
            if(b.n.val <= b.left || b.n.val >=b.right){
                if(b.n.val <= b.left) {
                    System.out.println(b.n.val + "<=" + b.left);
                    System.out.println("key <= LEFT");
                } else if(b.n.val >=b.right){
                    System.out.println(b.n.val + ">=" + b.right);
                    System.out.println("key" + ">=" + "Right");
                }
                return false;
            }

            if(b.n.left!=null){
                System.out.println("left - "+b.n.left.val);
                queue.offer(new BNode(b.n.left, b.left, b.n.val));
            }
            if(b.n.right!=null){
                System.out.println("right - "+b.n.right.val);
                queue.offer(new BNode(b.n.right, b.n.val, b.right));
            }
        }
        return true;
    }

    // Driver program to test above functions
    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader("I-ADS.txt"))) {
            for (String line; (line = br.readLine()) != null; ) {
                nums.add(Integer.parseInt(line));
                //System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*int x = nums.get(nums.size()-1);
        nums.remove(nums.size()-1);
*/
        Tree root = new Tree(nums.get(0));
        int i = 1;
        Tree actual = root;
        for(; i < nums.size(); i++) {
            Tree nw = new Tree(nums.get(i));

            if(actual.val < nums.get(i)) {
                actual.right = nw;
            } else {
                actual.left = nw;
            }
            actual = nw;
        }
        int[] order = new int[nums.size()];
        BinarySearchTree bst = new BinarySearchTree();
        System.out.println(bst.isValidBST(root));

    }

}

// This code has been contributed by Mayank Jaiswal
