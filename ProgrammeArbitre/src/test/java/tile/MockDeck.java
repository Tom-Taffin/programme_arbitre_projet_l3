package tile;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public class MockDeck extends Deck{
    public MockDeck() throws IOException, ParseException {
        super( "src/test/java/tile/tilesMock.json");
    }
}
