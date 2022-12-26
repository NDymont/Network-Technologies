import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


class ReceivedData implements Runnable {

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    private String nameFile;
    private long sizeFile;
    private File file;
    private long transmissionTimeStart;
    private long transmissionTimeFinish;
    private long prevSize;
    private long prevTime;

    private long acceptedSize = 0;

    private static final int MILISECONDS_IN_SECOND = 1000;
    private static final int BYTES_IN_MEGABYTE = 1048576;

    public ReceivedData(Socket socket) throws IOException {
        this.socket = socket;
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
    }

    private void countSpeed() {
        long currTime = System.currentTimeMillis();
        long currSize = acceptedSize;
        double averageSpeed = (double) currSize * MILISECONDS_IN_SECOND / BYTES_IN_MEGABYTE / (currTime - transmissionTimeStart);
        double instantSpeed = (double) (currSize - prevSize) * MILISECONDS_IN_SECOND / BYTES_IN_MEGABYTE / (currTime - prevTime);
        System.out.println("Client: [" + socket.getPort() + "]\ninstant  speed : " + instantSpeed +
                " MB/s\naverage speed : " + averageSpeed + " MB/s");
        prevSize = currSize;
        prevTime = currTime;
        System.out.println("------------------------------------------------------------------");
    }

    @Override
    public void run() {
        System.out.println("Client [" + socket.getPort() + "] connected");
        System.out.println("------------------------------------------------------------------");
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
        scheduledThreadPool.scheduleAtFixedRate(this::countSpeed, 1, 3, TimeUnit.SECONDS);
        acceptFile();
        scheduledThreadPool.shutdown();

    }

    private void sendRespondToClient() throws IOException {
        String message;
        if (sizeFile == acceptedSize) {
            message = "The file was successfully received";
        } else {
            message = "File transfer failed";
        }
        outputStream.write(message.getBytes());
        outputStream.flush();
    }

    private void acceptFile() {
        transmissionTimeStart = System.currentTimeMillis();
        prevTime = transmissionTimeStart;
        prevSize = 0;
        try {
            acceptNameFile();
            acceptSizeFile();
            acceptFileContent();
            transmissionTimeFinish = System.currentTimeMillis();
            sendRespondToClient();
            System.out.println("receiving the file from the sender [" + socket.getPort() + "] has been completed");
            System.out.println("time taken: " + (double)(transmissionTimeFinish - transmissionTimeStart) / MILISECONDS_IN_SECOND + " sec");
            System.out.println("------------------------------------------------------------------");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void acceptNameFile() throws IOException {
        int lengthNameFile = inputStream.read();
        byte[] buf = new byte[lengthNameFile];
        inputStream.read(buf);
        nameFile = new String(buf, StandardCharsets.UTF_8);
    }

    private void acceptSizeFile() throws IOException {
        sizeFile = inputStream.readLong();
    }

    private void acceptFileContent() throws IOException {

        int bytes = 0;
        file = new File("src\\uploads\\" + nameFile);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        long size = sizeFile;
        byte[] buffer = new byte[4 * 1024];
        while (acceptedSize < sizeFile && (bytes = inputStream.read(
                buffer, 0, (int) Math.min(buffer.length, sizeFile - acceptedSize))) != -1) {
            fileOutputStream.write(buffer, 0, bytes);
            acceptedSize += bytes;
        }
        fileOutputStream.close();
    }
}