

//Şükrü Can Gültop	
//2014400201
//sukru.gultop@boun.edu.tr
//CMPE436-Assignment 2


public class ThreadWorker extends Thread {
    int i, j, n;
    int[][] grid;
    int nextValue;
    int generations;
    CountingSemaphore barrier1;
    CountingSemaphore barrier2;
    CountingSemaphore mutex;

    ThreadWorker(int x, int y, int[][] grid, int generations, CountingSemaphore b1, CountingSemaphore b2, CountingSemaphore mutex){
        this.mutex = mutex;
        barrier1 = b1;
        barrier2 = b2;
        this.n=grid.length*grid[0].length;
        this.grid=grid;
        this.i = x;
        this.j = y;
        nextValue = grid[x][y];
        this.generations = generations;
    }

    public void run(){
        for (int x=0; x<generations; x++) {
	    //total number of 1s in neighbours
            int sum = 0;
            if (j > 0)
                sum += grid[i][j - 1];

            if (j < grid[i].length - 1)
                sum += grid[i][j + 1];

            if (i > 0)
                sum += grid[i - 1][j];

            if (i > 0 && j > 0)
                sum += grid[i - 1][j - 1];

            if (i > 0 && j < grid[i].length - 1)
                sum += grid[i - 1][j + 1];

            if (j > 0 && i < grid.length - 1)
                sum += grid[i + 1][j - 1];

            if (j < grid[i].length - 1 && i < grid.length - 1)
                sum += grid[i + 1][j + 1];

            if (i + 1 < grid.length)
                sum += grid[i + 1][j];

            if (grid[i][j] == 1) {
                if (!(sum == 2 || sum == 3)) {
                    nextValue = 0;
                }
            } else {
                if (sum == 3)
                    nextValue = 1;
            }
	    //lock for counter.
            mutex.P();
            Grid.counter+=1;
	    //if n threads are waiting unlocks the first barrier and locks the second barrier
            if (Grid.counter == n){
                barrier2.P();
                barrier1.V();
            }
            mutex.V();

            // first barriers to wait for updating grid cells.
            barrier1.P();
            barrier1.V();
	    
            grid[i][j] = nextValue;
		
	    //lock for counter
            mutex.P();
            Grid.counter-=1;
	    //if all the threads update and comes to hear, they can continue to looping (second barrier)
            if (Grid.counter == 0) {
                barrier1.P();
                barrier2.V();
            }
            mutex.V();

            barrier2.P();
            barrier2.V();
        }

    }


}



