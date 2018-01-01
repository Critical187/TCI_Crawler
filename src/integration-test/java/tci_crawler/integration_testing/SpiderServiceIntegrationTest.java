package tci_crawler.integration_testing;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class SpiderServiceIntegrationTest {
    private static String SpiderServiceURL = "http://localhost:8080/WCA/api/crawler/";
    private static String HELLO_URL = "hello";
    private static String CRAWL_URL = "crawl/";
    private static String WEBSITE_URL = "i315379.hera.fhict.nl";
    private static String JSON_REGEX = "(AndrÃ©)|([AndrÃ©])|\\W|(\\r)|(\\n)|\\s+";
    @Test
    public void givenURLDoesNotExist_whenSiteIsCrawled_then400IsReceived()
            throws IOException {

        // Given
        String url = RandomStringUtils.randomAlphabetic( 8 );
        HttpUriRequest request = new HttpGet( SpiderServiceURL + CRAWL_URL + url );

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        // Then
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    @Test
    public void
    givenRequestWithNoAcceptHeader_whenFullCrawlIsExecuted_thenDefaultResponseContentTypeIsJson()
            throws ClientProtocolException, IOException {

        // Given
        String jsonMimeType = "application/json";
        HttpUriRequest request = new HttpGet( SpiderServiceURL + CRAWL_URL + WEBSITE_URL );

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute( request );

        // Then
        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        assertEquals( jsonMimeType, mimeType );
    }

    @Test
    public void
    crawlRequestWithWebsite_CorrectWebsiteIsGiven_ShouldReturnJSONWithCrawledContent() throws IOException{

            // Given
            String expectedMessage =ConversionUtil.getConversionUtil().ConvertFromJSONFileToString("TotalCrawl.JSON");
            HttpUriRequest request = new HttpGet( SpiderServiceURL + CRAWL_URL + WEBSITE_URL );

            // When
            HttpResponse response = HttpClientBuilder.create().build().execute(request);

            // Then
            HttpEntity entity = response.getEntity();
            String actualMessage = ConversionUtil.getConversionUtil().ConvertToString(entity);
            String actualMessageCleaned = (setTimeToZero(actualMessage)).replaceAll(JSON_REGEX,"");
            String expectedMessageCleaned =  (setTimeToZero(expectedMessage)).replaceAll(JSON_REGEX,"");
            assertEquals(expectedMessageCleaned, actualMessageCleaned);

    }

    @Test
    public void
    givenRequestWithNoAcceptHeader_whenPartialCrawlIsExecuted_thenDefaultResponseContentTypeIsJson()
            throws ClientProtocolException, IOException {

        // Given
        String jsonMimeType = "application/json";
        HttpUriRequest request = new HttpGet( SpiderServiceURL + CRAWL_URL + WEBSITE_URL + "/e" );

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute( request );

        // Then
        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        assertEquals( jsonMimeType, mimeType );
    }

    @Test
    public void givenURLDoesNotExist_whenSiteIsPartlyCrawled_then400IsReceived()
            throws IOException {

        // Given
        String url = RandomStringUtils.randomAlphabetic( 8 );
        HttpUriRequest request = new HttpGet( SpiderServiceURL + CRAWL_URL + url +"$error" );

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        // Then
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    @Test
    public void
    crawlRequestWithWebsiteAndSearchTerm_CorrectWebsiteIsGiven_ShouldReturnJSONWithCrawledContent() throws IOException {

        // Given
        String expectedMessage =ConversionUtil.getConversionUtil().ConvertFromJSONFileToString("SingleCrawlForForrestGump.JSON");
        HttpUriRequest request = new HttpGet( SpiderServiceURL + CRAWL_URL + WEBSITE_URL+"/Forrest%20Gump" );

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        HttpEntity entity = response.getEntity();
        String actualMessage = ConversionUtil.getConversionUtil().ConvertToString(entity);
        String actualMessageCleaned = (setTimeToZero(actualMessage)).replaceAll(JSON_REGEX,"");
        String expectedMessageCleaned =  (setTimeToZero(expectedMessage)).replaceAll(JSON_REGEX,"");
        assertEquals(expectedMessageCleaned, actualMessageCleaned);

    }

    private String
    setTimeToZero(String jsonString){
        String[] strings = jsonString.split("\"time\":");
        return strings[0]+"\"time\":0}";
    }
}
