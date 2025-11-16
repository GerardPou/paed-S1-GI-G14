import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Quest {
    private String nom;
    private String assignatura;
    private LocalDate dataLliurament;
    private int tempsEstimat;
    private int dificultat;
    private int progres;
    private String pes;
    private String ubicacio;

    public Quest(String nom, String assignatura, LocalDate dataLliurament, int tempsEstimat,
                 int dificultat, int progres, String pes, String ubicacio) {
        this.nom = nom;
        this.assignatura = assignatura;
        this.dataLliurament = dataLliurament;
        this.tempsEstimat = tempsEstimat;
        this.dificultat = dificultat;
        this.progres = progres;
        this.pes = pes;
        this.ubicacio = ubicacio;
    }
    public String getNom() { return nom; }
    public String getAssignatura() { return assignatura; }
    public LocalDate getDataLliurament() { return dataLliurament; }
    public int getTempsEstimat() { return tempsEstimat; }
    public int getDificultat() { return dificultat; }
    public int getProgres() { return progres; }
    public String getPes() { return pes; }
    public String getUbicacio() { return ubicacio; }

    public void setNom(String nom) { this.nom = nom; }
    public void setAssignatura(String assignatura) { this.assignatura = assignatura; }
    public void setDataLliurament(LocalDate dataLliurament) {
        this.dataLliurament = dataLliurament;
    }
    public void setTempsEstimat(int tempsEstimat) { this.tempsEstimat = tempsEstimat; }
    public void setDificultat(int dificultat) { this.dificultat = dificultat; }
    public void setProgres(int progres) { this.progres = progres; }
    public void setPes(String pes) { this.pes = pes; }
    public void setUbicacio(String ubicacio) { this.ubicacio = ubicacio; }

    public double calcularNivellDificultat() {
        double pesImportancia;
        switch (this.pes) {
            case "#FF8000": pesImportancia = 1.5; break;
            case "#CC00FF": pesImportancia = 1.2; break;
            default: pesImportancia = 1.0;
        }

        double restant = 1 - (this.progres / 100.0);
        double nivell = (this.dificultat * 10 + this.tempsEstimat / 60.0) * restant * pesImportancia;

        return nivell;
    }

}