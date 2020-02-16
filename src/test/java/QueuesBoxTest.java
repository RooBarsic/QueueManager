import helpers.ControllerIO;
import logic.queue.QueuesBox;
import org.junit.Test;
import logic.customer.Customer;
import logic.queue.EngineeredQueue;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Author: Farrukh Karimov
 * Modification Date: 15.05.2020
 */
public class QueuesBoxTest {
    @Test
    public void cantAddDuplicatedElements(){
        final String queueName = "mockito";
        final ControllerIO mockitoController = Mockito.mock(ControllerIO.class);
        final QueuesBox<Customer> queuesBox = new QueuesBox<>(mockitoController);

        assertEquals(queuesBox.size(), 0);

        assertTrue(queuesBox.addQueue(queueName));
        assertEquals(queuesBox.size(), 1);

        assertFalse(queuesBox.addQueue(queueName));
        assertEquals(queuesBox.size(),  1);
    }

    @Test
    public void cantChangeQueueDataFromOutside(){
        final String queueName = "data";
        final ControllerIO mockitoController = Mockito.mock(ControllerIO.class);
        final QueuesBox<Customer> queuesBox = new QueuesBox<>(mockitoController);

        assertEquals(queuesBox.size(), 0);

        assertTrue(queuesBox.addQueue(queueName));
        assertEquals(queuesBox.size(), 1);

        List<EngineeredQueue<Customer>> queuesBoxValues = queuesBox.values();
        assertEquals(queuesBoxValues.size(), 1);
        assertEquals(queuesBoxValues.get(0).getQueueName(), queueName);

        queuesBoxValues.remove(0);
        assertEquals(queuesBoxValues.size(), 0);
        assertEquals(queuesBox.size(), 1);
    }
}
