import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;

public class PortTest {
    private static final Logger LOGGER = Logger.getLogger(PortTest.class);
    private ExecutorService executor;

    @Test
    public void startThreadsTest() {

    }

    @Test
    public void checkRandomTest() {
        for (int i = 0; i < 10; i++) {
            System.out.println((int) ((Math.random() * 6) + 5) / 2);
        }

    }
}
