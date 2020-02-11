package queue.logic;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Farrukh Karimov
 * Modification Date: 09.02.2020
 */
public class EngineeredQueue {
    @NotNull
    private String queueName;
    private int servedCustomersNumber = 0;
    private List<Customer> customers = new ArrayList<>();

    public EngineeredQueue(@NotNull final String queueName){
        this.queueName = queueName;
    }

    public String getQueueName(){
        return queueName;
    }

    public void addCustomer(final Customer customer){
        servedCustomersNumber++;
        customers.add(customer);
    }

    public Customer deleteCustomer(final Customer customer){
        final int customerPosition = customers.indexOf(customer);
        if(customerPosition >= 0){
            return customers.remove(customerPosition);
        }
        return null;
    }

    public Customer deleteCustomer(final String customerPhoneNumber){
        return deleteCustomer(new Customer(customerPhoneNumber));
    }

    public int findCustomerPosition(final String customerPhoneNumber){
        return findCustomerPosition(new Customer(customerPhoneNumber));
    }

    public int findCustomerPosition(final Customer customer){
        final int customerPosition = customers.indexOf(customer);
        if(customerPosition == -1){
            return -1;
        }
        return customerPosition + 1;
    }

    public int queueSize(){
        return customers.size();
    }

    public int getServedCustomersNumber(){
        return servedCustomersNumber;
    }

    public void clear(){
        customers.clear();
    }
}
