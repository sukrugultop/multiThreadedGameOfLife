//Şükrü Can Gültop	
//2014400201
//sukru.gultop@boun.edu.tr
//CMPE436-Assignment 2




import java.util.Random;


public class Grid {
    public static volatile int counter = 0;


    // current situation of the grid.
    int[][] grid;
    // maxgenerations input
    int generations;
    // worker threads
    ThreadWorker[][] threads ;
    // sem1 = semaphore for first barrier, sem2 = semaphore for second barrier, mutex = for counter
    CountingSemaphore sem1, sem2, mutex;


    //generates semaphores and random input, and creates threads.
    Grid(int m, int n, int generations){
        Random random = new Random();
        grid = new int[m][n];
        sem1 = new CountingSemaphore(0);
        sem2 = new CountingSemaphore(1);
        mutex = new CountingSemaphore(1);
        threads = new ThreadWorker[m][n];
        this.generations = generations;
        for (int i=0; i<m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = random.nextInt(2);
                threads[i][j] = new ThreadWorker(i,j,grid,generations, sem1, sem2, mutex);
            }
        }
    }
    //generates semaphores and creates threads
    Grid(int[][] grid, int generations){
        sem1 = new CountingSemaphore(0);
        sem2 = new CountingSemaphore(1);
        mutex = new CountingSemaphore(1);
        this.grid = grid;
        this.generations = generations;
        threads = new ThreadWorker[grid.length][grid[0].length];
        for (int i=0; i<grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                threads[i][j] = new ThreadWorker(i,j,this.grid,generations,sem1,sem2,mutex);
            }
        }
    }

     
    //starts all the threads 
    void generation(){
        for (int i=0; i<grid.length; i++)
            for (int j=0; j<grid[0].length; j++)
                threads[i][j].start();
    }

    @Override
    public String toString() {
        String ret = "";
        for (int i=0; i<grid.length; i++){
            for (int j=0; j<grid[0].length; j++){
                ret+=grid[i][j] +  " ";
            }
            ret+="\n";
        }
        return ret;

    }
}
