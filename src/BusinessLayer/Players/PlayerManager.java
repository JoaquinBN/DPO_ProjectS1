package BusinessLayer.Players;

import PersistenceLayer.ExecutionFileManager;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    private final ArrayList<Player> players;
    private final ExecutionFileManager executionFileManager;

    /**
     * Constructor for PlayerManager
     * @param executionFileManager the execution file man1ager
     */
    public PlayerManager(ExecutionFileManager executionFileManager) {
        players = new ArrayList<>();
        this.executionFileManager = executionFileManager;
    }

    /**
     * Add a new player to the list of players
     * @param playerName the name of the player
     */
    public void addPlayer(String playerName) {
        players.add(new Player(playerName));
    }

    /**
     * Retrieve player from the list of players
     * @param playerName the name of the player
     * @param investigationPoints the amount of investigation points the player has
     */
    public void retrievePlayer(String playerName, int investigationPoints) {
        players.add(new Player(playerName, investigationPoints));
    }

    /**
     * Get index of player in the list of players
     * @param index the index of the player
     * @return the player at the given index
     */
    public Player getPlayerByIndex(int index) {
        return players.get(index);
    }

    /**
     * Get player by the name of the player.
     * @param playerName the name of the player
     * @return the player with the given name
     */
    public Player getPlayerByName(String playerName) {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Get the number of players in the system
     * @return the amount of players in the system
     */
    public int getTotalPlayers() {
        return players.size();
    }

    /**
     * Check if player is dead
     * @param index the index of the player
     * @return true if the player is dead, false otherwise
     */
    public boolean playerIsDead(int index){
        return players.get(index).getStatus();
    }

    /**
     * Check if any player is alive
     * @return false if any player is alive, true otherwise
     */
    public boolean allPlayersareDead(){
        for(Player player: players){
            if(!player.getStatus()){
                return false;
            }
        }
        return true;
    }

    /**
     * Load data from the player system
     * @throws IOException if there is an error reading the file
     * @throws CsvException if there is an error reading the file
     */
    public void loadPlayersData() throws IOException, CsvException {
        List<String[]> playersData = executionFileManager.readPlayersData().subList(1, executionFileManager.readPlayersData().size());
        for (String[] playerData : playersData) {
            retrievePlayer(playerData[0], Integer.parseInt(playerData[1]));
        }
    }

    /**
     * Save data to the player system
     * @throws IOException if there is an error writing to the file
     */
    public void saveData() throws IOException {
        players.removeIf(Player::getStatus);
        List<String[]> playersData = new ArrayList<>();
        for(Player player: players){
            playersData.add(player.getInfo());
        }
        executionFileManager.writePlayersData(playersData);

    }
}
