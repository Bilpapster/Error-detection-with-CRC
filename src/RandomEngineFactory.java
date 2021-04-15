import java.util.Random;

public class RandomEngineFactory {
    private static final Random SINGLE_ENGINE = new Random();

    private RandomEngineFactory() { }

    public static Random getEngine() {
        return SINGLE_ENGINE;
    }
}
