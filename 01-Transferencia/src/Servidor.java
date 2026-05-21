import java.net.Socket;

public class Servidor {
    public static final int PORT = 9999;
    public static final String HOST = "localhost";

    private Socket serverSocket;
    
    public Socket connectar() {
        try {
            serverSocket = new Socket(HOST, PORT);
        } catch(Exception exception) {
            System.out.println(exception.getMessage());
        }

        return serverSocket;
    }

    public void tancarConnexio(Socket socket) {
        try {
            socket.close();
        } catch(Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void enviarFitxers() {
        
    }
}