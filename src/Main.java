public class Main {
    public static void main(String[] args) {
        String bitSeries = "1010001101";
        String divisor = "110101";
        double bitErrorRate = 1e-3;

        for (int i = 0; i < 1000000; i++) {
            Message message = new Message(10);
            message.transmit(divisor, bitErrorRate);
            FileManager.appendToDataFile(message, !message.receive(divisor));
            System.out.println(message);
        }
    }
}
