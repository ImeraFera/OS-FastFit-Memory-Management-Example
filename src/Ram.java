import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Ram {
    Map<Integer, Process> RAM = new LinkedHashMap<>();
    List<Process> processListesi = new ArrayList<>();
    List<BosAlan> ramdekiBosAlanlar = new LinkedList<>();
    List<PCB> pcbler = new ArrayList<>();

    public Ram() {
        Process isletimSistemi = new Process("İşletim Sistemi", "0", 0,
                100000, 300, 200, 150, 350);
        isletimSistemi.baslangicAdresi = 0;
        isletimSistemi.bitisAdresi = (isletimSistemi.globalKB + isletimSistemi.stackKB + isletimSistemi.heapKB
                + isletimSistemi.kodKB) * 1024 - 1;
        RAM.put(0, isletimSistemi);

        for (int i = 1; i < 10; i++) {
            RAM.put(i, null);
        }

    }

    public List<String> DosyaOku() throws FileNotFoundException {

        try {
            System.out.println("girdi.txt dosyası okundu.");
            File file = new File("girdi.txt");
            Scanner scanner = new Scanner(file);
            List<String> processBilgileri = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String processBilgisi = scanner.nextLine();
                processBilgileri.add(processBilgisi);
            }
            return processBilgileri;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void RamdenProcessSil(Process processBilgisi) {
        Process oncekiProcessBilgisi = new Process(null, null, 0, 0, 0, 0,
                0, 0);
        for (Map.Entry<Integer, Process> entry : RAM.entrySet()) {
            Integer key = entry.getKey();
            Process process = entry.getValue();
            if (process == processBilgisi) {
                int boyut = process.globalKB + process.kodKB + process.heapKB + process.stackKB;
                int index = key;
                int baslangicAdresi = oncekiProcessBilgisi.globalKB + oncekiProcessBilgisi.kodKB
                        + oncekiProcessBilgisi.heapKB + oncekiProcessBilgisi.stackKB * 1024 + 1;
                int bitisAdresi = boyut * 1024;
                BosAlan bosAlan = new BosAlan(index, boyut);
                bosAlan.baslangicAdresi = processBilgisi.baslangicAdresi;
                bosAlan.bitisAdresi = processBilgisi.bitisAdresi;
                ramdekiBosAlanlar.add(bosAlan);
                process.baslangicAdresi = -2;
                process.bitisAdresi = -1;
                for (PCB pcb : pcbler) {
                    if (pcb.process.pid.equals(process.pid)) {
                        pcbler.remove(pcb);
                        break;
                    }
                }
                RAM.put(index, null);

                return;
            } else {
                oncekiProcessBilgisi = process;
            }
        }

    }

    public void UygunYereYerlestir(Process processBilgisi) {
        Process oncekiProcessBilgileri = new Process(null, null, 0, 0, 0, 0,
                0, 0);
        for (Map.Entry<Integer, Process> entry : RAM.entrySet()) {
            Integer key = entry.getKey();
            Process process = entry.getValue();
            int processBoyutu = processBilgisi.globalKB + processBilgisi.heapKB + processBilgisi.stackKB
                    + processBilgisi.kodKB;
            if (process != null) {
                oncekiProcessBilgileri = process;
                continue;
            } else {
                if (ramdekiBosAlanlar.size() == 0) {
                    processBilgisi.baslangicAdresi = oncekiProcessBilgileri.bitisAdresi + 1;
                    processBilgisi.bitisAdresi = processBilgisi.baslangicAdresi + processBoyutu * 1024 - 1;
                    RAM.put(key, processBilgisi);
                    pcbler.add(new PCB(processBilgisi));
                    return;
                } else {
                    for (int i = 0; i < ramdekiBosAlanlar.size(); i++) {
                        if (ramdekiBosAlanlar.get(i).boyut >= processBoyutu) {
                            int boyut = processBilgisi.globalKB + processBilgisi.heapKB + processBilgisi.kodKB
                                    + processBilgisi.stackKB;
                            processBilgisi.baslangicAdresi = ramdekiBosAlanlar.get(i).baslangicAdresi;
                            processBilgisi.bitisAdresi = processBilgisi.baslangicAdresi + boyut * 1024 - 1;

                            RAM.put(ramdekiBosAlanlar.get(i).index, processBilgisi);
                            ramdekiBosAlanlar.add(new BosAlan(i, ramdekiBosAlanlar.get(i).boyut - boyut));
                            ramdekiBosAlanlar.remove(i);
                            pcbler.add(new PCB(processBilgisi));

                            return;
                        }
                    }
                }
            }
        }

    }

    public void PcbDurumuYazdir(String processinIsmi, int pcbZamani) {
        for (int i = 0; i < pcbler.size(); i++) {
            if (pcbler.get(i).process.ad.equalsIgnoreCase(processinIsmi)) {

                System.out.println(
                        pcbler.get(i).toString(pcbZamani));

            }

        }
    }

    public void RamDurumuYazdir(int ramDurumZamani) {
        String pcbsiBulunanProcessler = "";
        for (Map.Entry<Integer, Process> entry : RAM.entrySet()) {
            Integer key = entry.getKey();
            Process process = entry.getValue();
            if (process != null) {
                System.out.println(process.baslangicAdresi + " ve " + process.bitisAdresi + " Adresleri arasında "
                        + process.ad + " bulunmaktadır.");
                pcbsiBulunanProcessler += process.ad + "-";
            }
        }
        System.out.println("PCB'leri bulunan processler : ");

        for (int i = 0; i < pcbsiBulunanProcessler.split("-").length; i++) {
            if (!pcbsiBulunanProcessler.split("-")[i].equals("İşletim Sistemi")) {

                System.out.print(pcbsiBulunanProcessler.split("-")[i] + " ");

            }

        }

    }

    public void ProcessleriRameEkle(int ramDurumuSuresi) {

        for (int i = 0; i <= ramDurumuSuresi; i++) {

            for (Process processBilgileri : processListesi) {
                if (i == processBilgileri.baslangic) {
                    UygunYereYerlestir(processBilgileri);
                }
                if (i == processBilgileri.bitis) {
                    RamdenProcessSil(processBilgileri);
                }
            }

        }
    }

    public void ProcessleriOlusturVeListeyeEkle() throws FileNotFoundException {

        List<String> processBilgileri = DosyaOku();
        for (String processBilgisi : processBilgileri) {
            String[] processDizi = processBilgisi.split(" ");
            Random r = new Random();
            String pid = String.valueOf(r.nextInt(1000, 9999));

            Process process = new Process(processDizi[0], pid,
                    Integer.parseInt(processDizi[1]), Integer.parseInt(processDizi[2]),
                    Integer.parseInt(processDizi[3]), Integer.parseInt(processDizi[5]),
                    Integer.parseInt(processDizi[4]), Integer.parseInt(processDizi[6]));
            processListesi.add(process);
        }

    }

}
