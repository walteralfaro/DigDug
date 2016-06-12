package pruebaServer;

import java.util.ArrayList;

public class Game extends Thread {
	
	private static Game gameInstance = new Game();
	private GameThread gameThread;
	private String gameName;
	private Integer maxPlayers;
	private ArrayList<Player> players = new ArrayList<Player>();
	private Boolean isCreated = false;
	private Boolean isStarted = false;
	
	private Game() {
	}
	
	public static Game getGameInstance() {
		return gameInstance;
	}
	
	public void createGame(String gameName, Integer maxPlayers, ArrayList<Integer> questionsId) {
		this.gameName = gameName;
		this.maxPlayers = maxPlayers;
		this.isCreated = true;
		gameThread = new GameThread(maxPlayers, questionsId);
	}
	
	public void startGame() {
		isStarted = true;
		gameThread.setPlayers(players);
		gameThread.start();
	}
	
	public Boolean isCreated() {
		return isCreated;
	}
	
	public void addPlayer(Integer playerId, String playerName) {
		players.add(new Player(playerId, playerName));
		Logger.info(playerName + " se ha unido a la partida. (" + players.size() + "/" + maxPlayers + ")");
	}
	
	public String getGameName() {
		return gameName;
	}
	
	public Integer getMaxPlayers() {
		return maxPlayers;
	}
	
	public Boolean getWaitingAnswer() {
		return gameThread.getWaitingAnswer();
	}

	public void setAnswer(Integer playerId, String answer) {
		gameThread.setAnswer(playerId, answer);
	}

	public void removePlayer(Integer playerId) {
		if(isStarted) {
			gameThread.removePlayer(playerId);
		} else { 
			Player playerToRemove = null;
			for(Player eachPlayer: players)
				if(eachPlayer.getId().equals(playerId))
					playerToRemove = eachPlayer;
			
			if(playerToRemove != null) { 
				Logger.info(playerToRemove.getName() + " ha dejado la partida. (" + (players.size() - 1) + "/" + maxPlayers + ")");
				players.remove(playerToRemove);
			}
		}
	}
	
	public Boolean gameIsFull() {
		if(players.size() == maxPlayers)
			return true;
		return false;
	}
	
	public Boolean canStartGame() {
		if(players.size() >= 2)
			return true;
		return false;
	}

	public Boolean playerInGame(Integer playerId) {
		for(Player eachPlayer: players) 
			if(eachPlayer.getId().equals(playerId))
				return true;
		return false;
	}
	
	public Boolean isStarted() {
		return isStarted;
	}
	
	public Player getAdminPlayer() {
		return players.get(0);
	}
	
	public void resetGame() {
		players = new ArrayList<Player>();
		gameName = null;
		maxPlayers = null;
		isCreated = false;
		isStarted = false;
	}

	public void cancelGame() {
		UserConnection userConnectionInstance = UserConnection.getInstance();
		removePlayer(getAdminPlayer().getId());
		Logger.info("Envíado aviso de que la partida ha sido cancelada a todos los jugadores");
		for(Player eachPlayer: players) {
			try {
				Integer playerId = eachPlayer.getId();
				userConnectionInstance.blockSocket(playerId);
				userConnectionInstance.sendPackage(playerId, new PlayerDisconnectPackage(null, true));
				userConnectionInstance.releaseSocket(playerId);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		resetGame();
	}

	public boolean empty() {
		if(players.size() > 1)
			return false;
		return true;
	}

}