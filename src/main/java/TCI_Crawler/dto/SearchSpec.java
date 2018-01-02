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

    private String timeConverter(long time_elapsed) {
        long hoursInMilliSeconds = 60 * 60 * 1000;
        long minutesInMilliSeconds = 60 * 1000;
        long secondsInMilliSeconds = 1000;

        String convenientTime = "";
        long time_remaining = time_elapsed;

        //hours
        if (time_remaining / hoursInMilliSeconds > 0) {
            convenientTime += time_remaining / hoursInMilliSeconds + "h ";
            time_remaining %= hoursInMilliSeconds;
        }

        //minutes
        if (time_remaining / minutesInMilliSeconds > 0) {
            convenientTime += time_remaining / minutesInMilliSeconds + "m ";
            time_remaining %= minutesInMilliSeconds;
        }

        //seconds
        if ((time_remaining * 1.0) / secondsInMilliSeconds > 0.01) {
            convenientTime += (1.0 * time_remaining) / secondsInMilliSeconds + "s ";
            time_remaining %= secondsInMilliSeconds;
        }

        return convenientTime.trim();
    }

    public int getID() {
        return id;
    }
}
