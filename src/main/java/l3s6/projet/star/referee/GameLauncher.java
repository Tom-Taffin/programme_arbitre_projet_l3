package l3s6.projet.star.referee;

import java.io.IOException;

import org.json.simple.parser.ParseException;

public class GameLauncher {
    public static void main(String[] args) throws IOException, ParseException {

        Game game = new Game("tile/tiles.json");

        game.initializePlayers();
        game.initializeFirstPlayer();

        while (!game.isFinished()) {
            game.offerTile();
            if (game.checkPlayerMove()){
                game.sendMove();
                game.playMove();
            }
            game.changeCurrentPlayer();
        }
        game.sendWinner();
    }
}
