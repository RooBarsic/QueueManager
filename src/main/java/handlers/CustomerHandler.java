package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import logic.customer.Customer;
import logic.queue.EngineeredQueue;
import logic.queue.QueuesBox;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CustomerHandler {
    HttpServer server;


    public CustomerHandler(HttpServer server, QueuesBox queuesBox) {
        this.server = server;


        server.createContext("/api/getAllQueues", (exchange -> {
            String respText = "";
            if (!queuesBox.getQueuesNames().isEmpty()) {
                for (Object name : queuesBox.getQueuesNames()) respText = respText.concat(name.toString().concat("\n"));
            } else {
                respText = "Empty...";
            }
            endResponse(exchange, respText);
        }));

        server.createContext("/api/addToQueue", (exchange -> {
            Map<String, String> x = splitQuery(exchange.getRequestURI().getRawQuery());


            String respText = "";
                if (x.get("queueName") == null) {
                    respText = "You need to specify queue name";
                } else {

                    EngineeredQueue<Customer> customerEngineeredQueue = queuesBox.getQueue(x.get("queueName"));

                    if (customerEngineeredQueue == null) {
                        respText = "No such queue";
                    } else {

                        if (x.get("phoneNumber") == null) {
                            respText = "You need to specify phone number";

                        } else {

                            if (customerEngineeredQueue.add(new Customer(x.get("nameUser"),x.get("phoneNumber")))) respText = "Success";
                            else
                                respText = "There is another customer with this phone number";
                        }
                    }
                }

            endResponse(exchange, respText);
        }));


        server.createContext("/api/deleteFromQueue", (exchange -> {
            Map<String, String> x = splitQuery(exchange.getRequestURI().getRawQuery());
            String respText;
            if (x.get("queueName") == null) {
                respText = "You need to specify queue name";
            } else {
                EngineeredQueue<Customer> customerEngineeredQueue = queuesBox.getQueue(x.get("queueName"));
                if (customerEngineeredQueue == null) {
                    respText = "No such queue";
                } else {
                    if (x.get("phoneNumber") == null) {
                        respText = "You need to specify phone number";
                    } else {
                        Customer temp = new Customer(x.get("phoneNumber"));

                        if (customerEngineeredQueue.findIndex(temp)==-1) respText = "No such user in given queue!";
                        else{
                            customerEngineeredQueue.remove(temp);
                            respText = "Deleted";
                        }

                    }
                }
            }
            endResponse(exchange, respText);

        }));

        server.createContext("/api/getQueue", (exchange -> {
            Map<String, String> x = splitQuery(exchange.getRequestURI().getRawQuery());
            String respText = "";
            if (x.get("queueName") == null) {
                respText = "You need to specify queue name";
            } else {

                EngineeredQueue<Customer> customerEngineeredQueue = queuesBox.getQueue(x.get("queueName"));

                if (customerEngineeredQueue == null) {
                    respText = "No such queue";
                } else if (customerEngineeredQueue.size()==0) respText="There is nobody in this queue";
                else {
                    int i = 1;
                    for (Customer cs : customerEngineeredQueue.values()) {
                        respText = respText + cs.getPhoneNumber() + "\n";
                        i++;
                    }
                 //   respText="There are " + customerEngineeredQueue.size() + " people before you";
                }
            }
            endResponse(exchange, respText);
        }));

        //TODO write README file
        server.createContext("/api/getMyPosition",(exchange ->{
            Map<String, String> x = splitQuery(exchange.getRequestURI().getRawQuery());
            String respText = "";
            if (x.get("queueName") == null) {
                respText = "You need to specify queue name";
            } else {
                EngineeredQueue<Customer> customerEngineeredQueue = queuesBox.getQueue(x.get("queueName"));
                if (customerEngineeredQueue == null) respText = "No such queues";
                else {
                    if (x.get("phoneNumber") == null) {
                        respText = "You need to specify phone number";
                    } else {
                        int index = customerEngineeredQueue.findIndex(new Customer(x.get("phoneNumber")));
                        if (index==-1) respText = "There is no user with this number phone in given queue";
                        else respText = "There are " + --index +  " people before you";
                    }
                }
            }
            endResponse(exchange,respText);
        }));
    }

    public static void endResponse(HttpExchange exchange, String response) throws IOException {
        String encoding = "UTF-8";

        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=" + encoding);
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(response.getBytes());
        output.flush();
        exchange.close();

    }

    public static Map<String, String> splitQuery(String query) {
        Map<String, String> params = new HashMap<>();

        if (query == null || "".equals(query)) {
            return Collections.emptyMap();
        }
        String[] keyval = query.split("&");
        for (String s : keyval
        ) {
            String[] kv = s.split("=");
            params.put(kv[0], kv[1]);
        }
        return params;
    }
}
