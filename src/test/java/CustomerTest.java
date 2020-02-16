import org.junit.Test;
import logic.customer.Customer;

import static org.junit.Assert.assertEquals;

/**
 * Author: Farrukh Karimov
 * Modification Date: 15.02.2020
 */
public class CustomerTest {
    private final String customerName = "Ali";
    private final String customerPhoneNumber = "+1234567890";

    @Test
    public void canGetCustomerParameters(){
        Customer customer = new Customer(customerName, customerPhoneNumber);
        assertEquals(customer.getName(), customerName);
        assertEquals(customer.getPhoneNumber(), customerPhoneNumber);
    }

    @Test
    public void canChangeCustomerName(){
        Customer customer = new Customer(customerName, customerPhoneNumber);
        assertEquals(customer.getName(), customerName);
        final String newCustomerName = "Malik";
        customer.setName(newCustomerName);
        assertEquals(customer.getName(), newCustomerName);
    }
}
