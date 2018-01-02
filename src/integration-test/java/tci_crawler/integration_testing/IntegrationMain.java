package tci_crawler.integration_testing;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;

// TODO remove this class after tests done.
public class IntegrationMain {
    public static void main(String[] args) {
        String expectedMessage = IntegrationTestsUtil.ConvertFromJSONFileToString("TotalCrawl.JSON");
        HttpUriRequest request = new HttpGet( "http://localhost:8080/WCA/api/crawler/crawl/i315379.hera.fhict.nl" );
        try {


        // When
        HttpResponse response = HttpClientBuilder.create().build().execute( request );

        // Then

        HttpEntity entity = response.getEntity();
        String actualMessage = IntegrationTestsUtil.ConvertToString(entity);
        expectedMessage = setTimeToZero(expectedMessage.replaceAll("(AndrÃ©)|(\\r)|(\\n)|\\s+",""));
            actualMessage = setTimeToZero(actualMessage.replaceAll("(AndrÃ©)|(\\r)|(\\n)|\\s+",""));
        System.out.println(expectedMessage);
        System.out.println(actualMessage);
        System.out.println(String.valueOf(expectedMessage.compareTo(actualMessage)));
        }catch (Exception e){};

    }
    private static String
    setTimeToZero(String jsonString){
        String[] strings = jsonString.split("\"time\":");
        return strings[0]+"\"time\":0}";
    }
}
