public class SpiderMain
{
    /**
     * This is our test. It creates a spider (which creates spider legs) and crawls the web.
     * 
     * @param args
     *            - not used
     */
    public static void main(String[] args)
    {
        Spider spider = new Spider();
        spider.search("http://i315379.hera.fhict.nl","killme");
    }
}