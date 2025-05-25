import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class AverageTask implements Callable<Double> {
    private final int[] data;
    private final int start;
    private final int end;

    AverageTask(int[] data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    @Override
    public Double call() {
        double sum = 0;
        for (int i = start; i < end; i++) {
            sum += data[i];
        }
        return sum / (end - start);
    }
}

public class Pool {
    public static void main(String[] args) {
        int[] data = new int[1_000_000];

        Random rand = new Random();

        for (int i = 0; i < data.length; i++) {
            data[i] = rand.nextInt(100);
        }

        ExecutorService executor = Executors.newFixedThreadPool(4);
        @SuppressWarnings("unchecked")
        Future<Double>[] partialResults = (Future<Double>[]) new Future[4];

        int chunkSize = data.length / 4;
        for (int i = 0; i < 4; i++) {
            int start = i * chunkSize;
            int end = (i == 3) ? data.length : start + chunkSize;

            partialResults[i] = executor.submit(new AverageTask(data, start, end));
        }

        double totalSum = 0;
        for (Future<Double> result : partialResults) {
            try {
                totalSum += result.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        double average = totalSum / data.length;
        System.out.println("Rata-rata: " + average);

        executor.shutdown();
    }
}