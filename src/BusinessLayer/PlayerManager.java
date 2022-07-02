package BusinessLayer;

import BusinessLayer.Entities.Player;
import PersistenceLayer.ExecutionFileManager;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * PlayerManager is a class in charge of the logic behind the players information.
 */
public class PlayerManager {
    private final ArrayList<Player> players;
    private final ExecutionFileManager executionFileManager;
    private String errorMessage;

    /**
     * Constructor for PlayerManager
     * @param executionFileManager the execution file manager
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
     * Get the players list
     * @return the players list
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Get the number of players in the system
     * @return the amount of players in the system
     */
    public int getTotalPlayers() {
        return players.size();
    }

    /**
     * Check if any player is alive
     * @return false if any player is alive, true otherwise
     */
    public boolean allPlayersareDead(){
        for(Player player: players){
            if(!player.isDead()){
                return false;
            }
        }
        return true;
    }

    /**
     * Load data from the player system
     */
    public boolean loadPlayersData(){
        List<String[]> playersData;
        try {
            playersData = executionFileManager.readPlayersData().subList(1, executionFileManager.readPlayersData().size());
            for (String[] playerData : playersData) {
                retrievePlayer(playerData[0], Integer.parseInt(playerData[1]));
            }
            return true;
        } catch (IOException | CsvException e) {
           errorMessage = "Error loading data of players";
           return false;
        }
    }

    /**
     * Save data to the player system
     */
    public boolean saveData(){
        players.removeIf(Player::isDead);
        List<String[]> playersData = new ArrayList<>();
        for(Player player: players){
            playersData.add(player.getInfo());
        }
        try {
            executionFileManager.writePlayersData(playersData);
            return true;
        } catch (IOException e) {
            errorMessage = "Error saving players' data inside the execution file";
            return false;
        }

    }

    /**
     * Get the error message
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
