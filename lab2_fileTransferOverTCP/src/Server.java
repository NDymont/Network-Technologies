import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Not enough parameters\nRequired parameters : server port number");
        }
        ServerSocket server;
        try {
            server = new ServerSocket(Integer.parseInt(args[0]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        System.out.println("SERVER STARTED");
        System.out.println("------------------------------------------------------------------");
        try {
            while (true) {
                Socket socket = server.accept();
                try {
                    threadPool.submit(new ReceivedData(socket));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    socket.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("------------------------------------------------------------------");
            System.out.println("SERVER STOPPED");
            threadPool.shutdown();
            try {
                server.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
