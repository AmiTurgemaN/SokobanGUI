package model.data.beans;

import java.io.Serializable;

public class Level implements Serializable {

	private static final long serialVersionUID = 1L;

	public Level() {
	}
	
	public Level(String levelName, String levelString) {
		this.levelName = levelName;
		this.levelString = levelString;
	}
	
	public String levelName;
	public String levelString;
	
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getLevelString() {
		return levelString;
	}
	public void setLevelString(String levelString) {
		this.levelString = levelString;
	}
	
	
}
