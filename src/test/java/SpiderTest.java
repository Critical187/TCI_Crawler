import org.junit.*;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;


public class SpiderTest {

    @Test
    public void testCrawlingForEntireWebsite(){

    }

    @Test
    public void testCrawlingForSpecificMovie(){
        Movie expectedRes = new Movie("The Lord of the Rings: The Fellowship of the Ring",
                "Drama", "Blu-ray", "2001", "Peter Jackson",
                new String[]{"J.R.R. Tolkien", "Fran Walsh", "Philippa Boyens", "Peter Jackson"},
                new String[]{"Ron Livingston", "Jennifer Aniston", "David Herman", "Ajay Naidu", "Diedrich Bader", "Stephen Root"});

        Movie actualRes = new Movie("The Lord of the Rings: The Fellowship of the Ring",
                "Drama", "Blu-ray", "2001", "Peter Jackson",
                new String[]{"J.R.R. Tolkien", "Fran Walsh", "Philippa Boyens", "Peter Jackson"},
                new String[]{"Ron Livingston", "Jennifer Aniston", "David Herman", "Ajay Naidu", "Diedrich Bader", "Stephen Root"});

        assertThat(actualRes,samePropertyValuesAs(expectedRes));
    }

    @Test
    public void testCrawlingForSpecificMusic(){

    }

    @Test
    public void testCrawlingForSpecificBook(){

    }

    @Test
    public void testDetailsAboutEntireWebsiteCrawl(){

    }

    @Test
    public void testDetailsAboutSpecificMovieCrawl(){

    }

    @Test
    public void testDetailsAboutSpecificMusicCrawl(){

    }

    @Test
    public void testDetailsAboutSpecificBookCrawl(){

    }
}
