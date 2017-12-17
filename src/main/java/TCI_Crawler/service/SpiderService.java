package TCI_Crawler.service;

import TCI_Crawler.crawler.Spider;
import TCI_Crawler.dto.SearchResult;
import TCI_Crawler.searchObjects.Book;
import com.owlike.genson.Genson;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("spiderservice")
@Singleton
public class SpiderService {

    private final Spider spider;

    public SpiderService() {
        this.spider = new Spider();
    }

    @GET
    @Path("crawl/{url}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crawlWebsite(@PathParam("url") String url) {
        try {
            String fullURL = "http://"+url+"/";
            SearchResult searchResult = this.spider.search(fullURL, null);
            this.spider.clear();
            Book book = new Book("h","Disney",3,"",new String[]{"Yui Kiahu"},"Camagochi","Kahhh");
            Genson genson = new Genson();
            String bookie = genson.serialize(searchResult);
            return Response.ok(bookie).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("crawl/{url}/{title}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crawlWebsiteForItem(@PathParam("url") String url, @PathParam("title") String titleName) {
        try {
            SearchResult searchResult = this.spider.search(url, titleName.isEmpty() ? null : titleName);
            this.spider.clear();
            return Response.ok(searchResult).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello() {
        return Response.ok("Wasup.").build();
    }


}
