package l3s6.projet.star.referee.tile;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
    @Test
    public void testDataInitialisation() throws IOException, ParseException, EmptyDeckException {
        Deck deck = new Deck("src/test/java/l3s6/projet/star/referee/tile/tilesMock.json");
        String field = "f1-f1-f1-f1";
        String city = "c1-c1-c1-c1";
        int amountFields = 4;
        int amountCities = 3;

        int amount = amountCities + amountFields;

        while(!deck.isEmpty()){
            String nextTile = deck.drawTile();

            if(field.equals(nextTile)){
                amountFields --;
            } else if(city.equals(nextTile)) {
                amountCities --;
            }

            amount--;

        }
        assertEquals(0, amountFields);
        assertEquals(0, amountCities);
        assertEquals(0, amount);
    }
}
