package TCI_Crawler.exceptions;

/**
 * A class, that represents an exception thrown when no connection could be established to a given website.
 */
public class InvalidSiteException extends Exception {

    /**
     * Initializes a new instance of the {@link InvalidSiteException} class.
     *
     * @param message The exception message.
     */

    public InvalidSiteException(String message) {
        super(message);
    }
}
