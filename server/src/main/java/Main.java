public class Main {
    public static void main(String[] args) {
        ServerNIO serverNIO = ServerNIO.getInstance();
        try {
            serverNIO.doWork();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
