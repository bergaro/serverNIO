import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientNIO {

    protected void doWork() throws IOException {
        String hostname = "127.0.0.1";
        int port = 24001;
        InetSocketAddress socketAddress = new InetSocketAddress(hostname, port);
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(socketAddress);
        try (Scanner scanner = new Scanner(System.in)) {
            ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
            String msg;while (true) {
                System.out.println("Please enter your message with spaces");
                msg = scanner.nextLine();
                socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
                if ("end".equals(msg)) break;
                int bytesCount = socketChannel.read(inputBuffer);
                System.out.println(new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8).trim());
                inputBuffer.clear();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            socketChannel.close();
        }
    }
}
