public class RaceConditionExample {

    static int counter = 0;

    public static void main(String[] args) throws InterruptedException {

        Thread[] threads = new Thread[2];
        MyRunnable runnable = new MyRunnable();

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(runnable);
        }

        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        System.out.println("Counter akhir: " + counter);  // Harusnya 20_000
    }
}


class MyRunnable implements Runnable {
    
    @Override
    public synchronized void run() {
        for (int i = 0; i < 10000; i++) {
            RaceConditionExample.counter++;
        }
    }
}