import com.owlike.genson.Genson;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

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
}
