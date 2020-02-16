import org.junit.Test;
import logic.customer.Customer;
import logic.queue.EngineeredQueue;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Author: Farrukh Karimov
 * Modification Date: 15.02.2020
 */
public class EngineeredQueueTest {
    @Test
    public void canSearchElementPosition(){
        final String phoneNumber = "+33333333";
        final Customer customer1 = new Customer( "+11111111");
        final Customer customer2 = new Customer("+22222222");
        final Customer customer3 = new Customer(phoneNumber);
        final EngineeredQueue<Customer> queue = new EngineeredQueue<>("queue-1");

        queue.add(customer1);
        queue.add(customer2);
        queue.add(customer3);
        assertEquals(queue.size(), 3);

        assertEquals(queue.findIndex(customer1), 1);
        assertEquals(queue.findIndex(customer2), 2);
        assertEquals(queue.findIndex(customer3), 3);
        assertEquals(queue.findIndex(new Customer(phoneNumber)), 3);

        queue.remove(customer2);
        assertEquals(queue.size(), 2);

        assertEquals(queue.findIndex(customer3), 2);
        assertEquals(queue.findIndex(customer2), -1);
    }

    @Test
    public void cantAddDuplicatedElements(){
        final String queueName = "duplicate";
        final String customerName = "Sam";
        final String customerPhone = "1111111111";
        final EngineeredQueue<Customer> queue = new EngineeredQueue<>(queueName);
        final Customer customer = new Customer(customerName, customerPhone);

        assertEquals(queue.size(), 0);
        assertTrue(queue.add(customer));
        assertEquals(queue.size(), 1);
        assertFalse(queue.add(customer));
        assertFalse(queue.add(new Customer(customerName, customerPhone)));
        assertEquals(queue.size(), 1);
    }

    @Test
    public void cantChangeQueueDataFromOutside(){
        final String queueName = "outsider";
        final EngineeredQueue<String> queue = new EngineeredQueue<>(queueName);
        queue.add("aaa");
        queue.add("bbb");
        assertEquals(queue.size(), 2);
        final List<String> queueValues = queue.values();
        queueValues.remove("aaa");
        assertEquals(queueValues.size(), 1);
        assertEquals(queue.size(), 2);
    }
}
