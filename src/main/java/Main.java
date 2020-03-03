import application.console.ConsoleQueue;
import helpers.ControllerIO;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * modified : 15.02.2020
 */
public class Main {


    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in);
            PrintWriter printWriter = new PrintWriter(System.out)) {
            ControllerIO controllerIO = new ControllerIO(scanner, printWriter);
            ConsoleQueue consoleQueue = new ConsoleQueue(controllerIO);
            consoleQueue.runQueueDemo();
        }
    }
}
