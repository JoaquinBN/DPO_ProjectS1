package BusinessLayer.Players;

import PersistenceLayer.ExecutionFileManager;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    private final ArrayList<Player> players;
    private final ExecutionFileManager executionFileManager;

    public PlayerManager(ExecutionFileManager executionFileManager) {
        players = new ArrayList<>();
        this.executionFileManager = executionFileManager;
    }

    public void addPlayer(String playerName) {
        players.add(new Player(playerName));
    }

    public void retrievePlayer(String playerName, int investigationPoints) {
        players.add(new Player(playerName, investigationPoints));
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


    public void loadPlayersData() throws IOException, CsvException {
        List<String[]> playersData = executionFileManager.readPlayersData().subList(1, executionFileManager.readPlayersData().size());
        for (String[] playerData : playersData) {
            retrievePlayer(playerData[0], Integer.parseInt(playerData[1]));
        }
    }

    public void saveData() throws IOException {
        players.removeIf(Player::getStatus);
        List<String[]> playersData = new ArrayList<>();
        for(Player player: players){
            playersData.add(player.getInfo());
        }
        executionFileManager.writePlayersData(playersData);

    }
}
