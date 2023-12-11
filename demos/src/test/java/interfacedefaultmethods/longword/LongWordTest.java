package interfacedefaultmethods.longword;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class LongWordTest {

    @Test
    void testLongWord() {
        LongWord longWord = new LongWord(Paths.get("src/test/java/resources/longword.txt"));
        String expectedWord = "LONGWORD";

        Assertions.assertEquals(expectedWord, longWord.getLongWord());
    }

    @Test
    void testLongWordFileIsNotExisting() {
        LongWord longWord = new LongWord(Paths.get("src/test/resources/xy.txt"));
        Exception ex = Assertions.assertThrows(IllegalStateException.class,
            () -> longWord.getLongWord());
        Assertions.assertEquals("Can't read file", ex.getMessage());
    }

}
