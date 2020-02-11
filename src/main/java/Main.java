import queue.logic.Customer;
import queue.logic.EngineeredQueue;

import java.util.Scanner;

/**
 * modified : 11.02.2020
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world.");
        final String ostup = "::: ";
        final String commandsInfo = "add, remove, position, size, served, help, exit";
        EngineeredQueue queue = new EngineeredQueue("myQue-1");
        try(Scanner scanner = new Scanner(System.in)) {
            System.out.println(ostup + " Please write one of following commands : " + commandsInfo);
            String command = "-";
            while (command != null) {
                final String customerName, customerPhoneNumber;
                command = scanner.next();
                switch(command){
                    case "add" :
                        System.out.print(ostup + " phone : ");
                        customerPhoneNumber = scanner.next();
                        System.out.print(ostup + " name : ");
                        customerName = scanner.next();
                        queue.addCustomer(new Customer(customerName, customerPhoneNumber));
                        break;
                    case "remove":
                        if(queue.queueSize() == 0){
                            System.out.println(ostup + " queue is empty.");
                            break;
                        }
                        System.out.print(ostup + " phone : ");
                        customerPhoneNumber = scanner.next();
                        Customer customer = queue.deleteCustomer(customerPhoneNumber);
                        if(customer == null){
                            System.out.println(ostup + " this customer is not in the queue");
                        } else {
                            System.out.println(ostup + " customer " + customer.getName() + " lived the queue");
                        }
                        break;
                    case "position":
                        if(queue.queueSize() == 0){
                            System.out.println(ostup + " queue is empty.");
                            break;
                        }
                        System.out.print(ostup + " phone : ");
                        customerPhoneNumber = scanner.next();
                        final int customerPosition = queue.findCustomerPosition(customerPhoneNumber);
                        if(customerPosition == -1){
                            System.out.println(ostup + " this customer is not in the queue");
                        } else {
                            System.out.println(ostup + " customer position is " + customerPosition);
                        }
                        break;
                    case "size":
                        System.out.println(ostup + " queue size now is " + queue.queueSize());
                        break;
                    case "served":
                        System.out.println(ostup + " total number of served customers is " + queue.getServedCustomersNumber());
                        break;
                    case "help":
                        System.out.println(ostup + " Please write one of following commands :" + commandsInfo);
                        break;
                    case "exit":
                        command = null;
                        System.out.println(ostup + " Bye.");
                        break;
                    default:
                        System.out.println(ostup + " wrong command name. ");
                        System.out.println(ostup + " Please write one of following commands :" + commandsInfo);
                }
            }
        }
    }
}
