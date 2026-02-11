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
    private final ArrayList<Tile> deck;
    protected String JSON_PATH = "src/main/java/tile/tiles.json";

    public Deck() {
        this.deck = new ArrayList<>();
        //initializeDeck();
    }

    /**
     * Initializes deck with the given path to the JSON file.
     */
    protected void initializeDeck() {
        JSONParser parser = new JSONParser();

        JSONArray array = null;
        try {
            array = (JSONArray) parser.parse(new FileReader(JSON_PATH));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        for (Object o : array) {
            JSONObject tileJSON = (JSONObject) o;

            String representation = (String) tileJSON.get("representation");
            long amount = (Long) tileJSON.get("number");

            Tile tile = null;
            try {
                tile = new TileBuilder().buildTile(representation);
            } catch (WrongTileSyntaxException e) {
                throw new RuntimeException(e);
            }

            for(int i = 0; i < amount; i++){
                deck.add(tile);
            }
        }
    }
}
