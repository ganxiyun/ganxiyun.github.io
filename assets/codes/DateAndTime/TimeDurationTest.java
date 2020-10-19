import com.google.common.base.Stopwatch;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TimeDurationTest {
    public static void main(String[] args) {
        long sMillis = System.currentTimeMillis();
        Instant sInstant = Instant.now();
        long sNano = System.nanoTime();
        Stopwatch stopwatch = Stopwatch.createStarted();

        // it takes 6 seconds approximately in my PC
        Random r = new Random();
        for (int i = 1; i < (1 << 30); ++i) {
            r.nextInt(i);
        }

        long eMillis = System.currentTimeMillis();
        stopwatch.stop();

        System.out.println("using currentTimeMillis\t" + (eMillis - sMillis));
        System.out.println("using Duration\t" + Duration.between(sInstant , Instant.now()));
        System.out.println("using nanoTime\t" + (System.nanoTime() - sNano));
        System.out.println("using Stopwatch\t" + stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
}
