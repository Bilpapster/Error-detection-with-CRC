/**
 * A class that represents a bit sequence in our system. In fact, the class is a
 * StringBuilder with some special methods and is implemented using composition.
 *
 * @author Vasileios Papastergios
 */
public class BitSequence {
    private StringBuilder sequence = new StringBuilder();

    /**
     * Constructs a randomly generated bit sequence with the given number of bits.
     *
     * @param numberOfBits the number of bits in the sequence.
     */
    public BitSequence(int numberOfBits) {
        for (int character = 0; character < numberOfBits; character++) {
            this.sequence.append(RandomEngine.getInstance().nextBoolean() ? '1' : '0');
        }
    }

    /**
     * Constructs a bit sequence with the given string representation.
     *
     * @param bitSeriesString the string representation of the sequence.
     */
    public BitSequence(String bitSeriesString) {
        this.sequence = new StringBuilder(bitSeriesString);
    }

    /**
     * Utility getter for the length attribute (number of bits in sequence).
     *
     * @return the number of bits in the sequence.
     */
    public int getLength() {
        return sequence.length();
    }

    /**
     * Checks whether the current sequence represents the number 0 (binary).
     *
     * @return true if the current sequence is equal to number zero, else false.
     */
    public boolean isZero() {
        if (this.sequence.length() == 0) return true;

        for (int index = 0; index < this.sequence.length(); index++) {
            if (this.sequence.charAt(index) != '0') return false;
        }
        return true;
    }

    /**
     * Appends the string representation of the char argument to this sequence.
     * The argument is appended to the contents of this sequence. The length of this
     * sequence increases by 1.
     *
     * @param characterToAppend the character to append to the current sequence.
     */
    public void append(char characterToAppend) {
        this.sequence.append(characterToAppend);
    }


    /**
     * Appends the given number of zeros ('0') at the end of the current sequence.
     *
     * @param numberOfZeros the number of zeros to append to the current sequence.
     */
    public void appendZeros(int numberOfZeros) {
        this.sequence.append("0".repeat(Math.max(0, numberOfZeros)));
    }

    /**
     * Returns a substring of the current sequence in the range [start, end).
     *
     * @param start the starting index (included in the returned String)
     * @param end   the ending index (NOT included in the returned String)
     * @return the substring in range [start, end) of the current sequence.
     */
    public String subString(int start, int end) {
        return this.sequence.substring(start, end);
    }

    /**
     * Checks whether the current sequence starts with the given character.
     *
     * @param character the character to check whether the current sequence
     *                  starts with.
     * @return true if the sequence starts with the given character, else false.
     */
    public boolean startsWith(char character) {
        return this.sequence.charAt(0) == character;
    }


    /**
     * Returns the {@code char} value in this sequence at the specified index.
     * The first char value is at index 0, the next at index 1, and so
     * on, as in array indexing.
     *
     * @param position the index of the desired {@code char} value.
     * @return the {@code char} value at the specified index.
     */
    public char charAt(int position) {
        return sequence.charAt(position);
    }

    /**
     * Sets the size of the sequence to the given one, in case the new size is
     * greater than or equal to the current. Size increase is performed by adding
     * leading appropriate number of leading zeros to the sequence.
     *
     * @param newSize the new, desired size of the sequence.
     * @return the current sequence.
     */
    public BitSequence setSeriesSize(int newSize) {
        if (newSize < sequence.length()) return this;

        int numberOfZerosToInsert = newSize - sequence.length();
        for (int leadingZero = 0; leadingZero < numberOfZerosToInsert; leadingZero++) {
            sequence.insert(0, '0');
        }
        return this;
    }

    /**
     * Performs the operation of addition, during which the borrows that occur
     * are ignored. In fact, the operation is the XOR one, between the current
     * and the given binary number (the first in the form of a bit sequence and
     * the second as a String). The result is stored to the current object (the
     * bit series is altered).
     *
     * @param binaryNumber the String representation of the binary number to XOR
     *                     with the current one.
     */
    public void XOR(String binaryNumber) {
        int thisIndex = sequence.length() - 1;
        int otherIndex = binaryNumber.length() - 1;

        /*
         * '1' XOR '1' => '0'
         * '1' XOR '0' => '1'
         * '0' XOR '1' => '1'
         * '0' XOR '0' => '0'
         * So, if left == right => '0', else '1'
         */
        while (thisIndex >= 0 && otherIndex >= 0) {
            if (this.sequence.charAt(thisIndex) == binaryNumber.charAt(otherIndex)) {
                this.sequence.setCharAt(thisIndex, '0');
            } else {
                this.sequence.setCharAt(thisIndex, '1');
            }
            thisIndex--;
            otherIndex--;
        }
    }

    /**
     * Performs the operation of addition, during which the borrows that occur
     * are ignored. In fact, the operation is the XOR one, between the current
     * and the given binary number (both in the form of a bit sequence).
     * The result is stored to the current object (the bit series is altered).
     *
     * @param bitSequence the bit sequence representation of the binary number to
     *                    XOR with the current one.
     */
    public void XOR(BitSequence bitSequence) {
        XOR(bitSequence.toString());
    }

    /**
     * Simulates the distortion that can occur on a bit sequence during its
     * transmission via a data channel. The probability of a bit in the
     * sequence to be distorted is equal to the given bit error rate.
     * The bit error rate must belong to the range [0, 1].
     *
     * @param bitErrorRate the bit error rate, in range [0, 1].
     * @return true if at least one bit of the sequence has been distorted
     * else false.
     */
    public boolean distort(double bitErrorRate) {
        boolean seriesDistorted = false;

        /* parses through the bits in the sequence and toggles them, with the given probability */
        for (int bitPosition = 0; bitPosition < sequence.length(); bitPosition++) {
            if (RandomEngine.getInstance().nextFloat() < bitErrorRate) {
                toggleBitAt(bitPosition);
                seriesDistorted = true;  // even one and only bit toggle is enough to set the message distorted
            }
        }
        return seriesDistorted;
    }

    /**
     * Toggles the bit of the sequence that is in the desired position.
     *
     * @param position the bit position of the sequence to toggle.
     */
    public void toggleBitAt(int position) {
        if (sequence.charAt(position) == '1') {
            sequence.setCharAt(position, '0');
            return;
        }
        sequence.setCharAt(position, '1');
    }

    /**
     * Trims unnecessary zeros ('0') at the start of the sequence.
     *
     * @return the current sequence (after trimming).
     */
    public BitSequence trimLeadingZeros() {
        if (sequence.length() == 0) return this;
        if (!(sequence.charAt(0) == '0')) return this;

        sequence.deleteCharAt(0);
        return trimLeadingZeros();
    }

    /**
     * Overridden toString method.
     *
     * @return the string representation of the sequence.
     */
    @Override
    public String toString() {
        return sequence.toString();
    }
}
