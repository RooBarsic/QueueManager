package exampler.console;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Вспомогательный класс для работы с множеством очередей
 * Author: Farrukh Karimov
 * Modification Date: 13.02.2020
 */
public class MultiQueueController {
    private final ControllerIO controllerIO;
    private final Map<String, SingleQueueController> queueControllerByName = new HashMap<>();

    public MultiQueueController(@NotNull final ControllerIO controllerIO) {
        this.controllerIO = controllerIO;
    }

    /**
     * Метод для проверки существования очереди
     * @param queueName получает на вход имя очереди
     * @return возвращет true если очередь с таким именем существует
     */
    public boolean queueExist(@NotNull final String queueName){
        if(!queueControllerByName.containsKey(queueName)){
            controllerIO.printErrorMessage("queue " + queueName + " not exist");
            return false;
        }
        return true;
    }

    /**
     * Метод для добавления новой очереди
     * @param queueName получает на вход имя очереди
     * @return возвращает false если очередь с таким именем уже существует
     */
    public boolean addQueue(@NotNull final String queueName){
        if(queueControllerByName.containsKey(queueName)) {
            controllerIO.printErrorMessage("queue " + queueName + " already exist. ");
            return false;
        }
        queueControllerByName.put(queueName, new SingleQueueController(queueName));
        return true;
    }

    /**
     * Метод для получения очереди по имени
     * @param queueName получает на вход имя очереди
     * @return возвращает null если такой очереди не существует
     */
    @Nullable
    public SingleQueueController getQueueController(@NotNull final String queueName){
        return queueExist(queueName) ?
                queueControllerByName.get(queueName) :
                null;
    }

    /**
     * Метод для удаления очереди по имени
     * @param queueName получает на вход имя очереди
     */
    public void removeQueue(@NotNull final String queueName){
        queueControllerByName.remove(queueName);
    }

    /**
     * Метод для удаления всех очередей
     */
    public void removeAll(){
        queueControllerByName.clear();
    }

    /**
     * Метод для получения списка имён существующих очередей
     * @return возвращает список имён существующих очередей
     */
    public List<String> getQueuesNames(){
        return new ArrayList<>(queueControllerByName.keySet());
    }
}
