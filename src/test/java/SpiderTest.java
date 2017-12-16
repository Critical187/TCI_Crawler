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
                "Drama", 2001, "Blu-ray","Peter Jackson",
                new String[]{"J.R.R. Tolkien", "Fran Walsh", "Philippa Boyens", "Peter Jackson"},
                new String[]{"Ron Livingston", "Jennifer Aniston", "David Herman", "Ajay Naidu", "Diedrich Bader", "Stephen Root"});

        Movie actualRes = new Movie("The Lord of the Rings: The Fellowship of the Ring",
                "Drama", 2001, "Blu-ray","Peter Jackson",
                new String[]{"J.R.R. Tolkien", "Fran Walsh", "Philippa Boyens", "Peter Jackson"},
                new String[]{"Ron Livingston", "Jennifer Aniston", "David Herman", "Ajay Naidu", "Diedrich Bader", "Stephen Root"});

        assertThat(actualRes,samePropertyValuesAs(expectedRes));
    }

    @Test
    public void testCrawlingForSpecificMusic(){
        Music expectedRes = new Music("The Very Thought of You", "Jaz", 2003,
                "MP3", "Nat King Cole\n");

        Music actualRes = new Music("The Very Thought of You", "Jaz", 2003,
                "MP3", "Nat King Cole\n");

        assertThat(actualRes,samePropertyValuesAs(expectedRes));
    }

    @Test
    public void testCrawlingForSpecificBook(){
        Book expectedRes = new Book("A Design Patterns: Elements of Reusable Object-Oriented Software",
                "Tech", 1994, "Paperback",
                new String[]{"Erich Gamma", "Richard Helm", "Ralph Johnson", "John Vlissides"},
                "978-0201633610", "Prentice Hall");

        Book actualRes = new Book("A Design Patterns: Elements of Reusable Object-Oriented Software",
                "Tech", 1994, "Paperback",
                new String[]{"Erich Gamma", "Richard Helm", "Ralph Johnson", "John Vlissides"},
                "978-0201633610", "Prentice Hall");

        assertThat(actualRes,samePropertyValuesAs(expectedRes));
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
