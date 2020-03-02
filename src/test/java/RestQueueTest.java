import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.hamcrest.CoreMatchers.hasItem;

public class RestQueueTest {
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
    public void tryingToAddNewQueue() throws UnsupportedEncodingException {

        RestAssured.post("/api/addNewQueue?queueName=Ilia").then().statusCode(201).assertThat()
                .body("", hasItem("New queue Ilia has been created"));
    }

    @Test
    public void tryingToAddQueueWithSameName() throws UnsupportedEncodingException {
        RestAssured.post("/api/addNewQueue?queueName=Ilia");
        RestAssured.post("/api/addNewQueue?queueName=Ilia").then().statusCode(409).assertThat()
                .body("", hasItem("Queue Ilia already exist."));
    }

    public void tryingToAddQueueWithAnotherMethod() throws UnsupportedEncodingException {
        RestAssured.get("/api/addNewQueue?queueName=Ilia").then().statusCode(405).assertThat()
                .body("", hasItem("Use another method"));
    }


    @Test
    public void tryingToDeleteQueue() throws UnsupportedEncodingException {
        RestAssured.post("/api/addNewQueue?queueName=Ilia");
        RestAssured.delete("/api/deleteQueue?queueName=Ilia").then().statusCode(200).assertThat()
                .body("", hasItem("Queue Ilia deleted"));
        RestAssured.delete("/api/deleteQueue?queueName=Ilia").then().statusCode(404).assertThat()
                .body("", hasItem("Queue Ilia does not exist."));
    }

    @Test
    public void tryingToDeleteQueueWithAnotherMethod() throws UnsupportedEncodingException {
        RestAssured.post("/api/addNewQueue?queueName=Ilia");
        RestAssured.get("/api/deleteQueue?queueName=Ilia").then().statusCode(405).assertThat()
                .body("", hasItem("Use another method"));

    }

}
