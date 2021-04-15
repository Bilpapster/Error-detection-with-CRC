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

        this.bitSeries.appendZeros(numberOfFcsBits);
        BitSeries frameCheckSequence = calculateModulo2Division(p);

        this.bitSeries.addWithoutBorrow(frameCheckSequence.setSeriesSize(numberOfFcsBits).toString());

        System.out.println("Before transmission: " + bitSeries);
        boolean flag = bitSeries.distort(0.01);
        System.out.println("After  transmission: " + bitSeries + " " + flag);
        return flag;
    }

    public void receive(String p) {
        BitSeries remainder = calculateModulo2Division(p);
        System.out.println(remainder);
        System.out.println(remainder.isZero());
    }

    public BitSeries calculateModulo2Division(String divisor) {
        int seriesIndex = divisor.length(), numberOfFcsBits = divisor.length() - 1;
        BitSeries currentWord = new BitSeries(bitSeries.subString(0, seriesIndex));

        while (seriesIndex < this.bitSeries.getLength()) {
            if (currentWord.startsWith('0')) {
                currentWord.trimLeadingZeros();
                int numberOfMissingBits = divisor.length() - currentWord.getLength();
                for (int missingBit = 0; missingBit < numberOfMissingBits; missingBit++) {
                    currentWord.append(bitSeries.charAt(seriesIndex++));
                    if (seriesIndex >= bitSeries.getLength()) return currentWord;
                }
            }
            currentWord.addWithoutBorrow(divisor);
//            System.out.println(currentWord);
        }
        return currentWord;
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
