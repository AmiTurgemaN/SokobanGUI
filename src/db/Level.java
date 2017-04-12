package db;

import javax.persistence.*;

@Entity(name="Levels")
public class Level {
	@Id
	private String levelName;

	public Level() {
	}

	public Level(String levelName) {
		this.levelName = levelName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	
}
