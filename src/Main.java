public class Main {
    public static void main(String[] args) {
        String bitSeries = "1010001101";
        String divisor = "110101";
        Message message = new Message(bitSeries);
        message.transmit(divisor);
        message.receive(divisor);
        System.out.println(message);

//        Message message = new Message("000000110101110");
//        System.out.println(message.calculateModulo2Division(divisor));
    }
}
