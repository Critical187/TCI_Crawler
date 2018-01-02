package TCI_Crawler.service;

import TCI_Crawler.crawler.Spider;
import TCI_Crawler.dto.SearchResult;
import TCI_Crawler.dto.SearchSpec;
import com.google.gson.GsonBuilder;
import org.apache.regexp.RE;

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
            //Make the url a proper URL
            String fullURL = "http://" + url + "/";
            //Fetch SearchResult
            SearchResult searchResult = this.spider.search(fullURL, null);
            //sorting the searchResult
            searchResult.Sort();
            //write Search Spec
            createSearchSpec(spider, searchResult);
            //Convert To JSON
            String json = new GsonBuilder().setPrettyPrinting().create().toJson(searchResult);

            return Response.ok(json).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }finally {
            //always clean spider
            this.spider.clear();
        }
    }

    @GET
    @Path("crawl/{url}/{title}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crawlWebsiteForItem(@PathParam("url") String url, @PathParam("title") String titleName) {
        try {
            //Make the url a proper URL
            String fullURL = "http://" + url + "/";
            //Fetch SearchResult
            SearchResult searchResult = this.spider.search(fullURL, titleName.isEmpty() ? null : titleName);
            //write Search Spec
            createSearchSpec(spider, searchResult);
            //Convert To JSON
            String json = new GsonBuilder().setPrettyPrinting().create().toJson(searchResult);

            return Response.ok(json).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }finally {
            //always clean spider
            this.spider.clear();
        }
    }

    @GET
    @Path("spec/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSpecForCrawlID(@PathParam("id") int id){
        SearchSpec searchSpec;
        searchSpec = searchSpecHashMap.get(id);
        if (searchSpec == null)
            return Response.status(Response.Status.NO_CONTENT).entity("No Specs found for ID: "+id).build();
        //Convert To JSON
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(searchSpec);

        return Response.status(Response.Status.OK).entity(json).build();
    }
    //private methods


    private void createSearchSpec(Spider spider, SearchResult searchResult){
        //Attach ID
        searchResult.setId(createID());
        //build a Search Spec
        SearchSpec searchSpec;
        searchSpec = writeSearchSpec(spider, searchResult);
        //save the Search Spec
        saveSearchSpec(searchSpec);
    }
    private SearchSpec writeSearchSpec(Spider spider, SearchResult searchResult){
        int id = searchResult.getId();
        long time_elapsed = searchResult.getTime();
        int pages_explored = spider.getNumberOfPagesExplored();
        int search_depth = -1;
        return new SearchSpec(id, time_elapsed,pages_explored,search_depth);
    }

    private void saveSearchSpec(SearchSpec searchSpec){
        searchSpecHashMap.put(searchSpec.getID(),searchSpec);
    }
    private int createID(){
        int id = -1;
        for (int i : searchSpecHashMap.keySet()){
            if (i > id)
                id = i;

        }
        if(id != -1)
            id++;
        else id = 1;
        return id;
    }
}
