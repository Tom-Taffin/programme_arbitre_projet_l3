package tile;

import l3s6.projet.star.game.edge.EdgeNoRoad;
import l3s6.projet.star.game.edge.Topology;
import l3s6.projet.star.game.edge.Zone;
import l3s6.projet.star.game.tile.Tile;
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

        int amount = 0;

        while(deck.hasTiles()){
            if(amount < 4){
                assertEquals(field, deck.getNextTile());
            } else {
                assertEquals(city, deck.getNextTile());
            }

            amount += 1;

        }

        assertEquals(7, amount);
    }
}
