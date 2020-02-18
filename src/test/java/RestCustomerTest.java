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
        RestAssured.get("/api/addNewQueue?queueName=Ilia");
        RestAssured.get("/api/addToQueue").then().statusCode(200).assertThat()
                .body("", hasItem("You need to specify queue name"));
        RestAssured.get("/api/addToQueue?queueName=Ilia").then().statusCode(200).assertThat()
                .body("", hasItem("You need to specify phone number"));
        RestAssured.get("/api/addToQueue?phoneNumber=9012").then().statusCode(200).assertThat()
                .body("", hasItem("You need to specify queue name"));
        RestAssured.get("/api/addToQueue?queueName=Ilia&phoneNumber=9012").then().statusCode(200).assertThat()
                .body("", hasItem("Success"));
        RestAssured.get("/api/addToQueue?queueName=Ilia&phoneNumber=9012").then().statusCode(200).assertThat()
                .body("", hasItem("There is another customer with this phone number"));
    }

    @Test
    public void tryingToGetQueue() throws UnsupportedEncodingException {
        RestAssured.get("/api/addNewQueue?queueName=Ilia");
        RestAssured.get("/api/addToQueue?queueName=Ilia&phoneNumber=8012");
        RestAssured.get("/api/addToQueue?queueName=Ilia&phoneNumber=9012");

        RestAssured.get("/api/getQueue").then().statusCode(200).assertThat()
                .body("", hasItem("You need to specify queue name"));

        RestAssured.get("/api/getQueue?queueName=Ilia").then().statusCode(200).assertThat()
                .body("", hasItem("8012\n9012\n"));
    }

    @Test
    public void tryingToGetAllQueues() throws UnsupportedEncodingException {


        RestAssured.get("/api/getAllQueues").then().statusCode(200).assertThat()
                .body("", hasItem("Empty..."));
        RestAssured.get("/api/addNewQueue?queueName=Ilia");

        RestAssured.get("/api/getAllQueues").then().statusCode(200).assertThat()
                .body("", hasItem("Ilia\n"));
    }

    @Test
    public void tryingToDeleteFromQueue() throws UnsupportedEncodingException {

        RestAssured.get("/api/addNewQueue?queueName=Ilia");
        RestAssured.get("/api/addToQueue?queueName=Ilia&phoneNumber=8012");
        RestAssured.get("/api/deleteFromQueue").then().statusCode(200).assertThat()
                .body("", hasItem("You need to specify queue name"));
        RestAssured.get("/api/deleteFromQueue?phoneNumber=8921").then().statusCode(200).assertThat()
                .body("", hasItem("You need to specify queue name"));

        RestAssured.get("/api/deleteFromQueue?queueName=Ilia2").then().statusCode(200).assertThat()
                .body("", hasItem("No such queue"));
        RestAssured.get("/api/deleteFromQueue?queueName=Ilia").then().statusCode(200).assertThat()
                .body("", hasItem("You need to specify phone number"));
        RestAssured.get("/api/deleteFromQueue?queueName=Ilia&phoneNumber=8012").then().statusCode(200).assertThat()
                .body("", hasItem("Deleted"));

    }


}
