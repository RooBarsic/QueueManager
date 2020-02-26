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

        RestAssured.get("/api/addNewQueue?queueName=Ilia").then().statusCode(200).assertThat()
                .body("", hasItem("New queue Ilia has been created"));
    }

    @Test
    public void tryingToAddQueueWithSameName() throws UnsupportedEncodingException {
        RestAssured.get("/api/addNewQueue?queueName=Ilia");
        RestAssured.get("/api/addNewQueue?queueName=Ilia").then().statusCode(200).assertThat()
                .body("", hasItem("Queue Ilia already exist."));
    }

    @Test
    public void tryingToDeleteQueue() throws UnsupportedEncodingException {
        RestAssured.get("/api/addNewQueue?queueName=Ilia");
        RestAssured.get("/api/deleteQueue?queueName=Ilia").then().statusCode(200).assertThat()
                .body("", hasItem("Queue Ilia deleted"));
        RestAssured.get("/api/deleteQueue?queueName=Ilia").then().statusCode(200).assertThat()
                .body("", hasItem("Queue Ilia does not exist."));
    }
  //2200-3305-2730-0012199

}
