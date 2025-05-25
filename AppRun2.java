import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Scanner;

public class AppRun2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan jumlah thread : ");
        int jumlahThread = scanner.nextInt();
        System.out.print("Masukkan jumlah aktivitas Toko Online : ");
        int jumlahAktivitas = scanner.nextInt();

        ExecutorService executor = Executors.newFixedThreadPool(jumlahThread);

        for (int i = 1; i <= jumlahAktivitas; i++) {
            String barang = new Random().nextBoolean() ? "Laptop" : "Keyboard";
            
            if (i % 2 == 0) {
                executor.execute(() -> {
                    Inventori.tambahStok(barang, new Random().nextInt(7) + 1);
                });
            } else {
                executor.execute(() -> {
                    Inventori.kurangiStok(barang, new Random().nextInt(8) + 1);
                });
            }
        }


        executor.shutdown();

        while (!executor.isTerminated()) {}
        
        System.out.println("Stok akhir Laptop: " + Inventori.laptop.getStok());
        System.out.println("Stok akhir Keyboard: " + Inventori.keyboard.getStok());
    }
}

class Inventori {
    static Laptop laptop = new Laptop("Asus", 10);
    static Keyboard keyboard = new Keyboard("Logitech", 5);

    static synchronized void tambahStok(String namaBarang, int jumlah) {
        namaBarang = namaBarang.toLowerCase();
        String threadName = Thread.currentThread().getName().substring(7);

        try {
            Thread.sleep(450);
        } catch(InterruptedException e) {
            System.out.println("Kesalahan saat sistem restock: " + e.getMessage());
        }
        
        System.out.print(threadName + " - ");
        if (namaBarang.equals("laptop")) {
            laptop.tambahStok(jumlah);
        } else if (namaBarang.equals("keyboard")) {
            keyboard.tambahStok(jumlah);
        }
    }
    
    static synchronized void kurangiStok(String namaBarang, int jumlah) {
        namaBarang = namaBarang.toLowerCase();
        String threadName = Thread.currentThread().getName().substring(7);

        try {
            Thread.sleep(250);
        } catch(InterruptedException e) {
            System.out.println("Kesalahan saat sistem beli: " + e.getMessage());
        }
        
        System.out.print(threadName + " - ");

        if (namaBarang.equals("laptop")) {
            laptop.kurangiStok(jumlah);
        }else if (namaBarang.equals("keyboard")){
            keyboard.kurangiStok(jumlah);
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
        System.out.print("Berhasil beli " + jumlah + " ");
    }

    protected void infoRestock(int jumlah) {
        System.out.print("Restock " + jumlah + " ");
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
        System.out.print("Laptop " + this.merk + ". ");
        System.out.println("Stok sekarang: " + getStok());
    }
    
    @Override
    public void kurangiStok(int jumlah) {
        if (jumlah > getStok()) {
            System.out.println("Gagal, membeli " + jumlah + " laptop. Stok tidak mencukupi.");
            return;
        }
        super.kurangiStok(jumlah);
        super.infoBeli(jumlah);
        System.out.print("Laptop " + this.merk + ". ");
        System.out.println("Stok tersisa: " + getStok());
    }
}

class Keyboard extends Barang {
    Keyboard(String merk, int stok) {
        super(merk, stok);
    }
    @Override
    public void tambahStok(int jumlah) {
        super.tambahStok(jumlah);
        super.infoRestock(jumlah);
        System.out.print("Keyboard " + this.merk + ". ");
        System.out.println("Stok sekarang: " + getStok());
    }
    
    @Override
    public void kurangiStok(int jumlah) {
        if (jumlah > getStok()) {
            System.out.println("Gagal, membeli " + jumlah + " keyboard. Stok tidak mencukupi.");
            return;
        }
        super.kurangiStok(jumlah);
        super.infoBeli(jumlah);
        System.out.print("Keyboard " + this.merk + ". ");
        System.out.println("Stok tersisa: " + getStok());
    }
}
