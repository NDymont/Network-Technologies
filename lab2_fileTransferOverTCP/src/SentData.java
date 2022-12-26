import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SentData {
    private String nameFile;
    private long sizeFile;
    private File file;
    private Socket socket;

    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public SentData(String nameFile) {
        file = new File(nameFile);
        this.nameFile = file.getName();
        sizeFile = file.length();
    }

    public void sendFile(Socket socket) {
        this.socket = socket;
        try {
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            sendNameFile();
            sendSizeFile();
            sendContentFile();
            receiveResponseFromServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void receiveResponseFromServer() throws IOException {
        byte[] buf = new byte[40];
        inputStream.read(buf);
        String message = new String(buf, StandardCharsets.UTF_8);
        System.out.println("ResponseFromServer : " + message);
    }

    private void sendNameFile() throws IOException {
        outputStream.write(nameFile.length());
        outputStream.flush();
        outputStream.write(nameFile.getBytes());
        outputStream.flush();
    }

    private void sendSizeFile() throws IOException {
        outputStream.writeLong(sizeFile);
    }

    private void sendContentFile() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        int bytes = 0;
        byte[] buffer = new byte[4 * 1024];
        while ((bytes = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytes);
            outputStream.flush();
        }
        fileInputStream.close();
    }
}
