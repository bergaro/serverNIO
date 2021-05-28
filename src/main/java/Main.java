public class Main {
    public static void main(String[] args) {
        ClientNIO clientNIO = new ClientNIO();
        try {
            clientNIO.doWork();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
