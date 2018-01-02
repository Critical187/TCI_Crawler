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
        long hoursInMiliSeconds = 60 * 60 * 1000;
        long minutesInMiliSeconds = 60 * 1000;
        long secondsInMiliSeconds = 1000;

        String convenientTime = "";
        long time_remaining = time_elapsed;


        //hours
        if (time_remaining / hoursInMiliSeconds > 0  )
        {
            convenientTime += time_remaining / hoursInMiliSeconds +"h ";
            time_remaining %=  hoursInMiliSeconds;
        }
        //minutes
        if(time_remaining / minutesInMiliSeconds > 0)
        {
            convenientTime += time_remaining / minutesInMiliSeconds +"m ";
            time_remaining %=  minutesInMiliSeconds;
        }
        //seconds
        if((time_remaining * 1.0)  / secondsInMiliSeconds > 0.01)
        {
            convenientTime += (1.0 *  time_remaining) / secondsInMiliSeconds +"s ";
            time_remaining %=  secondsInMiliSeconds;
        }
        return convenientTime.trim();
    }
    public int getID(){return id;}
}
