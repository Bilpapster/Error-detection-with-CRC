/**
 * A class that represents a message in our system. A message consists of a
 * bit sequence and can be distorted during getting transmitted via a channel.
 *
 * @author Vasileios Papastergios
 */
public class Message {

    private final BitSequence bitSequence;
    private boolean errorDuringTransmission = false;

    /**
     * Constructs a message object with the given string representation
     * of the bit sequence.
     *
     * @param bitSeriesString the string representation of the bit sequence.
     */
    public Message(String bitSeriesString) {
        bitSequence = new BitSequence(bitSeriesString);
    }

    /**
     * Constructs a message object with the given number of bits. The
     * bit sequence is generated randomly.
     *
     * @param numberOfBits the number of bits in the bit sequence.
     */
    public Message(int numberOfBits) {
        bitSequence = new BitSequence(numberOfBits);
    }

    /**
     * Transmits the current message, taking into account the given bit error
     * rate of the transmission channel. The probability that a bit of the
     * sequence is distorted is equal to this bit error rate.
     *
     * @param bitErrorRate the bit error rate of the transmission channel.
     */
    public void transmit(double bitErrorRate) {
        errorDuringTransmission = bitSequence.distort(bitErrorRate);
    }

    /**
     * Executes CRC on the current message and appends the FCS sequence at
     * the end of the current message. After the execution of this method,
     * the message is ready to be transmitted.
     * <p>
     * The method, also, executes validation check on the CRC divisor passed
     * as argument. The divisor is deemed valid if it starts and ends with '1'.
     * In case the divisor is not valid, the FCS is not appended.
     *
     * @param divisor the CRC divisor (number P, according to the wording).
     * @return true if FCS appendix is executed (divisor is valid), else false.
     */
    public boolean appendFrameCheckSequence(String divisor) {
        if (!checkCRCDivisorValidity(divisor)) return false;

        int numberOfFcsBits = divisor.length() - 1;

        /* simulates n shifts to the left, by appending n 0 at the end of the sequence */
        bitSequence.appendZeros(numberOfFcsBits);

        /* initializes the FCS as the remainder of the modulo-2 division between M and P */
        BitSequence frameCheckSequence = calculateModulo2Division(divisor);

        /* appends the FCS at the end of the original message */
        bitSequence.addWithoutBorrow(frameCheckSequence.setSeriesSize(numberOfFcsBits));
        return true;
    }

    /**
     * A simple utility private method to check the validity of the CRC
     * dicisor (number P, according to the wording).
     *
     * @param divisor the divisor to check.
     * @return true if the divisor starts and ends with '1', else false.
     */
    private boolean checkCRCDivisorValidity(String divisor) {
        if (!divisor.startsWith("1")) return false;
        return divisor.endsWith("1");
    }

    /**
     * Simulates the receiving of a message. CRC is executed and the remainder
     * is checked.
     * <p>
     * In case the remainder is equal to zero, then we get that
     * the message was not distorted during transmission, so the transmission
     * was successful.
     * <p>
     * In case the remainder is not equal to zero, then we get that
     * (at least one) error has occurred during transmission, so the message
     * needs to be re-transmitted.
     *
     * @param divisor the CRC divisor.
     * @return true if no error(s) are detected, else false.
     */
    public boolean receive(String divisor) {
        return calculateModulo2Division(divisor).isZero();
    }

    /**
     * Executes a modulo-2 division with dividend the sequence of the current message
     * and divisor the given sequence (given as String). Calculates only the remainder
     * of the division, ignoring the quotient.
     *
     * @param divisor the divisor of the modulo-2 division as String.
     * @return the remainder of the modulo-2 division.
     */
    public BitSequence calculateModulo2Division(String divisor) {

        /* represents the index to the sequence bit that is next to be used during division */
        int seriesIndex = divisor.length();

        /* represents the remainder of the division, its final value is formed dynamically (inside loop) */
        BitSequence remainder = new BitSequence(bitSequence.subString(0, seriesIndex));

        while (seriesIndex < bitSequence.getLength()) {

            /* if the currently checked word has leading zeros, they are trimmed
             * and the missing bits are appended at the end, taken from the actual sequence  */
            if (remainder.startsWith('0')) {
                remainder.trimLeadingZeros();
                int numberOfMissingBits = divisor.length() - remainder.getLength();
                for (int missingBit = 0; missingBit < numberOfMissingBits; missingBit++) {
                    remainder.append(bitSequence.charAt(seriesIndex++));
                    if (seriesIndex >= bitSequence.getLength()) return remainder;
                }
            }

            /* the remainder is updated as: remainder = remainder XOR P */
            remainder.addWithoutBorrow(divisor);
        }

        /* at the end of the while-loop, we have found the actual remainder of the division */
        return remainder;
    }

    /**
     * Simple utility getter for the bit sequence string representation.
     *
     * @return the string representation of the bit sequence of the current message.
     */
    public String getSequenceString() {
        return bitSequence.toString();
    }

    /**
     * Checks whether the current message has distortion errors.
     *
     * @return true if the current message has been distorted, else false.
     */
    public boolean hasError() {
        return errorDuringTransmission;
    }

    /**
     * Overridden toString method.
     *
     * @return the string representation of the message, as well as the status of
     * it (distorted / not distorted).
     */
    @Override
    public String toString() {
        return bitSequence.toString() + " (" + (((errorDuringTransmission) ? "distorted" : "not distorted") + ")");
    }
}
