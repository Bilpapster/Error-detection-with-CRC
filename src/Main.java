public class Main {
    public static void main(String[] args) {

        BitSeries bitSeries = new BitSeries("101010");
        System.out.println(bitSeries);
        bitSeries.setSeries("0101011");
        System.out.println(bitSeries);
        bitSeries.setSeries("111");
        System.out.println(bitSeries);
        bitSeries.setSeries("0000000000");
        System.out.println(bitSeries);


//        System.out.println('1' > '0');

//        Message message = new Message(10);
//        System.out.println(message.decimalToBinary(14));

//        for (int i = 0; i < 100; i++) {
//            Message message = new Message(10);
//            String p = "110101";
//            message.transmit(p);
//            message.distort(1e-3);
//            System.out.println(message);
//        }

//        System.out.println("Final message " + message);
//        System.out.println(message.addLeadingZeros("1110", 10));
    }
}
