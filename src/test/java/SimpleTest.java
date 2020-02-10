import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleTest {
    @Test
    public void sumCorrectForPositiveNumbers(){
        int a = 10;
        int b = 353;
        assertEquals(a + b, b + a);
    }
}
