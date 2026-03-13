package l3s6.projet.star.referee.player;

import l3s6.projet.star.game.meeple.Color;

public class Player {
    private final String id;
    private int score;
    private int nbBlames;
    private int nbMeeples;
    private final Color color;

    public Player(String id, Color color, int nbMeeples) {
        this.id = id;
        this.score = 0;
        this.nbBlames = 0;
        this.nbMeeples = nbMeeples;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getNumberOfBlames(){
        return nbBlames;
    }

    public int getScore() {
        return score;
    }

    public String getID() {
        return id;
    }

    public void addPoints(int points){
        this.score += points;
    }

    public void blame(){
        this.nbBlames += 1;
    }

    public void incrementMeepleCount(){
        this.nbMeeples += 1;
    }

    public void decrementMeepleCount(){
        this.nbMeeples -= 1;
        if (this.nbMeeples < 0) {
            this.nbMeeples = 0;
        }
    }

    public boolean hasMeeples(){
        return this.nbMeeples > 0;
    }
}
