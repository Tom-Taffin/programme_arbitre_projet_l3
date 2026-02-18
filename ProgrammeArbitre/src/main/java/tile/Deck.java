package tile;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private final ArrayList<String> tiles;
    protected String jsonPath;

    public Deck(String path) throws IOException, ParseException {
        this.jsonPath = path;
        this.tiles = new ArrayList<>();
        initializeDeck();
        shuffle();
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
                tiles.add(representation);
            }
        }
    }

    /**
     * @return the representation of the tile on top of the tiles.
     */
    public String drawTile(){
        return this.tiles.remove(0);
    }

    /**
     * @return true if the tiles is not empty.
     */
    public boolean hasTiles(){
        return !this.tiles.isEmpty();
    }

    /**
     * Shuffles the tiles.
     */
    public void shuffle(){
        Collections.shuffle(this.tiles);
    }
}
