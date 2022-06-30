package BusinessLayer.Players;

import java.util.ArrayList;

public class PlayerManager {
    private ArrayList<Player> players;

    public PlayerManager() {
        players = new ArrayList<>();
    }

    public void addPlayer(String playerName) {
        players.add(new Player(playerName));
    }

    public Player getPlayerByIndex(int index) {
        return players.get(index);
    }

    public Player getPlayerByName(String playerName) {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                return player;
            }
        }
        return null;
    }

    public int getTotalPlayers() {
        return players.size();
    }

    public boolean playerIsDead(int index){
        return players.get(index).getStatus();
    }

    public boolean allPlayersareDead(){
        for(Player player: players){
            if(!player.getStatus()){
                return false;
            }
        }
        return true;
    }


}
