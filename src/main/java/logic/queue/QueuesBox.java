package logic.queue;

import helpers.ControllerIO;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Вспомогательный класс для работы с множеством очередей
 * Author: Farrukh Karimov
 * Modification Date: 15.02.2020
 */
public class QueuesBox<T> {
    private final ControllerIO controllerIO;
    private final Map<String, EngineeredQueue<T>> queueByName = new HashMap<>();

    public QueuesBox(@NotNull final ControllerIO controllerIO){
        this.controllerIO = controllerIO;
    }

    /**
     * Метод для проверки существования очереди
     * @param queueName получает на вход имя очереди
     * @return возвращет true если очередь с таким именем существует, иначе возвращает false
     */
    public boolean queueExist(@NotNull final String queueName){
        if(!queueByName.containsKey(queueName)){

            controllerIO.printErrorMessage("queue " + queueName + " not exist");

            return false;
        }
        return true;
    }

    /**
     * Метод для добавления новой очереди
     * @param queueName получает на вход имя очереди
     * @return возвращает false если очередь с таким именем уже существует, иначе возвращает true
     */
    public boolean addQueue(@NotNull final String queueName){
        if(queueByName.containsKey(queueName)) {
            controllerIO.printErrorMessage("queue " + queueName + " already exist. ");
            return false;
        }
        queueByName.put(queueName, new EngineeredQueue<T>(queueName));
        return true;
    }

    /**
     * Метод для получения очереди по имени
     * @param queueName получает на вход имя очереди
     * @return возвращает null если такой очереди не существует, иначе возвращает эту очередь
     */
    @Nullable
    public EngineeredQueue<T> getQueue(@NotNull final String queueName){
        return queueExist(queueName) ?
                queueByName.get(queueName) :
                null;
    }

    /**
     * Метод для удаления очереди по имени
     *
     * @param queueName получает на вход имя очереди
     */
    public Boolean removeQueue(@NotNull final String queueName) {

        if (queueByName.containsKey(queueName)) {
            queueByName.remove(queueName);
            return true;
        }
        return false;
    }

    /**
     * Метод для удаления всех очередей
     */
    public void removeAll(){
        queueByName.clear();
    }

    /**
     * Метод для получения списка имён существующих очередей
     * @return возвращает список имён существующих очередей
     */
    public List<String> getQueuesNames(){
        return new ArrayList<>(queueByName.keySet());
    }

    /**
     * @return возвращает количество элементов в QueuesBox
     */
    public int size(){
        return queueByName.size();
    }

    /**
     * @return возвращает содержимое QueuesBox
     */
    public List<EngineeredQueue<T>> values(){
        return new ArrayList<>(queueByName.values());
    }
}