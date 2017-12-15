import java.util.List;

public class BaseWithLinks {

    private final Base retrievedObject;

    private final List<String> retrievedLinks;

    public BaseWithLinks(Base retrievedObject, List<String> retrievedLinks) {
        this.retrievedObject = retrievedObject;
        this.retrievedLinks = retrievedLinks;
    }

    public List<String> getRetrievedLinks() {
        return retrievedLinks;
    }

    public Base getRetrievedObject() {
        return retrievedObject;
    }
}
