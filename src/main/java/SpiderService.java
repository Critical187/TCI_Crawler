import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.net.URL;

@Path("Spider")
@Singleton
public class SpiderService {

    public SpiderService(){

    }

    @GET
    @Path("totalCrawl")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crawlWebsite(URL url){

        return Response.serverError().build();
    }
    @GET
    @Path("specificCrawl")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crawlWebsiteForItem(URL url, String name){

        return Response.serverError().build();
    }

}
