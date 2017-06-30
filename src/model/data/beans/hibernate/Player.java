package model.data.beans.hibernate;

public class Player {
	private String playerName;

	public Player() {
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Player(String playerName) {
		this.playerName = playerName;
	}
}
