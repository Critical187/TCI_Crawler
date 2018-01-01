package TCI_Crawler.service;

import TCI_Crawler.crawler.Spider;
import TCI_Crawler.dto.SearchResult;
import com.google.gson.GsonBuilder;
import com.owlike.genson.Genson;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("crawler")
@Singleton
public class SpiderService {

    private final Spider spider;
    private final Genson genson;
    private List<SearchResult> searchResults;
    public SpiderService() {
        this.spider = new Spider();
        this.genson = new Genson();
    }

    @GET
    @Path("crawl/{url}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crawlWebsite(@PathParam("url") String url) {
        try {
            String fullURL = "http://" + url + "/";
            SearchResult searchResult = this.spider.search(fullURL, null);
            //sorting the searchResult
            searchResult.Sort();
            String json = this.genson.serialize(searchResult);
            json = new GsonBuilder().setPrettyPrinting().create().toJson(searchResult);
            this.spider.clear();
            return Response.ok(json).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }finally {
            this.spider.clear();
        }
    }

    @GET
    @Path("crawl/{url}/{title}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crawlWebsiteForItem(@PathParam("url") String url, @PathParam("title") String titleName) {
        try {
            String fullURL = "http://" + url + "/";
            SearchResult searchResult = this.spider.search(fullURL, titleName.isEmpty() ? null : titleName);
            String json = this.genson.serialize(searchResult);

            this.spider.clear();
            return Response.ok(json).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }finally {
            this.spider.clear();
        }
    }
}
