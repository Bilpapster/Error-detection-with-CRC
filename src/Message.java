public class Message {

    private final BitSeries bitSeries;
    private boolean errorDuringTransmission = false;

    public Message(String bitSeriesString) {
        this.bitSeries = new BitSeries(bitSeriesString);
    }

    public Message(int numberOfBits) {
        this.bitSeries = new BitSeries(numberOfBits);
    }

    public boolean transmit(String divisor, double bitErrorRate) {

        int numberOfFcsBits = divisor.length() - 1;
        if (!divisor.startsWith("1")) return false;
        if (!divisor.endsWith("1")) return false;

        this.bitSeries.appendZeros(numberOfFcsBits);
        BitSeries frameCheckSequence = calculateModulo2Division(divisor);

        this.bitSeries.addWithoutBorrow(frameCheckSequence.setSeriesSize(numberOfFcsBits));

        System.out.println("Before transmission: " + bitSeries);
        errorDuringTransmission = bitSeries.distort(bitErrorRate);
        return true;
    }

    public void receive(String divisor) {
        BitSeries remainder = calculateModulo2Division(divisor);
        System.out.println(remainder);
        System.out.println(remainder.isZero());
    }

    public BitSeries calculateModulo2Division(String divisor) {
        int seriesIndex = divisor.length(), numberOfFcsBits = divisor.length() - 1;
        BitSeries remainder = new BitSeries(bitSeries.subString(0, seriesIndex));

        while (seriesIndex < this.bitSeries.getLength()) {
            if (remainder.startsWith('0')) {
                remainder.trimLeadingZeros();
                int numberOfMissingBits = divisor.length() - remainder.getLength();
                for (int missingBit = 0; missingBit < numberOfMissingBits; missingBit++) {
                    remainder.append(bitSeries.charAt(seriesIndex++));
                    if (seriesIndex >= bitSeries.getLength()) return remainder;
                }
            }
            remainder.addWithoutBorrow(divisor);
        }
        return remainder;
    }

    @Override
    public String toString() {
        return bitSeries.toString() + ": " + ((errorDuringTransmission) ? "Distorted" : "Not distorted");
    }

}
