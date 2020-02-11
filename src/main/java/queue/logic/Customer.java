package queue.logic;

import org.jetbrains.annotations.NotNull;

/**
 * Author: Farrukh Karimov
 * Modification Date: 09.02.2020
 */
public class Customer {
    private String name;
    @NotNull
    private final String phoneNumber;

    public Customer(final  String customerName, @NotNull final  String phoneNumber){
        this.name = customerName;
        this.phoneNumber = phoneNumber;
    }

    public Customer(@NotNull final String phoneNumber){
        this.name = "-";
        this.phoneNumber = phoneNumber;
    }

    public String getName(){
        return name;
    }

    public void setName(final String customerName){
        name = customerName;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        return getPhoneNumber() != null ? getPhoneNumber().equals(customer.getPhoneNumber()) : customer.getPhoneNumber() == null;
    }

    @Override
    public int hashCode() {
        return getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0;
    }
}