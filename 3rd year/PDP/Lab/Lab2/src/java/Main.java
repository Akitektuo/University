package java;


import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        final var vector1 = Arrays.asList(1, 2, 3, 4);
        final var vector2 = Arrays.asList(5, 6, 7, 8);
        var size = Math.min(vector1.size(), vector2.size());

        var notifier = new Notifier<Integer>();

        var producer = new Thread(() -> {
            IntStream.range(0, size).forEach(index -> notifier.notify(vector1.get(index) * vector2.get(index)));
        });

        var consumer = new Thread(() -> {
            var sum = IntStream.range(0, size).map(index -> notifier.waitForData()).sum();

            System.out.println("The sum is $sum");
        });

        producer.start();
        consumer.start();
    }
}
