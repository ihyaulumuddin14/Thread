
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class StudiKasus2 {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 1; i <= 15; i++) {
            try {
                if (i % 2 == 0) {
                    Thread.sleep(250);
                    executor.execute(() -> {
                        Inventori.tambahStok("Laptop", new Random().nextInt(5) + 1);
                    });
                } else {
                    executor.execute(() -> {
                        Inventori.kurangiStok("Laptop", new Random().nextInt(4) + 1);
                    });
                }
            } catch (InterruptedException e) {
                System.out.println("Terjadi kesalahan saat menghitung faktorial: " + e.getMessage());
            }
        }


        executor.shutdown();

        while (!executor.isTerminated()) {}
        
        System.out.println("Stok akhir Laptop: " + Inventori.laptop.getStok());
    }
}

class Inventori {
    static Laptop laptop = new Laptop("Asus", 10);
    
    static synchronized void tambahStok(String namaBarang, int jumlah) {
        namaBarang = namaBarang.toLowerCase();
        String threadName = Thread.currentThread().getName().substring(7);

        try {
            Thread.sleep(350);
        } catch(InterruptedException e) {
            System.out.println("Terjadi kesalahan saat menghitung faktorial: " + e.getMessage());
        }
        
        System.out.print(threadName + " - ");
        if (namaBarang.equals("laptop")) {
            laptop.tambahStok(jumlah);
        }
    }
    
    static synchronized void kurangiStok(String namaBarang, int jumlah) {
        namaBarang = namaBarang.toLowerCase();
        String threadName = Thread.currentThread().getName().substring(7);

        try {
            Thread.sleep(350);
        } catch(InterruptedException e) {
            System.out.println("Terjadi kesalahan saat menghitung faktorial: " + e.getMessage());
        }
        
        System.out.print(threadName + " - ");

        if (namaBarang.equals("laptop")) {
            laptop.kurangiStok(jumlah);
        }
    }
}

class Barang {
    protected String merk;
    private int stok;

    public Barang(String merk, int stok) {
        this.merk = merk;
        this.stok = stok;
    }

    public void tambahStok(int jumlah) {
        stok += jumlah;
    }

    public void kurangiStok(int jumlah) {
        stok -= jumlah;
    }

    protected void infoBeli(int jumlah) {
        System.out.print("Berhasil beli " + jumlah + ". ");
    }

    protected void infoRestock(int jumlah) {
        System.out.print("Restock " + jumlah + ". ");
    }

    public int getStok() {
        return stok;
    }
}

class Laptop extends Barang {
    Laptop(String merk, int stok) {
        super(merk, stok);
    }

    @Override
    public void tambahStok(int jumlah) {
        super.tambahStok(jumlah);
        super.infoRestock(jumlah);
        System.out.println("Stok sekarang: " + getStok());
    }

    @Override
    public void kurangiStok(int jumlah) {
        if (jumlah > getStok()) {
            System.out.println("Stok tidak mencukupi.");
            return;
        }
        super.kurangiStok(jumlah);
        super.infoBeli(jumlah);
        System.out.println("Stok tersisa: " + getStok());
    }
}