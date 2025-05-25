class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;
        System.out.println("Count incremented by " + Thread.currentThread().getName() + ": " + getCount());
    }

    public int getCount() {
        return count;
    }
}

class MyRunnable implements Runnable {
    private Counter counter;

    public MyRunnable(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1; i++) {
            counter.increment();
        }
    }
}


public class Main {
    public static void main(String[] args) {
        Counter counter = new Counter();
        MyRunnable myRunnable = new MyRunnable(counter);

        Thread thread1 = new Thread(myRunnable);
        Thread thread2 = new Thread(myRunnable);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final count: " + counter.getCount());
    }
}
