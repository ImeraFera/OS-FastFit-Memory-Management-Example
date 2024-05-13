public class PCB {

    public String pid;
    String yartilisZamani;
    String boyut;
    Process process;

    public PCB(Process processBilgisi) {
        process = processBilgisi;
        pid = processBilgisi.pid;
        yartilisZamani = String.valueOf(processBilgisi.baslangic);
        boyut = String.valueOf(
                processBilgisi.heapKB + processBilgisi.stackKB + processBilgisi.kodKB + processBilgisi.globalKB);

    }

    public String toString(int ramDurumZamani) {
        String cikti = "Process No : " + pid + "\n" + "PRocess yaratılma zamanı: " + yartilisZamani + ". saniye\n"
                + "PRocess büyüklüğü : " + boyut + " KB";

        return cikti;
    }
}
