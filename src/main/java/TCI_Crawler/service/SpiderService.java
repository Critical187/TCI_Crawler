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

/**
 * The service, exposing the crawler API functionality.
 */
@Path("crawler")
@Singleton
public class SpiderService {

    /**
     * The spider to be used in this service.
     */
    private final TreeSpider treeSpider;

    /**
     * Initializes a new instance of the {@link SpiderService} class.
     */
    public SpiderService() {
        treeSpider = new TreeSpider(new DetailsStorageHandler());
    }

    /**
     * Recursively crawls a given url and returns a JSON response with contents of the crawl.
     *
     * @param url The url to crawl.
     * @return JSON response with the contents of the crawl.
     */
    @GET
    @Path("crawl/{url}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crawlWebsite(@PathParam("url") String url) {
        try {
            // Make the url a proper URL since passing 'https://' in the API would be considered a new path
            // due to the usage of '//'.
            String fullURL = "http://" + url + "/";
            SearchResult searchResult = this.treeSpider.search(fullURL, null);
            String json = new GsonBuilder().setPrettyPrinting().create().toJson(searchResult);

            return Response.ok(json).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } finally {
            this.treeSpider.clear();
        }
    }

    /**
     * Recursively crawls a given url for the object with the specified title and returns a JSON response with contents
     * of the crawl.
     *
     * @param url       The url to crawl.
     * @param titleName The title to search for.
     * @return JSON response with the contents of the crawl.
     */
    @GET
    @Path("crawl/{url}/{title}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crawlWebsiteForItem(@PathParam("url") String url, @PathParam("title") String titleName) {
        try {
            // Make the url a proper URL since passing 'http://' in the API would be considered a new path
            // due to the usage of '//'.
            String fullURL = "http://" + url + "/";
            SearchResult searchResult = this.treeSpider.search(fullURL, titleName.isEmpty() ? null : titleName);
            String json = new GsonBuilder().setPrettyPrinting().create().toJson(searchResult);

            return Response.ok(json).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } finally {
            this.treeSpider.clear();
        }
    }

    /**
     * Retrieves the details about a specific crawl as a JSON response.
     *
     * @param id The identifier of the crawl to retrieve details for.
     * @return JSON response with the contents of the details.
     */
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

        String json = new GsonBuilder().setPrettyPrinting().create().toJson(searchDetails.get());

        return Response.status(Response.Status.OK).entity(json).build();
    }
}
