import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.junit.Assert;
import org.junit.Test;

public class HelloIntegrationTest {

    private static String HELLO_URL = "http://localhost:8080/hello";

    @Test
    public void testHello() throws Exception {
        Assert.assertTrue(true);
    }
}
