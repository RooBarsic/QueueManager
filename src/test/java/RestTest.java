import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.hamcrest.CoreMatchers.hasItem;

public class RestTest {
    Application app;

    @Before
    public void setup() throws IOException {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8000;

        app = new Application();
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
}
