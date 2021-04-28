public class TransmissionSimulator {

    /* true: data gathering (to file), false: random message execution (to console) */
    private static final boolean dataGathering = false;

    private static final int numberOfMessageBits = 10;   // number k (decimal), according to the wording
    private static final String CRCDivisor = "110101";   // number P (binary), according to the wording
    private static final double bitErrorRate = 1e-3;     // number E (decimal, 0 <= E <= 1), according to the wording

    public static void main(String[] args) {
        if (dataGathering) runDataGathering();
        else runASingleRandomTransmission();
    }

    /**
     * A simple method to simulate a single transmission of a randomly generated message.
     * Prints informative messages to console.
     * <p>
     * After the message construction, CRC is executed on it. As a result of this procedure,
     * a sequence called Frame Check Sequence (FCS) is appended to the original message.
     * Afterwards, the message is transmitted via a channel. The channel has a specific
     * bit error rate, which means that the message may have been distorted when it reaches
     * the receiver end of the channel.
     * <p>
     * At the receiver end, CRC is executed again, in order to check whether the message
     * has been transmitted intact, or it has been distorted. In case distortion has occurred,
     * the message needs to be re-transmitted and re-checked, until it reaches the receiver
     * with no errors detected.
     */
    private static void runASingleRandomTransmission() {
        /* constructs a random original message */
        Message message = new Message(numberOfMessageBits);
        System.out.println("Message to transmit: " + message.getSequenceString());

        /* simulates the CRC execution and FCS appendix */
        message.appendFCS(CRCDivisor);
        System.out.println("After FCS appendix : " + message.getSequenceString());

        /* simulates the transmission via a noisy channel */
        message.transmit(bitErrorRate);
        System.out.println("After transmission : " + message.toString());

        /* simulates the CRC check at the receiver end */
        System.out.println((message.receive(CRCDivisor)) ?
                "No error detected. Transmission was successful." :
                "Error(s) Detected. Re-transmission is needed.");
    }

    /**
     * A driver method in order to gather raw data for statistic analysis on the execution
     * of the system.
     * <p>
     * Repeats the transmission procedure for 20M times, writing the results to a data file.
     * The information stored for each one of the 20M randomly generated messages is:
     * - the transmitted bit series
     * - error occurrence during transmission (boolean - at least one error)
     * - error detection at the receiver end (boolean)
     */
    private static void runDataGathering() {
        for (int i = 0; i < 20_000_000; i++) {
            Message message = new Message(numberOfMessageBits);
            message.appendFCS(CRCDivisor);
            message.transmit(bitErrorRate);
            FileManager.appendToDataFile(message, !message.receive(CRCDivisor));
        }
    }
}
