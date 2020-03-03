import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.hamcrest.CoreMatchers.hasItem;

public class RestCustomerTest {
    Application app = new Application();

    @Before
    public void setup() throws IOException {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8000;
        Application.main(new String[0]);
    }

    @After
    public void end() {
        app.getServer().stop(0);
    }

    @Test
    public void tryingToAddNewCustomerToQueue() throws UnsupportedEncodingException {
        RestAssured.post("/api/addNewQueue?queueName=Ilia");
        RestAssured.post("/api/addToQueue").then().statusCode(400).assertThat()
                .body("", hasItem("You need to specify queue name"));
        RestAssured.post("/api/addToQueue?queueName=Ilia").then().statusCode(400).assertThat()
                .body("", hasItem("You need to specify phone number"));
        RestAssured.post("/api/addToQueue?phoneNumber=9012").then().statusCode(400).assertThat()
                .body("", hasItem("You need to specify queue name"));
        RestAssured.post("/api/addToQueue?queueName=Ilia&phoneNumber=9012").then().statusCode(201).assertThat()
                .body("", hasItem("Success"));
        RestAssured.post("/api/addToQueue?queueName=Ilia&phoneNumber=9012").then().statusCode(409).assertThat()
                .body("", hasItem("There is another customer with this phone number"));
        RestAssured.post("/api/addToQueue?queueName=KILL&phoneNumber=9012").then().statusCode(404).assertThat()
                .body("", hasItem("No such queue"));
        RestAssured.get("/api/addToQueue?queueName=Ilia&phoneNumber=9012").then().statusCode(405).assertThat()
                .body("", hasItem("Use another method"));

    }

    @Test
    public void tryingToGetQueue() throws UnsupportedEncodingException {
        RestAssured.post("/api/addNewQueue?queueName=Ilia");
        RestAssured.post("/api/addToQueue?queueName=Ilia&phoneNumber=8012");
        RestAssured.post("/api/addToQueue?queueName=Ilia&phoneNumber=9012");

        RestAssured.get("/api/getQueue").then().statusCode(400).assertThat()
                .body("", hasItem("You need to specify queue name"));

        RestAssured.get("/api/getQueue?queueName=Ilia").then().statusCode(200).assertThat()
                .body("", hasItem("8012\n9012\n"));
        RestAssured.post("/api/getQueue?queueName=Ilia").then().statusCode(405).assertThat()
                .body("", hasItem("Use another method"));
    }

    @Test
    public void tryingToGetAllQueues() throws UnsupportedEncodingException {


        RestAssured.get("/api/getAllQueues").then().statusCode(200).assertThat()
                .body("", hasItem("Empty..."));
        RestAssured.post("/api/addNewQueue?queueName=Ilia");

        RestAssured.get("/api/getAllQueues").then().statusCode(200).assertThat()
                .body("", hasItem("Ilia\n"));
        RestAssured.post("/api/getAllQueues").then().statusCode(405).assertThat()
                .body("", hasItem("Use another method"));
    }

    @Test
    public void tryingToDeleteFromQueue() throws UnsupportedEncodingException {

        RestAssured.post("/api/addNewQueue?queueName=Ilia");
        RestAssured.post("/api/addToQueue?queueName=Ilia&phoneNumber=8012");

        RestAssured.get("/api/deleteFromQueue?queueName=Ilia&phoneNumber=8012").then().statusCode(405).assertThat()
                .body("", hasItem("Use another method"));

        RestAssured.delete("/api/deleteFromQueue").then().statusCode(400).assertThat()
                .body("", hasItem("You need to specify queue name"));

        RestAssured.delete("/api/deleteFromQueue?phoneNumber=8921").then().statusCode(400).assertThat()
                .body("", hasItem("You need to specify queue name"));

        RestAssured.delete("/api/deleteFromQueue?queueName=Ilia2").then().statusCode(404).assertThat()
                .body("", hasItem("No such queue"));

        RestAssured.delete("/api/deleteFromQueue?queueName=Ilia").then().statusCode(400).assertThat()
                .body("", hasItem("You need to specify phone number"));

        RestAssured.delete("/api/deleteFromQueue?queueName=Ilia&phoneNumber=8012").then().statusCode(200).assertThat()
                .body("", hasItem("Deleted"));

    }

    @Test
    public void tryingToGetPosition() throws UnsupportedEncodingException {

        RestAssured.post("/api/addNewQueue?queueName=Ilia");
        RestAssured.post("/api/addToQueue?queueName=Ilia&phoneNumber=8012");
        RestAssured.get("/api/getIndex?queueName=Ilia&phoneNumber=8012").then().statusCode(200).assertThat()
                .body("", hasItem("Your position is 1 in queue Ilia"));
    }

    @Test
    public void tryingToGetPositionWithNoCustomer() throws UnsupportedEncodingException {

        RestAssured.post("/api/addNewQueue?queueName=Ilia");
        RestAssured.post("/api/addToQueue?queueName=Ilia&phoneNumber=8012");
        RestAssured.get("/api/getIndex?queueName=Ilia&phoneNumber=8020").then().statusCode(404).assertThat()
                .body("", hasItem("There is no such user in this queue"));
    }

}
