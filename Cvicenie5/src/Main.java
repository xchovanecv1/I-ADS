import java.io.*;

import static java.lang.Integer.max;

public class Main {

    static class Pair{
        int first, second;
        int pick=0, second_p = 0;
        public String toString(){
            return first + " " + second + " " + pick;
        }
    }
    public Pair[][] findMoves(int pots[]){

        Pair[][] moves = new Pair[pots.length][pots.length];

        for(int i=0; i < moves.length; i++){
            for(int j=0; j < moves[i].length; j++){
                moves[i][j] = new Pair();
            }
        }

        for(int i=0; i < pots.length; i++){
            moves[i][i].first = pots[i];
            //to track the sequence of moves
            moves[i][i].pick = i;
        }

        for(int l = 2; l <= pots.length; l++){
            for(int i=0; i <= pots.length - l; i++){
                int j = i + l -1;
                if(pots[i] + moves[i+1][j].second > moves[i][j-1].second + pots[j]){
                    moves[i][j].first = pots[i] + moves[i+1][j].second;
                    moves[i][j].second = moves[i+1][j].first;
                    moves[i][j].pick = i;
                }else{
                    moves[i][j].first = pots[j] + moves[i][j-1].second;
                    moves[i][j].second = moves[i][j-1].first;
                    moves[i][j].pick =j;
                }
            }
        }

        return moves;
    }
    //prints the sequence of values and indexes
    public void printSequence(int pots[], Pair moves[][]){
        int i = 0;
        int j = pots.length - 1;
        int step;
        for (int k = 0; k < pots.length; k++) {
            step = moves[i][j].pick;
            //this is the value of pick and its index
            System.out.print("value: " + pots[step] + " " + "index: " + step + " ");
            if (step <= i) {
                i = i + 1;
            } else {
                j = j - 1;
            }
        }
    }
    public static void main(String args[]) throws IOException {
        Main npg = new Main();

        FileInputStream fstream = new FileInputStream("I-ADS.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

        strLine = br.readLine();

        int pot[] = new int[strLine.length()];
        for(int i=0; i < strLine.length(); i++) {
            pot[i] = Integer.parseInt(""+strLine.charAt(i));
        }

//        int pot[] = {3, 1, 5, 6, 2, 9, 3};
        Pair[][] moves = npg.findMoves(pot);
/*
        for (int i = 0; i < moves.length; i++) {
            for (int j = 0; j < moves[i].length; j++) {
                System.out.print(moves[i][j] + "  ");
            }
            System.out.print("\n");
        }*/
        System.out.print(moves[0][pot.length-1].second + "  ");
        System.out.println("The moves by first player and second player:");
        //npg.printSequence(pot, moves);
    }
}
