package db;

import javax.persistence.*;

@Entity(name="Players")
public class Player {
	@Id
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
