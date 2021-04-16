public class BitSequence {
    private StringBuilder sequence = new StringBuilder();

    public BitSequence(int numberOfBits) {
        for (int character = 0; character < numberOfBits; character++) {
            this.sequence.append(RandomEngineFactory.getEngine().nextBoolean() ? '1' : '0');
        }
    }

    public BitSequence(String bitSeriesString) {
        this.sequence = new StringBuilder(bitSeriesString);
    }

    public int getLength() {
        return sequence.length();
    }

    public boolean isZero() {
        if (this.sequence.length() == 0) return true;

        for (int index = 0; index < this.sequence.length(); index++) {
            if (this.sequence.charAt(index) != '0') return false;
        }
        return true;
    }

    public BitSequence append(char characterToAppend) {
        this.sequence.append(characterToAppend);
        return this;
    }

    public BitSequence appendZeros(int numberOfZeros) {
        this.sequence.append("0".repeat(Math.max(0, numberOfZeros)));
        return this;
    }

    public String subString(int start, int end) {
        return this.sequence.substring(start, end);
    }

    public boolean startsWith(char character) {
        return this.sequence.charAt(0) == character;
    }

    public char charAt(int position) {
        return sequence.charAt(position);
    }

    public BitSequence setSeriesSize(int newSize) {
        if (newSize < sequence.length()) return this;

        int numberOfZerosToInsert = newSize - sequence.length();
        for (int leadingZero = 0; leadingZero < numberOfZerosToInsert; leadingZero++) {
            sequence.insert(0, '0');
        }
        return this;
    }

    public BitSequence addWithoutBorrow(String binaryNumber) {
        int thisIndex = sequence.length() - 1;
        int otherIndex = binaryNumber.length() - 1;

        while (thisIndex >= 0 && otherIndex >= 0) {
            if (this.sequence.charAt(thisIndex) == binaryNumber.charAt(otherIndex)) {
                this.sequence.setCharAt(thisIndex, '0');
            } else {
                this.sequence.setCharAt(thisIndex, '1');
            }
            thisIndex--; otherIndex--;
        }
        return this;
    }

    public BitSequence addWithoutBorrow(BitSequence bitSequence) {
        return addWithoutBorrow(bitSequence.toString());
    }

    public boolean distort(double bitErrorRate) {
        boolean seriesDistorted = false;
        for (int bitPosition = 0; bitPosition < sequence.length(); bitPosition++) {
            if (RandomEngineFactory.getEngine().nextFloat() < bitErrorRate) {
                toggleBitAt(bitPosition);
                seriesDistorted = true;
            }
        }
        return seriesDistorted;
    }

    public void toggleBitAt(int position) {
        if (sequence.charAt(position) == '1') {
            sequence.setCharAt(position, '0');
            return;
        }
        sequence.setCharAt(position, '1');
    }

    public BitSequence trimLeadingZeros() {
        if (sequence.length() == 0) return this;
        if (!(sequence.charAt(0) == '0')) return this;

        sequence.deleteCharAt(0);
        return trimLeadingZeros();
    }

    @Override
    public String toString() {
        return sequence.toString();
    }
}
