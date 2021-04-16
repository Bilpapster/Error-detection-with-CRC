import java.util.Random;

/**
 * A very simple class to represent a a random engine in our
 * system. The class actually consists of a {@code Random}
 * object and follows the Singleton design pattern.
 *
 * @author Vasileios Papastergios
 */
public class RandomEngine {
    private static final Random SINGLE_ENGINE = new Random();

    private RandomEngine() { /* empty private constructor, according to the Singleton design pattern */ }

    public static Random getInstance() {
        return SINGLE_ENGINE;
    }
}
