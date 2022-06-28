package BusinessLayer.Players;

public class Player {
    private String name;
    private int investigationPoints;

    public Player(String name) {
        this.name = name;
        this.investigationPoints = 5;
    }

    public String getName() {
        return name;
    }

    public int getInvestigationPoints() {
        return investigationPoints;
    }

    public void addInvestigationPoints(int investigationPoints) {
        this.investigationPoints += investigationPoints;
    }
}
