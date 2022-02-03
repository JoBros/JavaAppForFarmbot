import Main.Logger;
import org.junit.Test;

public class LoggerTest {
    @Test
    public void loggertest(){
        for(int i = 1; i < 100; i++) {
            Logger lt = new Logger();
            lt.logError("Ich bin ein Error");
            lt.logInfo("Ich bin eine Info");
            lt.logWarn("Ich bin eine Warnung");
        }
    }
}
