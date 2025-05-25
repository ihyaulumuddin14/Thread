import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;

public class AppRun1 {
    private static ExecutorService executor;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan jumlah thread : ");
        int jumlahThread = scanner.nextInt();

        executor = Executors.newFixedThreadPool(jumlahThread);
        
        executor.execute(() -> Pekerjaan.digitFaktorial());
        executor.execute(() -> Pekerjaan.cekKeberadaanPrima(10000, 9873));
        executor.execute(() -> Pekerjaan.hitungKuadrat(15458));
        executor.execute(() -> Pekerjaan.cariMaksimum());
        executor.execute(() -> Pekerjaan.jumlahkanArray());

        executor.shutdown();
    }
}

class Pekerjaan {
    // 1. hitung digit faktorial
    static void digitFaktorial() {
        String threadName = Thread.currentThread().getName();
        try {
            System.out.println(threadName + " - Mulai faktorial...");

            BigInteger hasil = BigInteger.valueOf(1);
            for (int i = 2; i <= 20000; i++) {
                hasil = hasil.multiply(BigInteger.valueOf(i));
            }
            System.out.println(threadName + " - Selesai faktorial: didapatkan angka sebanyak " + hasil.toString().length() + " digit.");
        } catch (Exception e) {
            System.err.println("Terjadi kesalahan saat menghitung faktorial: " + e.getMessage());
        }
    }

    // 2. cek keberadaan bilangan prima
    static void cekKeberadaanPrima(int batas, int tebakan) {
        String threadName = Thread.currentThread().getName();
        List<Integer> bilanganPrima = new ArrayList<>();
        StringJoiner result = new StringJoiner(", ", "[", "]");
        
        try {
            System.out.println(threadName + " - Mulai mengumpulkan bilangan prima...");
            
            Thread.sleep(2000);

            for (int i = 2; i <= batas; i++) {
                if (isPrime(i)) bilanganPrima.add(i);
            }

            Set<Integer> primaAcak = new HashSet<>(bilanganPrima);

            System.out.println(
                threadName + " - " +
                (primaAcak.contains(tebakan) ? 
                tebakan + " ditemukan dalam bilangan prima." : 
                tebakan + " tidak ditemukan dalam bilangan prima 2 sampai " + batas + ".")
            );
        } catch (InterruptedException e) {
            System.out.println("Operasi cek bilangan prima terputus: " + e.getMessage());
        }

    }

    static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    // 3. hitung kuadrat
    static void hitungKuadrat(int n) {
        String threadName = Thread.currentThread().getName();
        BigInteger kuadrat = BigInteger.ZERO;

        
        try {
            System.out.println(threadName + " - Hitung kuadrat...");

            Thread.sleep(3000);

            if (n < 0) throw new IllegalArgumentException("Kuadrat tidak didefinisikan untuk bilangan negatif.");
            BigInteger x = BigInteger.valueOf(n);
            kuadrat = x.multiply(x);

            System.out.println(threadName + " - Hasil kuadrat dari " + n + " adalah: " + kuadrat);
        } catch (InterruptedException e) {
            System.err.println("Terjadi kesalahan saat menghitung kuadrat: " + e.getMessage());
        }
    }

    // 4. Cari maksimum dari array besar
    static void cariMaksimum() {
        String threadName = Thread.currentThread().getName();

        try {
            System.out.println(threadName + " - Cari maksimum...");

            Thread.sleep(3500);

            Random rand = new Random();
            int[] data = rand.ints(10_000_000, 0, 1_000_000).toArray();
            int max = Integer.MIN_VALUE;
            for (int num : data) {
                if (num > max) max = num;
            }

            System.out.println(threadName + " - Maksimum: " + max);
        } catch (InterruptedException e) {
            System.out.println("Operasi cari maksimum terputus: " + e.getMessage());
        }
    }

    // 5. Jumlahkan isi array besar
    static void jumlahkanArray() {
        String threadName = Thread.currentThread().getName();
        try {
            System.out.println(threadName + " - Jumlahkan array...");

            Thread.sleep(3000);

            Random rand = new Random();
            int[] data = rand.ints(10_000_000, 0, 1000).toArray();
            long sum = 0;
            for (int num : data) {
                sum += num;
            }

            System.out.println(threadName + " - Total jumlah: " + sum);
        } catch (InterruptedException e) {
            System.out.println("Operasi jumlahkan array terputus: " + e.getMessage());
        }
    }
}