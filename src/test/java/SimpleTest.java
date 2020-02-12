import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleTest {
    @Test
    public void sumCorrectForPositiveNumbers(){
        int a = 10;
        int b = 353;
        assertEquals(a + b, b + a);
    }
}
