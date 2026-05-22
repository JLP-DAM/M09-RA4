import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Client {

    public static final int PORT = 9999;
    public static final String HOST = "localhost";
    public static final String DIR_ARRIBADA = "/tmp/";

    private Socket socket;

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public void connectar() {

        try {

            System.out.println(String.format("Connectant a -> %s:%s", HOST, PORT));

            socket = new Socket(HOST, PORT);

            System.out.println("Connexio acceptada: "
                    + socket.getInetAddress());

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void rebreFitxers() {

        Scanner scanner = new Scanner(System.in);

        try {

            while (true) {

                System.out.print(
                        "Nom del fitxer a rebre ('sortir' per sortir): ");

                String nomFitxer = scanner.nextLine();

                objectOutputStream.writeObject(nomFitxer);
                objectOutputStream.flush();

                if (nomFitxer.equalsIgnoreCase("sortir")) {
                    break;
                }

                byte[] contingut = (byte[]) objectInputStream.readObject();

                if (contingut == null) {
                    System.out.println("No s'ha pogut rebre el fitxer.");
                    continue;
                }

                String nomGuardar =
                        DIR_ARRIBADA +
                        Paths.get(nomFitxer).getFileName().toString();

                Files.write(Paths.get(nomGuardar), contingut);

                System.out.println("Nom del fitxer a guardar: "
                        + nomGuardar);

                System.out.println("Fitxer rebut i guardat com: "
                        + nomGuardar);
            }

            scanner.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void tancarConnexio() {

        try {

            if (objectInputStream != null) {
                objectInputStream.close();
            }

            if (objectOutputStream != null) {
                objectOutputStream.close();
            }

            if (socket != null) {
                socket.close();
            }

            System.out.println("Connexio tancada.");

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Client client = new Client();

        client.connectar();

        client.rebreFitxers();

        client.tancarConnexio();
    }
}