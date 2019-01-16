//Şükrü Can Gültop	
//2014400201
//sukru.gultop@boun.edu.tr
//CMPE436-Assignment 2


import java.io.File;
import java.util.Scanner;

public class Run {
    public static void main(String[] args) {
        if (args.length!=3 && args.length!=4){
            throw new Error("input should be  [M] [N] [maxGenerations] or  [M] [N] [maxGenerations]  [input.txt]");
        }
        int m = Integer.parseInt(args[0]); // M = rows of the input
        int n = Integer.parseInt(args[1]); // N = columns of the input
        int maxGenerations = Integer.parseInt(args[2]); // number of max generations
        String file = ""; // if there is input file, this is the path of that file.
        Grid input; // Grid object of the given input or random input

        if (args.length == 4) {
            file = args[3];
            // creates the grid with given input
            input = new Grid(createMatrix(new File(file),m,n),maxGenerations);
        }else{
            // creates the grid with random 0s and 1s.
            input = new Grid(m,n,maxGenerations);
        }
        System.out.println("input is : ");
        System.out.println(input);
        input.generation();
        System.out.println("After "+maxGenerations+". generation :\n" + input);


    }

    /**
     * Creates a matrix according to given input file and returns it.
     *
     * @param input file that contains the initial state of the game
     * @param m  number of rows of the input
     * @param n  number of columns of the input
     * @return double dimentional array form of input
     */
    static int[][] createMatrix(File input, int m, int n){
        int[][] matrix = null;
        try (Scanner scan = new Scanner(input)) {
            matrix = new int[m][n];
            for (int i=0; i<m; i++){
                for (int j=0; j<n; j++){
                    int tmp = scan.nextInt();
                    if(tmp!=1 && tmp!=0){
                        System.out.println("Illegal input, inputs should be 0 or 1");
                        System.exit(1);
                    }
                    matrix[i][j] = tmp;
                }
            }
        }catch (Exception e){
            throw new Error("The file format not as expected");
        }
        return matrix;
    }
}
