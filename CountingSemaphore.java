//Şükrü Can Gültop	
//2014400201
//sukru.gultop@boun.edu.tr
//CMPE436-Assignment 2



class CountingSemaphore { // Different from the book
    // used for synchronization of cooperating threads
    int value; // semaphore is initialized to the number of resources
    CountingSemaphore(int initValue) {
        value = initValue;
    }
    synchronized void P() { // blocking
        while (value == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        value--;
    }
    synchronized void V() { // non-blocking
        value++;
        notify();
    }
}
