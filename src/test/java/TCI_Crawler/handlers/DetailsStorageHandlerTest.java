package TCI_Crawler.handlers;

import TCI_Crawler.searchObjects.SearchDetails;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.*;

public class DetailsStorageHandlerTest {

    private DetailsStorageHandler detailsStorageHandler;

    @Before
    public void setUp() {
        this.detailsStorageHandler = new DetailsStorageHandler();
    }

    @Test
    public void testAddingAndRetrievingDetailFromHandler() {
        SearchDetails details = new SearchDetails(1, 100, 1, 1);
        this.detailsStorageHandler.addDetails(details);
        Optional<SearchDetails> retrievedDetails = this.detailsStorageHandler.getDetails(1);

        // Assert that there is indeed a value in the Optional container.
        assertTrue(retrievedDetails.isPresent());
        // Assert that the added details object is equal to the retrieved one.
        assertThat(details, samePropertyValuesAs(retrievedDetails.get()));
    }

    @Test
    public void testRetrievingDetailsFromHandlerForNonExistingId() {
        Optional<SearchDetails> retrievedDetails = this.detailsStorageHandler.getDetails(1);
        // Assert that there is no value in the Optional container.
        assertFalse(retrievedDetails.isPresent());
    }

    @Test
    public void testGetNextIdReturnsIncrementalValue() {
        for (int i = 0; i < 100; i++) {
            this.detailsStorageHandler.getNextId();
        }

        int retrievedId = this.detailsStorageHandler.getNextId();

        // Assert that after calling the method 100 times, the retrieved value is as expected.
        assertEquals(100, retrievedId);
    }
}
