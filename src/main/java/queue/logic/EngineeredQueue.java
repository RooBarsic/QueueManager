package queue.logic;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Наша спроектированная очередь
 * Author: Farrukh Karimov
 * Modification Date: 12.02.2020
 */
public class EngineeredQueue {
    @NotNull
    private String queueName;
    private int servedCustomersNumber = 0;
    private List<Customer> customers = new ArrayList<>();
    private Set<String> phoneNumbersSet = new HashSet<>();

    /**
     * Конструктор - создание новой очереди с определенным именем
     * @param queueName - принимает имя очереди
     */
    public EngineeredQueue(@NotNull final String queueName){
        this.queueName = queueName;
    }

    @NotNull
    public String getQueueName(){
        return queueName;
    }

    /**
     * Метод для добавления клиента в очередь
     * @param customer - принимает клиента
     * @return возвращает false если такой клиент уже есть, иначе добавлет клиента в очередь и возвращает true
     */
    public boolean addCustomer(@NotNull final Customer customer){
        if(phoneNumbersSet.add(customer.getPhoneNumber())){
            servedCustomersNumber++;
            customers.add(customer);
            return true;
        }
        return false;
    }

    /**
     * Метод для удаления клиента из очереди.
     * @param customer - принимает на вход пользователя которого надо удалить
     * @return возвращяет false если такого клиента нет в очереди, иначе возвращяет true
     */
    public boolean removeCustomer(@NotNull final Customer customer){
        if(phoneNumbersSet.remove(customer.getPhoneNumber())){
            return customers.remove(customer);
        }
        return false;
    }

    /**
     * Метод для удаления клиента из очереди по номеру его телефона
     * @param customerPhoneNumber - принимает на вход номет телефона клиента которого надо удалить
     * @return возвращяет flase если такого клиента нет в очереди, иначе возвращяет true
     */
    public boolean removeCustomer(@NotNull final String customerPhoneNumber){
        return removeCustomer(new Customer(customerPhoneNumber));
    }

    /**
     * Метод для нахождения позиции клиента в очереди, по номеру телефона
     * @param customerPhoneNumber - принимает на вход номер телефона клиента
     * @return возвращяет -1 если клиента нет в очереди
     */
    public int findCustomerPosition(@NotNull final String customerPhoneNumber){
        return findCustomerPosition(new Customer(customerPhoneNumber));
    }

    /**
     * Метод для нахождения позиции клиента в очереди
     * @param customer - принимает на вход клиента
     * @return возвращяет -1 если клиента нет в очереди
     */
    public int findCustomerPosition(@NotNull final Customer customer){
        final int customerPosition = customers.indexOf(customer);
        if(phoneNumbersSet.contains(customer.getPhoneNumber())){
            return customers.indexOf(customer) + 1;
        }
        return -1;
    }

    /**
     * @return возвращает количество клиентов в очереди
     */
    public int queueSize(){
        return customers.size();
    }

    /**
     * @return возвращает обшее количество клиентов, которые были в этой очереди
     */
    public int getServedCustomersNumber(){
        return servedCustomersNumber;
    }

    /**
     * метод для очистки очереди
     */
    public void clear(){
        customers.clear();
        phoneNumbersSet.clear();
    }
}
