public class Main {
    public static void main(String[] args) {
        String bitSeries = "1010001101";
        String divisor = "110101";
//        Message message = new Message(bitSeries);
        Message message = new Message(10);

        message.transmit(divisor, 1e-3);
        message.receive(divisor);
        System.out.println(message);
    }
}
