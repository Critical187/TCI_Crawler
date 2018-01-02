package tci_crawler.integration_testing;

import TCI_Crawler.crawler.Spider;
import TCI_Crawler.dto.SearchResult;
import TCI_Crawler.exceptions.InvalidCategoryException;
import TCI_Crawler.exceptions.InvalidSiteException;
import TCI_Crawler.searchObjects.SearchObjectBase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SpiderIntegrationTest {

    private Spider spider;
    private String URL = "http://i315379.hera.fhict.nl/";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        this.spider = new Spider();
    }

    @Test
    public void testSearchForInvalidWebsite()
            throws InvalidCategoryException, InvalidSiteException {
        String websiteURL = "https://notarealwebsite.comm";
        thrown.expect(InvalidSiteException.class);
        thrown.expectMessage(String.format("Could not establish connection to URL '%s'.", websiteURL));

        this.spider.search(websiteURL, null);
    }

    @Test
    public void testSearchValidWebsiteWithNoSearchWord()
            throws InvalidCategoryException, InvalidSiteException {
        SearchResult retrievedObject = this.spider.search(this.URL, null);

        // Assert that the amount of books retrieved is as expected (4 for the specific site).
        assertThat(retrievedObject.getBooks(), hasSize(4));
        // Assert that the amount of music retrieved is as expected (4 for the specific site).
        assertThat(retrievedObject.getMusic(), hasSize(4));
        // Assert that the amount of movies retrieved is as expected (4 for the specific site).
        assertThat(retrievedObject.getMovies(), hasSize(4));
        // Assert that the search took at least 1 millisecond.
        assertTrue(retrievedObject.getTime() > 0);

        Comparator<String> stringComparator = Comparator.naturalOrder();

        // Retrieve the book names and make a copy. Then sort the copy and
        // verify that the original list is sorted as well by comparing the two lists.
        List<String> originalBookNames = retrievedObject.getBooks()
                .stream()
                .map(SearchObjectBase::getName)
                .collect(Collectors.toList());
        List<String> bookNamesCopy = new ArrayList(originalBookNames);
        bookNamesCopy.sort(stringComparator);

        // Retrieve the music names and make a copy. Then sort the copy and
        // verify that the original list is sorted as well by comparing the two lists.
        List<String> originalMusicNames = retrievedObject.getMusic()
                .stream()
                .map(SearchObjectBase::getName)
                .collect(Collectors.toList());
        List<String> musicNamesCopy = new ArrayList(originalMusicNames);
        musicNamesCopy.sort(stringComparator);

        // Retrieve the movie names and make a copy. Then sort the copy and
        // verify that the original list is sorted as well by comparing the two lists.
        List<String> originalMovieNames = retrievedObject.getMovies()
                .stream()
                .map(SearchObjectBase::getName)
                .collect(Collectors.toList());
        List<String> movieNamesCopy = new ArrayList(originalMovieNames);
        movieNamesCopy.sort(stringComparator);

        // Compare both the original and copy list of names to verify that the original one
        // is sorted as expected.
        assertArrayEquals(bookNamesCopy.toArray(), originalBookNames.toArray());
        assertArrayEquals(musicNamesCopy.toArray(), originalMusicNames.toArray());
        assertArrayEquals(movieNamesCopy.toArray(), originalMovieNames.toArray());
    }

    @Test
    public void testSearchValidWebsiteWithSearchWord()
            throws InvalidCategoryException, InvalidSiteException {
        String searchWord = "Office Space";
        SearchResult retrievedObject = this.spider.search(this.URL, searchWord);

        // Assert that there are no books since the search word refers to a movie.
        assertThat(retrievedObject.getBooks(), hasSize(0));
        // Assert that there is no music since the search word refers to a movie.
        assertThat(retrievedObject.getMusic(), hasSize(0));
        // Assert there is exactly one movie.
        assertThat(retrievedObject.getMovies(), hasSize(1));
        // Assert that the search took at least 1 millisecond.
        assertTrue(retrievedObject.getTime() > 0);

        // Assert that the proper movie is returned by checking the title.
        assertEquals(searchWord, retrievedObject.getMovies().get(0).getName());
    }
}
