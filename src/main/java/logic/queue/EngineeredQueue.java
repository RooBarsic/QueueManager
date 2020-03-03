package logic.queue;


import org.jetbrains.annotations.NotNull;

import javax.swing.Timer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Наша спроектированная очередь
 * Author: Farrukh Karimov
 * Modification Date: 19.02.2020
 */
public class EngineeredQueue<T> {
    private int taskExecutionDelayMs = 3 * 60 * 1000;
    @NotNull
    private String queueName;
    private Timer releasingTimer;
    private final List<T> customersList = new ArrayList<>();
    private final Set<T> customersSet = new HashSet<>();

    /**
     * Конструктор для создания новой очереди с определенным именем и дефолтной задержкой на обслуживании
     * @param queueName - принимает имя очереди
     */
    public EngineeredQueue(@NotNull final String queueName){
        this.queueName = queueName;
        initTimer();
    }

    /**
     * Конструктор для создания новой очереди с заданным именем и задержкой на обслуживании
     * @param queueName - имя очереди
     * @param taskExecutionDelayMs - задержка на обслуживании в мс
     */
    public EngineeredQueue(@NotNull final String queueName, final int taskExecutionDelayMs){
        this.queueName = queueName;
        this.taskExecutionDelayMs = taskExecutionDelayMs;
        initTimer();
    }

    private void initTimer(){
        releasingTimer = new Timer(taskExecutionDelayMs, actionEvent -> {
            synchronized (customersList){
                if(customersList.size() > 0){
                    synchronized (customersSet){
                        remove(customersList.get(0));
                    }
                }
            }
        });
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
            if(customersList.size() == 1){
                releasingTimer.start();
            }
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
            final int varIndex = customersList.indexOf(var);
            customersList.remove(varIndex);
            if(customersList.size() == 0){
                releasingTimer.stop();
            } else if(varIndex == 0){
                releasingTimer.restart();
            }
            return true;
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