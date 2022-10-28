import java.io.IOException;
import java.net.Socket;

public class Client {

    private static Socket clientSocket;
    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.out.println("Not enough parameters\nRequired parameters : file path, IP address of the server, server port number");
            System.exit(1);
        }
        clientSocket = new Socket(args[1], Integer.parseInt(args[2]));
        SentData data = new SentData(args[0]);
        data.sendFile(clientSocket);
    }
}