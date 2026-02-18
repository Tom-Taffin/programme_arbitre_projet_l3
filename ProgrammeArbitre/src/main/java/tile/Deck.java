package tile;

import l3s6.projet.star.game.tile.Tile;

import l3s6.projet.star.game.tile.TileBuilder;
import l3s6.projet.star.game.tile.WrongTileSyntaxException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import java.util.ArrayList;

public class Deck {
    private final ArrayList<String> deck;
    protected String jsonPath;

    public Deck(String path) throws IOException, ParseException {
        this.jsonPath = path;
        this.deck = new ArrayList<>();
        initializeDeck();
    }

    /**
     * Initializes deck with the given path to the JSON file.
     */
    protected void initializeDeck() throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        JSONArray array = (JSONArray) parser.parse(new FileReader(jsonPath));

        for (Object o : array) {
            JSONObject tileJSON = (JSONObject) o;

            String representation = (String) tileJSON.get("representation");
            long amount = (Long) tileJSON.get("number");

            for(int i = 0; i < amount; i++){
                deck.add(representation);
            }
        }
    }

    /**
     * Returns the tile on top of the deck.
     * @return the tile on top of the deck.
     */
    public String getNextTile(){
        return this.deck.remove(0);
    }

    /**
     * Returns true if there are tiles in the deck, i.e. if it is not empty.
     * @return true if the deck is not empty.
     */
    public boolean hasTiles(){
        return !this.deck.isEmpty();
    }
}
