public class Message {

    private final BitSequence bitSequence;
    private boolean errorDuringTransmission = false;

    public Message(String bitSeriesString) {
        bitSequence = new BitSequence(bitSeriesString);
    }

    public Message(int numberOfBits) {
        bitSequence = new BitSequence(numberOfBits);
    }

    public boolean transmit(String divisor, double bitErrorRate) {
        if (!checkCRCDivisorValidity(divisor)) return false;

        int numberOfFcsBits = divisor.length() - 1;
        bitSequence.appendZeros(numberOfFcsBits);
        BitSequence frameCheckSequence = calculateModulo2Division(divisor);
        bitSequence.addWithoutBorrow(frameCheckSequence.setSeriesSize(numberOfFcsBits));
        errorDuringTransmission = bitSequence.distort(bitErrorRate);
        return true;
    }

    private boolean checkCRCDivisorValidity(String divisor) {
        if (!divisor.startsWith("1")) return false;
        return divisor.endsWith("1");
    }

    public boolean receive(String divisor) {
        return calculateModulo2Division(divisor).isZero();
    }

    public BitSequence calculateModulo2Division(String divisor) {
        int seriesIndex = divisor.length();
        BitSequence remainder = new BitSequence(bitSequence.subString(0, seriesIndex));

        while (seriesIndex < this.bitSequence.getLength()) {
            if (remainder.startsWith('0')) {
                remainder.trimLeadingZeros();
                int numberOfMissingBits = divisor.length() - remainder.getLength();
                for (int missingBit = 0; missingBit < numberOfMissingBits; missingBit++) {
                    remainder.append(bitSequence.charAt(seriesIndex++));
                    if (seriesIndex >= bitSequence.getLength()) return remainder;
                }
            }
            remainder.addWithoutBorrow(divisor);
        }
        return remainder;
    }

    public String getSequenceString() {
        return bitSequence.toString();
    }

    public boolean hasError() {
        return errorDuringTransmission;
    }

    @Override
    public String toString() {
        return bitSequence.toString() + ": " + ((errorDuringTransmission) ? "Distorted" : "Not distorted");
    }
}
