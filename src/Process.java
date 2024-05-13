public class Process {
    String ad;
    String pid;
    int baslangic;
    int bitis;
    int kodKB;
    int heapKB;
    int stackKB;
    int globalKB;
    int baslangicAdresi = -2;
    int bitisAdresi = -1;

    public Process(String ad, String pid, int baslangic, int bitis, int kodKB, int heapKB, int stackKB,
            int globalKB) {
        this.ad = ad;
        this.pid = pid;
        this.baslangic = baslangic;
        this.bitis = bitis;
        this.kodKB = kodKB;
        this.heapKB = heapKB;
        this.stackKB = stackKB;
        this.globalKB = globalKB;
    }

    @Override
    public String toString() {
        return "Process{" +
                "ad='" + ad + '\'' +
                ", pid='" + pid + '\'' +
                ", baslangic=" + baslangic +
                ", bitis=" + bitis +
                ", kodKB=" + kodKB +
                ", heapKB=" + heapKB +
                ", stackKB=" + stackKB +
                ", globalKB=" + globalKB +
                '}';
    }

}
