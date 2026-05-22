import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Servidor {

    public static final int PORT = 9999;
    public static final String HOST = "localhost";

    private ServerSocket serverSocket;
    private Socket socket;

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public Socket connectar() {
        try {
            serverSocket = new ServerSocket(PORT);

            System.out.println("Acceptant connexions en -> " + HOST + ":" + PORT);
            System.out.println("Esperant connexio...");

            socket = serverSocket.accept();

            System.out.println("Connexio acceptada: " + socket.getInetAddress());

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            return socket;

        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public void enviarFitxers() {

        try {

            while (true) {
                System.out.println("Esperant el nom del fitxer del client...");

                String nomFitxer = (String) objectInputStream.readObject();

                System.out.println("Nomfitxer rebut: " + nomFitxer);

                if (nomFitxer == null || nomFitxer.equalsIgnoreCase("sortir")) {
                    System.out.println("Nom del fitxer buit o nul. Sortint...");
                    break;
                }

                Fitxer fitxer = new Fitxer(nomFitxer);

                byte[] contingut = fitxer.getContingut();

                if (contingut == null) {
                    objectOutputStream.writeObject(null);
                    continue;
                }

                System.out.println("Contingut del fitxer a enviar: "
                        + contingut.length + " bytes");

                objectOutputStream.writeObject(contingut);
                objectOutputStream.flush();

                System.out.println("Fitxer enviat al client: " + nomFitxer);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void tancarConnexio(Socket socket) {

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

            if (serverSocket != null) {
                serverSocket.close();
            }

            System.out.println("Tancant connexió amb el client: "
                    + socket.getInetAddress());

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Servidor servidor = new Servidor();

        Socket socket = servidor.connectar();

        servidor.enviarFitxers();

        servidor.tancarConnexio(socket);
    }
}