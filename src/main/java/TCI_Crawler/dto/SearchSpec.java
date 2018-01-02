package TCI_Crawler.dto;

public class SearchSpec {
    private final int id;
    private final String time_elapsed;
    private final int pages_explored;
    private final int search_depth;

    public SearchSpec(int id, long time_elapsed, int pages_explored, int search_depth) {
        this.id = id;
        this.time_elapsed = timeConverter(time_elapsed);
        this.pages_explored = pages_explored;
        this.search_depth = search_depth;
    }
    private String timeConverter(long time_elapsed){

        return "";
    }
    public int getID(){return id;}
}
