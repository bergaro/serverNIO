import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ServerNIO {
    private static ServerNIO instance;

    private ServerNIO() {}

    protected static ServerNIO getInstance() {
        if(instance == null) {
            instance = new ServerNIO();
        }
        return instance;
    }

    protected void doWork() throws IOException {
        String hostname = "127.0.0.1";
        int port = 24001;
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(hostname, port));
        System.out.println("Server start");
        mainL:
        while (true) {
            try(SocketChannel socketChannel = serverChannel.accept()) {
                ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
                while (socketChannel.isConnected()) {
                    int bytesCount = socketChannel.read(inputBuffer);
                    if (bytesCount == -1) break;
                    String msg = new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
                    inputBuffer.clear();
                    if(msg.equals("end")) {
                        break mainL;
                    }
                    socketChannel.write(ByteBuffer.wrap((msg.replaceAll(" ", "")).getBytes(StandardCharsets.UTF_8)));
                }
            } catch (IOException err) {
                System.out.println(err.getMessage());
            }
        }
        System.out.println("Server stopped.");
    }

}
