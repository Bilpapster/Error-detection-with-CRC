public class BitSeries {
    private StringBuilder series;

    public BitSeries(int numberOfBits) {
//        this.series = new StringBuilder("1");
        for (int character = 0; character < numberOfBits; character++) {
            this.series.append(RandomEngineFactory.getEngine().nextBoolean() ? '1' : '0');
        }
    }

    public BitSeries(String bitSeriesString) {
        this.series = new StringBuilder(bitSeriesString);
    }

    public int getLength() {
        return series.length();
    }

    public boolean isZero() {
        if (this.series.length() == 0) return true;

        for (int index = 0; index < this.series.length(); index++) {
            if (this.series.charAt(index) != '0') return false;
        }
        return true;
    }

    public BitSeries append(char characterToAppend) {
        this.series.append(characterToAppend);
        return this;
    }

    public BitSeries append(String stringToAppend) {
        this.series.append(stringToAppend);
        return this;
    }

    public BitSeries appendZeros(int numberOfZeros) {
        for (int zero = 0; zero < numberOfZeros; zero++) this.series.append('0');
        return this;
    }

    public boolean transmit(String p) {
        int numberOfFcsBits = p.length() - 1;

        if (!p.startsWith("1")) return false;
        if (!p.endsWith("1")) return false;

        return true;
    }

    public String subString(int start, int end) {
        return this.series.substring(start, end);
    }

    public String subString(int start) {
        return this.series.substring(start);
    }

    public boolean startsWith(char character) {
        return this.series.charAt(0) == character;
    }

    public boolean endsWith(char character) {
        return this.series.charAt(series.length() - 1) == character;
    }

    public char charAt(int position) {
        return series.charAt(position);
    }

    public void setCharAt(int position, char character) {
        series.setCharAt(position, character);
    }

    public BitSeries setSeriesSize(int newSize) {
        if (newSize < series.length()) return this;

        int numberOfZerosToInsert = newSize - series.length();
        for (int leadingZero = 0; leadingZero < numberOfZerosToInsert; leadingZero++) {
            series.insert(0, '0');
        }
        return this;
    }

    public BitSeries addWithoutBorrow(String binaryNumber) {
        int thisIndex = series.length() - 1;
        int otherIndex = binaryNumber.length() - 1;

        while (thisIndex >= 0 && otherIndex >= 0) {
            if (this.series.charAt(thisIndex) == binaryNumber.charAt(otherIndex)) {
                this.series.setCharAt(thisIndex, '0');
            } else {
                this.series.setCharAt(thisIndex, '1');
            }
            thisIndex--;
            otherIndex--;
        }
        return this;
    }

    public BitSeries addWithoutBorrow(StringBuilder binaryNumber) {
        return addWithoutBorrow(binaryNumber.toString());
    }

    public boolean distort(double bitErrorRate) {
        boolean seriesDistorted = false;
        for (int bitPosition = 0; bitPosition < series.length(); bitPosition++) {
            if (RandomEngineFactory.getEngine().nextFloat() < bitErrorRate) {
                toggleBitAt(bitPosition);
                seriesDistorted = true;
            }
        }
        return seriesDistorted;
    }

    public void toggleBitAt(int position) {
        if (series.charAt(position) == '1') {
            series.setCharAt(position, '0');
            return;
        }
        series.setCharAt(position, '1');
    }

    public BitSeries setSeries(String newSeries) {
        if (this.series.length() < newSeries.length()) this.series.setLength(newSeries.length());

        int characterPosition;
        for (characterPosition = 0; characterPosition < newSeries.length(); characterPosition++) {
            this.series.setCharAt(characterPosition, newSeries.charAt(characterPosition));
        }

        while (characterPosition != this.series.length()) {
            this.series.deleteCharAt(characterPosition);
        }
        return this;
    }

    public BitSeries trimLeadingZeros() {
        if (series.length() == 0) return this;
        if (!(series.charAt(0) == '0')) return this;

        series.deleteCharAt(0);
        return trimLeadingZeros();
    }

    @Override
    public String toString() {
        return series.toString();
    }
}
