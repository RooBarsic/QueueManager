package exampler.console;

import org.jetbrains.annotations.NotNull;
import queue.logic.Customer;
import queue.logic.EngineeredQueue;

/**
 * Вспомогательный класс для работы с очередью
 * Author: Farrukh Karimov
 * Modification Date: 13.02.2020
 */
public class SingleQueueController {
    private final EngineeredQueue queue;
    private final String controllerName;

    SingleQueueController(@NotNull final String queueName){
        queue = new EngineeredQueue(queueName);
        controllerName = queueName;
    }

    /**
     * Метод для получаения информации о количестве клиентов в очереди по имени очереди
     * @return возвращает -1 - если такая очередь не существует, иначе возвращает количество кливентов в очереди
     */
    public int queueSize(){
        return queue.queueSize();
    }

    /**
     * Метод для добавления клиента в очередь.
     * Получает на вход :
     * @param customer клиент которого надо добавить в эту очередь
     * @return возвращет true - если клиент успешно добавлен
     */
    public boolean addCustomer(@NotNull final Customer customer){
        return queue.addCustomer(customer);
    }

    /**
     * Метод для удаления клиента из очередь.
     * Получает на вход :
     * @param customer клиент которого надо удалить из этой очереди
     * @return возвращет true - если клиент успешно удалён
     */
    public boolean removeCustomer(@NotNull final Customer customer){
        return queue.removeCustomer(customer);
    }

    /**
     * Метод для получения позиции клиента в очереди.
     * Получает на вход :
     * @param customer клиент позицию которого ищем в этой очереди
     * @return возвращет -1 - если такой клиент не найден в этой очереди, иначе возвращет позицию клиента в очереди
     */
    public int getCustomerPosition(@NotNull final Customer customer){
        return queue.findCustomerPosition(customer);
    }

    /**
     * Метод для очистки содержимого очереди
     * @return возвращет false если очередь с таким название не существует
     */
    public boolean cleanQueue(){
        queue.clear();
        return true;
    }

    /**
     * Метод для получения имени контроллера ( которое является также именем очереди )
     * @return возвращает имя контроллера
     */
    public String getControllerName(){
        return controllerName;
    }
}
