package TCI_Crawler.exceptions;

import TCI_Crawler.searchObjects.SearchObjectBase;

/**
 * A class, that represents an exception thrown when a {@link SearchObjectBase} that was found
 * has invalid category.
 */
public class InvalidCategoryException extends Exception {

    /**
     * Initializes a new instance of the {@link InvalidCategoryException} class.
     *
     * @param message The exception message.
     */
    public InvalidCategoryException(String message) {
        super(message);
    }
}
