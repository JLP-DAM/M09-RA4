import java.io.File;
import java.nio.file.Files;

public class Fitxer {

    private String nom;
    private byte[] contingut;

    public Fitxer(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public byte[] getContingut() {
        File file = new File(nom);

        if (!file.exists()) {
            System.out.println("El fitxer no existeix.");
            return null;
        }

        try {
            contingut = Files.readAllBytes(file.toPath());
            return contingut;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}