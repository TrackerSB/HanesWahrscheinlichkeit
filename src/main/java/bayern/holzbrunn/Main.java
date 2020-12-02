package bayern.holzbrunn;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Main {
    private static final long MAX_NUM_ITERATIONS = Math.round(Math.pow(10, 8));
    private static final Random RANDOM = new Random();
    private static final double INITIAL_LOWER = 0;
    private static final double INITIAL_UPPER = 1;

    private static double getUniformRandom(double lower, double upper) {
        return RANDOM.nextDouble() * upper + lower;
    }

    public static void main(String[] args) {
        Instant start = Instant.now();
        double numEvents = LongStream.range(0, MAX_NUM_ITERATIONS)
                .parallel()
                .mapToDouble(i -> {
                    double a = getUniformRandom(INITIAL_LOWER, INITIAL_UPPER);
                    double newUpper = 1 - a;
                    double b = getUniformRandom(INITIAL_LOWER, newUpper);
                    double c = 1 - (b + a);
                    List<Double> sorted = List.of(a, b, c).stream().sorted().collect(Collectors.toList());
                    return (sorted.get(0) + sorted.get(1)) > sorted.get(2) ? 1 : 0;
                })
                .sum();
        Instant finish = Instant.now();
        System.out.printf("Die Wahrscheinlichkeit ist %1.2f%%%n", numEvents/MAX_NUM_ITERATIONS*100);
        System.out.printf("Finished in %d ms%n", Duration.between(start, finish).toMillis());
    }
}
