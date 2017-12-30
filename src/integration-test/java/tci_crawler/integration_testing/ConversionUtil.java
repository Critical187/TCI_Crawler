package tci_crawler.integration_testing;

import com.owlike.genson.Genson;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class ConversionUtil {
    private Genson genson;
    static private ConversionUtil instance;
    static public ConversionUtil getConversionUtil(){if(instance == null){instance = new ConversionUtil();} return instance;}
    private ConversionUtil(){
       genson = new  Genson();
    }
    public String ConvertToString(HttpEntity entity) throws IOException{
        return EntityUtils.toString(entity);
    }
    public String ConvertFromJSONFileToString(String urlFromFile){
        try {
            //Get file from resources folder
            InputStream stream = ConversionUtil.class.getResourceAsStream(urlFromFile);

        return  IOUtils.toString(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
