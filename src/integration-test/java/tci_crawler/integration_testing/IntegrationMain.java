package tci_crawler.integration_testing;

// TODO remove this class after tests done.
public class IntegrationMain {
    public static void main(String[] args) {
        String expectedMessage = IntegrationTestsUtil.ConvertFromJSONFileToString("SearchDetailsForOne.JSON");



        expectedMessage = IntegrationTestsUtil.setElapsedTimeToZero(expectedMessage.replaceAll("(AndrÃ©)|(\\r)|(\\n)|\\s+",""));

        System.out.println(expectedMessage);

    }

}
