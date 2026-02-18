package tile;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public class MockDeck extends Deck{
    public MockDeck() throws IOException, ParseException {
        JSON_PATH = "src/test/java/tile/tilesMock.json";
        initializeDeck();
    }
}
