import com.owlike.genson.Genson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class SpiderServiceIntegrationTest {
    private static String SpiderServiceURL = "http://localhost:8080/WCA/api/spiderservice/";
    private static String HELLO_URL = "hello";

    @Test
    public void requestHelloTest() throws IOException{
        HttpUriRequest request = new HttpGet( SpiderServiceURL + HELLO_URL );
        // When

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
        HttpEntity response = httpResponse.getEntity();
        String message = ConversionUtil.getConversionUtil().ConvertToString(response);
        Assert.assertEquals("Wasup.",message);
    }
}
