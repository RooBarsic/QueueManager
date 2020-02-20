package logic.queue;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Наша спроектированная очередь
 * Author: Farrukh Karimov
 * Modification Date: 15.02.2020
 */
public class EngineeredQueue<T> {
    @NotNull
    private String queueName;
    private List<T> customersList = new ArrayList<>();
    private Set<T> customersSet = new HashSet<>();

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
     * Метод для добавления обекта в очередь
     * @param var принимает на вход обект
     * @return возвращает true если элемент успешно добавлен, false если такой елемент уже существует
     */
    public boolean add(@NotNull final T var){

        if(customersSet.add(var)){
            customersList.add(var);
            return true;
        }
        return false;
    }

    /**
     * :TODO оптимизировать время работы, сейчай O(N)
     * Метод для удаления обекта из очереди
     * @param var принимает на вход обект
     * @return возвращает true если такой обект был в очереди и мы только что его удалили, false иначе
     */
    public boolean remove(@NotNull final T var){
        if(customersSet.remove(var)){
            return customersList.remove(var);
        }
        return false;
    }

    /**
     * :TODO оптимизировать время работы, сейчай O(N)
     * Метод для поиска позиции обекта в очереди
     * @param var принимает на вход обект
     * @return возвращает позицию обекта в очереди - если в очереди есть такой обект ( иначе возвращает -1 )
     */
    public int findIndex(@NotNull final T var){
        if(customersSet.contains(var)){
            return customersList.indexOf(var) + 1;
        }
        return -1;
    }

    /**
     * @return возвращает количество элементов в очереди
     */
    public int size(){
        return customersList.size();
    }

    /**
     * Метод для очистки очереди
     */
    public void clear(){
        customersList.clear();
        customersSet.clear();
    }

    /**
     * @return возвращает содержимое очереди
     */
    public List<T> values(){
        return new ArrayList<>(customersList);
    }
}