package tci_crawler.integration_testing;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;

public class ConversionUtil {
    static private ConversionUtil instance;

    static public ConversionUtil getConversionUtil() {
        if (instance == null) {
            instance = new ConversionUtil();
        }
        return instance;
    }

    private ConversionUtil() {
    }

    public String ConvertToString(HttpEntity entity) throws IOException {
        return EntityUtils.toString(entity);
    }

    public String ConvertFromJSONFileToString(String urlFromFile) {
        try {
            //Get file from resources folder
            //ClassLoader classLoader = getClass().getClassLoader();
            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(urlFromFile);
            return IOUtils.toString(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}