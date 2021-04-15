public class Message {

    private BitSeries bitSeries;
    private boolean errorDuringTransmission = false;

    public Message(String bitSeriesString) {
        this.bitSeries = new BitSeries(bitSeriesString);
    }

    public Message(int numberOfBits) {
        this.bitSeries = new BitSeries(numberOfBits);
    }

    public boolean transmit(String p) {

        int numberOfFcsBits = p.length() - 1;

        if (!p.startsWith("1")) return false;
        if (!p.endsWith("1")) return false;


        StringBuilder currentNumber = new StringBuilder();
        int currentBitSeriesIndex = 0;
        for (int i = 0; i < numberOfFcsBits; i++) {
//            currentNumber.append(bitSeries.charAt(currentBitSeriesIndex++));
            bitSeries.append('0');
        }

//        while (currentBitSeriesIndex < bitSeries.length()) {
//            if ()


//            currentNumber.append(bitSeries.charAt(currentBitSeriesIndex++));
//        }


//        System.out.println("Initial message: " + bitSeries);

        int messageInDecimal = binaryToDecimal(bitSeries.toString() + "00000");
//        System.out.println("Message in decimal: " + messageInDecimal);

        int pInDecimal = binaryToDecimal(p);
//        System.out.println("P in decimal: " + pInDecimal);


        int modulo = messageInDecimal % pInDecimal;
//        System.out.println("Modulo in decimal: " + modulo);

//        System.out.println("Modulo in binary: " + decimalToBinary(modulo));


        this.bitSeries.append(addLeadingZeros(decimalToBinary(modulo), numberOfFcsBits));
//        System.out.println("Final message " + bitSeries);
        return true;
    }



    public int binaryToDecimal(String binaryNumber) {
        int decimalNumber = 0;
        int factor = 1;

        for (int bitPosition = binaryNumber.length() - 1; bitPosition >= 0; bitPosition--) {
            if (binaryNumber.charAt(bitPosition) == '1') decimalNumber += factor;
            factor *= 2;
        }
        return decimalNumber;
    }

    public String decimalToBinary(int decimalNumber) {
        StringBuilder binaryNumber = new StringBuilder();

        while (decimalNumber != 0) {
            if (decimalNumber % 2 == 1) binaryNumber.append('1');
            else binaryNumber.append('0');
            decimalNumber /= 2;
        }

        binaryNumber.reverse();
        return binaryNumber.toString();
    }

    public String addLeadingZeros(String binaryNumber, int newSize) {
        if (newSize < binaryNumber.length()) return binaryNumber;

        StringBuilder numberWithLeadingZeros = new StringBuilder(binaryNumber);
        for (int leadingZero = 0; leadingZero < newSize - binaryNumber.length(); leadingZero++) {
            numberWithLeadingZeros.insert(0, '0');
        }
        return numberWithLeadingZeros.toString();
    }

    @Override
    public String toString() {
        return bitSeries.toString() + ": " + ((errorDuringTransmission) ?
                "Distorted" : "Not distorted");
    }

}
