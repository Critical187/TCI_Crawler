package tci_crawler.integration_testing;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;

// TODO remove this class after tests done.
public class IntegrationMain {
    public static void main(String[] args) {
        String expectedMessage = IntegrationTestsUtil.ConvertFromJSONFileToString("SearchSpecForOne.JSON");

        try {

        expectedMessage = IntegrationTestsUtil.setElapsedTimeToZero(expectedMessage.replaceAll("(AndrÃ©)|(\\r)|(\\n)|\\s+",""));

        System.out.println(expectedMessage);


        }catch (Exception e){};

    }

}
