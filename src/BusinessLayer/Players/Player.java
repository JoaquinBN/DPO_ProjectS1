package BusinessLayer.Players;

public class Player {
    private String name;
    private int investigationPoints;
    private boolean isDead;

    public Player(String name) {
        this.name = name;
        this.investigationPoints = 5;
        this.isDead = false;
    }

    public Player(String name, int investigationPoints) {
        this.name = name;
        this.investigationPoints = investigationPoints;
        this.isDead = false;
    }

    public String getName() {
        return name;
    }

    public int getInvestigationPoints() {
        return investigationPoints;
    }

    public void addInvestigationPoints(int investigationPoints) {
        this.investigationPoints += investigationPoints;
        if(this.investigationPoints <= 0) {
            this.investigationPoints = 0;
            isDead = true;
        }
    }

    public boolean getStatus(){
        return isDead;
    }

    public String[] getInfo(){
        String[] info = new String[2];
        info[0] = name;
        info[1] = Integer.toString(investigationPoints);
        return info;
    }
}
