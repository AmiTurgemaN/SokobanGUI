package db;

import java.text.DecimalFormat;

import javax.persistence.*;

@Entity(name="Games")
public class Game {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int gameID;
	
	private String playerName;
	private String levelName;
	private int moves;
	private int time;
	
	public Game() {
	}
	
	public Game(String playerName, String levelName, int moves, int time) {
		this.playerName = playerName;
		this.levelName = levelName;
		this.moves = moves;
		this.time = time;
	}
	
	public Game(Player player,Level level,int moves,int time)
	{
		this.playerName=player.getPlayerName();
		this.levelName=level.getLevelName();
		this.moves = moves;
		this.time = time;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}
	
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getTimeAsString() {
		String time;
		DecimalFormat df = new DecimalFormat("00");
		int seconds = this.getTime()%60;
		int minutes = this.getTime()/60%60;
		int hours = this.getTime()/60/60;
		time = df.format(hours)+":"+df.format(minutes)+":"+df.format(seconds);
		return time;
	}
	
}
