package TCI_Crawler.service;

import TCI_Crawler.crawler.Spider;
import TCI_Crawler.dto.SearchResult;
import TCI_Crawler.dto.SearchSpec;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;

@Path("crawler")
@Singleton
public class SpiderService {

    private final Spider spider;
    private HashMap<Integer, SearchSpec> searchSpecHashMap;
    public SpiderService() {
        this.spider = new Spider();
        this.searchSpecHashMap = new HashMap<>();
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
            //TODO get ID
            //TODO build SearchSpec
            //TODO put it in the map
            String json = new GsonBuilder().setPrettyPrinting().create().toJson(searchResult);

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
            String json = new GsonBuilder().setPrettyPrinting().create().toJson(searchResult);

            this.spider.clear();
            return Response.ok(json).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }finally {
            this.spider.clear();
        }
    }

    private SearchSpec writeSearchSpec(Spider spider, SearchResult searchResult){
        int id = searchResult.getId();
        long time_elapsed = searchResult.getTime();
        int pages_explored = spider.getNumberOfPagesExplored();
        int search_depth = -1;
        return new SearchSpec(id, time_elapsed,pages_explored,search_depth);
    }
}
