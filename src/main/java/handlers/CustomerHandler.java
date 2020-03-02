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
            int respCode = 0;
            String respText = "";
            if (exchange.getRequestMethod().equals("GET")) {


                if (!queuesBox.getQueuesNames().isEmpty()) {
                    for (Object name : queuesBox.getQueuesNames())
                        respText = respText.concat(name.toString().concat("\n"));

                    respCode = 200;
                } else {
                    respText = "Empty...";
                    respCode = 200;
                }
            } else {
                respCode = 405;
                respText = "Use another method";
            }
            endResponse(exchange, respText, respCode);
        }));

        server.createContext("/api/addToQueue", (exchange -> {
            Map<String, String> x = splitQuery(exchange.getRequestURI().getRawQuery());


            String respText = "";
            int respCode = 0;
            if (exchange.getRequestMethod().equals("POST")) {

                if (x.get("queueName") == null) {
                    respText = "You need to specify queue name";
                    respCode = 400;
                } else {


                    EngineeredQueue<Customer> customerEngineeredQueue = queuesBox.getQueue(x.get("queueName"));


                    if (customerEngineeredQueue == null) {
                        respText = "No such queue";
                        respCode = 404;
                    } else {

                        if (x.get("phoneNumber") == null) {
                            respText = "You need to specify phone number";
                            respCode = 400;

                        } else {

                            if (customerEngineeredQueue.add(new Customer(x.get("phoneNumber")))) {
                                respText = "Success";
                                respCode = 201;
                            } else {
                                respText = "There is another customer with this phone number";
                                respCode = 409;
                            }

                        }
                    }
                }
            } else {
                respCode = 405;
                respText = "Use another method";
            }

            endResponse(exchange, respText, respCode);
        }));

        server.createContext("/api/deleteFromQueue", (exchange -> {

            Map<String, String> x = splitQuery(exchange.getRequestURI().getRawQuery());
            String respText;
            int respCode = 0;

            if (exchange.getRequestMethod().equals("DELETE")) {

                if (x.get("queueName") == null) {
                    respCode = 400;
                    respText = "You need to specify queue name";
                } else {
                    EngineeredQueue<Customer> customerEngineeredQueue = queuesBox.getQueue(x.get("queueName"));
                    if (customerEngineeredQueue == null) {
                        respCode = 404;
                        respText = "No such queue";
                    } else {

                        if (x.get("phoneNumber") == null) {
                            respCode = 400;
                            respText = "You need to specify phone number";
                        } else {
                            respCode = 200;
                            customerEngineeredQueue.remove(new Customer(x.get("phoneNumber")));
                            respText = "Deleted";
                        }
                    }
                }
            } else {
                respCode = 405;
                respText = "Use another method";
            }
            endResponse(exchange, respText, respCode);

        }));
        server.createContext("/api/getQueue", (exchange -> {
            Map<String, String> x = splitQuery(exchange.getRequestURI().getRawQuery());
            String respText = "";
            int respCode = 0;

            if (exchange.getRequestMethod().equals("GET")) {


                if (x.get("queueName") == null) {
                    respCode = 400;
                    respText = "You need to specify queue name";
                } else {


                    EngineeredQueue<Customer> customerEngineeredQueue = queuesBox.getQueue(x.get("queueName"));


                    if (customerEngineeredQueue == null) {
                        respText = "No such queue";
                        respCode = 404;
                    } else {

                        int i = 1;
                        for (Customer cs : customerEngineeredQueue.values()) {


                            respText = respText + cs.getPhoneNumber() + "\n";
                            i++;
                            respCode = 200;
                        }


                    }
                }
            } else {
                respCode = 405;
                respText = "Use another method";
            }
            endResponse(exchange, respText, respCode);
        }));
        server.createContext("/api/getIndex", (exchange -> {
            Map<String, String> x = splitQuery(exchange.getRequestURI().getRawQuery());


            String respText = "";
            int respCode = 0;
            if (exchange.getRequestMethod().equals("GET")) {

                if (x.get("queueName") == null) {
                    respText = "You need to specify queue name";
                    respCode = 400;
                } else {


                    EngineeredQueue<Customer> customerEngineeredQueue = queuesBox.getQueue(x.get("queueName"));


                    if (customerEngineeredQueue == null) {
                        respText = "No such queue";
                        respCode = 404;
                    } else {

                        if (x.get("phoneNumber") == null) {
                            respText = "You need to specify phone number";
                            respCode = 400;

                        } else {

                            if (customerEngineeredQueue.findIndex(new Customer(x.get("phoneNumber"))) != -1) {
                                respText = "Your position is " + customerEngineeredQueue.findIndex(new Customer(x.get("phoneNumber"))) + " in queue " + x.get("queueName");
                                respCode = 200;
                            } else {
                                respText = "There is no such user in this queue";
                                respCode = 404;
                            }

                        }
                    }
                }
            } else {
                respCode = 405;
                respText = "Use another method";
            }

            endResponse(exchange, respText, respCode);
        }));
    }


    public static void endResponse(HttpExchange exchange, String response, int respCode) throws IOException {
        String encoding = "UTF-8";

        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=" + encoding);
        exchange.sendResponseHeaders(respCode, response.getBytes().length);
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
