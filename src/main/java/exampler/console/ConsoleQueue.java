package exampler.console;

import org.jetbrains.annotations.NotNull;

/**
 * Console application demo
 * Author: Farrukh Karimov
 * Modification Date: 12.02.2020
 */
public class ConsoleQueue {
    private final ControllerIO controllerIO;
    private final String availableCommands;
    private final MultiQueueController multiQueueController;

    public ConsoleQueue(@NotNull final ControllerIO controllerIO){
        this.controllerIO = controllerIO;
        this.multiQueueController = new MultiQueueController(controllerIO);
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
            multiQueueController.removeAll();

            controllerIO.printMessage(" Please write one of following commands : ");
            controllerIO.printMessage(getAvailableCommandsInfo());

            String command = "-";
            while (!command.equals("exit")) {
                command = controllerIO.getField("command");
                final String queueName;
                switch (command) {
                    case "add-queue":
                        multiQueueController.addQueue(
                                controllerIO.getField("queue name")
                        );
                        break;
                    case "clean-queue":
                        multiQueueController
                                .getQueueController(controllerIO.getField("queue name"))
                                .cleanQueue();
                        break;
                    case "remove-queue":
                        multiQueueController.removeQueue(
                                controllerIO.getField("queue name")
                        );
                        break;
                    case "queue-names-list":
                        multiQueueController
                                .getQueuesNames()
                                .forEach(controllerIO::printMessage);
                        break;
                    case "queue-size":
                        queueName = controllerIO.getField("queue name");
                        if(multiQueueController.queueExist(queueName)){
                            controllerIO.printMessage(
                                    Integer.toString(
                                            multiQueueController
                                                    .getQueueController(queueName)
                                                    .queueSize()
                                    )
                            );
                        }
                        break;
                    case "customer-position":
                        queueName = controllerIO.getField("queue name");
                        controllerIO.printMessage(
                                Integer.toString(
                                        multiQueueController.queueExist(queueName) ?
                                                multiQueueController
                                                        .getQueueController(queueName)
                                                        .getCustomerPosition(controllerIO.getCustomer())
                                                : -1
                                )
                        );
                        break;
                    case "add-customer":
                        queueName = controllerIO.getField("queue name");
                        if(multiQueueController.queueExist(queueName)){
                            multiQueueController
                                .getQueueController(queueName)
                                .addCustomer(controllerIO.getCustomer());
                        }
                        break;
                    case "remove-customer":
                        queueName = controllerIO.getField("queue name");
                        if(multiQueueController.queueExist(queueName)){
                            multiQueueController
                                .getQueueController(queueName)
                                .removeCustomer(controllerIO.getCustomer());
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
