package player;

public class Player {
    private final String name;
    private int score;
    private int nbBlames;
    private int nbMeeples;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.nbBlames = 0;
        this.nbMeeples = 8;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
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
}
