package tile;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
    @Test
    public void testDataInitialisation() throws IOException, ParseException {
        Deck deck = new MockDeck();
        String field = "f1-f1-f1-f1";
        String city = "c1-c1-c1-c1";
        int amountFields = 4;
        int amountCities = 3;

        while(deck.hasTiles()){
            if(field.equals(deck.drawTile())){
                amountFields --;
            } else {
                amountCities --;
            }

        }
        assertEquals(0, amountFields);
        assertEquals(0, amountCities);
    }
}
