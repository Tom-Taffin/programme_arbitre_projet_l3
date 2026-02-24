package player;

public class Player {
    private final String name;
    private int score;
    private int nbBlames;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.nbBlames = 0;
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
}
