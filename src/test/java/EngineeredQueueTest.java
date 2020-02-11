import org.junit.Test;
import queue.logic.Customer;
import queue.logic.EngineeredQueue;

import static org.junit.Assert.assertEquals;


public class EngineeredQueueTest {
    @Test
    public void canSearchElementPosition(){
        final String phoneNumber = "+33333333";
        final Customer customer1 = new Customer( "+11111111");
        final Customer customer2 = new Customer("+22222222");
        final Customer customer3 = new Customer(phoneNumber);
        final EngineeredQueue queue = new EngineeredQueue("queue-1");

        queue.addCustomer(customer1);
        queue.addCustomer(customer2);
        queue.addCustomer(customer3);
        assertEquals(queue.queueSize(), 3);

        assertEquals(queue.findCustomerPosition(customer1), 1);
        assertEquals(queue.findCustomerPosition(customer2), 2);
        assertEquals(queue.findCustomerPosition(customer3), 3);
        assertEquals(queue.findCustomerPosition(new Customer(phoneNumber)), 3);

        queue.deleteCustomer(customer2);
        assertEquals(queue.queueSize(), 2);

        assertEquals(queue.findCustomerPosition(customer3), 2);
        assertEquals(queue.findCustomerPosition(customer2), -1);
    }
}
