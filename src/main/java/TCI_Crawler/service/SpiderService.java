package TCI_Crawler.service;

import TCI_Crawler.crawler.TreeSpider;
import TCI_Crawler.dto.SearchResult;
import TCI_Crawler.dto.CrawlDetails;
import TCI_Crawler.handlers.DetailsStorageHandler;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("crawler")
@Singleton
public class SpiderService {

    private final TreeSpider treeSpider;

    public SpiderService() {
        treeSpider = new TreeSpider(new DetailsStorageHandler());
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
            SearchResult searchResult = this.treeSpider.search(fullURL, null);
            //Convert To JSON
            String json = new GsonBuilder().setPrettyPrinting().create().toJson(searchResult);

            return Response.ok(json).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } finally {
            this.treeSpider.clear();
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
            SearchResult searchResult = this.treeSpider.search(fullURL, titleName.isEmpty() ? null : titleName);
            //Convert To JSON
            String json = new GsonBuilder().setPrettyPrinting().create().toJson(searchResult);

            return Response.ok(json).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } finally {
            this.treeSpider.clear();
        }
    }

    @GET
    @Path("details/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDetailsForCrawlID(@PathParam("id") int id) {
        Optional<CrawlDetails> searchDetails = this.treeSpider.getSearchDetails(id);
        if (!searchDetails.isPresent()) {
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity(String.format("No crawl details found for ID '%d'.", id))
                    .build();
        }
        //Convert To JSON
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(searchDetails.get());

        return Response.status(Response.Status.OK).entity(json).build();
    }
}
