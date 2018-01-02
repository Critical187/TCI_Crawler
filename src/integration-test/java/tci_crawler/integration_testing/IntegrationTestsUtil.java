package tci_crawler.integration_testing;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;

public class IntegrationTestsUtil {

    public static String ConvertToString(HttpEntity entity) throws IOException {
        return EntityUtils.toString(entity);
    }

    public static String ConvertFromJSONFileToString(String urlFromFile) {
        try {
            //Get file from resources folder
            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(urlFromFile);
            return IOUtils.toString(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String setTimeToZero(String jsonString) {
        String[] strings = jsonString.split("\"time\":");
        return strings[0] + "\"time\":0}";
    }
}
