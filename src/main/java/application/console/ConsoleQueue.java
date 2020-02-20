package application.console;

import helpers.ControllerIO;
import logic.customer.Customer;
import logic.queue.QueuesBox;
import org.jetbrains.annotations.NotNull;


/**
 * Console application demo
 * Author: Farrukh Karimov
 * Modification Date: 12.02.2020
 */
public class ConsoleQueue {
    private final ControllerIO controllerIO;
    private final String availableCommands;
    private final QueuesBox<Customer> queueBox;

    public ConsoleQueue(@NotNull final ControllerIO controllerIO){
        this.controllerIO = controllerIO;
        this.queueBox = new QueuesBox<Customer>(controllerIO);
        availableCommands = "\nadd-queue [queueName] \n" +
                "clean-queue [queueName] \n" +
                "remove-queue [queueName] \n" +
                "queue-names-list \n" +
                "queue-size [queueName] \n" +
                "add-customer [queueName; customerPhoneNumber; customerName or '-' ] \n" +
                "remove-customer [queueName; customerPhoneNumber; customerName or '-' ] \n" +
                "customer-position [queueName; customerPhoneNumber; customerName or '-' ] \n" +
                "help \n" +
                "exit";
    }

    /**
     * Метод для запуска демонстрационной версии работы с очередями,
     * с поддержкой всех команд
     * @return возвращает true при успешном завершении работы.
     */
    public boolean runQueueDemo(){
        try {
            queueBox.removeAll();

            controllerIO.printMessage(" Please write one of following commands : ");
            controllerIO.printMessage(getAvailableCommandsInfo());

            String command = "-";
            while (!command.equals("exit")) {
                command = controllerIO.getField("command");
                final String queueName;
                switch (command) {
                    case "add-queue":
                        queueBox.addQueue(
                                controllerIO.getField("queue name")
                        );
                        break;
                    case "clean-queue":
                        queueBox.getQueue(
                                controllerIO.getField("queue name")
                        ).clear();
                        break;
                    case "remove-queue":
                        queueBox.removeQueue(
                                controllerIO.getField("queue name")
                        );
                        break;
                    case "queue-names-list":
                        queueBox.getQueuesNames()
                                .forEach(controllerIO::printMessage);
                        break;
                    case "queue-size":
                        queueName = controllerIO.getField("queue name");
                        if(queueBox.queueExist(queueName)){
                            controllerIO.printMessage(
                                    Integer.toString(
                                            queueBox.getQueue(queueName)
                                                    .size()
                                    )
                            );
                        }
                        break;
                    case "customer-position":
                        queueName = controllerIO.getField("queue name");
                        controllerIO.printMessage(
                                Integer.toString(
                                        queueBox.queueExist(queueName) ?
                                                queueBox.getQueue(queueName)
                                                        .findIndex(controllerIO.getCustomer())
                                                : -1
                                )
                        );
                        break;
                    case "add-customer":
                        queueName = controllerIO.getField("queue name");
                        if(queueBox.queueExist(queueName)){
                            queueBox.getQueue(queueName)
                                    .add(controllerIO.getCustomer());
                        }
                        break;
                    case "remove-customer":
                        queueName = controllerIO.getField("queue name");
                        if(queueBox.queueExist(queueName)){
                            queueBox.getQueue(queueName)
                                    .remove(controllerIO.getCustomer());
                        }
                        break;
                    case "help":
                        controllerIO.printMessage(getAvailableCommandsInfo());
                        break;
                    case "exit":
                        controllerIO.printMessage("Bye.");
                        break;
                    default:
                        controllerIO.printErrorMessage("Wrong command name.");
                        controllerIO.printMessage("Please write one of following commands :");
                        controllerIO.printMessage(getAvailableCommandsInfo());
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Метод для получения информации о доступных командах
     */
    public String getAvailableCommandsInfo(){
        return availableCommands;
    }
}