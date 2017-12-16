package service;

import crawler.Spider;
import dto.SearchResult;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("crawlService")
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
            SearchResult searchResult = this.spider.search(url, null);
            this.spider.clear();
            return Response.ok(searchResult).build();
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
    public String sayHello() {
        return "Wasup.";
    }
}
