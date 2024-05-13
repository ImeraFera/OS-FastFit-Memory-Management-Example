import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Ram RAM = new Ram();
        RAM.ProcessleriOlusturVeListeyeEkle();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Lütfen RAM’in durumunu görmek istediğiniz saniyeyi giriniz.");
        int ramDurumZamani = scanner.nextInt();
        System.out.println(ramDurumZamani + ". Saniyede RAM’in dolu olan kısımları:");
        RAM.ProcessleriRameEkle(ramDurumZamani);
        RAM.RamDurumuYazdir(ramDurumZamani);
        System.out.println();
        System.out.println(ramDurumZamani + ". saniyedeki PCB’sini görüntülemek istediğiniz proses ismini giriniz:");
        Scanner scannerr = new Scanner(System.in);
        String processBilgisiOgren = scannerr.nextLine();
        System.out.println(processBilgisiOgren + " isimli prosesin " + ramDurumZamani
                + ". Saniyedeki PCB bilgileri şu şekildedir:");
        RAM.PcbDurumuYazdir(processBilgisiOgren, ramDurumZamani);
    }
}
